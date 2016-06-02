var gulp = require("gulp");
var express = require("./express.js");

var LOG = console;

var server = {
	port : 8080,
	livereloadPort : 35729,
	basePath : './src/main/webapp/',
	context : '/mychart',
	_lr : null,

	start : function() {
		express.start(this.basePath, this.context, this.port);
	},

	stop : function() {
		express.stop();
	},

	livereload : function() {
		this._lr = require('tiny-lr')();
		this._lr.listen(this.livereloadPort);
	},

	livestart : function() {
		this.start();
		this.livereload();
	},

	notify : function(event) {
		var fileName = require("path").relative(this.basePath, event.path);
		LOG.info('Alteração detectada em', fileName);

		this._lr.changed({
			body : {
				files : [ fileName ]
			}
		});
	}
};

gulp.task('start-server', function() {
	server.livestart();
});

var ts = require('gulp-type');
var tsProject = ts.createProject({
	declarationFiles : true,
	noExternalResolve : true
});

gulp.task('compile-ts', function() {
	var tsResult = gulp.src('src/main/typescript/app/**/*.ts').pipe(ts(tsProject));
	tsResult.dts.pipe(gulp.dest(server.basePath + '/app/definitions'));
	return tsResult.js.pipe(gulp.dest(server.basePath + '/app'));
});

gulp.task('watch-ts', [ 'compile-ts' ], function() {
	gulp.watch('src/main/typescript/app/**/*.ts', [ 'compile-ts' ]);
});

gulp.task('watch-files', [ 'watch-ts', 'start-server' ], function() {
	gulp.watch([ './*.js', server.basePath + '/*.html', server.basePath + '/app/*.js', server.basePath + '/app/**/*.html',
			server.basePath + '/app/**/*.js', server.basePath + '/app/**/*.css', 'src/test/stub/**/*.json' ], function(event) {
		server.notify(event);
	});
});

gulp.task('listen-stdin', [ 'start-server' ], function() {
	require('keypress')(process.stdin);
	process.stdin.on('keypress', function(ch, key) {
		if (key && key.ctrl && key.name == 'c') {
			process.stdin.pause();
			gulp.run('shutdown', function() {
				process.exit();
			});
		}
	});

	process.stdin.setRawMode(true);
	process.stdin.resume();
});

gulp.task('stub-api', function() {
	return gulp.src('src/test/stub/**/*.json')/*
												 * .pipe(require("gulp-rename")(function(path) { path.extname = "" }))
												 */.pipe(gulp.dest(server.basePath + '/rest/'));
});

gulp.task('shutdown', function() {
	server.stop();

	return gulp.src('src/main/webapp/rest/', {
		read : false
	}).pipe(require('gulp-clean')());
});

gulp.task('default', [ 'watch-files', 'listen-stdin', 'stub-api' ], function() {
	LOG.info('Servidor iniciado: http://localhost:' + server.port + server.context);
});
