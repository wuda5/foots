# 指定基础镜像
FROM openjdk:8-jdk-alpine
# 维护者信息
MAINTAINER dbg
# 用于指定持久化目录
VOLUME /tmp
# 将本地文件添加到容器中
ADD /target/gmis-offlinepay-server.jar gmis-offlinepay-server.jar
# 指定于外界交互的端口
EXPOSE 8782
# 配置容器，使其可执行化
ENTRYPOINT exec java $JAVA_OPTS -jar /gmis-offlinepay-server.jar