#variable for java compiler

JCC = javac

CLASSES = ArkadiuszMain.java Account.java AccountVault.java CharacterList.java CharacterNode.java EncoderDecoder.java

#CLASSPATH = <path to all dependecy .jar files. See documentation for required dependencies>
CLASSPATH = ./dependencies/ejml-simple-0.37.1.jar:./dependencies/commons-lang3-3.8.1/commons-lang3-3.8.1.jar:./dependencies/ejml-core-0.37.1.jar:./dependencies/ejml-ddense-0.37.1.jar
#CLASSPATH = ./dependencies/commons-lang3-3.8.1/commons-lang3-3.8.1.jar:./dependencies/ejml-core-0.37.1.jar:./dependencies/ejml-ddense-0.37.1.jar:./dependencies/ejml-simple-0.37.1.jar

#variable for flags
#-g compiles with debugging information
#-cp defines classpath 

JFLAGS = -g -cp $(CLASSPATH)

default: ArkadiuszMain.class Account.class AccountVault.class CharacterList.class CharacterNode.class EncoderDecoder.class

ArkadiuszMain.class: ArkadiuszMain.java
	$(JCC) $(JFLAGS) $(CLASSES)
   
Account.class: Account.java
	$(JCC) $(JFLAGS) $(CLASSES)
   
AccountVault.class: AccountVault.java
	$(JCC) $(JFLAGS) $(CLASSES)
   
CharacterList.class: CharacterList.java
	$(JCC) $(FLAGS) $(CLASSES)
   
CharacterNode.class: CharacterNode.java
	$(JCC) $(JFLAGS) $(CLASSES)
   
EncoderDecoder.class: EncoderDecoder.java
	$(JCC) $(JFLAGS) $(CLASSES)
   
# To start over from scratch, type 'make clean'.
# Removes all .class files, so that the next make rebuilds them
#
clean: 
	$(RM) *.class
