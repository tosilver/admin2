#!/bin/sh
#if [ $RUN_ENV ];then
#    echo "RUN_EVN = $RUN_ENV"
#else
#    echo "RUN_EVN not exists"
#fi
[ -z "$RUN_ENV" ] && RUN_ENV=dev
echo "RUN_EVN is $RUN_ENV"
#mvn clean install -e -U -Dmaven.test.skip=true -P${RUN_ENV}
#echo '************************** install *************************'

#!/bin/sh
git checkout master
git fetch --all
git reset --hard origin/master
git pull
echo '********** update success **********'


#更新&编译
#git pull && mvn clean package -e -Dmaven.test.skip=true -Pdoc
