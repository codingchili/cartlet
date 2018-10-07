FROM anapsix/alpine-java:latest

MAINTAINER codingchili@github

# make sure to run 'gradlew jar' before building the container.
# example: 'gradlew jar && docker build .'

RUN mkdir -p /opt/webshop/
COPY build/libs/webshoppe.jar /opt/webshop/
COPY database.sql /opt/webshop/

RUN apk add mysql && \
    apk add mysql-client && \
    mkdir -p /run/mysqld/ && \
    mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql && \
    cd /opt/webshop

EXPOSE 8080/tcp


ENTRYPOINT ["/bin/sh", "-c", "echo 'Starting database..' && nohup mysqld --user=root > /dev/null 2>&1 & sleep 3 && cd /opt/webshop && mysql -e 'create database webshop' && mysql webshop < database.sql && java -jar ./webshoppe.jar"]