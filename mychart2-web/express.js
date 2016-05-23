var LOG = console;

var express = require('express');
var app = express();
var server;

app.get('*', function(req, res, next) {
	if ((req.path.indexOf('/rest') != -1) && (!req.path.endsWith('.json'))) {
		LOG.info('GET', req.originalUrl);
		res.redirect(req.path + '.json');
	} else {
		next();
	}
});

app.post('*', function(req, res) {
	LOG.info('POST', req.originalUrl, req.body || '');
	res.status('201').header('Location', req.path + '/1').end();
});

app.put('*', function(req, res) {
	LOG.info('PUT', req.originalUrl, req.body || '');
	res.status('204').end();
});

app.delete('*', function(req, res) {
	LOG.info('DELETE', req.originalUrl, req.body || '');
	res.status('204').end();
});

this.start = function(basePath, context, port) {
	app.use(require('connect-livereload')());
	app.use(require('body-parser').json());
	app.use(context, express.static(basePath));
	server = app.listen(port);
};

this.stop = function() {
	server.close();
};
