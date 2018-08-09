#!/bin/bash
PID=$(cat ./shutdown.pid)
kill $PID
echo $PID was killed