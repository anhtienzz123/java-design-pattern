package behavioral.interpreter.simplearithmeticexpressionlanguage;

import java.util.HashMap;
import java.util.Map;

// Context: Holds variable values for interpretation
public class Context {
	private Map<String, Integer> variables = new HashMap<>();

	public void setVariable(String name, int value) {
		variables.put(name, value);
	}

	public int getVariable(String name) {
		return variables.getOrDefault(name, 0); // Default to 0 if undefined
	}
}
