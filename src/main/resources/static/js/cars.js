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
        event.target.innerText = "В избранное";
        event.target.classList.remove("remove-favorite-button");
        event.target.classList.add("add-favorite-button");
        event.target.removeEventListener("click", remove_favorite);
        event.target.addEventListener("click", add_favorite);
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
        event.target.innerText = "Удалить";
        event.target.classList.remove("add-favorite-button");
        event.target.classList.add("remove-favorite-button");
        event.target.removeEventListener("click", add_favorite);
        event.target.addEventListener("click", remove_favorite)
    }
}

for(let favorite_button of document.querySelectorAll(".add-favorite-button")){
    favorite_button.addEventListener("click", add_favorite);
}

for(let favorite_button of document.querySelectorAll(".remove-favorite-button")){
    favorite_button.addEventListener("click", remove_favorite);
}

const scroll_to = document.querySelector(".scroll-to");
if(scroll_to != null) scroll_to.scrollIntoView();