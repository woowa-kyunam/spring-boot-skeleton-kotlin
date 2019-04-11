# Spring Boot Skeleton

Spring boot skeleton project - kotlin

- 로컬 DB Docker Container
```bash
$ cd skeleton-api/docker/mysql
$ docker build --tag skeleton-mysql .
$ docker run -d -p 3306:3306 skeleton-mysql
```

- 로컬 DB Container + Spring boot App Container
```bash
$ docker-compose -f skeleton-api-compose.yml up -d
```

