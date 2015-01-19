var mysql = require("mysql");
var md5 = require("MD5");
var sha1 = require("sha1");
var config = require("config");

console.log_copy = console.log.bind(console);

function handleMySQL(){
	var client = mysql.createConnection({
		user: config.mysql.user,
		password: config.mysql.passwd,
		database: config.mysql.database
	});
	
	client.connect(function(error){
		if (error){
			console.log("Error connecting to the MySQL: " + error);
			setTimeout(handleMySQL, 2000);
		}
	});
	
	client.on("error", function(error){
		console.log("Error in the MySQL database: " + error);
		if (error.code == "PROTOCOL_CONNECTION_LOST"){
			handleMySQL();
		} else {
			throw error;
		}
	});
	
	exports.login = function(user, passwd, callback){
		passwd = passwd + "hiicametoplayonminecraftpls";
		passwd = md5(passwd);
		passwd = sha1(passwd);
		passwd = sha1(passwd);
		passwd = sha1(passwd);
		passwd = md5(passwd);
		passwd = md5(passwd);
		
		client.query("SELECT * FROM users WHERE username = '" + user + "' AND password = '" + passwd + "'", function(error, result){
			callback(result);
		});
	}
	
	exports.staff = function(user, socket, success, fail){
		client.query("SELECT show_staff FROM users WHERE username = '" + user + "'", function(error, result){
			if (result[0].show_staff == "y"){
				success();
			} else if (fail){
				fail();
			} else {
				socket.emit("command_notstaff");
			}
		});
	}
	
	exports.op = function(user){
		client.query("UPDATE users SET show_staff = 'y' WHERE username = '" + user + "'");
	}
	
	exports.deop = function(user){
		client.query("UPDATE users SET show_staff = 'n' WHERE username = '" + user + "'");
	}
	
	exports.mysql = client;
	
}

exports.timestamp = function(){
	var date = new Date().toString();
	var split = date.split(" ");
	var time = split[4] + " " + split[5];
	return time;
}

console.log = function(data){
	this.log_copy("[" + exports.timestamp() + "]:", data);
}

handleMySQL();