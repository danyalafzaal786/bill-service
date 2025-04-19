# 🧾 Billing Service (Currency Conversion Service)

A Spring Boot microservice to calculate the final bill after applying discounts and converting currency based on real-time exchange rates.

---

## 📦 Features

- Calculates percentage and flat discounts based on user type and purchase.
- Converts currency using a third-party API (`https://v6.exchangerate-api.com/v6/`).
- Basic authentication via Spring Security.
- Caching with `@Cacheable` for exchange rate calls.
- Unit tests with mocking for all components.
- Maven build with code quality tools and test coverage.
- SonarQube integration-ready.

---

## 🔧 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Web
- Spring Test
- Maven
- Jacoco (code coverage)
- SonarQube (code quality)
- ExchangeRate-API

---

## 🚀 How to Run

```bash
# Clone the repo
git clone https://github.com/danyalafzaal786/bill-service.git
cd bill-service

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

---

## 🌍 API Endpoint

The service exposes the following API endpoint to calculate the final payable amount based on the provided bill details:

| Method    | Path          | Description   |
|-----------|--------------------------|----------|
| POST      | /api/calculate | Returns discounted & converted bill  |

### Sample Request
The request should be sent as a JSON payload with the following structure:
```json
{
  "items": [
    {
      "name": "item1",
      "price": 450,
      "category": "GROCERY"
    },
    {
      "name": "item2",
      "price": 1050,
      "category": "NON_GROCERY"
    }
  ],
  "userType": "EMPLOYEE",
  "customerTenure": 1,
  "originalCurrency": "USD",
  "targetCurrency": "PKR"
}
```
### Sample Response
```json
{
    "totalBill": 1500,
    "totalDiscount": 390.0,
    "conversionRate": 280.8215,
    "totalPayableBillInTargetCurrency": 311711.86500,
    "targetCurrency": "PKR"
}
```

---

## ✅ Run Tests
### Unit Tests
To run the unit tests, execute the following command:
```bash
mvn test
```
### Run with Coverage:
To generate test coverage reports using Jacoco, use the following:
```bash
mvn clean verify
```
or
```bash
mvn jacoco:report
```
### Generated Jacoco Report:
Once the tests are completed, a report will be generated in `target/site/jacoco/`. Open the `index.html` file to view the code coverage summary.
```bash
open target/site/jacoco/index.html  # On macOS or Linux
start target/site/jacoco/index.html  # On Windows
```

---

## 🧼 Code Quality
To run static code analysis and check for code style issues, use the following command:
```bash
# Run static analysis (Checkstyle)
mvn checkstyle:check
```
(Optional) To run a SonarQube scan for code quality, run:
```bash
# Run SonarQube scan (make sure SonarQube is running locally)
mvn sonar:sonar \
  -Dsonar.projectKey=billing-service \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=sonarqube-token
```

---

## 🔗 External Integration
* Currency conversion via [ExchangeRate-API](https://www.exchangerate-api.com/)
* Add your API key to application.properties:
```
currency.exchange.api.key=your-api-key
```