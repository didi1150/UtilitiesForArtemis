package utils;

public class EraDecoder {

	private String[] hexa = new String[] { "0xbfffff00", "0x440", "0xbffffef0", "0xbffffee0", "0xbffffed0",
			"0xbffffec4", "0xbffffec0", "0xbffffec8", "0x4c4", "0x440", "0x54f", "0x4d7", "0x440", "0x5c3",
			"0x55555555", "0x5a0", "0xbffffec4", "0xbffffed0", "0xbffffee0", "0xbffffef0", "0xbfffff00" };

	public String[] getHexa() {
		return hexa;
	}

	public static String hexToBin(String hex) {
		hex = hex.replaceAll("0", "0000");
		hex = hex.replaceAll("1", "0001");
		hex = hex.replaceAll("2", "0010");
		hex = hex.replaceAll("3", "0011");
		hex = hex.replaceAll("4", "0100");
		hex = hex.replaceAll("5", "0101");
		hex = hex.replaceAll("6", "0110");
		hex = hex.replaceAll("7", "0111");
		hex = hex.replaceAll("8", "1000");
		hex = hex.replaceAll("9", "1001");
		hex = hex.replaceAll("A", "1010");
		hex = hex.replaceAll("B", "1011");
		hex = hex.replaceAll("C", "1100");
		hex = hex.replaceAll("D", "1101");
		hex = hex.replaceAll("E", "1110");
		hex = hex.replaceAll("F", "1111");
		return hex;
	}

	public static void main(String[] args) {
//		for (String string : new EraDecoder().getHexa()) {
//			string = hexToBin(string.substring(2).toUpperCase());
//			string = String.format("%32s", string).replace(' ', '0');
//
//			int offsetBits = (int) (Math.log(32) / Math.log(2));
//			int indexBits = (int) (Math.log(4) / Math.log(2));
//			int tagBits = 32 - offsetBits - indexBits;
//			long tag = Integer.parseInt(string.substring(0, tagBits), 2);
//			int index = Integer.parseInt(string.substring(tagBits, tagBits + indexBits), 2);
//			System.out.println("Tag: " + tag + " | Set: " + index);
//		}

		CacheSimulation cacheSimulation = new CacheSimulation(4, 4, 32, EvictingPolicy.LFU);
		for (String string : new EraDecoder().getHexa()) {
			string = hexToBin(string.substring(2).toUpperCase());
			string = String.format("%32s", string).replace(' ', '0');
			cacheSimulation.accessMemory(string);

		}
		cacheSimulation.printCache();
	}
}
