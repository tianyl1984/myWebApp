var http = require("http");
var url = "http://127.0.0.1:8082/dc-framework/example/";
var data = "assure_phone=&captcha_value=&check_passwd=0&login_type=login&new_password=&password=%1B%AA%01%BA%7D%FC%1F%9EQ&password1=tyl123456&read=1&retype_newpassword=&short_message=none&show_assure=none&show_captcha=none&show_change_password=block&show_read=none&show_tip=none&terminal=pc&uri=&username=tianyale";
var options = {
	hostname : '8.8.8.8',
	port : 90,
	path : '/login',
	method : 'POST',
	headers : {
	   "Content-length" : data.length
	}
};

var req = http.request(options, function(res) {
	res.setEncoding('utf8');
	res.on('data', function(chunk) {
		console.log('BODY: ' + chunk);
	});
});

req.on('error', function(e) {
	console.log('problem with request: ' + e.message);
});

req.write(data);
req.end();
