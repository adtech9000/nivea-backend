FROM openjdk:oracle
RUN rm -f /etc/localtime
RUN ln -s /usr/share/zoneinfo/Africa/Lagos /etc/localtime
COPY target/*.jar /opt/app.jar
EXPOSE 8080
CMD ["java", "-Xms256m", "-Xms256m", "-jar", "/opt/app.jar"]