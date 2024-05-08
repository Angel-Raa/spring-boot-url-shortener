FROM ubuntu:latest
LABEL authors="=angelaguero"

ENTRYPOINT ["top", "-b"]