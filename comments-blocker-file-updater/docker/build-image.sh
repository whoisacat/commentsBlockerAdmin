#!/bin/sh
cd ..
docker build -f docker/Dockerfile --platform linux/amd64 -t whoisacat/ura_comments_blocker_updater:separated_front .
docker push whoisacat/ura_comments_blocker_updater:separated_front
