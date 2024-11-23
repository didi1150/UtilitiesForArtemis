package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagReplacer {
	public static void write(String input, Object... replacements) {
		input = input.replace('⎵', ' ');
		// () = Group, [^>]* = Zero or more from anything that isn't >
		Pattern pattern = Pattern.compile("<([^>]*)>");
		Matcher matcher = pattern.matcher(input);

		List<MatchInfo> matches = new ArrayList<>();
		while (matcher.find()) {
			matches.add(new MatchInfo(matcher.start(), matcher.end()));
		}
		if (matches.size() == 0) {
			System.out.println(input);
			return;
		}
		StringBuilder result = new StringBuilder(input);
		for (int i = matches.size() - 1; i >= 0; i--) {
			MatchInfo match = matches.get(i);
			if (i < replacements.length) {
				result.replace(match.start, match.end, String.valueOf(replacements[i]));
			}
		}

		System.out.println(result.toString());
	}

	private static class MatchInfo {
		final int start;
		final int end;

		MatchInfo(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

}