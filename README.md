# Development Activity Dashboard - Backend

This Java Spring Boot REST API queries GitHub endpoints to retrieve user metrics for commits, pull requests, and pull request comments.

## Prerequisites

Ensure you have Java 21 installed and set as your `$JAVA_HOME`

## Running the application

1. Add your [GitHub Authorization Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens) to the `application.properties` `github.bearer.token` property
2. `mvn clean install` to download dependencies and build the service
3. `mvn spring-boot:run` to run the service

## Using the service

### Sample curl requests

- `curl -X GET "http://localhost:8080/commits" -H "dateFrom: 2020-01-01" -H "dateTo: 2024-12-25" -H "repositoryUrl: https://api.github.com/repos/spring-projects/spring-framework"`


- `curl -X GET "http://localhost:8080/pull-requests" -H "dateFrom: 2020-01-01" -H "dateTo: 2024-12-25" -H "repositoryUrl: https://api.github.com/repos/spring-projects/spring-framework"`


- `curl -X GET "http://localhost:8080/pull-request-comments" -H "dateFrom: 2020-01-01" -H "dateTo: 2024-12-25" -H "repositoryUrl: https://api.github.com/repos/spring-projects/spring-framework"`

## Additional Notes

This application is in the alpha phase and therefore the following limitations apply:

1. There is no validation on the GitHub URL
2. The GitHub URL must follow the 'API' format, e.g. https://api.github.com/repos/spring-projects/spring-framework
3. There is no validation on date input fields
4. Only the first 100 results are shown

## Titania Reviewer Notes

Areas for improvement:

1. Input to the REST API is not validated. This should also be done on the UI. But if hitting the API directly we should validate the URL and date formats.
2. It would be a good idea convert URLs in the format https://github.com/spring-projects/spring-framework to https://api.github.com/repos/spring-projects/spring-framework
3. Dates are required, they should be optional.
4. An ExceptionHandler should be added to handle all exceptions that may be thrown from the service.
5. The `Counter` class could potentially use Generics - but it's ok as it currently is for this limited use case.
6. The API is limited to `/commits`, `/pulls`, `/pulls-comments` - more endpoints could be added for better metrics.