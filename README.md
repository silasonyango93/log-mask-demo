### What does this Project do
- A sample project to demonstrate log-masking.

### Project details
- Run using Java 17.
- Below are the terminal commands to use to run the project

```java
./mvnw clean package

./mvnw -pl books-api-demo spring-boot:run

``` 

### Curl request to create a book.

```java
curl --request POST \
  --url http://localhost:8080/client-api/v1/book-management/books \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/12.3.1' \
  --data '{
  "title": "Effective Java",
  "author": "Joshua Bloch",
  "email": "joshua.bloch@example.com",
  "phoneNumber": "0712345678"
}
'

``` 
