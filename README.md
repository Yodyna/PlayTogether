# PlayTogether

## Mysql without Docker

### login as root
  $ mysql -u root -p
### create database
  $ create database playtogether
### change spring.datasource.url in application-dev.properties on localhost ip

## Mysql with Docker

### install docker on Windows 10
    https://docs.docker.com/v17.09/docker-for-windows/install/
### run 'Docker Quickstart Terminal'  
### check docker-machine ip
  $ docker-machine ip
### change spring.datasource.url in application-dev.properties on docker-machine ip
	
### run mysql 
  $ cd docker
  $ docker-compose up --build
 
### run mysql in docker
  $ docker exec -it mysql mysql -u root -p
		
## docker containers
### list running docker containers
  $ docker ps
### list running and stopped docker containers
  $ docker ps -a
### stop all running docker containers
  $ docker stop $(docker ps -a -q)
### remove all docker containers
  $ docker rm $(docker ps -a -q)
	
## docker images
### list all docker images
  $ docker images
### remove unused and old docker images
  $ docker rmi $(docker images --filter "dangling=true" -q --no-trunc)
### remove all docker images
  $ docker rmi -f $(docker images -aq)	
