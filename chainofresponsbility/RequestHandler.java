package chainofresponsbility;

interface RequestHandler {
    void setNext(RequestHandler next);

    void handle(Request request);
}
