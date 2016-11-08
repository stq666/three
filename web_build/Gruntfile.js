module.exports = function (grunt) {
    'use strict';
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-concat');
    var userConfig = require('./build.config.js');
    var taskConfig = {
        clean: {
            options: { force: true },
            js:['<%=js_dir%>/common.js','<%=js_dir%>/module.js','<%=js_dir%>/moduleMain.js','<%=index%>/index.html'],
            css:['<%=css_dir%>/common.css'],
            admin_js:['<%=admin_js_dir%>/common.js','<%=admin_js_dir%>/module.js','<%=admin_js_dir%>/moduleMain.js','<%=admin_index%>/index.html'],
            admin_css:['<%=admin_css_dir%>/common.css']
        },
        copy: {
            main: {
                files: [
                    {
                        src: '<%= orig_dir %>/angular/angular.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-advanced-searchbox/dist/angular-advanced-searchbox-tpls.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap-switch/dist/js/bootstrap-switch.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular/dist/angular-local-storage.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-route/angular-route.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-ui-router/release/angular-ui-router.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-cookies/angular-cookies.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-resource/angular-resource.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-animate/angular-animate.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap/dist/js/bootstrap.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/jquery/dist/jquery.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-file-upload/angular-file-upload.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/es5-shim/es5-sham.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/es5-shim/es5-shim.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-bootstrap/ui-bootstrap.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-bootstrap/ui-bootstrap-tpls.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/ng-table/dist/ng-table.min.js',
                        dest: '<%= js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap/fonts/*',
                        dest: '<%= font_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap/dist/css/bootstrap.min.css',
                        dest: '<%= css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-advanced-searchbox/dist/angular-advanced-searchbox.min.css',
                        dest: '<%= css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap/dist/css/bootstrap-theme.min.css',
                        dest: '<%= css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular/angular-csp.css',
                        dest: '<%= css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/ng-table/dist/ng-table.min.css',
                        dest: '<%= css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css',
                        dest: '<%= css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    }
                ]
            },
            admin_rpc: {
                files: [
                    {
                        src: '<%= orig_dir %>/angular/angular.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-advanced-searchbox/dist/angular-advanced-searchbox-tpls.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap-switch/dist/js/bootstrap-switch.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular/dist/angular-local-storage.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-route/angular-route.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-ui-router/release/angular-ui-router.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-cookies/angular-cookies.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-resource/angular-resource.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-animate/angular-animate.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap/dist/js/bootstrap.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/jquery/dist/jquery.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-file-upload/angular-file-upload.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/es5-shim/es5-sham.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/es5-shim/es5-shim.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-bootstrap/ui-bootstrap.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-bootstrap/ui-bootstrap-tpls.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/ng-table/dist/ng-table.min.js',
                        dest: '<%= admin_js_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap/fonts/*',
                        dest: '<%= admin_font_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap/dist/css/bootstrap.min.css',
                        dest: '<%= admin_css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular-advanced-searchbox/dist/angular-advanced-searchbox.min.css',
                        dest: '<%= admin_css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap/dist/css/bootstrap-theme.min.css',
                        dest: '<%= admin_css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/angular/angular-csp.css',
                        dest: '<%= admin_css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/ng-table/dist/ng-table.min.css',
                        dest: '<%= admin_css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    },
                    {
                        src: '<%= orig_dir %>/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css',
                        dest: '<%= admin_css_dir %>/',
                        expand: true,
                        flatten: true,
                        filter: 'isFile'
                    }
                ]
            }

        },
        concat: {
            common_js: {
                src: ['<%=js_dir%>/jquery.min.js','<%=js_dir%>/angular.min.js','<%=js_dir%>/angular-route.min.js','<%=js_dir%>/angular-ui-router.min.js','<%=js_dir%>/angular-cookies.min.js'
                    ,'<%=js_dir%>/angular-resource.min.js','<%=js_dir%>/angular-animate.min.js','<%=js_dir%>/es5-shim.min.js','<%=js_dir%>/es5-sham.min.js'
                    ,'<%=js_dir%>/console-sham.min.js','<%=js_dir%>/angular-file-upload.min.js','<%=js_dir%>/bootstrap.min.js','<%=js_dir%>/md5.js'
                    ,'<%=js_dir%>/ui-bootstrap.min.js','<%=js_dir%>/ui-bootstrap-tpls.min.js','<%=js_dir%>/ng-table.min.js','<%=js_dir%>/pagination.js','<%=js_dir%>/slider.js'
                    ,'<%=js_dir%>/jquery-ui.min.js','<%=js_dir%>/angular-local-storage.min.js'],
                dest: '<%=js_dir%>/common.js'
            },
            common_css: {
                src: ['<%=css_dir%>/*'],
                dest: '<%=css_dir%>/common.css'
            },
            admin_common_css: {
                src: ['<%=admin_css_dir%>/*'],
                dest: '<%=admin_css_dir%>/common.css'
            },
            moduleMain_js:{
                src:['<%=module_js%>/csHttp.js','<%=module_js%>/rpcServices.js','<%=module_js%>/security.js','<%=module_js%>/organization.js','<%=module_js%>/system.js','<%=module_js%>/demo.js','<%=module_js%>/performance.js','<%=module_js%>/member.js','<%=module_js%>/app.js'],
                dest:'<%=js_dir%>/moduleMain.js'
            },
            admin_moduleMain_js:{
                src:['<%=admin_module_js%>/csHttp.js','<%=admin_module_js%>/rpcServices.js','<%=admin_module_js%>/security.js','<%=admin_module_js%>/member.js','<%=admin_module_js%>/app.js'],
                dest:'<%=admin_js_dir%>/moduleMain.js'
            },
            module_js:{
                src:['<%=module_js%>/*/*/*.js','<%=module_js%>/*/*.js'],
                dest:'<%=js_dir%>/module.js'
            },
            admin_module_js:{
                src:['<%=admin_module_js%>/*/*/*.js','<%=admin_module_js%>/*/*.js'],
                dest:'<%=admin_js_dir%>/module.js'
            }
        },
        pkg: grunt.file.readJSON("package.json")
    };

    grunt.initConfig(grunt.util._.extend(taskConfig, userConfig));
    grunt.registerTask('update', ["copy:main"]);
    grunt.registerTask('build_dev',function(){
        var html=grunt.file.read("../web_rec/src/main/webapp/base.html",null);
        grunt.file.recurse('../web_rec/src/main/webapp/module/.', function(abspath, rootdir, subdir, filename){
            if(filename.indexOf("Ctrl")>=0){
                html+="<script type='text/javascript' src='module/"+subdir+"/"+filename+"'></script>";
            }
        });
        html+="</body></html>";
        grunt.file.write('../web_rec/src/main/webapp/index.html', html,null);
    });
    grunt.registerTask('build_adminDev',function(){
        var html=grunt.file.read("../web_admin/src/main/webapp/base.html",null);
        grunt.file.recurse('../web_admin/src/main/webapp/module/.', function(abspath, rootdir, subdir, filename){
            if(filename.indexOf("Ctrl")>=0){
                html+="<script type='text/javascript' src='module/"+subdir+"/"+filename+"'></script>";
            }
        });
        html+="</body></html>";
        grunt.file.write('../web_admin/src/main/webapp/index.html', html,null);
    });
    grunt.registerTask("build_product",'build js and css',['clean:js','clean:css','concat:common_js','concat:common_css','concat:moduleMain_js','concat:module_js']);
    grunt.registerTask("build",'build js and css',['clean:js','clean:css','concat:common_css','concat:moduleMain_js','build_dev']);
    grunt.registerTask("builda_product",'build js and css',['clean:admin_js','clean:admin_css','copy:admin_rpc','concat:admin_common_js','concat:admin_common_css','concat:admin_moduleMain_js','concat:admin_module_js']);
    grunt.registerTask("builda",'build js and css',['clean:admin_js','clean:admin_css','copy:admin_rpc','concat:admin_common_css','concat:admin_moduleMain_js','build_adminDev']);

};
