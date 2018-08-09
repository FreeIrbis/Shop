#!/bin/bash
java -XX:+HeapDumpOnOutOfMemoryError -jar shop_server-0.1.0.jar -Xms512m -Xmx1G &
