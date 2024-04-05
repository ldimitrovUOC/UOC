package leetcode.array.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldBreak {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WorldBreak worldBreak = new WorldBreak();
		long startTime = System.nanoTime();
		Runtime runtime = Runtime.getRuntime();

		long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Memoria utilizada antes de la ejecución: " + memoryBefore + " bytes");

		System.out.println(worldBreak.wordBreakCorrecto(
				"palipalo",
				Arrays.asList("palo", "pa")));

		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1_000_000_000; // Tiempo en nanosegundos
		System.out.println("Tiempo de ejecución: " + duration + " segundos");

		long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Memoria utilizada después de la ejecución: " + memoryAfter + " bytes");
	}

	// MI SOLUCION
	public boolean wordBreak(String s, List<String> wordDict) {
		if (s.isBlank())
			return true;
		Set<String> listaPalabrasEncontradas = new HashSet<String>();

		String palabra = "";
		for (int i = 0; i < s.length(); i++) {
			palabra += s.charAt(i);
			for (int j = i + 1; j < s.length(); j++) {
				palabra += s.charAt(j);
				if (this.inDiccionary(palabra, wordDict)) {
					this.wordBreak(s.substring(j + 1), wordDict);
					listaPalabrasEncontradas.add(palabra);
				}
			}
			palabra = "";
		}

		return false;
	}

	private boolean inDiccionary(String s, List<String> list) {
		return list.stream().anyMatch(word -> word.equals(s));
	}

	// SOLUCION POR RECURSIVIDAD
	public boolean wordBreakCorrecto(String s, List<String> wordDict) {
		return wordBreakHelper(s, new HashSet<>(wordDict), 0);
	}

	private boolean wordBreakHelper(String s, Set<String> wordDictSet, int start) {
		if (start == s.length()) {
			return true;
		}

		for (int end = start + 1; end <= s.length(); end++) {
			String subString = s.substring(start, end);
			if (wordDictSet.contains(subString) && wordBreakHelper(s, wordDictSet, end)) {
				return true;
			}
		}

		return false;
	}

	// SOLUCION MÁS EFICIENTE
	public boolean wordBreakEficiente(String s, List<String> wordDict) {
		Set<String> wordDictSet = new HashSet<>(wordDict);
		Boolean[] memo = new Boolean[s.length()];
		return wordBreakHelper(s, wordDictSet, 0, memo);
	}

	private boolean wordBreakHelper(String s, Set<String> wordDictSet, int start, Boolean[] memo) {
		if (start == s.length()) {
			return true; // La cadena completa se ha descompuesto en palabras válidas.
		}

		if (memo[start] != null) {
			return memo[start];
		}

		for (int end = start + 1; end <= s.length(); end++) {
			String subString = s.substring(start, end);
			if (wordDictSet.contains(subString) && wordBreakHelper(s, wordDictSet, end, memo)) {
				memo[start] = true;
				return true;
			}
		}

		memo[start] = false;
		return false; // No se pudo descomponer la cadena.
	}
}
