var express = require("express");
var app = express();
var http = require("http").Server(app);
var sock = require("socket.io");
var io = sock(http);

var KEY = "connect.sid";
var SECRET = "One three two five";

var commands = [
	"/clear - Clears chat.",
	"/help - Shows the help menu.",
	"/login &lt;username&gt; - Login to Sync."
];
var staff_commands = [
	"/add &lt;song&gt; [bg] - Add a video (Replaces the current video.)",
	"/op &lt;user&gt; - Op a user.",
	"/deop &lt;user&gt; - De-op a user.",
	"/play - Play the current video.",
	"/pause - Pause the current video.",
	"/seek &lt;time&gt; - Seek to a specifc time.",
	"/stop - Stops the current video."
];

var utils = require("./utils");
var ips = {};
var history = {};
var temp_bg = "";

var current_time = 0;
var current_interval = null;
var current_id = "";
var current_title = "";
var current_type = "";
var current_duration = 0;
var current_bg = "bg";

app.use(express.static(__dirname + "/www"));

io.on("connection", function(socket){
	socket.ip = socket.request.socket.remoteAddress;
	console.log("A user has connected! IP Address: " + socket.ip);
	socket.user = "";
	if (ips[socket.ip]){
		var response = {
			success: true
		};
		socket.emit("login", response);
		socket.user = ips[socket.ip];
		history[socket.user][0] = 0;
		update_clients();
		console.log("The user " + ips[socket.ip] + " has logged in!");
	}
	socket.join("default");
	update_clients();
	if (current_interval){
		socket.emit("add", current_id, current_type, current_title);
		if (current_interval == "paused"){
			setTimeout(function(){
				socket.emit("pause");
			}, 5000);
		}
	}
	if (current_bg != "bg"){
		socket.emit("bg", current_bg);
	}
	socket.on("disconnect", function(){
		socket.leave("default");
		io.emit("chat_logout", socket.user);
		update_clients();
		ips[socket.ip] = socket.user;
		console.log((socket.user == "" ? "A user" : socket.user) + " has disconnected!");
	});
	socket.on("chat", function(data){
		console.log("[" + socket.user + "] " + data.message);
		data.username = socket.nick ? socket.nick : socket.user;
		data.timestamp = utils.timestamp();
		if (data.message.indexOf(">") == 0){
			data.green = true;
		}
		io.emit("chat", data);
	});
	socket.on("duration", function(time){
		current_duration = time;
	});
	socket.on("chat_login", function(){
		io.emit("chat_login", socket.user);
	});
	socket.on("history", function(message){
		if (history[socket.user]){
			history[socket.user].push(message);
			history[socket.user][0] = 0;
		}
	});
	socket.on("history_up", function(){
		if (history[socket.user]){
			var his = history[socket.user];
			his[0] += 1;
			var message = his[his.length - his[0]];
			if (message && message != his[0]){
				socket.emit("history", message);
			} else {
				his[0] = his.length - 1;
			}
		}
	});
	socket.on("history_down", function(){
		if (history[socket.user]){
			var his = history[socket.user];
			his[0] -= 1;
			var message = his[his.length - his[0]];
			if (his[0] >= 0){
				socket.emit("history", message);
			} else {
				his[0] = 0;
			}
		}
	});
	socket.on("command", function(command){
		var user = socket.user;
		if (command.indexOf("/login") == -1){
			console.log((user == "" ? "Anonymous" : user) + " ran command: " + command);
		}
		command = command.substring(1, command.length);
		var args = command.split(" ");
		try {
			switch (args[0]){
				case "help":
					utils.staff(user, socket, function(){
						socket.emit("help", commands.concat(staff_commands));
					}, function(){
						socket.emit("help", commands);
					});
					break;
				case "login":
					utils.login(args[1], args[2], function(result){
						var worked = result.length > 0;
						var response = {
							success: worked
						};
						socket.emit("login", response);
						if (worked){
							socket.user = args[1];
							history[socket.user] = [0];
							update_clients();
							console.log("The user " + args[1] + " has logged in!");
						}
					});
					break;
				case "nick":
					socket.nick = "";
					for (var i = 1; i < args.length; i++){
						socket.nick += " " + args[i];
					}
					io.emit("nick", socket.user, socket.nick);
					update_clients();
					break;
				case "unnick":
					socket.nick = null;
					io.emit("nick", socket.user, socket.user);
					update_clients();
					break;
				case "dw":
				case "sg":
				case "bsg":
					temp_bg = args[0];
				case "add":
					utils.staff(user, socket, function(){
						var cont = false;
						if (args[1].indexOf("youtube.com/watch") > -1){
							current_type = "YouTube";
							current_id = args[1].split("v=")[1];
							var pos = current_id.indexOf("&");
							if (pos > -1){
								current_id = current_id.substring(0, pos);
							}
							cont = true;
						} else if (args[1].indexOf("/file/d/") > -1){
							current_type = "GoogleDrive";
							current_id = args[1].substring(args[1].indexOf("/file/d/"), args[1].length);
							current_id = current_id.replace("/file/d/", "");
							current_id = current_id.substring(0, current_id.indexOf("/"));
							cont = true;
						} else if (args[1].indexOf(".mp4", args[1].length - ".mp4".length) > -1){
							current_type = "RAW";
							current_id = args[1];
							cont = true;
						}
						current_title = "";
						if (args.length > 2){
							for (var i = 2; i < args.length; i++){
								current_title += (" " + args[i]);
							}
							current_title = current_title.replace(" ", "");
						}
						if (cont){
							io.emit("add", current_id, current_type, current_title);
							if (current_interval){
								clearInterval(current_interval);
							}
							current_time = 0;
							current_interval = setInterval(sync, 3000);
							if (current_bg != "bg"){
								current_bg = "bg";
								temp_bg = "";
								io.emit("bg", current_bg);
							}
							if (temp_bg != ""){
								current_bg = temp_bg;
								io.emit("bg", current_bg);
							}
							socket.emit("duration");
						}
					});
					break;
				case "op":
					utils.staff(user, socket, function(){
						utils.op(args[1]);
					});
					break;
				case "deop":
					utils.staff(user, socket, function(){
						utils.deop(args[1]);
					});
					break;
				case "play":
					utils.staff(user, socket, function(){
						io.emit("play");
						if (current_interval){
							clearInterval(current_interval);
						}
						current_interval = setInterval(sync, 3000);
					});
					break;
				case "pause":
					utils.staff(user, socket, function(){
						io.emit("pause");
						if (current_interval){
							clearInterval(current_interval);
							current_interval = "paused";
						}
					});
					break;
				case "clear":
					socket.emit("clear");
					break;
				case "seek":
					utils.staff(user, socket, function(){
						if (args[1].indexOf(":") > -1){
							var split = args[1].split(":");
							var seconds = (parseInt(split[0]) * 60) + parseInt(split[1]);
							io.emit("seek", seconds);
							current_time = seconds;
						}
					});
					break;
				case "stop":
					utils.staff(user, socket, function(){
						if (current_interval){
							clearInterval(current_interval);
						}
						current_interval = null;
						io.emit("stop");
						io.emit("reset");
						if (current_bg != "bg"){
							current_bg = "bg";
							temp_bg = "";
							io.emit("bg", current_bg);
						}
					});
					break;
			}
		} catch (error){
			console.log(socket.user + " has triggered an error!");
		}
	});
});

http.listen(8080, function(){
	console.log("Listening on port 8080.");
});

function update_clients(){
	var clients = io.sockets.adapter.rooms["default"];
	var obj = {
		total: Object.keys(clients).length,
		list: []
	};
	getClients().forEach(function(client){
		if (client.user != ""){
			obj.list.push(client.nick ? client.nick + " (" + client.user + ")" : client.user);
		}
	});
	io.emit("clients", obj);
}

function sync(){
	if (current_duration && current_duration != 0 && current_time >= current_duration){
		clearInterval(current_interval);
		current_interval = null;
		io.emit("reset", current_bg != "");
		if (current_bg != "bg"){
			current_bg = "bg";
			io.emit("bg", current_bg);
		}
	}
	io.emit("sync", current_time);
	current_time += 3;
}

function getClients(){
	var res = [];
	var room = io.sockets.adapter.rooms["default"];
	if (room){
		for (var id in room){
			res.push(io.sockets.adapter.nsp.connected[id]);
		}
	}
	return res;
}