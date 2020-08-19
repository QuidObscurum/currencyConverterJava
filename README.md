# Currency Converter in Java
## Requirements
Apache Maven 3.6.3

Java version: 1.8

MySQL Server version >= 8.0.17

## How to run
Clone the repo.

Install dependencies with maven.

Create MySQL database ```bankdb```. Review the scripts in ```src/main/resources/db/migration``` before launching the application.
The database table will be populated by Flyway on startup thanks to Spring Boot integration.

Replace database username and password in ```src/main/resources/hibernate.properties``` with valid data.

Run Spring Boot Application ```CurrencyConverter```.

## How to test
Apart from running the test suite in the project source code, you can go to Postman
and issue a POST request to `127.0.0.1:8080/convert` with JSON body:

`{
	"fromCurrencyValue" : 1000,
	"fromCurrency": "BYN",
	"toCurrency": "EUR"
}`
.

Or run the following command from your terminal:

```
curl -i -H "Accept: application/json" -H "Content-Type:application/json" \ 

-X POST --data '{"fromCurrencyValue": 1000, "fromCurrency": "BYN", "toCurrency": "EUR"}' "http://localhost:8080/convert"
```
