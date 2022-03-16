FROM openjdk:8
COPY ./out/production/visable-messaging-system/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java","visable"]

FROM postgres:9.3
ENV POSTGRES_USER docker
ENV POSTGRES_PASSWORD docker
ENV POSTGRES_DB docker
ADD userTblCreate.sql /docker-entrypoint-initdb.d/
ADD messageTblCreate.sql /docker-entrypoint-initdb.d/