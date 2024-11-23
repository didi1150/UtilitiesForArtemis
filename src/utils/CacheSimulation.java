package utils;

import java.util.HashMap;
import java.util.Map;

import utils.cacheline.CacheLine;
import utils.cacheline.FIFOCacheLine;
import utils.cacheline.LRUCacheLine;
import utils.cacheline.LFUCacheLine;

public class CacheSimulation {
	private final int numSets; // Anzahl der Sets
	private final int associativity; // Assoziativität (Anzahl der Zeilen pro Set)
	private final int lineSize; // Cachezeilengröße in Bytes
	private final Map<Integer, CacheLine> cache; // Set -> (Tag -> Speicherblock)
	private EvictingPolicy evictingPolicy;

	public CacheSimulation(int numSets, int associativity, int lineSize, EvictingPolicy evictingPolicy) {
		this.numSets = numSets;
		this.associativity = associativity;
		this.lineSize = lineSize;
		this.evictingPolicy = evictingPolicy;
		this.cache = new HashMap<>();

		// Initialisierung: LRU-Cache für jedes Set
		for (int i = 0; i < numSets; i++) {
			if (evictingPolicy == EvictingPolicy.LRU)
				cache.put(i, new LRUCacheLine(associativity, i));
			else if (evictingPolicy == EvictingPolicy.LFU)
				cache.put(i, new LFUCacheLine(associativity, i));
			else
				cache.put(i, new FIFOCacheLine(associativity, i));
		}
	}

	public void accessMemory(String byteAddress) {
		int offsetBits = (int) (Math.log(lineSize) / Math.log(2));
		int indexBits = (int) (Math.log(associativity) / Math.log(2));
		int tagBits = lineSize - offsetBits - indexBits;
		long tag = Integer.parseInt(byteAddress.substring(0, tagBits), 2);
		int setIndex = Integer.parseInt(byteAddress.substring(tagBits, tagBits + indexBits), 2);

		CacheLine cacheLine = cache.get(setIndex);
		TagPair tagPair = new TagPair(tag, 1);
		cacheLine.addEntry(tagPair);
	}

	public void printCache() {
		System.out.println("--------------------------- " + evictingPolicy.name() + " --------------------------");
		System.out.println("|  Set  ||   Tag 1   |   Tag 2   |   Tag 3   |   Tag 4   |");
		System.out.println("|-------||-----------|-----------|-----------|-----------|");
		for (int i = 0; i < numSets; i++) {
			System.out.print("|   " + i + "   ||");
			CacheLine cacheLine = cache.get(i);
			for (TagPair tagPair : cacheLine.getTagPairs()) {
				long tag = tagPair.getTag();
				String output = "" + tag;
				while (output.length() < 11) {
					if (output.length() == 10)
						output = ' ' + output;
					else
						output = ' ' + output + ' ';
				}
				System.out.print(output + '|');
			}
			for (int j = cacheLine.getSize(); j < associativity; j++) {
				System.out.print("           |");
			}
			System.out.println();
		}

		System.out.println();

		System.out.println("|  Set  ||   Tag 1   |   Tag 2   |   Tag 3   |   Tag 4   |");
		System.out.println("|-------||-----------|-----------|-----------|-----------|");
		for (int i = 0; i < numSets; i++) {
			System.out.print("|   " + i + "   ||");
			CacheLine cacheLine = cache.get(i);
			for (TagPair tagPair : cacheLine.getTagPairs()) {
				long value = tagPair.getValue();
				String output = "" + value;
				while (output.length() < 11) {
					if (output.length() == 10)
						output = ' ' + output;
					else
						output = ' ' + output + ' ';
				}
				if (output.isBlank())
					output = output.substring(0, output.length() - 1) + "|";
				System.out.print(output + '|');
			}
			for (int j = cacheLine.getSize(); j < associativity; j++) {
				System.out.print("           |");
			}
			System.out.println();
		}

		System.out.println("--------------------------- " + evictingPolicy.name() + " --------------------------");
	}

}
