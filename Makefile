build: build-server
	@echo 'Done.'

run-server: build-server
	java -jar ./bin/cuik.jar

build-server: clean-server bin
	gradle -p cuik-server build
	gradle -p cuik-server shadowJar
	mv cuik-server/cuik.jar ./bin/

clean-server:
	gradle -p cuik-server clean

.docker-pull:
	docker pull lscr.io/linuxserver/mariadb:latest
	touch .docker-pull

tag:
	docker build -t cuik-server:$(version) cuik-server

db: .docker-pull
	docker run -d \
		--name=mariadb \
		-e PUID=1000 -e PGID=1000 -e TZ=Etc/UTC \
		-e MYSQL_ROOT_PASSWORD="pwd_root" \
		-e MYSQL_USER="user1" \
		-e MYSQL_PASSWORD="pwd1" \
		-p 3306:3306 \
		-v ./.local:/config \
		lscr.io/linuxserver/mariadb:latest

	@sleep 1 # dormimos un cachin para que Docker termine de armar el container
	docker exec -i mariadb mariadb < db.sql

clean-db:
	docker stop mariadb
	docker rm mariadb
	rm -rf .docker-pull .local

bin:
	@mkdir -p bin

# specifically do not touch the db
clean: clean-server
	rm -rf bin
	@echo "[!] Did not clean the db. Run \`make clean-db\` if you need to do so"
