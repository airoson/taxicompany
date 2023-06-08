let token = document.querySelector("meta[name='_csrf']").getAttribute("content");
let header = document.querySelector("meta[name='_csrf_header']").getAttribute("content")

document.getElementById("addon-add").addEventListener("click", (event)=>{
    let node = document.createElement("li");
    let text = document.getElementById( "addon-text");
    if(text.value !== ""){
        let span = document.createElement("span");
        span.classList.add("addon-text");
        span.innerText = text.value;
        node.appendChild(span);
        text.value = "";
        let remove_button = document.createElement("button");
        remove_button.classList.add("change-list", "remove");
        remove_button.innerText = "-";
        remove_button.addEventListener("click", removeParentElem);
        node.appendChild(remove_button);
        node.classList.add("addon-list");
        document.getElementById( "addons").appendChild(node);
    }
});

function removeParentElem(event){
    event.target.parentElement.remove();
}

let file = null;

document.querySelector("input[type='file']").addEventListener("change", (event)=>{
    file = event.target.files[0];
    document.getElementById("preview-blank").remove();
    let img = document.createElement("img")
    img.setAttribute("src", URL.createObjectURL(file));
    img.classList.add("preview-image");
    document.getElementById("image-choose").appendChild(img);
}, false);

document.getElementById("put").addEventListener("click", async (event) => {
    let payFrequency = null;
    let rentalPeriod = null;
    if (document.getElementById("pay-frequency").value !== "") {
        payFrequency = parseInt(document.getElementById("pay-frequency").value);
    }
    if (document.getElementById("rental-period").value !== "") {
        rentalPeriod = parseInt(document.getElementById("rental-period").value);
    }
    let gearbox = document.getElementById("gearbox").options[document.getElementById("gearbox").selectedIndex].value;
    let engine = document.getElementById("engine").options[document.getElementById("engine").selectedIndex].value;
    let rates = []
    let photos = []
    let addons = []
    for (let rate of document.querySelectorAll(".rate-checkbox")) {
        if (rate.checked)
            rates.push(rate.value);
    }
    for (let addon of document.querySelectorAll(".addon-text")) {
        addons.push(addon.innerText)
    }
    if (file != null) {
        let data = new FormData()
        data.append('file', file)
        await fetch("http://localhost:8080/api/image", {
            method: "PUT",
            headers: {
                [header]: token,
            },
            body: data
        }).then(response => Promise.all([response.status, response.json()]))
            .then(function ([status, myJson]) {
                if (status === 200) {
                    photos.push({"id": myJson["id"]});
                }
            });
    }
    let car = {
        "name": document.getElementById("name").value,
        "price": parseInt(document.getElementById("price").value),
        "rentalPeriod": rentalPeriod,
        "payFrequency": payFrequency,
        "engine": engine,
        "gearbox": gearbox,
        "addons": addons,
        "rates": rates,
        "photos": photos
    };
    let json = JSON.stringify(car);
    let response = await fetch("http://localhost:8080/api/cars",
        {
            method: "PUT",
            headers: {
                [header]: token,
                "Content-Type": "application/json"
            },
            body: json
        });
    if (response.status !== 200) {
        document.querySelector(".error-message").style.display = "block";
        document.querySelector(".success-message").style.display = "none";
    } else {
        document.querySelector(".error-message").style.display = "none";
        document.querySelector(".success-message").style.display = "block";
    }
});