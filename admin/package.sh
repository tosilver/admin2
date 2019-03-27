#!/bin/sh
echo '更新代码...' && git pull \
  && echo '打包代码...' && mvn clean package -e -Ppro -Dmaven.test.skip=true