# Docker
This project uses Docker to containerize the Spring Boot Load Balancer and Backend Service.The same Backend Docker image is used to run multiple backend instances on different ports.

## Technologies

- Docker
- Spring Boot
- Java 21
- Maven

## Docker Structure
loadBalancer/
├── Dockerfile
└── ...

backend/
├── Dockerfile
└── ...

## Create Docker Network
```bash
docker network create balancer-network
```

Check the network:
```bash
docker network ls
```

## Build Load Balancer Image
```bash
cd loadBalancer
mvn clean package
docker build -t load-balancer .
```

## Build Backend Image
```bash
cd backend
mvn clean package
docker build -t backend-service .
```

Check the images:
```bash
docker images
```

## Run Load Balancer
```bash
docker run -d `
  --name load-balancer `
  --network balancer-network `
  -p 8080:8080 `
  load-balancer
```

## Run Backend Instances

### Backend 1
```bash
docker run -d `
  --name backend-1 `
  --network balancer-network `
  -e SERVER_PORT=8081 `
  -e SERVER_ADDRESS=backend-1 `
  -e LOADBALANCER_URL=http://load-balancer:8080 `
  backend-service
```

### Backend 2
```bash
docker run -d `
  --name backend-2 `
  --network balancer-network `
  -e SERVER_PORT=8082 `
  -e SERVER_ADDRESS=backend-2 `
  -e LOADBALANCER_URL=http://load-balancer:8080 `
  backend-service
```

### Backend 3
```bash
docker run -d `
  --name backend-3 `
  --network balancer-network `
  -e SERVER_PORT=8083 `
  -e SERVER_ADDRESS=backend-3 `
  -e LOADBALANCER_URL=http://load-balancer:8080 `
  backend-service
```

## Verify Containers
```bash
docker ps
```

The following containers should be running:
```text
load-balancer
backend-1
backend-2
backend-3
```

## Test the Load Balancer

Check registered servers:
```text
GET http://localhost:8080/registry/servers
```

Test load balancing:
```text
GET http://localhost:8080/registry/hello
```

Requests are distributed using the Round Robin strategy:
```text
backend-1 → backend-2 → backend-3 → backend-1 → ...
```

## Docker Concepts Used

- Dockerfile
- Docker Images
- Docker Containers
- Docker Network
- Environment Variables
- Container-to-container communication
- Multiple backend instances from the same Docker image
