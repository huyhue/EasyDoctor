'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('.msg_container_base');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var user_id = null;
var appointmentId = document.querySelector('#appointment_id').value.trim();

var colors = [
	'#2196F3', '#32c787', '#00BCD4', '#ff5652',
	'#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

$(document).ready(function() {

	// Connect to WebSocket Server.
	connect();

	getLastMessages();
});

function getLastMessages() {
	$.ajax({
		url: "/appointments/messages/all",
		type: "POST",
		data: {
			appointmentId: appointmentId
		},
		error: function() {

		},
		success: function(data) {
			if (data != null && data != undefined) {
				var obj = JSON.parse(data);
				console.log("test obj: " + obj);
				var len = obj.length;
				for (var i = len - 1; i >= 0; i--) {
					// Add to MessageArea
					let messageElement = document.createElement('div');
					messageElement.classList.add('row', 'msg_container', 'base_receive');

					let messageElement0 = document.createElement('div');
					messageElement0.classList.add('col-md-1', 'col-xs-1');
					messageElement.appendChild(messageElement0);

					let avatarElement = document.createElement('i');
					avatarElement.classList.add('chat-avatar');
					let avatarText = document.createTextNode(obj[i].sender[0]);
					avatarElement.appendChild(avatarText);
					avatarElement.style['background-color'] = getAvatarColor(obj[i].sender);
					messageElement0.appendChild(avatarElement);

					let messageElement1 = document.createElement('div');
					messageElement1.classList.add('col-md-11', 'col-xs-11');
					messageElement.appendChild(messageElement1);

					let messageElement2 = document.createElement('div');
					messageElement2.classList.add('messages', 'msg_receive');
					messageElement1.appendChild(messageElement2);

					let textElement = document.createElement('p');
					let messageText = document.createTextNode(obj[i].content);
					textElement.appendChild(messageText);
					messageElement2.appendChild(textElement);

					let usernameElement = document.createElement('span');
					let usernameText = document.createTextNode(obj[i].role + " * " + obj[i].createdAt);
					usernameElement.appendChild(usernameText);
					messageElement2.appendChild(usernameElement);

					messageArea.appendChild(messageElement);
				}
				messageArea.scrollTop = messageArea.scrollHeight;
			}

		},
	});

}

function connect() {
	username = document.querySelector('#user_name').innerText.trim();
	user_id = document.querySelector('#user_id').value.trim();
	console.log("Test username: " + username + user_id);

	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, onConnected, onError);

}

function onConnected() {
	// Subscribe to the Public Topic
	stompClient.subscribe('/topic/public', onMessageReceived);

	// Tell your username, user_id to the server
	stompClient.send("/app/chat.addUser",
		{},
		JSON.stringify({ sender: username, sender_id: user_id, type: 'JOIN' })
	)

	connectingElement.classList.add('hidden');
}


function onError(error) {
	connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
	connectingElement.style.color = 'red';
}

function onMessageReceived(payload) {
	var message = JSON.parse(payload.body);
	console.log("Test received: " + payload.body);
	// Add to MessageArea
	let messageElement = document.createElement('div');
	messageElement.classList.add('row', 'msg_container', 'base_receive');
	
	if (message.type === 'JOIN') {
		messageElement.classList.add('event-message');
		message.content = message.sender + ' tham gia!';
	} else if (message.type === 'LEAVE') {
		messageElement.classList.add('event-message');
		message.content = message.sender + ' đã rời!';
	} else {
		let messageElement0 = document.createElement('div');
		messageElement0.classList.add('col-md-1', 'col-xs-1');
		messageElement.appendChild(messageElement0);
		let avatarElement = document.createElement('i');
		avatarElement.classList.add('chat-avatar');
		let avatarText = document.createTextNode(message.sender[0]);
		avatarElement.appendChild(avatarText);
		avatarElement.style['background-color'] = getAvatarColor(message.sender);
		messageElement0.appendChild(avatarElement);

		let messageElement1 = document.createElement('div');
		messageElement1.classList.add('col-md-11', 'col-xs-11');
		messageElement.appendChild(messageElement1);

		let messageElement2 = document.createElement('div');
		messageElement2.classList.add('messages', 'msg_receive');
		messageElement1.appendChild(messageElement2);

		let textElement = document.createElement('p');
		let messageText = document.createTextNode(message.content);
		textElement.appendChild(messageText);
		messageElement2.appendChild(textElement);

		let usernameElement = document.createElement('span');
		let usernameText = document.createTextNode(message.role + " vào lúc " + message.createdAt);
		usernameElement.appendChild(usernameText);
		messageElement2.appendChild(usernameElement);

		messageArea.appendChild(messageElement);
		messageArea.scrollTop = messageArea.scrollHeight;
	}

	/*var messageElement = document.createElement('li');

	 else {
		messageElement.classList.add('chat-message');

		var avatarElement = document.createElement('i');
		var avatarText = document.createTextNode(message.sender[0]);
		avatarElement.appendChild(avatarText);
		avatarElement.style['background-color'] = getAvatarColor(message.sender);

		messageElement.appendChild(avatarElement);

		var usernameElement = document.createElement('span');
		var usernameText = document.createTextNode(message.sender);
		usernameElement.appendChild(usernameText);
		messageElement.appendChild(usernameElement);
	}

	var textElement = document.createElement('p');
	var messageText = document.createTextNode(message.content);
	textElement.appendChild(messageText);

	messageElement.appendChild(textElement);

	messageArea.appendChild(messageElement);
	messageArea.scrollTop = messageArea.scrollHeight;*/
}

function sendMessage(event) {
	var messageContent = messageInput.value.trim();

	if (messageContent && stompClient) {
		var chatMessage = {
			sender: username,
			sender_id: user_id,
			id_appointment: appointmentId,
			content: messageInput.value,
			type: 'CHAT'
		};

		stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
		messageInput.value = '';
	}
	event.preventDefault();
}



function getAvatarColor(messageSender) {
	var hash = 0;
	for (var i = 0; i < messageSender.length; i++) {
		hash = 31 * hash + messageSender.charCodeAt(i);
	}

	var index = Math.abs(hash % colors.length);
	return colors[index];
}
messageForm.addEventListener('submit', sendMessage, true)