var config;

$.ajax({
	url: "config/default.json",
	async: false,
	dataType: "json",
	success: function(data){
		config = data;
	}
});