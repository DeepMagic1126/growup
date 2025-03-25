docker 部署 rocketMq
拉取镜像
docker pull apache/rocketmq
拉取web管理镜像
docker pull styletang/rocketmq-console-ng


创建docker网络
docker network create rocketmq-network

运行nameServer
docker run -d --name rmqnamesrv --network rocketmq-network -e "JAVA_OPT_EXT=-Xms512m -Xmx512m -Xmn256m" -p 9876:9876 apache/rocketmq sh mqnamesrv
运行broker
docker run -d --name rmqbroker --network rocketmq-network -e "NAMESRV_ADDR=rmqnamesrv:9876" -e "JAVA_OPT_EXT=-Xms512m -Xmx512m -Xmn256m" -p 10911:10911 -p 10909:10909 apache/rocketmq sh mqbroker -c /home/rocketmq/rocketmq-5.3.1/conf/broker.conf
docker run -d --name rmqbroker --network rocketmq-network -e "NAMESRV_ADDR=rmqnamesrv:9876" -e "JAVA_OPT_EXT=-Xms512m -Xmx512m -Xmn256m" -p 10911:10911 -p 10909:10909 apache/rocketmq sh mqbroker 
运行RocketMQ Console 这里的镜像不支持macos的环境需要自己重新打包部署镜像
docker run -d --name rocketmq-dashboard --network rocketmq-network -e "JAVA_OPTS=-Drocketmq.namesrv.addr=rmqnamesrv:9876" -p 8080:8080 custom-rocketmq-dashboad
