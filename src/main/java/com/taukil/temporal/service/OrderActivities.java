package com.taukil.temporal.service;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivities {
    @ActivityMethod
    boolean reserveInventory(String orderId);

    @ActivityMethod
    boolean processPayment(String orderId);

    @ActivityMethod
    boolean prepareShipping(String orderId);

    @ActivityMethod
    void releaseInventory(String orderId); // Releases inventory if order fails/cancels

    @ActivityMethod
    void issueRefund(String orderId); // Issues a refund if payment was processed but order is incomplete

    @ActivityMethod
    void sendNotification(String orderId, String status);
}
