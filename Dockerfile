# baseimage
FROM openjdk:17-slim
# 복사 내꺼 이미지
COPY ./build/libs/*T.jar app.jar
# ENV profiles prod
CMD ["java","-jar","app.jar"]
EXPOSE 8080
# docker build .