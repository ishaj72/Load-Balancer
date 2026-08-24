# Simple Load Balancer & Service Registry

A simple HTTP load balancer built with Spring Boot. 

It works with one main backend codebase running as multiple instances on your local machine (`localhost`) using different ports. Each instance automatically registers itself when it starts, and the load balancer distributes incoming requests among them using different balancing strategies. The application is also containerized using Docker, allowing multiple backend instances and the load balancer to run as independent containers on a custom Docker network.
---

## How It Works

1. **You start the Load Balancer** on a main port (like `8080`).
2. **You start multiple backend instances** from your backend app on different ports (like `8081`, `8082`, `8083` on `localhost`).
3. **Auto-Registration:** As soon as each backend instance starts up, it sends its port number to the load balancer.
4. **Request Routing:** When a user sends a request to the load balancer, it picks one of the running instances based on the selected strategy and forwards the request there.
5. **Auto-Deregistering:** Removes the backend from registered servers list .

---

## What This Project Does

- **Auto-Join:** Backend servers tell the load balancer and automatically on startup.
- **Local Multi-Instance Testing:** Run 2, 3, or more copies of your backend on `localhost` with different ports.
- **Different Routing Strategies:** Easily test and switch between multiple ways of sharing traffic.
- **Traffic Forwarding:** Passes incoming requests directly to the selected server and returns the answer.
-  **Deregistering:** When the backend stops the port deregisters itself or when the api is hit by user then.

## Reference Images 

### Getting registered ports
<img width="1428" height="567" alt="image" src="https://github.com/user-attachments/assets/0ef39050-e56c-4af7-aba0-dec16deaffe8" />

### Deregister the port
<img width="1425" height="607" alt="image" src="https://github.com/user-attachments/assets/40d16f93-667b-4d56-a2df-aab4b626b28c" />

---

## Load Balancing Strategies

| Strategy | How it works (Simple words) |
| :--- | :--- |
| **Round Robin** | Takes turns. Sends request 1 to Server A, request 2 to Server B, request 3 to Server C, then repeats. |
Other strategies will be added later

---

## How to Run It

1. **Start the Load Balancer:**
   - Run the load balancer app (runs on port `8080`).

2. **Start Backend Instance 1:**
   - Run your backend with `server.port=8081`.

3. **Start Backend Instance 2:**
   - Run another copy of your backend with `server.port=8082`.

4. **Test:**
   - Send requests to `http://localhost:8080`.
   - Watch the load balancer share the requests between port `8081` and `8082`.

5. **Deregister a server**
   - **`http://localhost:8080/registry/deregister`**
   - Sent to backend when you to deregister the port.
   - Need to send server and port in body with the api.

---
