#!/bin/bash
java -jar lolock-server.war \
    --server.port=3000 "$@"
