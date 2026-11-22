package zdemo.combinator.logsearchdemo;

import java.util.List;

@FunctionalInterface
public interface Finder {

	List<String> find(String text);

	static Finder contains(String word) {
		return text -> text.lines().filter(l -> l.toLowerCase().contains(word.toLowerCase())).toList();
	}

	default Finder and(Finder other) {
		return text -> {
			List<String> result = this.find(text);
			String joinResult = String.join("\n", result);
			return other.find(joinResult);
		};
	}

	default Finder or(Finder other) {
		return text -> {
			List<String> leftResult = this.find(text);
			List<String> rightResult = other.find(text);

			return text.lines().filter(l -> leftResult.contains(l) || rightResult.contains(l)).toList();
		};
	}
}
