#!/usr/bin/env bash

set -e

# Build the project and docker images
mvn clean package

# Remove existing containers
docker-compose stop
docker-compose rm -f

docker-compose up -d

docker-compose logs