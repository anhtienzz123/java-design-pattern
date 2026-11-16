package other.interceptingfilter.apigateway;

import java.util.ArrayList;
import java.util.List;

/** Holds the configured filters and the target. Clients send every request through here. */
public class FilterManager {

    private final List<Filter> filters = new ArrayList<>();
    private final Target target;

    public FilterManager(Target target) {
        this.target = target;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public String execute(Request request) {
        // A new chain per request, so each request starts from the first filter.
        return new FilterChain(filters, target).proceed(request);
    }
}
