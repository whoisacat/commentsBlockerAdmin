#!/bin/sh
cd ..
docker build -f docker/Dockerfile --platform linux/amd64 -t whoisacat/ura_comments_blocker_admin:kafka_test .
docker push whoisacat/ura_comments_blocker_admin:kafka_test
