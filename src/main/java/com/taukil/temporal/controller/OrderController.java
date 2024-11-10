package com.taukil.temporal.controller;

import com.taukil.temporal.service.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private WorkflowClient workflowClient;

    @PostMapping("/start/{orderId}")
    public String startOrderWorkflow(@PathVariable String orderId) {
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("ORDER_TASK_QUEUE")
                .setWorkflowId("ORDER-" + orderId)
                .build();

        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, options);
        WorkflowClient.start(workflow::processOrder, orderId);

        return "Order workflow started for order: " + orderId;
    }

    @GetMapping("/status/{orderId}")
    public String getOrderStatus(@PathVariable String orderId) {
        try {
            WorkflowStub workflowStub = workflowClient.newUntypedWorkflowStub("ORDER-" + orderId);
            return workflowStub.query("getStatus", String.class);
        } catch (Exception e) {
            return "Error retrieving workflow status: " + e.getMessage();
        }
    }

    @DeleteMapping("/cancel/{orderId}")
    public String cancelOrderWorkflow(@PathVariable String orderId) {
        WorkflowStub workflowStub = workflowClient.newUntypedWorkflowStub("ORDER-" + orderId);
        try {
            workflowStub.cancel();
            return "Workflow canceled for order: " + orderId;
        } catch (Exception e) {
            return "Error canceling workflow: " + e.getMessage();
        }
    }
}

