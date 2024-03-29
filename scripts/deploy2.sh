#!/bin/bash
#ABSPATH=$(readlink -f $0)
#ABSDIR=$(dirname $ABSPATH)
sudo chmod +x /home/ec2-user/luck-system-deploy/scripts/deploy2.sh
sudo chmod +x /home/ec2-user/luck-system-deploy/scripts/switch2.sh
BASE_PATH=/home/ec2-user/luck-system-deploy/
BUILD_PATH=$(ls $BASE_PATH/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_PATH)
echo "> build 파일명: $JAR_NAME"


echo "> 현재 구동중인 profile 확인"
CURRENT_PROFILE=$(curl -s http://3.34.36.9/api/health)
CURRENT_PROFILE=$(echo $CURRENT_PROFILE | tr -d '\r')

if [ $CURRENT_PROFILE == dev1 ]
then
   IDLE_PROFILE=dev2
  IDLE_PORT=8082
  CURRENT_PORT=8081
elif [ $CURRENT_PROFILE == dev2 ]
then
  IDLE_PROFILE=dev1
  IDLE_PORT=8081
  CURRENT_PORT=8082
else
  echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
  echo "> dev1을 할당합니다. IDLE_PROFILE: dev1"
  IDLE_PROFILE=dev1
  IDLE_PORT=8081
  CURRENT_PORT=8082
fi

echo "> $IDLE_PROFILE 배포"
sudo fuser -k -n tcp $IDLE_PORT
nohup java -jar -Dserver.port=${IDLE_PORT} /home/ec2-user/luck-system-deploy/build/libs/* --spring.profiles.active=${IDLE_PROFILE} > /home/ec2-user/nohup.out 2>&1 &
#sudo nohup java -jar /home/ec2-user/app/deploy/build/libs/DDAL-GGAK-BE-0.0.1-SNAPSHOT.jar --spring.config.location=file:/home/ubuntu/app/config/prod-application.yaml --spring.profiles.active=$IDLE_PROFILE --server.port=$IDLE_PORT > /dev/null 2>&1 &
#sudo nohup java -jar /home/ec2-user/app/deploy/build/libs/DDAL-GGAK-BE-0.0.1-SNAPSHOT.jar --spring.config.location=file:/home/ubuntu/app/config/application$IDLE_PORT.properties --spring.profiles.active=$IDLE_PROFILE --server.port=$IDLE_PORT > /dev/null 2>&1 &


echo "> $IDLE_PROFILE 10초 후 Health check 시작"
echo "> curl -s -L http://3.34.36.9:$IDLE_PORT/api/health "
sleep 10

for retry_count in {1..10}
do
  response=$(curl -s -o /dev/null -w "%{http_code}"  http://3.34.36.9:${IDLE_PORT}/api/health)

  if [ ${response} -eq 200 ]; then
      echo "> Health check 성공"
      break
  elif [ ${response} -eq 10 ]; then
      echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
      echo "> Health check: ${response}"
  fi

  if [ $retry_count -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done

echo "> 스위칭을 시도합니다..."
sleep 10
/home/ec2-user/luck-system-deploy/scripts/switch2.sh
#${ABSDIR}/switch2.sh