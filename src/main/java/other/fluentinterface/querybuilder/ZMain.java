package other.fluentinterface.querybuilder;

// Client code
public class ZMain {
	public static void main(String[] args) {
		// Build a simple query
		String query1 = QueryBuilder.fromTable("users").select("id", "name", "email").build();
		System.out.println("Query 1: " + query1);

		// Build a query with conditions
		String query2 = QueryBuilder.fromTable("orders").select("order_id", "amount").where("status = 'pending'")
				.where("amount > 100").build();
		System.out.println("Query 2: " + query2);

		// Build a query with sorting
		String query3 = QueryBuilder.fromTable("products").select("name", "price").where("category = 'electronics'")
				.orderBy("price").descending().build();
		System.out.println("Query 3: " + query3);

		// Build a minimal query
		String query4 = QueryBuilder.fromTable("employees").build();
		System.out.println("Query 4: " + query4);
		
//		== Ouput
//		Query 1: SELECT id, name, email FROM users
//		Query 2: SELECT order_id, amount FROM orders WHERE status = 'pending' AND amount > 100
//		Query 3: SELECT name, price FROM products WHERE category = 'electronics' ORDER BY price DESC
//		Query 4: SELECT * FROM employees
	}
}
