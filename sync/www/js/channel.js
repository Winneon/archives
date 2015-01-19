var socket = io(config.socketio);

var player;
var current_time = 0;
var logged_in = false;
var temp_user = "";

var chat = $("ul.chat");

$("form.chatinput input").keydown(function(event){
	if (event.which == 13){
		if ($(this).val() != ""){
			$(this).parent().submit();
		}
		return false;
	}
});

$("form.chatinput input").keydown(function(event){
	if (event.which == 38 || event.which == 40){
		if (event.which == 38){
			socket.emit("history_up");
		} else {
			socket.emit("history_down");
		}
		return false;
	}
});

$("form.chatinput").submit(function(){
	var input = $("form.chatinput input").val();
	if (logged_in || input.split(" ")[0] == "/login" || input.split(" ")[0] == "/help" || $("form.chatinput input").attr("type") == "password"){
		var cont = false;
		if ($("form.chatinput input").attr("type") == "password"){
			socket.emit("command", "/login " + temp_user + " " + input);
			temp_user = "";
			$("form.chatinput input").attr("type", "text");
		} else if (input.indexOf("/login") == 0 && input.split(" ").length == 2){
			$("ul.chat").append($("<li>").html("<span style='color: #FFCC00'><strong>Type your password in the box:</strong></span>"));
			$("form.chatinput input").attr("type", "password");
			temp_user = input.split(" ")[1];
		} else if (input.indexOf("/") == 0){
			socket.emit("command", input);
			cont = true;
		} else {
			var obj = {
				message: input,
				username: "",
				timestamp: "",
				green: false
			};
			socket.emit("chat", obj);
			cont = true;
		}
		if (cont){
			socket.emit("history", input);
		}
	} else {
		$("ul.chat").append($("<li>").html("<span style='color: red'><strong>You are not logged in!</strong></span>"));
		$("ul.chat").append($("<li>").html("<span style='color: red'>Type /login &lt;username&gt; to login.</span>"));
	}
	$("form.chatinput input").val("");
	chat.scrollTop(50000);
	return false;
});

socket.on("help", function(commands){
	$("ul.chat").append($("<li>").html("<span style='color: #FFCC00'><strong>List of commands:</strong></span>"));
	for (var i = 0; i < commands.length; i++){
		$("ul.chat").append($("<li>").html("<span style='color: #FFCC00'>" + commands[i] + "</span>"));
	}
	chat.scrollTop(50000);
});

socket.on("history", function(message){
	$("form.chatinput input").val(message);
});

socket.on("login", function(res){
	console.log(res);
	if (res.success){
		$("ul.chat").append($("<li>").html("<span style='color: green'><strong>Logged in successfully!</strong></span>"));
		$("ul.chat").append($("<li>").html("<span style='color: green'>For command help, type /help.</span>"));
		logged_in = true;
		socket.emit("chat_login");
	} else {
		$("ul.chat").append($("<li>").html("<span style='color: red'><strong>Invalid login!</strong></span>"));
	}
	chat.scrollTop(50000);
});

socket.on("clients", function(clients){
	$("div.total").html("<strong>" + clients.total + " users connected</strong>");
	console.log(clients);
	$("ul.full_list").html("");
	clients.list.forEach(function(client){
		$("ul.full_list").append($("<li/>").html(client));
	});
});

socket.on("nick", function(user, nick){
	$("ul.chat").append($("<li>").html("<span style='color: #00B4CC'><strong>" + user + " is now called " + nick + "!</strong></span>"));
	chat.scrollTop(50000);
});

socket.on("chat", function(data){
	console.log(data);
	var message = data.green ? "<span style='color: green;'>" + data.message + "</span>" : data.message;
	message = urlify(message);
	chat.append($("<li>").html("<strong>" + data.username + ":</strong> " + message).attr("title", data.timestamp));
	chat.scrollTop(50000);
});

socket.on("chat_login", function(who){
	$("ul.chat").append($("<li>").html("<span style='color: #FF9D00'><strong>" + who + " has logged in!</strong></span>"));
	chat.scrollTop(50000);
});

socket.on("chat_logout", function(who){
	if (who != ""){
		$("ul.chat").append($("<li>").html("<span style='color: #FF9D00'><strong>" + who + " has logged out!</strong></span>"));
		chat.scrollTop(50000);
	}
});

socket.on("command_notstaff", function(){
	$("ul.chat").append($("<li>").html("<span style='color: red'><strong>You are not staff!</strong> Nice try.</span>"));
	chat.scrollTop(50000);
});

socket.on("clear", function(){
	$("ul.chat").html("");
});

socket.on("play", function(){
	player.play();
});

socket.on("pause", function(){
	var interval = setInterval(function(){
		if (player){
			player.pause();
			clearInterval(interval);
		}
	}, 500);
});

socket.on("seek", function(time){
	if (player){
		player.seek(time);
	}
});

socket.on("stop", function(){
	if (player){
		player.stop();
	}
});

socket.on("duration", function(){
	var interval = setInterval(function(){
		if (player){
			if (player.getDuration() > 0){
				socket.emit("duration", player.getDuration());
				clearInterval(interval);
			} else if (player.getLoadStatus() == 1){
				console.log("test");
				socket.emit("duration", 10);
				clearInterval(interval);
			}
		}
	}, 500);
});

socket.on("add", function(id, type, title){
	switch (type){
		case "YouTube":
			player = new YouTubePlayer(id, true, title);
			break;
		case "GoogleDrive":
			player = new GoogleDrivePlayer(id, true, title);
			break;
		case "RAW":
			player = new RAWPlayer(id, true, title);
			break;
	}
	if (title != ""){
		setTitle(title);
	}
});

socket.on("sync", function(time){
	console.log("Sync update: " + time);
	if (time > 0 && player){
		if (player.getTime() - time > 4 || time - player.getTime() > 4){
			player.seek(time);
			player.play();
		}
	}
});

socket.on("bg", function(wall){
	$("html").css("background", "url('img/" + wall + ".png') no-repeat center center fixed");
});

socket.on("reset", function(){
	setTitle("");
	$("#player").replaceWith($("<object/>").attr("id", "player"));
	resize_player("#player");
});

window.onresize = function(event){
	resize_player("#player");
	$("ul.chat").height($(window).height() - 102);
}

$("div.list").hover(function(){
	$("ul.full_list").show();
}, function(){
	$("ul.full_list").hide();
});

$(document).ready(function(){
	$("ul.chat").height($(window).height() - 102);
	setTimeout(function(){
		var secs = 1.5 * 1000;
		$("div.title").animate({
			opacity: 1
		}, secs);
		$("div.control").animate({
			opacity: 1
		}, secs);
		$("div.wrapper").animate({
			opacity: 1
		}, secs);
	}, 1250);
	resize_player("#player");
});

function urlify(text, callback){
	var regex = /(https?:\/\/[^\s]+)/g;
	return text.replace(regex, function(url){
		if (/\.(jpg|gif|png)$/.test(url)){
			return "<br /><img src='" + url + "' />";
		} else {
			return "<a href='" + url + "'>" + url + "</a>";
		}
	});
}