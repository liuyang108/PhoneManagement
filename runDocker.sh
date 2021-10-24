#!/bin/bash

mvn clean package

docker build -t phone_management .

docker run -p8080:8080 phone_management:latest

