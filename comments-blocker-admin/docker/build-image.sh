#!/bin/sh
cd ..
docker build -f docker/Dockerfile --platform linux/amd64 -t whoisacat/ura_comments_blocker_admin:appletest .
docker push whoisacat/ura_comments_blocker_admin:appletest
