package utils.cacheline;

import utils.TagPair;

public class LFUCacheLine extends CacheLine {

	public LFUCacheLine(int size, int lineNumber) {
		super(size, lineNumber);
	}

	@Override
	public void addEntry(TagPair tagPair) {
		if (contains(tagPair.getTag())) {
			System.out.println("Set: " + lineNumber + " contains tag: " + tagPair.getTag());
			Integer index = indexLookup.get(tagPair.getTag());
			int value = tagPairs.get(index).getValue();
			tagPair.setValue(value + 1);
			tagPairs.set(index, tagPair);
		} else {
			if (tagPairs.size() >= getSize()) {
				int index = -1;
				long minValue = Integer.MAX_VALUE;
				for (int i = 0; i < tagPairs.size(); i++) {
					if (tagPairs.get(i).getValue() < minValue) {
						minValue = tagPairs.get(i).getValue();
						index = i;
					}
				}
				TagPair evictedTagPair = tagPairs.get(index);
				indexLookup.remove(evictedTagPair.getTag());

				System.out.println("Set: " + lineNumber + " evicting tag: " + evictedTagPair.getTag());

				System.out.println("Set: " + lineNumber + " adding tag: " + tagPair.getTag());
				tagPairs.set(index, tagPair);
				indexLookup.put(tagPair.getTag(), tagPairs.size() - 1);
			} else {

				System.out.println("Set: " + lineNumber + " adding tag: " + tagPair.getTag());
				tagPairs.add(tagPair);
				indexLookup.put(tagPair.getTag(), tagPairs.size() - 1);
			}

		}
	}
}
