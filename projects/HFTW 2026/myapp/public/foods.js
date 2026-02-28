console.log("foods.js loaded");
document.addEventListener("DOMContentLoaded", () => {
  fetch("http://192.168.178.73:7000/foods")
    .then(response => {
      if (!response.ok) {
        throw new Error("Failed to fetch foods");
      }
      return response.json();
    })
    .then(foods => {
      console.log(foods);
      renderFoods(foods);
    })
    .catch(err => {
      console.error("Error:", err);
    });
});

function renderFoods(foods) {
  const list = document.getElementById("foods-list");

  foods.forEach(food => {
    const li = document.createElement("li");
    li.textContent = food.name;
    list.appendChild(li);
  });
}

function addFoods()
{
    let description = document.getElementById("description").value; 
    let quantity = document.getElementById("quantity").value;
    let area = document.getElementById("area").value;

    const url = "http://192.168.178.73:7000/addFood";
    const data = {
        name:description,
        quantity:quantity,
        area:area
    };

    fetch(url, {
    method: 'POST', // Specify the HTTP method
    headers: {
        'Content-Type': 'application/json' // Tell the server we're sending JSON
    },
    body: JSON.stringify(data) // Convert the JS object into a JSON string
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json(); // Parse the JSON response from the server
    })
    .then(result => {
        console.log('Success:', result);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}