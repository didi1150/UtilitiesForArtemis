package utils.cacheline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.TagPair;

/**
 * Standard Cacheline
 */
public abstract class CacheLine {

	private int size;
	protected List<TagPair> tagPairs = new ArrayList<TagPair>();
	protected Map<Long, Integer> indexLookup = new HashMap<Long, Integer>();
	protected int lineNumber;

	public CacheLine(int size, int lineNumber) {
		this.size = size;
		this.lineNumber = lineNumber;
	}

	public abstract void addEntry(TagPair tagPair);

	public void replaceTagPair(int index, TagPair tagPair) {
		tagPairs.set(index, tagPair);
		indexLookup.put(tagPair.getTag(), index);
	}

	public boolean contains(long tag) {
		return indexLookup.containsKey(tag);
	}

	public void updateTagPair(int index) {
		tagPairs.get(index).setValue(tagPairs.get(index).getValue() + 1);
	}

	public int getIndex(long tag) {
		return indexLookup.get(tag);
	}

	public List<TagPair> getTagPairs() {
		return tagPairs;
	}

	public int getSize() {
		return size;
	}

	public int currentAmount() {
		return tagPairs.size();
	}
}
