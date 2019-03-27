# VERSION 0.0.1
FROM tomcat:7
# 签名
MAINTAINER YK "yk0025@hotmail.com"

#ENV TIME_ZONE Asiz/Shanghai
#设置时区
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
 && echo 'Asia/Shanghai' > /etc/timezone && date \
# 更改端口
# && sed -i 's/8080/8090/g' `grep 8080 -rl ./conf` \
# && sed -i 's/8009/8091/g' `grep 8009 -rl ./conf` \
# && sed -i 's/8005/8092/g' `grep 8005 -rl ./conf` \
# 删除不必要文件
 && rm -rf webapps/*
ADD target/*.war webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]