let subscription;

const stompClient = new StompJs.Client({

    brokerURL: 'wss://34ae-39-124-165-135.ngrok-free.app/ws-chat',
    connectHeaders : {
        Authorization : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsc2NAZ21haWwuY29tIiwiYXV0aCI6IlJPTEVfQURNSU4iLCJleHAiOjE3MDU0Njk3MDZ9.FoIQteN2r1N4TBYHJe--XwGwQj5ZM_pXVCKpoNIsZAQ"
    }
});

// Set up event handlers
stompClient.onConnect = onStompConnect;
stompClient.onWebSocketError = onWebSocketError;
stompClient.onStompError = onStompError;

// Function to handle Stomp connection
function onStompConnect(frame) {
    setConnected(true);
    console.log('Connected: ' + frame);
    console.log('jwt', stompClient.connectHeaders);
    subscription = stompClient.subscribe('/chatting/topic/room/1', (greeting) => {
        const content = greeting.body;
        showChatlist(content);
    }, stompClient.connectHeaders);
}

// Function to handle WebSocket error
function onWebSocketError(error) {
    console.error('Error with WebSocket:', error);
}

// Function to handle Stomp error
function onStompError(frame) {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
}

// Function to set the connection status
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#conversation").toggle(connected);
    $("#greetings").html("");
}

// Function to connect to the WebSocket
function connect() {
    stompClient.activate();
}

// Function to disconnect from the WebSocket
function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

// Function to send a message
function sendChat() {
    const headers = {
        Authorization: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsc2NAZ21haWwuY29tIiwiYXV0aCI6IlJPTEVfQURNSU4iLCJleHAiOjE3MDU0Njk3MDZ9.FoIQteN2r1N4TBYHJe--XwGwQj5ZM_pXVCKpoNIsZAQ"
    };
    stompClient.publish({
        headers: headers,
        destination: "/chatting/pub/message",
        body: JSON.stringify({'message': $("#chat").val(), 'roomId': 1})
    });

    // STOMP 프로토콜에서는 publish 대신 send를 사용합니다.

}
function unsubscribe() {
    // 저장한 subscription 객체를 사용하여 unsubscribe
    if (subscription) {
        stompClient.unsubscribe(subscription);
        setConnected(false);
        console.log("Unsubscribed");
    } else {
        console.error('No active subscription to unsubscribe.');
    }
}

// Function to display a greeting message
function showChatlist(message) {
    try {
        const parsedMessage = JSON.parse(message);
        const content = parsedMessage.message;
        console.log('content',content)
        $("#chatlist").append($("<tr><td>" + content + "</td></tr>"));
    } catch (error) {
        console.error('Error parsing WebSocket message:', error);
    }
}

// Set up event listeners
$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendChat());
    $("#unsubscribe").click(() => unsubscribe()); // Add this line for the 'Unsubscribe' button

});
