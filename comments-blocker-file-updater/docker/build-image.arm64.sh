#!/bin/sh
cd ..
docker build -f docker/Dockerfile -t whoisacat/ura_comments_blocker_updater:prometheus_test.arm64 .
docker push whoisacat/ura_comments_blocker_updater:prometheus_test.arm64
