/* $Name:  $ */
/* $Id: editor_plugin.js,v 1.2 2010/10/27 19:24:58 ajokela Exp $ */
(function(){tinymce.create("tinymce.plugins.Print",{init:function(a,b){a.addCommand("mcePrint",function(){a.getWin().print()});a.addButton("print",{title:"print.print_desc",cmd:"mcePrint"})},getInfo:function(){return{longname:"Print",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/print",version:tinymce.majorVersion+"."+tinymce.minorVersion}}});tinymce.PluginManager.add("print",tinymce.plugins.Print)})();
