package chainofresponsbility;

abstract class BaseHandler implements RequestHandler {
    protected RequestHandler next;

    @Override
    public void setNext(RequestHandler next) {
        this.next = next;
    }

    protected void forward(Request request) {
        if (next != null) {
            next.handle(request);
        }
    }
}

class AuthenticateHandler extends BaseHandler {
    @Override
    public void handle(Request request) {
        if (request.user == null) {
            System.out.println("AuthHandler: ❌ User not authenticated.");
            return;
        }

        System.out.println("AuthHandler: ✅ Authenticated.");
        forward(request);
    }
}

class AuthorizationHandler extends BaseHandler {
    @Override
    public void handle(Request request) {
        if (!"ADMIN".equalsIgnoreCase(request.userRole)) {
            System.out.println("AuthorizationHandler: ❌ Access denied.");
            return;
        }

        System.out.println("AuthorizationHandler: ✅ Authorized.");
        forward(request);
    }
}

class RateLimitHandler extends BaseHandler {
    @Override
    public void handle(Request request) {
        if (request.requestCount >= 100) {
            System.out.println("RateLimitHandler: ❌ Rate limit exceeded.");
            return;
        }

        System.out.println("RateLimitHandler: ✅ Within rate limit.");
        forward(request);
    }
}

class ValidationHandler extends BaseHandler {
    @Override
    public void handle(Request request) {
        if (request.payload == null || request.payload.trim().isEmpty()) {
            System.out.println("ValidationHandler: ❌ Invalid payload.");
            return;
        }

        System.out.println("ValidationHandler: ✅ Payload valid.");
        forward(request);
    }
}

class BusinessLogicHandler extends BaseHandler {
    @Override
    public void handle(Request request) {
        System.out.println("BusinessLogicHandler: 🚀 Processing request...");
    }
}
