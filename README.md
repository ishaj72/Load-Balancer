# Custom HTTP Load Balancer & Service Registry

A lightweight, modular Layer 7 (HTTP) Load Balancer and Service Registry built with Spring Boot. It dynamically discovers backend instances upon startup and provides an extensible routing engine designed to support multiple pluggable load balancing algorithms.

---

## Overview & Architecture

The system functions in two primary stages:

1. **Service Registration:** Backend instances automatically announce their availability upon initialization by sending their address (host/IP and port) to the registry endpoint.
2. **Dynamic Routing:** When incoming client traffic hits the load balancer, the core routing engine queries the active server registry, applies the selected load balancing strategy, and proxies the request to the chosen backend server.

---

## Key Features

- **Dynamic Auto-Discovery:** Backend nodes register dynamically at startup without needing hardcoded routing tables or restarts.
- **Pluggable Strategy Pattern:** Modular architecture allowing seamless addition and configuration of custom routing algorithms.
- **Transparent Reverse Proxying:** Forwards standard HTTP methods, headers, query parameters, and payload data.
- **Dynamic Node Management:** Maintains an in-memory pool of available healthy instances.

---

## Supported & Planned Strategies

| Strategy | Description |
| :--- | :--- |
| **Round Robin** | Distributes requests sequentially across the list of registered servers. |
| **Random Selection** | Routes each request to a randomly chosen healthy instance. |
| **Least Connections** | Directs traffic to the server with the fewest active concurrent requests. |
| **Weighted Round Robin** | Allocates proportionally more requests to nodes with higher configured capacity. |
| **IP Hash / Sticky Sessions** | Hashes the client's IP address to consistently map requests from the same client to the same backend node. |

---

## System Workflows

### 1. Registration Flow
- Backend service boots up and triggers a post-startup event.
- It detects its assigned port and host address.
- It sends a registration payload to the load balancer's `/registry/register` endpoint.
- The load balancer verifies the payload and updates its internal registry pool.

### 2. Request Routing Flow
- Client sends an HTTP request to the load balancer.
- The load balancer inspects the active registry.
- The configured routing strategy selects the best target instance.
- The load balancer forwards the request and relays the response back to the client.

---

## API Specifications

### Service Registry
- **`POST /registry/register`**
  - **Purpose:** Registers a newly started service node into the routing pool.
  - **Payload Format:** JSON containing `host` (String) and `port` (Integer).
  - **Expected Response:** `200 OK` or `201 Created`.

- **`POST /registry/deregister`** *(Planned)*
  - **Purpose:** Removes a terminating node from the routing pool before shutdown.

### Proxy Route
- **`ANY /**`**
  - **Purpose:** Wildcard route intercepting all non-registry requests and proxying them to backend targets.

---

## Getting Started

### Prerequisites
- Java JDK (17 or higher)
- Maven or Gradle wrapper
- Multiple backend service instances or port configurations

### Quickstart
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/ishaj72/Load-Balancer.git](https://github.com/ishaj72/Load-Balancer.git)
   cd Load-Balancer
