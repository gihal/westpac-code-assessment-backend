# westpac-code-assessment
Gihal Westpac Interview assessment - Backend

Savings Account API

A Spring Boot REST API for creating and retrieving savings bank accounts.

Requirements

The API supports:

* Create a new savings account.
* Retrieve a savings account.
* Auto-generate the account ID and account number.
* Customer name is mandatory.
* Account nickname is optional and must be between 5 and 30 characters when provided.
* Account nickname must be checked against a list of offensive terms.
* A customer cannot have more than five accounts.
* Account details are stored in PostgreSQL using a single table.

API

POST /api/v1/savings-accounts
GET /api/v1/savings-accounts/{accountNumber}

The API contract will be defined using OpenAPI.

Technology

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Bean Validation
* PostgreSQL
* Flyway
* Maven
* JUnit 5 / Mockito

Assumptions

* The requirements do not provide a customer ID. For this assessment, customerName is used to identify a customer when enforcing the five-account limit.
* accountNumber is used to retrieve an account.
* Nickname validation is case-insensitive.
* A local list of offensive terms will be used for nickname validation.
* The solution intentionally uses a single account table as required by the assessment.
* Authentication and authorization are outside the scope of this assessment.

TODO / Future Improvements

If additional time were available, the following would be considered:

* Redis caching for account retrieval.
* API rate limiting.
* Authentication and authorization.
* API circuit braker.
* PostgreSQL Testcontainers integration tests.
* Authentication and authorization.
* Idempotency for account creation.
* Stronger concurrency handling for the five-account limit.
* Stable customer ID and separate customer lifecycle management.
* Additional observability and operational monitoring.


Running the Application

Setup and execution instructions will be added as the implementation progresses.
