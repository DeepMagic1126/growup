docker 部署

docker pull apache/seata-server

快速开始
docker run -p 8091:8091 -p 7091:7091 --name=seata-server --restart=always  -d apache/seata-server:latest

指定配置开始
$ docker run --name seata-server \
-p 8091:8091 \
-p 7091:7091 \
-e SEATA_CONFIG_NAME=file:/root/seata-config/registry \
-v /PATH/TO/CONFIG_FILE:/root/seata-config  \
apache/seata-server

指定ip启动
$ docker run --name seata-server \
-p 8091:8091 \
-p 7091:7091 \
-e SEATA_IP=192.168.1.1 \
apache/seata-server
