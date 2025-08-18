package other.fluentinterface.httprequestbuilder;

// Client code demonstrating HTTP Request Builder with Fluent Interface
public class ZMain {
    public static void main(String[] args) {
        System.out.println("=== HTTP Request Builder - Fluent Interface Pattern Demo ===\n");

        // Example 1: Simple GET request
        HttpRequestBuilder.HttpRequest getRequest = HttpRequestBuilder
                .url("https://api.example.com/users")
                .get()
                .header("Accept", "application/json")
                .timeout(5000)
                .build();

        System.out.println("1. Simple GET Request:");
        System.out.println(getRequest);

        // Example 2: POST request with JSON body
        HttpRequestBuilder.HttpRequest postRequest = HttpRequestBuilder
                .url("https://api.example.com/users")
                .post()
                .contentType("application/json")
                .authorization("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
                .body("{\"name\":\"John Doe\",\"email\":\"john@example.com\"}")
                .timeout(10000)
                .build();

        System.out.println("2. POST Request with JSON:");
        System.out.println(postRequest);

        // Example 3: GET request with query parameters
        HttpRequestBuilder.HttpRequest queryRequest = HttpRequestBuilder
                .url("https://api.example.com/search")
                .get()
                .queryParam("q", "java design patterns")
                .queryParam("limit", "10")
                .queryParam("page", "1")
                .header("User-Agent", "MyApp/1.0")
                .header("Accept", "application/json")
                .build();

        System.out.println("3. GET Request with Query Parameters:");
        System.out.println(queryRequest);

        // Example 4: PUT request for updating resource
        HttpRequestBuilder.HttpRequest putRequest = HttpRequestBuilder
                .url("https://api.example.com/users/123")
                .put()
                .contentType("application/json")
                .authorization("Bearer token123")
                .header("X-API-Version", "v1")
                .body("{\"name\":\"Jane Doe\",\"status\":\"active\"}")
                .timeout(15000)
                .build();

        System.out.println("4. PUT Request for Update:");
        System.out.println(putRequest);

        // Example 5: DELETE request
        HttpRequestBuilder.HttpRequest deleteRequest = HttpRequestBuilder
                .url("https://api.example.com/users/456")
                .delete()
                .authorization("Bearer admin_token")
                .header("X-Confirm-Delete", "true")
                .build();

        System.out.println("5. DELETE Request:");
        System.out.println(deleteRequest);

        // Example 6: Complex request with multiple configurations
        HttpRequestBuilder.HttpRequest complexRequest = HttpRequestBuilder
                .url("https://api.example.com/data/export")
                .post()
                .contentType("application/x-www-form-urlencoded")
                .authorization("Basic dXNlcjpwYXNzd29yZA==")
                .header("Accept", "application/octet-stream")
                .header("X-Export-Format", "csv")
                .queryParam("start_date", "2024-01-01")
                .queryParam("end_date", "2024-12-31")
                .body("format=csv&include_headers=true&delimiter=comma")
                .timeout(30000)
                .build();

        System.out.println("6. Complex Export Request:");
        System.out.println(complexRequest);
    }
}