FROM postgres:12.14-alpine3.17

COPY init-script.sql /docker-entrypoint-initdb.d/

ENV POSTGRES_USER=twinkle_user
ENV POSTGRES_PASSWORD=trinkle_password