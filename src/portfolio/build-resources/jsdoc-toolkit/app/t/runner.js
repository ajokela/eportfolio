/* $Name:  $ */
/* $Id: runner.js,v 1.2 2010/10/27 19:24:56 ajokela Exp $ */
// try: java -jar ../../jsrun.jar runner.js

load("TestDoc.js");

TestDoc.prove("../frame/Opt.js");
TestDoc.prove("../lib/JSDOC.js");
TestDoc.prove("../frame/String.js");
TestDoc.prove("../lib/JSDOC/DocTag.js");
TestDoc.prove("../lib/JSDOC/DocComment.js");
TestDoc.prove("../lib/JSDOC/TokenReader.js");
TestDoc.prove("../lib/JSDOC/Symbol.js");

TestDoc.report();
