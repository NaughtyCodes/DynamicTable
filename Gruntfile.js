module.exports = function(grunt) {

   grunt.initConfig({
      //pkg: grunt.file.readJSON('package.json'),
      concat: {
         options: {
            separator: ';'
         },
         dist: {
            src: ['bower_components/angular/angular.js','bower_components/jquery/dist/jquery.js'],
            dest: 'dist/all.js'
         }
      },
     uglify: {
        options: {
         banner: '/*! Angular::jquery:: <%= grunt.template.today() %> */\n'
      },
      dist: {
         files: {
            'dist/all.min.js': ['<%= concat.dist.dest %>']
         }
      }
   },
//   watch: {
//      files: ['<%= jshint.files %>'],
//      tasks: ['jshint']
//   }

   });

   grunt.loadNpmTasks('grunt-contrib-uglify');
//   grunt.loadNpmTasks('grunt-contrib-jshint');
//   grunt.loadNpmTasks('grunt-contrib-watch');
   grunt.loadNpmTasks('grunt-contrib-concat');

   grunt.registerTask('default', ['concat','uglify']);

};