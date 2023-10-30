@echo OFF

javacc Parser0.jj && javac ICLInterpreter.java && java ICLInterpreter

pause

del *.class