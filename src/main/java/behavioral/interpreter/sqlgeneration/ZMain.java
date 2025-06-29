package behavioral.interpreter.sqlgeneration;

import java.util.Arrays;

/**
 * Main class demonstrating SQL generation using Interpreter pattern Shows
 * different ways to build and execute SQL queries
 */
public class ZMain {
	public static void main(String[] args) {
		System.out.println("=== SQL Generation using Interpreter Pattern ===\n");

		// Setup context with database metadata
		SQLContext context = setupContext();

		// Example 1: Simple SELECT query
		System.out.println("1. Simple SELECT query:");
		demonstrateSimpleSelect(context);

		// Example 2: SELECT with WHERE conditions
		System.out.println("\n2. SELECT with WHERE conditions:");
		demonstrateSelectWithWhere(context);

		// Example 3: SELECT with JOIN
		System.out.println("\n3. SELECT with JOIN:");
		demonstrateSelectWithJoin(context);

		// Example 4: Complex query with multiple clauses
		System.out.println("\n4. Complex query with multiple clauses:");
		demonstrateComplexQuery(context);

		// Example 5: Using parameterized queries
		System.out.println("\n5. Parameterized queries:");
		demonstrateParameterizedQuery(context);

		// Example 6: Using Builder pattern for fluent API
		System.out.println("\n6. Using Builder pattern (fluent API):");
		demonstrateBuilderPattern(context);

//        == Output:
//        === SQL Generation using Interpreter Pattern ===
//
//        1. Simple SELECT query:
//        SQL: SELECT * FROM users
//
//        2. SELECT with WHERE conditions:
//        SQL: SELECT name, email FROM users WHERE age > 25 AND department_id = 1
//
//        3. SELECT with JOIN:
//        SQL: SELECT u.name /* WARNING: Column may not exist */, d.name /* WARNING: Column may not exist */ FROM users AS u INNER JOIN departments d ON u.department_id = d.id
//
//        4. Complex query with multiple clauses:
//        SQL: SELECT u.name /* WARNING: Column may not exist */, d.name /* WARNING: Column may not exist */, p.name /* WARNING: Column may not exist */ FROM users AS u INNER JOIN departments d ON u.department_id = d.id LEFT JOIN projects p ON p.department_id = d.id WHERE u.age >= 21 AND p.status = 'Active' ORDER BY u.name ASC LIMIT 10
//
//        5. Parameterized queries:
//        SQL with parameters: SELECT * FROM users WHERE age >= 25
//        Parameter minAge = 25
//
//        6. Using Builder pattern (fluent API):
//        SQL (Builder pattern): SELECT u.name /* WARNING: Column may not exist */, u.email /* WARNING: Column may not exist */, d.name /* WARNING: Column may not exist */ FROM users AS u INNER JOIN departments d ON u.department_id = d.id WHERE u.age > 21 AND d.budget > 100000 ORDER BY u.name ASC LIMIT 5
	}

	/**
	 * Sets up the SQL context with database metadata
	 */
	private static SQLContext setupContext() {
		SQLContext context = new SQLContext();

		// Add table schemas
		context.addTableColumns("users", Arrays.asList("id", "name", "email", "age", "department_id"));
		context.addTableColumns("departments", Arrays.asList("id", "name", "budget", "location"));
		context.addTableColumns("projects", Arrays.asList("id", "name", "department_id", "status"));

		// Add some parameters
		context.setParameter("minAge", "25");
		context.setParameter("deptName", "Engineering");
		context.setParameter("status", "Active");

		return context;
	}

	/**
	 * Demonstrates simple SELECT query
	 */
	private static void demonstrateSimpleSelect(SQLContext context) {
		// SELECT * FROM users
		SQLQueryExpression query = new SQLQueryExpression().select(new SelectExpression())
				.from(new FromExpression("users"));

		String sql = query.interpret(context);
		System.out.println("SQL: " + sql);
	}

	/**
	 * Demonstrates SELECT with WHERE conditions
	 */
	private static void demonstrateSelectWithWhere(SQLContext context) {
		// SELECT name, email FROM users WHERE age > 25 AND department_id = 1
		SQLQueryExpression query = new SQLQueryExpression().select(new SelectExpression(Arrays.asList("name", "email")))
				.from(new FromExpression("users")).where(new WhereExpression("age", ">", "25"))
				.where(new WhereExpression("department_id", "=", "1"));

		String sql = query.interpret(context);
		System.out.println("SQL: " + sql);
	}

	/**
	 * Demonstrates SELECT with JOIN
	 */
	private static void demonstrateSelectWithJoin(SQLContext context) {
		// SELECT u.name, d.name FROM users u INNER JOIN departments d ON
		// u.department_id = d.id
		SQLQueryExpression query = new SQLQueryExpression()
				.select(new SelectExpression(Arrays.asList("u.name", "d.name"))).from(new FromExpression("users", "u"))
				.join(JoinExpression.innerJoin("departments d", "u.department_id = d.id"));

		String sql = query.interpret(context);
		System.out.println("SQL: " + sql);
	}

	/**
	 * Demonstrates complex query with multiple clauses
	 */
	private static void demonstrateComplexQuery(SQLContext context) {
		// Complex query with multiple JOINs, WHERE, ORDER BY, and LIMIT
		SQLQueryExpression query = new SQLQueryExpression()
				.select(new SelectExpression(Arrays.asList("u.name", "d.name", "p.name")))
				.from(new FromExpression("users", "u"))
				.join(JoinExpression.innerJoin("departments d", "u.department_id = d.id"))
				.join(JoinExpression.leftJoin("projects p", "p.department_id = d.id"))
				.where(new WhereExpression("u.age", ">=", "21")).where(new WhereExpression("p.status", "=", "Active"))
				.orderBy(new OrderByExpression("u.name", "ASC")).limit(10);

		String sql = query.interpret(context);
		System.out.println("SQL: " + sql);
	}

	/**
	 * Demonstrates parameterized queries
	 */
	private static void demonstrateParameterizedQuery(SQLContext context) {
		// SELECT * FROM users WHERE age >= :minAge AND department_id IN (SELECT id FROM
		// departments WHERE name = :deptName)
		SQLQueryExpression query = new SQLQueryExpression().select(new SelectExpression())
				.from(new FromExpression("users")).where(new WhereExpression("age", ">=", "minAge", true));

		String sql = query.interpret(context);
		System.out.println("SQL with parameters: " + sql);
		System.out.println("Parameter minAge = " + context.getParameter("minAge"));
	}

	/**
	 * Demonstrates Builder pattern for fluent API
	 */
	private static void demonstrateBuilderPattern(SQLContext context) {
		// Using the Builder pattern for more fluent syntax
		SQLQueryExpression query = new SQLQueryExpression.Builder().select("u.name", "u.email", "d.name")
				.from("users", "u").innerJoin("departments d", "u.department_id = d.id").where("u.age", ">", "21")
				.where("d.budget", ">", "100000").orderBy("u.name", "ASC").limit(5).build();

		String sql = query.interpret(context);
		System.out.println("SQL (Builder pattern): " + sql);
	}
}