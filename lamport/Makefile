# GNU Makefile
JAR=/usr/local/jdk1.8.0_131/bin/jar
JAVA=/usr/local/jdk1.8.0_131/bin/java
JAVAC=/usr/local/jdk1.8.0_131/bin/javac

JFLAGS = -g
.SUFFIXES: .java .class
.java.class:
	$(JAVAC) $(JFLAGS) $*.java

# default: Event LamportClock Main Request

# Event: Event.java
# 	$(JAVAC) $(JFLAGS) Event.java

# WSRecepcaoServerImpl: MYC/WSRecepcaoServerImpl.java
#   $(JAVAC) $(JFLAGS) MYC/WSRecepcaoServerImpl.java

CLASSES = \
	Event.java\
	LamportClock.java\
	Main.java
	Request.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -f *.class