package other.fluentinterface.querybuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Fluent Interface (Query Builder)
public class QueryBuilder {
	private String table;
	private List<String> columns = new ArrayList<>();
	private List<String> conditions = new ArrayList<>();
	private String orderBy;
	private boolean isAscending = true;

	// Private constructor to enforce factory method usage
	private QueryBuilder() {
	}

	// Factory method to start the fluent chain
	public static QueryBuilder fromTable(String table) {
		QueryBuilder builder = new QueryBuilder();
		builder.table = table;
		return builder;
	}

	// Chaining method: Select columns
	public QueryBuilder select(String... columns) {
		this.columns.addAll(Arrays.asList(columns));
		return this;
	}

	// Chaining method: Add a condition
	public QueryBuilder where(String condition) {
		this.conditions.add(condition);
		return this;
	}

	// Chaining method: Specify order by
	public QueryBuilder orderBy(String column) {
		this.orderBy = column;
		return this;
	}

	// Chaining method: Set ascending/descending order
	public QueryBuilder ascending() {
		this.isAscending = true;
		return this;
	}

	public QueryBuilder descending() {
		this.isAscending = false;
		return this;
	}

	// Terminal method: Build the query
	public String build() {
		StringBuilder query = new StringBuilder("SELECT ");
		if (columns.isEmpty()) {
			query.append("*");
		} else {
			query.append(String.join(", ", columns));
		}
		query.append(" FROM ").append(table);
		if (!conditions.isEmpty()) {
			query.append(" WHERE ").append(String.join(" AND ", conditions));
		}
		if (orderBy != null) {
			query.append(" ORDER BY ").append(orderBy).append(isAscending ? " ASC" : " DESC");
		}
		return query.toString();
	}
}
