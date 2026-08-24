const LOAD_BALANCER_URL = "http://localhost:8080";

let requestCounts = {};


async function loadServers() {

    try {

        const response = await fetch(
            `${LOAD_BALANCER_URL}/registry/servers`
        );

        if (!response.ok) {
            throw new Error("Unable to fetch servers");
        }

        const servers = await response.json();

        document.getElementById("lbStatus").textContent = "UP";
        document.getElementById("lbStatus").className =
            "status up";

        document.getElementById("serverCount").textContent =
            servers.length;

        displayServers(servers);

    } catch (error) {

        console.error(error);

        document.getElementById("lbStatus").textContent = "DOWN";
        document.getElementById("lbStatus").className =
            "status down";

        document.getElementById("serverCount").textContent = "0";
    }
}


function displayServers(servers) {

    const tableBody =
        document.getElementById("serverTableBody");

    tableBody.innerHTML = "";

    servers.forEach(server => {

        const key = `${server.host}:${server.port}`;

        if (!requestCounts[key]) {
            requestCounts[key] = 0;
        }

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${server.host}</td>

            <td>${server.port}</td>

            <td>
                <span class="status up">
                    UP
                </span>
            </td>

            <td id="count-${server.port}">
                ${requestCounts[key]}
            </td>
        `;

        tableBody.appendChild(row);
    });

    updateDistribution(servers);
}


async function sendRequest() {

    try {

        const response = await fetch(
            `${LOAD_BALANCER_URL}/registry/hello`
        );

        if (!response.ok) {
            throw new Error("Request failed");
        }

        const message = await response.text();

        document.getElementById("responseBox").textContent =
            message;

        document.getElementById("lastRequest").textContent =
            extractServer(message);

        updateRequestCount(message);

        await loadServers();

    } catch (error) {

        console.error(error);

        document.getElementById("responseBox").textContent =
            "Request failed. Check if the load balancer is running.";

    }
}

function extractServer(message) {

    const match = message.match(/808[0-9]/);

    if (match) {
        return `backend:${match[0]}`;
    }

    return message;
}

function updateRequestCount(message) {

    const port = extractPort(message);

    if (!port) {
        return;
    }

    const key = Object.keys(requestCounts)
        .find(k => k.endsWith(`:${port}`));

    if (key) {

        requestCounts[key]++;

        const countElement =
            document.getElementById(`count-${port}`);

        if (countElement) {
            countElement.textContent =
                requestCounts[key];
        }
    }
}

function extractPort(message) {

    const match = message.match(/808[0-9]/);

    if (match) {
        return match[0];
    }

    return null;
}


function updateDistribution(servers) {

    const container =
        document.getElementById("distribution");

    container.innerHTML = "";

    const values = servers.map(server => {

        const key =
            `${server.host}:${server.port}`;

        return requestCounts[key] || 0;

    });

    const max =
        Math.max(...values, 1);

    servers.forEach(server => {

        const key =
            `${server.host}:${server.port}`;

        const count =
            requestCounts[key] || 0;

        const percentage =
            (count / max) * 100;

        const item =
            document.createElement("div");

        item.className =
            "distribution-item";

        item.innerHTML = `

            <div class="distribution-header">

                <span>
                    ${server.host}:${server.port}
                </span>

                <span>
                    ${count}
                </span>

            </div>

            <div class="bar-container">

                <div
                    class="bar"
                    style="width: ${percentage}%">
                </div>

            </div>
        `;

        container.appendChild(item);
    });
}

document
    .getElementById("sendRequestBtn")
    .addEventListener(
        "click",
        sendRequest
    );


document
    .getElementById("refreshBtn")
    .addEventListener(
        "click",
        loadServers
    );

loadServers();