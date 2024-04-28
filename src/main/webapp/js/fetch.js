/* fetch("https://pokeapi.co/api/v2/pokemon/pikachu")
    .then(response => {
        if(response.ok){
            console.log(response.ok);
            return response.json();
        } else {
            throw new Error("Ain't got no resource, dawg!");
        }
    })
    .then(data => console.log(data))
    .catch(error => console.error(error)); */

/* async function fetchData(){
    try {

        const pokemonName = document.getElementById("messageField").value.toLowerCase();
        const imgElement = document.getElementById("pokemonSprite");

        const response = await fetch(`https://pokeapi.co/api/v2/pokemon/${pokemonName}`);

        if(response.ok){
            const data = await response.json();
            const pokemonSprite = data.sprites.front_default;

            imgElement.src = pokemonSprite;
            imgElement.style.display = "block"
        } else {
            imgElement.style.display = "none"
            throw new Error("Ain't got no resource, dawg!");
        }

    } catch (error) {
        console.error(error);
    }
} */

/* fetch('http://localhost:7796/jmstest/get',{
    method: 'GET',
    headers: {
        'Content-Type': 'application/json'
      }
})
    .then(response => {
        if(response.ok){
            console.log(response.ok);
            return response;
        } else {
            throw new Error("Ain't got no resource, dawg!");
        }
    })
    .then(data => console.log(data))
    .catch(error => console.error(error)); */

/* const request = new XMLHttpRequest();
request.open("GET", "https://localhost:7796/jmstest/get");
request.send();
request.onload = () => {
    if (request.status === 200){
        console.log(JSON.parse(request.response))
    } else {
        console.log(`Error: ${request.status}`)
    }
} */

function popupSnackbar(message) {
    console.log("snackbar")
    var x = document.getElementById("snackbar");
    x.className = "show";
    x.innerHTML = message;
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 1500);
  }

async function getMessage(){
    var responseMessage;
    try {
        const messageReceived = document.getElementById("messageReceived");
        const response = await fetch("http://192.168.1.73:7796/jmstest/get", {
            method: 'GET',
            header: {
                'Content-type': 'application/json'
            }
        });
        if(response.ok){
            const data = await response.json();
            var message = data.responseMessage;
            
            messageReceived.style.visibility = "visible"
            messageReceived.innerHTML = message;
            responseMessage = "Message Received!";
        } else {
            messageReceived.style.visibility = "hidden"
            responseMessage = "Ain't got no resource, dawg!"
            throw new Error(responseMessage);
        }
    } catch (error) {
        responseMessage = error;
        console.error(error);
    }
    popupSnackbar(responseMessage);
}

async function sendMessage(){
    var responseMessage;
    try {
        const messageValue = document.getElementById("messageSent").value;
        const messageJson = {'message': messageValue};
        // console.log({'messageValue' : messageValue, 'messageJson' : messageJson});
        // console.log(JSON.stringify({'messageValue' : messageValue, 'messageJson' : messageJson}));
        const response = await fetch("http://192.168.1.73:7796/jmstest/send", {
            method: 'POST',
            body: JSON.stringify(messageJson),
            header: {
                'Content-Type': 'application/json;'
            }
        });
        if(response.ok){
            const data = await response.json();
            console.log(data);
            responseMessage = "Message Sent!";
        } else {
            responseMessage = "Ain't got no resource, dawg!"
            throw new Error(responseMessage);
        }

    } catch (error) {
        responseMessage = error;
        console.error(error);
    }
    popupSnackbar(responseMessage);
}

/* const sendthing = () => {

    const messageValue = document.getElementById("messageSent").value;
    const messageJson = {'message': messageValue};
    console.log(messageValue);
    fetch("http://localhost:7796/jmstest/send", {
        method: 'POST',
        header: {
            'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;'
        },
        body: messageValue
    })
    .then(response => {
        if(response.ok){
            return response.json();
        } else {
            throw new Error("Ain't got no resource, dawg!");
        }
    })
    .then(data => console.log(data))
    .catch(error => console.error(error)); 
} */