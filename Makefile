#variable for java compiler

JCC = javac

CLASSES = Account.java AccountVault.java CharacterList.java CharacterNode.java EncoderDecoder.java

CLASSPATH = /home/kylej/Documents/Kyle/Dev/Arkadiusz2.0/ejml/ejml-simple-0.37.1.jar:/home/kylej/Documents/Kyle/Dev/Arkadiusz2.0/ejml/ejml-core-0.37.1.jar:/home/kylej/Documents/Kyle/Dev/Arkadiusz2.0/ejml/ejml-ddense-0.37.1.jar:/home/kylej/Documents/Kyle/Dev/Arkadiusz2.0/commons-lang3-3.8.1/commons-lang3-3.8.1.jar:.

#variable for flags
#-g compiles with debugging information
#-cp defines classpath 

JFLAGS = -g -cp $(CLASSPATH)

default: ArkadiuszMain.class Account.class AccountVault.class CharacterList.class CharacterNode.class EncoderDecoder.class

ArkadiuszMain.class: ArkadiuszMain.java
	$(JCC) $(JFLAGS) ArkadiuszMain.java $(CLASSES)
   
Account.class: Account.java
	$(JCC) $(JFLAGS) Account.java $(CLASSES)
   
AccountVault.class: AccountVault.java
	$(JCC) $(JFLAGS) AccountVault.java $(CLASSES)
   
CharacterList.class: CharacterList.java
	$(JCC) $(FLAGS) CharacterList.java $(CLASSES)
   
CharacterNode.class: CharacterNode.java
	$(JCC) $(JFLAGS) CharacterNode.java $(CLASSES)
   
EncoderDecoder.class: EncoderDecoder.java
	$(JCC) $(JFLAGS) EncoderDecoder.java $(CLASSES)
   
# To start over from scratch, type 'make clean'.
# Removes all .class files, so that the next make rebuilds them
#
clean: 
	$(RM) *.class
