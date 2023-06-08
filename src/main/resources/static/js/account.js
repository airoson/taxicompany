let token = document.querySelector("meta[name='_csrf']").getAttribute("content");
let header = document.querySelector("meta[name='_csrf_header']").getAttribute("content")

async function remove_favorite(event) {
    let car_id = event.target.getAttribute("car-id");
    let response = await fetch("http://localhost:8080/api/cars/favorites/" + car_id, {
        method: "DELETE",
        headers: {
            [header]: token,
        }
    });
    if (response.status !== 200) {
        alert("Не удалось удалить из избранного.");
    } else {
        window.location.replace(location.href);
    }
}

async function add_favorite(event) {
    let car_id = event.target.getAttribute("car-id");
    let response = await fetch("http://localhost:8080/api/cars/favorites/" + car_id, {
        method: "POST",
        headers: {
            [header]: token,
        }
    });
    if (response.status !== 200) {
        alert("Не удалось добавить в избранное.");
    } else {
        window.location.replace(location.href);
    }
}

for(let favorite_button of document.querySelectorAll(".add-favorite-button")){
    favorite_button.addEventListener("click", add_favorite);
}

for(let favorite_button of document.querySelectorAll(".remove-favorite-button")){
    favorite_button.addEventListener("click", remove_favorite);
}

document.getElementById("user-delete-button").addEventListener("click", async (event) => {
    let response = await fetch("http://localhost:8080/api/user/" + event.target.getAttribute("user-id"), {
        method: "DELETE",
        headers: {
            [header]: token
        }
    });
});