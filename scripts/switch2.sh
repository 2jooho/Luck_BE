#!/bin/bash
echo "> 현재 구동중인 Port 확인"
CURRENT_PROFILE=$(curl -s -L http://3.34.36.9/api/health | grep -Eo 'dev[12]')

# Idle Profile 찾기: s1이 사용중이면 s2가 Idle
if [ $CURRENT_PROFILE == dev1 ]
then
  IDLE_PORT=8081
  CURRENT_PORT=8082
elif [ $CURRENT_PROFILE == dev2 ]
then
  IDLE_PORT=8082
  CURRENT_PORT=8081
else
  echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
  echo "> 8081을 할당합니다."
  IDLE_PORT=8081
fi

echo "> 전환할 Port: $IDLE_PORT"
echo "> Port 전환"
echo "set \$service_url http://3.34.36.9:$IDLE_PORT;" |sudo tee /home/ec2-user/service_url.inc
cat /home/ec2-user/service_url.inc

PROXY_PORT=$(curl -s http://3.34.36.9/api/health)
echo "> Nginx Current Proxy Port: $PROXY_PORT"

echo "> Nginx Reload"
sudo service nginx reload

echo "> 이전에 구동중인 애플리케이션 종료"
if [-z  $CURRENT_PORT ]
then
echo "> 현재 구동중인 포트가 없습니다. 종료할 애플리케이션 없음."
else
IDLE_PID=$(sudo lsof -t -i:$CURRENT_PORT)
echo "> lsof 결과: $(sudo lsof -i:$CURRENT_PORT)"
if [ -z $IDLE_PID ]
then
echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
echo "> kill -9 $IDLE_PID"
sudo kill -9 $IDLE_PID
sleep 5
fi
fi
#
#exit 0
