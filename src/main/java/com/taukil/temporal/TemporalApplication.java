package com.taukil.temporal;

import com.taukil.temporal.service.impl.OrderActivitiesImpl;
import com.taukil.temporal.service.impl.OrderWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemporalApplication {

	public static void main(String[] args) {

		// Initialize Temporal client
		WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
		WorkflowClient client = WorkflowClient.newInstance(service);

		// Create a worker factory
		WorkerFactory factory = WorkerFactory.newInstance(client);

		// Create a worker that polls the ORDER_TASK_QUEUE for tasks
		Worker worker = factory.newWorker("ORDER_TASK_QUEUE");

		// Register workflows and activities to the worker
		worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
		worker.registerActivitiesImplementations(new OrderActivitiesImpl());

		// Start the worker to begin polling the task queue
		factory.start();

		System.out.println("Worker is now polling the ORDER_TASK_QUEUE...");
		SpringApplication.run(TemporalApplication.class, args);
	}

}
