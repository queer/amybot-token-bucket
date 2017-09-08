FROM openjdk:8-jre

ENTRYPOINT ["/usr/bin/java", "-Xmx1G", "-jar", "/usr/share/amybot/token-bucket.jar"]

ADD target/token-bucket-*.jar /usr/share/amybot/token-bucket.jar
