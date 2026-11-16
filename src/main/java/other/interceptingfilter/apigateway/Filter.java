package other.interceptingfilter.apigateway;

/**
 * One processing step that runs before the target.
 * A filter either passes the request on with {@code chain.proceed(request)}
 * or stops it by returning a response itself.
 */
public interface Filter {

    String doFilter(Request request, FilterChain chain);
}
