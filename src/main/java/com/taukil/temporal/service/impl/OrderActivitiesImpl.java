package com.taukil.temporal.service.impl;

import com.taukil.temporal.service.OrderActivities;
import org.springframework.stereotype.Service;

@Service
public class OrderActivitiesImpl implements OrderActivities {
    @Override
    public boolean reserveInventory(String orderId) {
        System.out.println("Reserving inventory for order: " + orderId);
        return true;
    }

    @Override
    public boolean processPayment(String orderId) {
        System.out.println("Processing payment for order: " + orderId);
        return true;
    }

    @Override
    public boolean prepareShipping(String orderId) {
        System.out.println("Preparing shipping for order: " + orderId);
        return true;
    }

    @Override
    public void releaseInventory(String orderId) {
        System.out.println("Releasing inventory for order: " + orderId);
    }

    @Override
    public void issueRefund(String orderId) {
        System.out.println("Issuing refund for order: " + orderId);
    }

    @Override
    public void sendNotification(String orderId, String status) {
        System.out.println("Sending notification: " + status + " for order: " + orderId);
    }

}

