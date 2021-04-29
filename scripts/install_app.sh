sudo killall -9 java
cd /home/Seminario/codedeploy
sudo chmod 765 treschat-0.0.1-SNAPSHOT.jar
nohup java -jar treschat-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &