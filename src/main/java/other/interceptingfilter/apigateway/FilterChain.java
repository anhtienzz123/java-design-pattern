package other.interceptingfilter.apigateway;

import java.util.List;

/** Runs the filters one by one, then calls the target. */
public class FilterChain {

    private final List<Filter> filters;
    private final Target target;
    private int index = 0;

    public FilterChain(List<Filter> filters, Target target) {
        this.filters = filters;
        this.target = target;
    }

    public String proceed(Request request) {
        if (index < filters.size()) {
            Filter next = filters.get(index++);
            return next.doFilter(request, this);
        }
        return target.handle(request);
    }
}
