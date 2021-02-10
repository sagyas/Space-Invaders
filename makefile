# 311247464
# assorsa
compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt
	rm sources.txt
run:
	java -cp biuoop-1.4.jar:bin:resources Ass7Game 
jar:
	jar cfm space-invaders.jar Manifest.mf -C bin . -C resources .
bin:
	mkdir bin