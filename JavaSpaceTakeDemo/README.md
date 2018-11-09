This is a demo of the "take" method in the JavaSpace05 specification.

Written by Dr Gary Allen, University of Huddersfield.

The "take" method will accept a collection of templates (rather than a single template) and return a collection of objects that match them (rather than a single object).

As usual, if using an IDE like eclipse or IntelliJ you need to create a java project with the correct classes added as libraries, then paste the code in and run it.  You will also need to edit the run configuration to pass the correct VM arguments.

If running from a command line, set up the classpath with:

	. jinicl

then compile with

	javac *.java

and run with:

	java -Djava.security.policy=policy.all -Djava.rmi.server.useCodebaseOnly=false TakeDemo


Look at the code, which has extensive comments, to see what is happening.


