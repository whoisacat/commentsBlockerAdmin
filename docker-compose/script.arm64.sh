#!/bin/sh

COUNT=$(cat ./docker-compose.arm64.yml | grep -c "Путь_до_папки_c_заблокированными_ip")
if [ $COUNT -gt 0 ]; then
    echo "Замените Путь_до_папки_c_заблокированными_ip в docker-compose.yml на ваш путь"
    exit 1
fi
COUNT=$(cat ./docker-compose.arm64.yml | grep -c "Путь_до_конфига")
if [ $COUNT -gt 0 ]; then
    echo "Замените Путь_до_конфига prometheus в docker-compose.yml на ваш путь"
    exit 1
fi
docker-compose -p comments_blocker_by_ip_admin -f ./docker-compose.arm64.yml up -d
