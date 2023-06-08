let token = document.querySelector("meta[name='_csrf']").getAttribute("content");
let header = document.querySelector("meta[name='_csrf_header']").getAttribute("content")
for(let add of document.querySelectorAll(".add")){
   add.addEventListener("click", (event)=>{
      let node = document.createElement("li");
      let car_id = event.target.getAttribute("car-id");
      let text = document.getElementById( car_id + "-addon-text");
      if(text.value !== ""){
         let span = document.createElement("span");
         span.classList.add("addon-text-" + car_id);
         span.innerText = text.value;
         node.appendChild(span);
         text.value = "";
         let remove_button = document.createElement("button");
         remove_button.classList.add("change-list", "remove");
         remove_button.innerText = "-";
         remove_button.addEventListener("click", removeParentElem);
         node.appendChild(remove_button);
         node.classList.add("addon-list-" + car_id);
         document.getElementById(car_id + "-addons").appendChild(node);
      }
   });
}

function removeParentElem(event){
   event.target.parentElement.remove();
}

for(let remove of document.querySelectorAll(".remove")){
   remove.addEventListener("click", removeParentElem);
}

let files = {}

for(let change_image of document.querySelectorAll(".change-image")){
   change_image.addEventListener("change", (event)=>{
      let file = event.target.files[0];
      files[event.target.getAttribute("car-id")] = event.target.files;
      document.getElementById(event.target.getAttribute("car-id") + "-image").setAttribute("src", URL.createObjectURL(file));
   }, false);
}

for(let update of document.querySelectorAll(".update-car")){
   update.addEventListener("click",  async (event) => {
      let payFrequency = null;
      let rentalPeriod = null;
      let car_id = event.target.getAttribute("car-id");
      if (document.getElementById(car_id + "-pay-frequency").value !== "") {
         payFrequency = parseInt(document.getElementById(car_id + "-pay-frequency").value);
      }
      if (document.getElementById(car_id + "-rental-period").value !== "") {
         rentalPeriod = parseInt(document.getElementById(car_id + "-rental-period").value);
      }
      let gearbox = document.getElementById(car_id + "-gearbox").options[document.getElementById(car_id + "-gearbox").selectedIndex].value;
      let engine = document.getElementById(car_id + "-engine").options[document.getElementById(car_id + "-engine").selectedIndex].value;
      let rates = []
      let photos = []
      let addons = []
      for (let rate of document.querySelectorAll(".rate-checkbox-" + car_id)) {
         if (rate.checked)
            rates.push(rate.value);
      }
      for (let addon of document.querySelectorAll(".addon-text-" + car_id)) {
         addons.push(addon.innerText)
      }
      if (files[car_id] !== undefined) {
         for (let file of files[car_id]) {
            let data = new FormData()
            data.append('file', file)
            await fetch("http://localhost:8080/api/image", {
               method: "PUT",
               headers: {
                  [header]: token,
               },
               body: data
            }).then(response => Promise.all([response.status, response.json()]))
                .then(function([status, myJson]) {
                   if (status == 200) {
                      photos.push({"id": myJson["id"]});
                   }
                })
         }
      }
      let car = {
         "name": document.getElementById(car_id + "-name").value,
         "price": parseInt(document.getElementById(car_id + "-price").value),
         "rentalPeriod": rentalPeriod,
         "payFrequency": payFrequency,
         "engine": engine,
         "gearbox": gearbox,
         "addons": addons,
         "rates": rates,
         "photos": photos
      };
      let json = JSON.stringify(car);
      console.log(json);;
      let response = await fetch("http://localhost:8080/api/cars/" + car_id,
          {
             method: "PATCH",
             headers: {
                [header]: token,
                "Content-Type": "application/json"
             },
             body: json
          });
      if (response.status !== 200) {
         document.querySelector(".error-message").style.display = "block";
      } else {
         document.querySelector(".error-message").style.display = "none";
         location.replace(location.href);
      }
   });
}

for(let delete_button of document.querySelectorAll(".delete-car")){
   delete_button.addEventListener("click",  (event) => {
      let response = fetch("http://localhost:8080/api/cars/" + event.target.getAttribute("car-id"), {
         headers: {
            [header]: token,
         },
         method: "DELETE"
      }).then((response) => {
         location.replace(location.href);
      });
   });
}