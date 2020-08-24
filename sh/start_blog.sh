#!/bin/sh
cd /home/blog/jar 
export BLOG=blog-blog-0.0.1.jar 
export BLOG_port=8080 
## 停止blog
P_ID=`lsof -i:$BLOG_port|grep "LISTEN"|awk '{print $2}'`
if [ "$P_ID" == "" ]; then 
	echo "===BLOG process not exists or stop success" 
else 
	kill -9 $P_ID 
	echo "BLOG killed success" 
fi 
## 启动blog
echo "--------blog 开始启动--------------" 

nohup java -jar -Xms512m -Xmx1024m $BLOG --spring.profiles.active=test & 
BLOG_pid=`lsof -i:$BLOG_port|grep "LISTEN"|awk '{print $2}'` 
until [ -n "$BLOG_pid" ] 
	do 
	  BLOG_pid=`lsof -i:$BLOG_port|grep "LISTEN"|awk '{print $2}'`  
	done 
echo "BLOG pid is $BLOG_pid" 
echo "--------blog 启动成功--------------" 