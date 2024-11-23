package utils;

public class TagPair {

	private long tag;
	private int count;

	public long getTag() {
		return tag;
	}

	public void setTag(long tag) {
		this.tag = tag;
	}

	public int getValue() {
		return count;
	}

	public void setValue(int value) {
		this.count = value;
	}

	public TagPair(long tag, int value) {
		this.tag = tag;
		this.count = value;
	}

}
