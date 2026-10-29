build: build-server build-web
	@echo 'Done.'

dev: build-server build-web
	@echo 'Watching...'

run-server: build-server
	java -jar ./bin/cuik.jar

watch-web:
	vite build cuik-web/ -w --emptyOutDir

build-server: clean-server bin
	gradle -p cuik-server build
	gradle -p cuik-server shadowJar
	mv cuik-server/cuik.jar ./bin/

build-web:
	vite build cuik-web/ --emptyOutDir

clean-server:
	gradle -p cuik-server clean

.docker-pull:
	docker pull mysql:latest
	touch .docker-pull

tag:
	docker build -t cuik-server:$(version) cuik-server

db-client:
	docker exec -it cuik-foods-db-1 mysql -u root -p

db: .docker-pull
	docker compose down && docker compose up -d

clean-db:
	docker compose down
	rm -rf .docker-pull

bin:
	@mkdir -p bin

clean: clean-server clean-db
	rm -rf bin
