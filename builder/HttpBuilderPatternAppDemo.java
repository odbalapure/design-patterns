package builder;

public class HttpBuilderPatternAppDemo {
    public static void main(String[] args) {
        HttpRequest req1 = new HttpRequest.Builder("https://api.example.com/data").build();
        HttpRequest req2 = new HttpRequest.Builder("https://api.example.com/data").method("POST")
                .body("\"{\"key\":\"value\"}\"").timeout(5000)
                .build();

        // HttpRequest [url=https://api.example.com/data, method=GET, headers={},
        // queryParams={}, body=null, timeout=30000]
        System.out.println(req1);
        // HttpRequest [url=https://api.example.com/data, method=POST, headers={},
        // queryParams={}, body="{"key":"value"}", timeout=5000]
        System.out.println(req2);
    }
}
