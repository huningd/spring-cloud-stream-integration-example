#!/usr/bin/env bash

if [ $# -ne 1 ]
then
    echo "Usage: 'run.sh DIRECTORY'"
    echo "e.g. ./run.sh docker/rabbitmq"
    exit 1
fi

path=$1"docker-compose.yml"
projectName="spring-stream"

set -e

# Build the project and docker images
mvn clean package

# Remove existing containers

docker-compose -p $projectName -f $path stop
docker-compose -p $projectName -f $path rm -f

docker-compose -p $projectName -f $path up -d

docker-compose -p $projectName -f $path logs