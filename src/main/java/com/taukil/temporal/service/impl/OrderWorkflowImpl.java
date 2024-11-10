package com.taukil.temporal.service.impl;

import com.taukil.temporal.service.OrderActivities;
import com.taukil.temporal.service.OrderWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class OrderWorkflowImpl implements OrderWorkflow {

    private String status = "Started"; // Status variable for tracking

    private final OrderActivities activities = Workflow.newActivityStub(
            OrderActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .build()
    );

    // Default constructor (no arguments)
    public OrderWorkflowImpl() {
        // No-arg constructor required for Temporal to instantiate the workflow
    }
    @Override
    public String processOrder(String orderId) {
        boolean inventoryReserved = false;
        boolean paymentProcessed = false;

        try {
            status = "Order Created";
            activities.sendNotification(orderId, status);

            // Step 1: Reserve Inventory
            inventoryReserved = activities.reserveInventory(orderId);
            if (!inventoryReserved) {
                status = "Inventory Reservation Failed";
                throw new RuntimeException("Failed to reserve inventory");
            }
            status = "Inventory Reserved";
            activities.sendNotification(orderId, status);

            // Step 2: Process Payment
            paymentProcessed = activities.processPayment(orderId);
            if (!paymentProcessed) {
                status = "Payment Failed";
                throw new RuntimeException("Payment processing failed");
            }
            status = "Payment Processed";
            activities.sendNotification(orderId, status);

            // Step 3: Prepare Shipping
            if (!activities.prepareShipping(orderId)) {
                status = "Shipping Preparation Failed";
                throw new RuntimeException("Shipping preparation failed");
            }
            status = "Shipping Prepared";
            activities.sendNotification(orderId, status);

            // Step 4: Complete Order
            status = "Order Completed";
            activities.sendNotification(orderId, status);
            return "Order processing completed successfully for order: " + orderId;
        } catch (Exception e) {
            if (paymentProcessed) {
                activities.issueRefund(orderId);
                activities.sendNotification(orderId, "Refund issued");
            }
            if (inventoryReserved) {
                activities.releaseInventory(orderId);
                activities.sendNotification(orderId, "Inventory released");
            }
            status = "Order Failed: " + e.getMessage();
            activities.sendNotification(orderId, status);
            throw e;
        }
    }

    @Override
    public String getStatus() {
        return status;
    }
}

