docker volume create db-volume

docker network create app-network

docker run --rm -p 3307:3306 -e MYSQL_ROOT_PASSWORD=hello -e MYSQL_PASSWORD=hello -v ./sql-scripts:/docker-entrypoint-initdb.d --name mysqldb --net app-network -d mysql:8.0

docker build -t coursesvcimg -f ./CourseMgmtMs/Dockerfile ./CourseMgmtMs
docker run --rm --name coursesvc -p 8080:8080 -p 9990:9990 -e MYSQL_PASSWORD=hello --net app-network -d coursesvcimg

timeout /t 8 /nobreak

docker exec -it coursesvc /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="jms-queue add --queue-address=enrollQueue --entries=[queue/enrollQueue jms/queue/enrollQueue java:jboss/exported/jms/queue/enrollQueue]"

docker restart coursesvc

docker build -t usersvcimg -f ./UserMgmtMs/Dockerfile ./UserMgmtMs
docker run --rm --name usersvc -p 8085:8085 -e MYSQL_PASSWORD=hello --net app-network -d usersvcimg

docker build -t notifysvcimg -f ./NotifMgmtMs/Dockerfile ./NotifMgmtMs
docker run --rm --name notifysvc -p 8090:8090 -e MYSQL_PASSWORD=hello --net app-network -d notifysvcimg

docker build -t frontsvcimg -f ./frontend-lms/Dockerfile ./frontend-lms
docker run --rm --name frontsvc -p 7000:7000 --net app-network -d frontsvcimg

pause
 