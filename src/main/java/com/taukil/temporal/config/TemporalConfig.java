package com.taukil.temporal.config;

import com.taukil.temporal.service.impl.OrderActivitiesImpl;
import com.taukil.temporal.service.impl.OrderWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class TemporalConfig {

    @Bean
    public WorkflowServiceStubs workflowServiceStubs() {
        return WorkflowServiceStubs.newInstance();
    }

    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs service) {
        return WorkflowClient.newInstance(service);
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient client) {
        return WorkerFactory.newInstance(client);
    }

    @Bean
    public Worker orderWorker(WorkerFactory factory) {
        Worker worker = factory.newWorker("ORDER_TASK_QUEUE");
        worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
        worker.registerActivitiesImplementations(new OrderActivitiesImpl());
        return worker;
    }

}
