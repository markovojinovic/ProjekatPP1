# makkefile begins

# Java compiler command
JC = javac

# Java virtual machine command
JVM = java

# Java source files
SRCS = Compiler.java Run.java

# Class files
CLASSES = $(SRCS:.java=.class)

# Default target
all: Compiler Run

# Target for compiling Java source files
Compiler: rs.ac.bg.etf.pp1.Compiler.class

# Target for running the "Compiler" program
Run: rs.etf.pp1.mj.runtime.Run.class
	$(JVM) Run test/program.obj

# Rule for compiling Java source files
%.class: %.java
	$(JC) $<

# Clean target to remove compiled class files
clean:
	rm -f $(CLASSES)