all: clean

clean:
	mvn clean

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn package

docker-build: build
	docker build -t spring-cashier .
	docker images

cashier-up:
	docker-compose up -d cashier

compose-down:
	docker-compose down 