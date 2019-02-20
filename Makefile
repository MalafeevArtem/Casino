.DEFAULT_GOAL := build-run

build-run: build run

run:
	java -jar ./target/Casino-1.0-SNAPSHOT-jar-with-dependencies.jar

clean:
	./mvnw clean package

build: clean
	jar cfe ./target/Casino.jar games.Slot -C ./target/classes .

update:
	./mvnw versions:update-properties versions:display-plugin-updates
