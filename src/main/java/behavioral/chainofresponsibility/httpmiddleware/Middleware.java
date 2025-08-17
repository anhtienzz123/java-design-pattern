package behavioral.chainofresponsibility.httpmiddleware;

public abstract class Middleware {
    protected Middleware next;

    public void setNext(Middleware next) {
        this.next = next;
    }

    public abstract void handle(HttpRequest request, HttpResponse response);

    protected void callNext(HttpRequest request, HttpResponse response) {
        if (next != null && !response.isProcessed()) {
            next.handle(request, response);
        }
    }
}