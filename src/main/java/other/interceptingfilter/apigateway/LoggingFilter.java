package other.interceptingfilter.apigateway;

/** Logs the request before it goes on, and the response on the way back. */
public class LoggingFilter implements Filter {

    @Override
    public String doFilter(Request request, FilterChain chain) {
        System.out.println("  [Log]  --> " + request.path());
        String response = chain.proceed(request);
        System.out.println("  [Log]  <-- " + response);
        return response;
    }
}
