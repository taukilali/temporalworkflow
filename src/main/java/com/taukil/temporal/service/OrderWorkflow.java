package com.taukil.temporal.service;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {
    @WorkflowMethod
    String processOrder(String orderId);

    // Define the status query method
    @QueryMethod
    String getStatus();

}

