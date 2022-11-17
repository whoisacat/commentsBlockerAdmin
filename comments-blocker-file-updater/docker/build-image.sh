#!/bin/sh
cd ..
docker build -f docker/Dockerfile -t whoisacat/ura_comments_blocker_updater:test .
docker push whoisacat/ura_comments_blocker_updater:test
