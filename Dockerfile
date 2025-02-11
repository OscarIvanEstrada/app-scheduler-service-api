FROM openjdk:11.0-jdk-slim-stretch
ARG DEPENDENCY=target
COPY ${DEPENDENCY}/app-scheduler-service-api-1.0.0-SNAPSHOT.jar /home/app-scheduler-service-api-1.0.0-SNAPSHOT.jar
RUN echo $ENVIROMENTS
ENTRYPOINT   [ "java","-jar","-Dspring.profiles.active=release","/home/app-scheduler-service-api-1.0.0-SNAPSHOT.jar" ] 


#DONE: Add relevant documentation like curls of the services
#Here are some example curl commands for the services:
#
#Add a new job:
#curl -X POST -H "Content-Type: application/json" -d '{"jobName":"testJob","groupName":"testGroup","service":"testService","cronExpression":"0 0/1 * 1/1 * ? *","description":"Test Job"}' http://localhost:8080/scheduler/addJob
#
#Get all jobs:
#curl http://localhost:8080/scheduler/getJobs
#
#Start a job:
#curl http://localhost:8080/scheduler/startJob?groupName=testGroup&jobName=testJob
#
#Pause a job:
#curl http://localhost:8080/scheduler/pauseJob?groupName=testGroup&jobName=testJob
#
#Resume a job:
#curl http://localhost:8080/scheduler/resumeJob?groupName=testGroup&jobName=testJob
#
#Delete a job:
#curl http://localhost:8080/scheduler/deleteJob?groupName=testGroup&jobName=testJob
#
#Update a job:
#curl http://localhost:8080/scheduler/updateJob?groupName=testGroup&jobName=testJob&cronExpression=0 0/2 * 1/1 * ? *&service=testService
#
#Get job state:
#curl http://localhost:8080/scheduler/jobState?groupName=testGroup&jobName=testJob
#
#Check job name:
#curl http://localhost:8080/scheduler/checkJobName?groupName=testGroup&jobName=testJob
#
#Is job running:
#curl http://localhost:8080/scheduler/isJobRunning?groupName=testGroup&jobName=testJob
#
#Stop a job:
#curl http://localhost:8080/scheduler/stopJob?groupName=testGroup&jobName=testJob

#DONE: application.yml variable documentation
#spring.application.name: The name of the application.
#spring.application.version: The version of the application.
#server.port: The port the application runs on.
#spring.datasource.url: The URL of the H2 database.
#spring.datasource.driverClassName: The driver class name for the H2 database.
#spring.datasource.username: The username for the H2 database.
#spring.datasource.password: The password for the H2 database.
#spring.jpa.database-platform: The database platform for JPA.
#spring.jpa.hibernate.ddl-auto: The DDL auto mode for Hibernate (create-drop, update, etc.).
#quartz.properties.org.quartz.scheduler.instanceName: The name of the Quartz scheduler instance.
#quartz.properties.org.quartz.threadPool.threadCount: The number of threads in the Quartz thread pool.
#quartz.properties.org.quartz.jobStore.class: The class of the Quartz job store.
#quartz.properties.org.quartz.jobStore.driverDelegateClass: The driver delegate class for the Quartz job store.
#
#

#run this command to deploy on GCP
gcloud app deploy

#First deploy you must be rename schema_rename_to_first_deploy.sql to schema.sql
