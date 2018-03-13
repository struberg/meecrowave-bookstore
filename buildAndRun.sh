#!/bin/sh
mvn clean package && docker build -t meecrowave-bookstore .
docker run -ti --rm -p 8080:8080 -p 8000:8000 -v `pwd`/target/classes:/extra_class_path meecrowave-bookstore
