FROM jboss/wildfly:21.0.1.Final

WORKDIR /opt/jboss/wildfly

COPY ./target/CourseMgmtMs-1.0.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh","-c","standalone-full.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
