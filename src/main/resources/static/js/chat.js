'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var userlistArea = document.querySelector('#userlistArea');
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
				var len = data.length;

				console.log("test json: " + data + ",len " + len);
				for (var i = 0; i < obj.length; ++i) {
                    console.log(obj[i].content);
                }
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
