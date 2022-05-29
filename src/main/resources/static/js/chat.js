'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('.msg_container_base');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var user_id = null;
var appointmentId = null;

var colors = [
	'#2196F3', '#32c787', '#00BCD4', '#ff5652',
	'#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

$(document).ready(function() {
	getLastMessages();
});

function getLastMessages() {
	appointmentId = document.querySelector('#appointment_id').value.trim();
	console.log("test chat: " + appointmentId);
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

				console.log("test json: " + data + ",len " + len);
				for (var i = len - 1; i >= 0; i--) {
                    console.log(obj[i].content);
                    // Add to MessageArea
					let messageElement = document.createElement('div');
					messageElement.classList.add('row','msg_container','base_receive');
					
					let messageElement0 = document.createElement('div');
					messageElement0.classList.add('col-md-1','col-xs-1');
					messageElement.appendChild(messageElement0);
					
					let avatarElement = document.createElement('i');
					avatarElement.classList.add('chat-avatar');
					let avatarText = document.createTextNode(obj[i].sender[0]);
					avatarElement.appendChild(avatarText);
					avatarElement.style['background-color'] = getAvatarColor(obj[i].sender);
					messageElement0.appendChild(avatarElement);
					
					let messageElement1 = document.createElement('div');
					messageElement1.classList.add('col-md-11','col-xs-11');
					messageElement.appendChild(messageElement1);
					
					let messageElement2 = document.createElement('div');
					messageElement2.classList.add('messages','msg_receive');
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
				/*for (var i = len - 1; i >= 0; i--) {

					// Add to MessageArea
					let messageElement = document.createElement('li');
					messageElement.classList.add('chat-message');

					let avatarElement = document.createElement('i');
					let avatarText = document.createTextNode(data[i].user_name[0]);
					avatarElement.appendChild(avatarText);
					avatarElement.style['background-color'] = getAvatarColor(data[i].user_name);

					messageElement.appendChild(avatarElement);

					let usernameElement = document.createElement('span');
					let usernameText = document.createTextNode(data[i].user_name);
					usernameElement.appendChild(usernameText);
					messageElement.appendChild(usernameElement);

					let textElement = document.createElement('p');
					let messageText = document.createTextNode(data[i].content);
					textElement.appendChild(messageText);

					messageElement.appendChild(textElement);

					messageArea.appendChild(messageElement);

				}
				messageArea.scrollTop = messageArea.scrollHeight;*/
			}

		},
	});

}



function getAvatarColor(messageSender) {
	var hash = 0;
	for (var i = 0; i < messageSender.length; i++) {
		hash = 31 * hash + messageSender.charCodeAt(i);
	}

	var index = Math.abs(hash % colors.length);
	return colors[index];
}
