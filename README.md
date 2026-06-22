# Spring Boot CI/CD to AWS ECR + EC2

This repository contains a Spring Boot web application for the Coursera guided project **Build CI/CD Pipeline with Docker, from Code to Deployment** by Adem Hassine.

The project demonstrates a complete DevOps flow:

- Build a Spring Boot application.
- Containerize it with a multi-stage Dockerfile.
- Build and push the Docker image with GitHub Actions.
- Store the image in AWS ECR.
- Deploy the latest image to an EC2 instance over SSH.
- Run the application with Docker on EC2.

## Tech Stack

- Java 17
- Spring Boot 3
- Maven
- Docker
- Docker Compose
- GitHub Actions
- AWS ECR
- AWS EC2

## Project Structure

```text
.
|-- .github/workflows/pipeline.yml
|-- src/main/java/com/example/springbootapp/
|-- src/main/resources/application.properties
|-- src/test/java/com/example/springbootapp/
|-- Dockerfile
|-- docker-compose.yml
|-- pom.xml
`-- README.md
```

## Application Endpoints

Home page:

```text
http://localhost:8080/
```

Health endpoint:

```text
http://localhost:8080/api/health
```

## Run Locally

Build the application:

```bash
mvn clean package
```

Run the application:

```bash
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

## Run with Docker

Build the Docker image:

```bash
docker build -t spring-boot-app .
```

Run the container:

```bash
docker run -d --name app -p 8080:8080 spring-boot-app
```

Open:

```text
http://localhost:8080
```

Spring Boot listens on port `8080` inside the container, so the correct mapping is:

```bash
-p 8080:8080
```

## Run with Docker Compose

```bash
docker compose up -d --build
```

Stop the app:

```bash
docker compose down
```

## CI/CD Pipeline

The GitHub Actions workflow is located at:

```text
.github/workflows/pipeline.yml
```

The pipeline runs on every push to the `main` branch and can also be started manually with `workflow_dispatch`.

Pipeline steps:

1. Checkout code.
2. Configure AWS credentials.
3. Login to Amazon ECR.
4. Build the Docker image.
5. Tag the image for ECR.
6. Push the image to ECR.
7. SSH into EC2.
8. Pull the latest image.
9. Stop and remove the old container.
10. Run the new container on port `8080`.

## Required GitHub Secrets

Create these secrets in your GitHub repository:

```text
AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY
EC2_HOST
SSH_PRIVATE_KEY_EC2
```

## AWS Values Used by the Pipeline

```text
AWS Region: eu-west-1
ECR Repository: project/spring-boot-app
Container Name: app
Host Port: 8080
Container Port: 8080
```

The deployed image is:

```text
831666161064.dkr.ecr.eu-west-1.amazonaws.com/project/spring-boot-app:latest
```

## EC2 Requirements

The EC2 instance should have:

- Docker installed.
- Permission to pull from ECR.
- Inbound security group rule for TCP port `8080`.
- SSH access from GitHub Actions using the `SSH_PRIVATE_KEY_EC2` secret.

After deployment, open:

```text
http://YOUR_EC2_PUBLIC_IP:8080
```

## Troubleshooting

Check if the container is running:

```bash
docker ps
```

Check application logs:

```bash
docker logs app
```

Test the app from the EC2 instance:

```bash
curl http://localhost:8080
curl http://localhost:8080/api/health
```

Restart manually:

```bash
docker stop app || true
docker rm app || true
docker run -d --name app --restart unless-stopped -p 8080:8080 831666161064.dkr.ecr.eu-west-1.amazonaws.com/project/spring-boot-app:latest
```

If the website does not open, check:

- The container is running.
- The Docker port mapping is `8080:8080`.
- The EC2 security group allows inbound TCP `8080`.
- The EC2 public IP address is correct.
- The latest Docker image was pushed to ECR.
- The EC2 instance has permission to pull from ECR.
