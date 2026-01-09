# 1단계: 빌드 환경 (JDK 17 기준)
FROM eclipse-temurin:17-jdk-focal
# JAR 파일을 컨테이너 안으로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
# 실행 명령 (위에서 배운 환경 변수 주입 구조)
ENTRYPOINT ["java", "-jar", "/app.jar"]