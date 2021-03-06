#!/usr/bin/env bash

set -e

# Change directories
cd docker

# Export the active docker machine IP
export DOCKER_IP=$(docker-machine ip $(docker-machine active))

# docker-machine doesn't exist in Linux, assign default ip if it's not set
DOCKER_IP=${DOCKER_IP:-0.0.0.0}

# Start the other containers
docker-compose -f docker-compose.yml up -d

# Attach to the log output of the cluster
docker-compose -f docker-compose.yml logs

# Display status of cluster
docker-compose -f docker-compose.yml ps