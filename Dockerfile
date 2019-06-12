#---------------------------------------------------------#
#                                                         #
# Stage 1: Build Image                                    #
#                                                         #
#---------------------------------------------------------#
FROM maven:3.5.2-jdk-9 AS builder
COPY . /usr/src/app/
WORKDIR /usr/src/app/  
RUN mvn clean package -DskipTests -Djavax.net.ssl.trustStorePassword=changeit

#---------------------------------------------------------#
#                                                         #
# Stage 2: Create Container Executable                    #
#                                                         #
#---------------------------------------------------------#
FROM openjdk:9
WORKDIR /root/
COPY --from=builder /usr/src/app/target/app.jar . 
EXPOSE 6030
ENTRYPOINT ["java", "-jar", "./app.jar"]
