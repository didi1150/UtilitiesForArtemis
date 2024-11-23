package utils.cacheline;

import java.util.Map.Entry;

import utils.TagPair;

public class LRUCacheLine extends CacheLine {

	private int time;

	public LRUCacheLine(int size, int lineNumber) {
		super(size, lineNumber);
	}

	@Override
	public void addEntry(TagPair tagPair) {
		time++;
		tagPair.setValue(time);
		if (!contains(tagPair.getTag())) {

			if (tagPairs.size() >= getSize()) {

				int index = -1;
				long minValue = Integer.MAX_VALUE;
				for (Entry<Long, Integer> entry : indexLookup.entrySet()) {
					if (minValue > tagPairs.get(entry.getValue()).getValue()) {
						minValue = tagPairs.get(entry.getValue()).getValue();
						index = entry.getValue();
					}
				}

				TagPair evictedTagPair = tagPairs.get(index);
				System.out.println("Set: " + lineNumber + " evicting tag: " + evictedTagPair.getTag());
				indexLookup.remove(evictedTagPair.getTag());
				indexLookup.put(tagPair.getTag(), index);
				tagPairs.set(index, tagPair);

				System.out.println("Set: " + lineNumber + " adding tag: " + tagPair.getTag());
			} else {
				tagPairs.add(tagPair);
				indexLookup.put(tagPair.getTag(), tagPairs.size() - 1);
				System.out.println("Set: " + lineNumber + " adding tag: " + tagPair.getTag());
			}
		} else {
			Integer index = indexLookup.get(tagPair.getTag());
			tagPairs.set(index, tagPair);
		}

	}
}
