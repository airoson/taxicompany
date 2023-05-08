for(let add of document.querySelectorAll(".add")){
   add.addEventListener("click", (event)=>{
      let node = document.createElement("li");
      let text = document.getElementById(event.target.getAttribute("car-id") + "-addon-text");
      if(text.value !== ""){
         node.innerText = text.value;
         text.value = "";
         let remove_button = document.createElement("button");
         remove_button.classList.add("change-list", "remove");
         remove_button.innerText = "-";
         remove_button.addEventListener("click", removeParentElem);
         node.appendChild(remove_button);
         document.getElementById(event.target.getAttribute("car-id") + "-addons").appendChild(node);
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
      files = event.target.files;
      files[event.target.getAttribute("car-id")] = files;
      document.getElementById(event.target.getAttribute("car-id") + "-image").setAttribute("src", URL.createObjectURL(file));
   }, false);
}

for(let update of document.querySelectorAll(".update-car")){
   update.addEventListener("click", async (event) => {
      let payFrequency = null;
      let rentalPeriod = null;
      let car_id = event.target.getAttribute("car-id");
      if (document.getElementById(car_id + "-pay-frequency") != null) {
         payFrequency = parseInt(document.getElementById(car_id + "-pay-frequency").value);
      }
      if (document.getElementById(car_id + "-rental-period")) {
         rentalPeriod = parseInt(document.getElementById(car_id + "-rental-period").value);
      }
      let gearbox = document.getElementById(car_id + "-gearbox").options[document.getElementById(car_id + "-gearbox")].value;
      let engine = document.getElementById(car_id + "-engine").options[document.getElementById(car_id + "-engine")].value;
      let rates = []
      let photos = []
      for (let rate of document.querySelectorAll("." + car_id + "-rate-checkbox")) {
         if (rate.hasAttribute("checked"))
            rates.push(rate.value);
      }
      if(car_id in files){
         for (let file of files[car_id]) {
            let reader = new FileReader();
            reader.readAsBinaryString(file)
            photos.push({"image": reader.result});
         }
      }
      let car = {
         "name": document.getElementById("name").value,
         "price": document.getElementById("price").value,
         "rentalPeriod": rentalPeriod,
         "payFrequency": payFrequency,
         "engine": engine,
         "gearbox": gearbox,
         "rates": rates,
         "photos": photos
      };
      let response = await fetch("http://localhost:8080/api/cars/" + event.target.getAttribute("car-id"),
          {
             method:"PATCH",
             headers:{
                "Content-Type":"application/json"
             },
             body: JSON.stringify(car)
          });
      if(response.status !== 200){
         event.target.nextSibling().style.display="static";
      }else{
         event.target.nextSibling().style.display="none";
      }
   });
}

for(let delete_button of document.querySelectorAll(".delete-button")){
   delete_button.addEventListener("click", async (event) => {
      let response = await fetch("http://localhost:8080/api/cars/" + event.target.getAttribute("car-id"), {
         method: "DELETE"
      });
   });
}