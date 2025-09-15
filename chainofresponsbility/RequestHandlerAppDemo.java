package chainofresponsbility;

public class RequestHandlerAppDemo {
    public static void main(String[] args) {
        // Create handlers
        RequestHandler authenticate = new AuthenticateHandler();
        RequestHandler authorization = new AuthorizationHandler();
        RequestHandler rateLimit = new RateLimitHandler();
        RequestHandler validation = new ValidationHandler();
        RequestHandler businessLogic = new BusinessLogicHandler();

        // Building the chain
        authenticate.setNext(authorization);
        authorization.setNext(rateLimit);
        rateLimit.setNext(validation);
        validation.setNext(businessLogic);

        // Send request through the chain
        Request request = new Request("john", "ADMIN", 10, "{ \"data\": \"valid\" }");
        authenticate.handle(request);

        System.out.println("\n--- Trying an invalid request ---");
        Request badRequest = new Request(null, "USER", 150, "");
        authenticate.handle(badRequest);

        // AuthHandler: ✅ Authenticated.
        // AuthorizationHandler: ✅ Authorized.
        // RateLimitHandler: ✅ Within rate limit.
        // ValidationHandler: ✅ Payload valid.
        // BusinessLogicHandler: 🚀 Processing request...

        // --- Trying an invalid request ---
        // AuthHandler: ❌ User not authenticated.
    }
}
