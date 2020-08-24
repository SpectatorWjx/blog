#!/bin/sh
cd /home/blog/jar 
export ADMIN=blog-admin-0.0.1.jar 
export ADMIN_port=8083 
## 停止admin
P_ID=`lsof -i:$ADMIN_port|grep "LISTEN"|awk '{print $2}'`
if [ "$P_ID" == "" ]; then 
	echo "===ADMIN process not exists or stop success" 
else 
	kill -9 $P_ID 
	echo "ADMIN killed success" 
fi 
## 启动admin
echo "--------admin 开始启动--------------" 

nohup java -jar -Xms512m -Xmx1024m $ADMIN --spring.profiles.active=test & 
ADMIN_pid=`lsof -i:$ADMIN_port|grep "LISTEN"|awk '{print $2}'` 
until [ -n "$ADMIN_pid" ] 
	do 
	  ADMIN_pid=`lsof -i:$ADMIN_port|grep "LISTEN"|awk '{print $2}'`  
	done 
echo "ADMIN pid is $ADMIN_pid" 
echo "--------admin 启动成功--------------" 