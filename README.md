# Parse Log file containing HTTP Requests

### Bash script

```
mvn spring-boot:run
```

### Open web browser to see swagger file

`localhost:8080/swagger-ui.html`

### Endpoints

1. The number of unique IP addresses `localhost:8080/uniqueIPAddresses`
2. The top 3 most visited URLs `localhost:8080/topThreeVisitedUrls`
3. The top 3 most active IP addresses `localhost:8080/topThreeMostActiveIPAddresses`


### Junits

`mvn test`

