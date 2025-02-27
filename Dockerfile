# 1️⃣ 빌드 환경: Gradle + JDK 21
FROM gradle:8.4-jdk21 AS builder

# 2️⃣ 작업 디렉토리 설정
WORKDIR /app

# 3️⃣ 프로젝트 파일 복사 및 종속성 캐싱
COPY . .

# 4️⃣ 빌드 실행 (Gradle Wrapper 사용)
RUN ./gradlew clean build -x test

# 5️⃣ 런타임 환경: OpenJDK 21
FROM openjdk:21-slim

# 6️⃣ 작업 디렉토리 설정
WORKDIR /app

# 7️⃣ 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 8️⃣ 컨테이너에서 실행할 명령어
CMD ["java", "-jar", "app.jar"]
