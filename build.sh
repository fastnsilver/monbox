#!/usr/bin/env bash

set -e

SCRIPT_DIR=$PWD

# Build the project and docker images
echo "Building monbox..."
touch $SCRIPT_DIR/.dockerize;
mvn clean install