# Springboot Temporal Sample project for ecommerce Order Processing

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org)

# Temporal

**Temporal** is an open-source platform designed for building and running highly reliable, distributed, and scalable applications. It provides a framework for managing the execution of workflows and long-running processes in a fault-tolerant and consistent manner, which is especially useful for systems requiring high reliability and state consistency.


### Step 1: Download and Start Temporal Server Locally

Execute the following commands to start a pre-built image along with all the dependencies.

```bash
curl -L https://github.com/temporalio/docker-compose/archive/refs/heads/master.zip -o temporal-docker.zip
unzip temporal-docker.zip
cd docker-compose-master
```

Start Temporal by running:

```bash
docker-compose up
```
### Step 2: Set Up a Java Application to Connect to Temporal

```bash
mvn clean install
mvn spring-boot:run
```

### Open Temporal at

```bash
http://localhost:8233/
```
### Workflow dashboard screenshot.

![alt text](https://github.com/taukilali/temporalworkflow/blob/main/assets/0.png)


![alt text](https://github.com/taukilali/temporalworkflow/blob/main/assets/1.png)


![alt text](https://github.com/taukilali/temporalworkflow/blob/main/assets/3.png)



![alt text](https://github.com/taukilali/temporalworkflow/blob/main/assets/2.png)




