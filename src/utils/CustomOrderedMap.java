package utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class CustomOrderedMap<K, V> {
	static class Entry<K, V> {
		K key;
		V value;

		Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private final LinkedList<Entry<K, V>> entries; // Zum Beibehalten der Einfügereihenfolge
	private final Map<K, Entry<K, V>> lookupMap; // Effizientes Suchen nach Schlüsseln

	public LinkedList<Entry<K, V>> entrySet() {
		return entries;
	}

	public boolean containsKey(K key) {
		if (lookupMap.containsKey(key))
			return true;
		return false;
	}

	public Set<K> keySet() {
		Set<K> set = new HashSet<K>();
		entries.forEach(entry -> {
			set.add(entry.key);
		});
		return set;
	}

	public int size() {
		return entries.size();
	}

	public CustomOrderedMap() {
		this.entries = new LinkedList<>();
		this.lookupMap = new HashMap<>();
	}

	/**
	 * Fügt ein neues Paar hinzu oder ersetzt den Wert, wenn der Schlüssel
	 * existiert.
	 */
	public void put(K key, V value) {
		if (lookupMap.containsKey(key)) {
			// Schlüssel existiert, nur den Wert aktualisieren
			Entry<K, V> entry = lookupMap.get(key);
			entry.value = value;
		} else {
			// Neuer Schlüssel, ans Ende der Liste hinzufügen
			Entry<K, V> entry = new Entry<>(key, value);
			entries.add(entry);
			lookupMap.put(key, entry);
		}
	}

	/**
	 * Ersetzt einen bestehenden Schlüssel durch einen neuen Schlüssel. Die Position
	 * in der Liste bleibt unverändert.
	 */
	public void replaceKey(K oldKey, K newKey, V newValue) {
		if (!lookupMap.containsKey(oldKey)) {
			throw new NoSuchElementException("Key " + oldKey + " does not exist.");
		}
		if (lookupMap.containsKey(newKey)) {
			throw new IllegalArgumentException("Key " + newKey + " already exists.");
		}

		// Hole das bestehende Entry und aktualisiere den Schlüssel und den Wert
		Entry<K, V> entry = lookupMap.get(oldKey);
		entry.key = newKey;
		entry.value = newValue;

		// Aktualisiere die Lookup-Map
		lookupMap.remove(oldKey);
		lookupMap.put(newKey, entry);
	}

	/**
	 * Gibt den Wert für einen Schlüssel zurück.
	 */
	public V get(K key) {
		Entry<K, V> entry = lookupMap.get(key);
		if (entry == null) {
			return null;
		}
		return entry.value;
	}

	/**
	 * Entfernt einen Schlüssel.
	 */
	public void remove(K key) {
		Entry<K, V> entry = lookupMap.remove(key);
		if (entry != null) {
			entries.remove(entry);
		}
	}

	/**
	 * Gibt die gesamte Map in Einfügereihenfolge aus.
	 */
	public void printMap() {
		for (Entry<K, V> entry : entries) {
			System.out.println("Key: " + entry.key + ", Value: " + entry.value);
		}
	}
}
