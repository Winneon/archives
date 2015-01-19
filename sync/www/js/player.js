function resize_player(player){
	var video = $(player);
	video.attr("width", $("div.videowrap").width());
	video.attr("height", $("div.videowrap").height());
}

function release_title(time){
	setTimeout(function(){
		if ($("div.videowrap:hover").length == 0){
			if ($("div.videotitle > span").html() != "Error loading file!"){
				$("div.videotitle").animate({
					opacity: 0
				}, 250);
			}
		}
	}, time);
}

function setTitle(title){
	var video_title = $("div.videotitle");
	var video_wrap = $("div.videowrap");
	video_wrap.unbind("mouseenter mouseleave");
	if (title == ""){
		video_wrap.css("background-color", "rgba(0, 0, 0, 0.75)");
		video_title.css("opacity", "1").css("background-color", "transparent");
		video_title.html("<span style='font-weight: 800;'>Nothing Playing</span>");
	} else if (title == "Error loading file!"){
		video_wrap.css("background-color", "rgba(0, 0, 0, 0.75)");
		video_title.css("opacity", "1").css("background-color", "transparent");
		video_title.html("<span style='font-weight: 800;'>" + title + "</span>");
		$("#player").replaceWith($("<object/>").attr("id", "player"));
	} else {
		video_wrap.css("background-color", "black");
		video_title.css("background-color", "rgba(0, 0, 0, 0.75)");
		video_title.html("<span style='font-weight: 800;'>Currently Playing:</span> " + title);
		$("div.videowrap").hover(function(){
			video_title.animate({
				opacity: 1
			}, 250);
		}, function(){
			release_title(250);
		});
		release_title(5000);
	}
}

function YouTubePlayer(id, autoplay, title){
	var self = this;
	
	self.init = function(id){
		var auto = autoplay ? 1 : 0;
		$("#player").replaceWith($("<object/>").attr("id", "player"));
		self.player = new YT.Player("player", {
			videoId: id,
			width: $("div.videowrap").width(),
			height: $("div.videowrap").height(),
			playerVars: {
				autoplay: auto,
				showinfo: 0
			},
			events: {
				onReady: function(){
					resize_player("#player");
				}
			}
		});
		self.loaded = 0;
		if (title == ""){
			$.ajax({
				url: "https://www.googleapis.com/youtube/v3/videos?key=" + config.key + "&id=" + id + "&part=snippet",
				dataType: "json",
				success: function(data){
					setTitle(data.items[0].snippet.title);
				}
			});
		}
	}
	
	self.pause = function(){
		if (self.player && self.player.pauseVideo){
			self.player.pauseVideo();
		}
	}
	
	self.play = function(){
		if (self.player && self.player.playVideo){
			self.player.playVideo();
		}
	}

	self.stop = function(){
		if (self.player && self.player.stopVideo){
			self.player.stopVideo();
		}
	}
	
	self.getPlayer = function(){
		return self.player;
	}
	
	self.getTime = function(){
		return self.player && self.player.getCurrentTime ? self.player.getCurrentTime() : -1;
	}
	
	self.getDuration = function(){
		return self.player && self.player.getDuration ? self.player.getDuration() : -1;
	}
	
	self.getLoadStatus = function(){
		return self.loaded;
	}
	
	self.seek = function(time){
		if (self.player && self.player.seekTo){
			self.player.seekTo(time, true);
		}
	}
	
	self.init(id, autoplay);
}

function GoogleDrivePlayer(id, autoplay, title){
	var self = this;
	
	self.init = function(id){
		var auto = autoplay ? "&autoplay=1" : "";
		var object = {
			allowfullscreen: "true",
			allowscriptaccess: "always",
			data: "https://video.google.com/get_player?el=leaf&cc_load_policy=1&enablejsapi=1",
			type: "application/x-shockwave-flash",
			wmode: "opaque",
			id: "player"
		};
		var params = [{
			name: "allowFullScreen",
			value: "true"
		},
		{
			name: "allowScriptAccess",
			value: "always"
		},
		{
			name: "opaque",
			value: "opaque"
		},
		{
			name: "flashvars",
			value: "status=ok&hl=ru&allow_embed=0&ps=docs&partnerid=30" + auto + "&docid=" + id + "&abd=0&public=false&el=leaf&showinfo=0"
		}];
		
		self.video_id = id;
		self.paused = false;
		self.player = $("<object/>", object)[0];
		self.loaded = 0;
		
		$(self.player).attr("data", object.data);
		resize_player(self.player);
		
		params.forEach(function(param){
			$("<param/>", param).appendTo(self.player);
		});
		
		$("#player").replaceWith(self.player);
		if (title == ""){
			$.ajax({
				url: "https://www.googleapis.com/drive/v2/files/" + id + "?key=" + config.key,
				dataType: "json",
				success: function(data){
					setTitle(data.title);
				}
			});
		}
	}
	
	self.pause = function(){
		if (self.player && self.player.pauseVideo){
			self.player.pauseVideo();
		}
	}
	
	self.play = function(){
		if (self.player && self.player.playVideo){
			self.player.playVideo();
		}
	}

	self.stop = function(){
		if (self.player && self.player.stopVideo){
			self.player.stopVideo();
		}
	}
	
	self.getPlayer = function(){
		return self.player;
	}
	
	self.getTime = function(){
		return self.player && self.player.getCurrentTime ? self.player.getCurrentTime() : -1;
	}
	
	self.getDuration = function(){
		return self.player && self.player.getDuration ? self.player.getDuration() : -1;
	}
	
	self.getLoadStatus = function(){
		return self.loaded;
	}
	
	self.seek = function(time){
		if (self.player && self.player.seekTo){
			self.player.seekTo(time, true);
		}
	}
	
	self.init(id, autoplay);
}

function RAWPlayer(id, autoplay, title){
	var self = this;
	
	self.init = function(id){
		var object = {
			id: "player",
			controls: "controls"
		};
		
		var source = {
			src: id,
			type: "video/mp4"
		};
		
		self.video_id = id;
		self.paused = false;
		self.player = $("<video/>", object)[0];
		self.loaded = 0;
		
		if (autoplay){
			$(self.player).attr("autoplay", "autoplay");
		}
		
		resize_player(self.player);
		var source = $("<source/>", source);
		
		source.bind("error", function(event){
			self.loaded = 1;
			setTitle("Error loading file!");
		}).appendTo(self.player);
		
		$("#player").replaceWith(self.player);
		if (title == ""){
			setTitle(id);
		}
	}
	
	self.pause = function(){
		if (self.player && self.player.pause){
			self.player.pause();
		}
	}
	
	self.play = function(){
		if (self.player && self.player.play){
			self.player.play();
		}
	}

	self.stop = function(){
		if (self.player && self.player.pause){
			self.player.pause();
		}
	}
	
	self.getPlayer = function(){
		return self.player;
	}
	
	self.getTime = function(){
		return self.player && self.player.currentTime ? self.player.currentTime : -1;
	}
	
	self.getDuration = function(){
		return self.player && self.player.duration ? self.player.duration : -1;
	}
	
	self.getLoadStatus = function(){
		return self.loaded;
	}
	
	self.seek = function(time){
		if (self.player && self.player.currentTime){
			self.player.currentTime = time;
		}
	}
	
	self.init(id, autoplay);
}