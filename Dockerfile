# Base image with JDK 17
FROM eclipse-temurin:17-jdk

WORKDIR /tests

# Install Maven and browsers
USER root
RUN apt-get update && \
    apt-get install -y maven wget unzip gnupg software-properties-common curl && \
    # Install Chrome
    wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list' && \
    apt-get update && apt-get install -y google-chrome-stable

# Copy project
COPY . /tests

# Build & run tests
CMD ["sh", "-c", "mvn clean install && mvn test"]
