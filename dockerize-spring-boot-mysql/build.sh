rm -f target/*.jar
./mvnw clean install -DskipTests

echo "App compiled successfully"