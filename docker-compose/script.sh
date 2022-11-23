#!/bin/sh

COUNT=$(cat ./docker-compose.yml | grep -c "Путь_до_папки")
if [ $COUNT -gt 0 ]; then
    echo "Замените Путь_до_папки в docker-compose.yml на ваш путь"
    exit 1
fi
docker-compose -f ./docker-compose.yml up -d
