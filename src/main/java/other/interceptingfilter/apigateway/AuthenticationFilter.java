package other.interceptingfilter.apigateway;

/** Stops the request if the token is wrong; otherwise passes it on. */
public class AuthenticationFilter implements Filter {

    @Override
    public String doFilter(Request request, FilterChain chain) {
        if (!"secret".equals(request.token())) {
            System.out.println("  [Auth] invalid token -> stop");
            return "401 Unauthorized";
        }
        System.out.println("  [Auth] token ok");
        return chain.proceed(request);
    }
}
