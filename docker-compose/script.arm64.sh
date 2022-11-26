#!/bin/sh

COUNT=$(cat ./docker-compose.yml | grep -c "Путь_до_папки")
if [ $COUNT -gt 0 ]; then
    echo "Замените Путь_до_папки в docker-compose.yml на ваш путь"
    exit 1
fi
docker-compose -p comments_blocker_by_ip_admin -f ./docker-compose.arm64.yml up -d
