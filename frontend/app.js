function onSignInPageOpened() {
    const token = localStorage.getItem('auth');
    if (token == null) {
        return;
    }

    fetch("http://localhost:8080/tasks?page=0&size=1", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        }
    }).then(r => {
        if (r.ok) {
            window.location.href = "/index.html";
        } else if (r.status === 401 || r.status === 403) {
            localStorage.removeItem('auth');
        }
    });
}

function signOut() {
    localStorage.removeItem('auth');
    window.location.href = "/signin.html"
}

function signIn() {
    const username = document.getElementById("input_username").value;
    const password = document.getElementById("input_password").value;

    fetch("http://localhost:8080/auth/signin", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({username: username, password: password})
    }).then(r => r.json())
        .then(j => {
            localStorage.setItem('auth', j.token);
            window.location.href = "/index.html";
        });
}

function getParams() {
    const params = {};
    location.search.substring(1)
        .split("&")
        .forEach(item => {
            const t = item.split("=")
            params[t[0]] = t[1]
        });

    return params;
}

function loadUserInfo() {
    const params = getParams();
    const page = parseInt(params.page, 10) || 0;

    const token = localStorage.getItem('auth');
    if (token == null) {
        signOut();
        return;
    }

    fetch("http://localhost:8080/users/info", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        }
    }).then(r => {
        if (r.ok) {
            return r.json();
        } else if (r.status === 401 || r.status === 403) {
            signOut();
        }
    }).then(j => {
        const userInfo = document.getElementById("user_info");
        userInfo.textContent = j.username;
    });
}

function loadTasks() {
    const params = getParams();
    const page = parseInt(params.page, 10) || 0;

    const token = localStorage.getItem('auth');
    if (token == null) {
        signOut();
        return;
    }

    fetch("http://localhost:8080/tasks?page=" + page, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        }
    }).then(r => {
        if (r.ok) {
            return r.json();
        } else if (r.status === 401 || r.status === 403) {
            signOut();
        }
    }).then(j => {
        const pageCountSpan = document.getElementById("page_count");
        pageCountSpan.textContent = `${page + 1} of ${j.totalPages}`;

        const tasksDiv = document.getElementById("tasks");
        for (const task of j.elements) {
            tasksDiv.innerHTML += `<div class="task">
                <div class="htmlmathparagraph">${task.text}</div>
                    ${function () {
                let result = "";
                for (const image of task.images) {
                    result += `<img src="http://localhost:8080/images/${image.id}/download" alt="${image.name}">`
                }
                return result;
            }()}
                </div>`;
        }

        const pagesDiv = document.getElementById("pages");
        for (let i = 0; i < j.totalPages; i++) {
            if (i === page) {
                pagesDiv.innerHTML += `<span class="page_link current_page_link">${i + 1}</span>`
            } else {
                pagesDiv.innerHTML += `<a href="/index.html?page=${i}" class="page_link">${i + 1}</a>`
            }
        }

        MathJax.typesetPromise();
    });
}