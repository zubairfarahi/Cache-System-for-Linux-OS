package observerPattern.classes;

import java.util.List;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import observerPattern.interfaces.CacheListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * The KeyStatsListener collects key-level stats for cache operations.
 *
 * @param <K>
 * @param <V>
 */
public class KeyStatsListener<K, V> implements CacheListener<K, V> {

	protected Map<String, Integer> keyhits;
	protected Map<String, Integer> keymisses;
	protected Map<String, Integer> keyupdates;

	private static int first = 0;

	public KeyStatsListener() {
		keyhits = new HashMap<>();
		keymisses = new HashMap<>();
		keyupdates = new HashMap<>();
	}

	/**
	 * Get the number of hits for a key.
	 *
	 * @param key
	 *            the key
	 * @return number of hits
	 */
	public int getKeyHits(K key) {
		/* implementation of getKeyHits */
		Set<Entry<String, Integer>> setHm = keyhits.entrySet();
		Iterator<Entry<String, Integer>> it = setHm.iterator();
		int nvalue = 0;
		while (it.hasNext()) {
			Entry<String, Integer> e = it.next();
			if ((e.getKey()).equals(key)) {
				nvalue = e.getValue();
			}
		}
		return (nvalue);
	}

	/**
	 * Get the number of misses for a key.
	 *
	 * @param key
	 *            the key
	 * @return number of misses
	 */
	public int getKeyMisses(K key) {
		/* : implementation getKeyMisses */
		Set<Entry<String, Integer>> setHm = keymisses.entrySet();
		Iterator<Entry<String, Integer>> it = setHm.iterator();
		int nvalue = 0;
		while (it.hasNext()) {
			Entry<String, Integer> e = it.next();
			if ((e.getKey()).equals(key)) {
				nvalue = e.getValue();
			}
		}
		return (nvalue);
	}

	/**
	 * Get the number of updates for a key.
	 *
	 * @param key
	 *            the key
	 * @return number of updates
	 */
	public int getKeyUpdates(K key) {
		Set<Entry<String, Integer>> setHm = keyupdates.entrySet();
		Iterator<Entry<String, Integer>> it = setHm.iterator();
		int nvalue = 0;
		while (it.hasNext()) {
			Entry<String, Integer> e = it.next();
			if ((e.getKey()).equals(key)) {
				nvalue = e.getValue();
			}
		}
		return (nvalue);
	}

	/**
	 * Map<String, Integer> unsortMap = new HashMap<String, Integer>().
	 * unsortMap.put("B", 55); unsortMap.put("A", 80); unsortMap.put("D", 20); *
	 * unsortMap.put("C", 70);
	 *
	 * System.out.println("Before sorting......"); printMap(unsortMap);
	 *
	 * System.out.println("After sorting ascending order......"); Map<String,
	 * Integer> sortedMapAsc = sortByComparator(unsortMap, ASC);
	 * printMap(sortedMapAsc);
	 *
	 *
	 * System.out.println("After sorting descindeng order......"); Map<String,
	 * Integer> sortedMapDesc = sortByComparator(unsortMap, DESC);
	 * printMap(sortedMapDesc);
	 */
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {
		List<Entry<String, Integer>> list;
		list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());
				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public static void printMap(Map<String, Integer> map) {
		for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
	}

	/**
	 * Get the @top most hit keys.
	 *
	 * @param top
	 *            number of top keys
	 * @return the list of keys
	 */
	public List<String> getTopHitKeys(int top) {
		/* ToDO: implement getTopHitKeys */
		List<String> tophitkeylist = new ArrayList<String>();
		Map<String, Integer> sortedMapAsc = sortByComparator(keyhits, false);

		for (Entry<String, Integer> entry : sortedMapAsc.entrySet()) {
			tophitkeylist.add("" + entry.getKey());
		}
		return (tophitkeylist);
	}

	/**
	 * Get the @top most missed keys.
	 *
	 * @param top
	 *            number of top keys
	 * @return the list of keys
	 */
	public List<String> getTopMissedKeys(int top) {
		/* ToDO: implement getTopMissedKeys */
		List<String> topmissekeys = new ArrayList<String>();
		Map<String, Integer> sortedMapAsc = sortByComparator(keymisses, false);

		for (Entry<String, Integer> entry : sortedMapAsc.entrySet()) {
			topmissekeys.add("" + entry.getKey());
		}
		return (topmissekeys);
	}

	/**
	 * Get the @top most updated keys.
	 *
	 * @param top
	 *            number of top keys
	 * @return the list of keys
	 */
	public List<String> getTopUpdatedKeys(int top) {
		/* ToDO: implement getTopUpdatedKeys */
		List<String> topupdatekeylist = new ArrayList<String>();
		Map<String, Integer> sortedMapAsc = sortByComparator(keyupdates, false);

		for (Entry<String, Integer> entry : sortedMapAsc.entrySet()) {
			topupdatekeylist.add("" + entry.getKey());
		}
		return (topupdatekeylist);
	}

	/* implementation of the CacheListener interface */
	public void onHit(K key) {
		if (!(keyhits.containsKey(key))) {
			keyhits.put((String) key, 1);
		} else {
			Set<Entry<String, Integer>> setHm = keyhits.entrySet();
			Iterator<Entry<String, Integer>> it = setHm.iterator();
			int nvalue = 0;

			while (it.hasNext()) {
				Entry<String, Integer> e = it.next();
				if ((e.getKey()).equals(key)) {
					nvalue = e.getValue() + 1;
					keyhits.put((String) key, nvalue); /** dwdwd */
				} /** dwdwd */
			} /** dwdwd */
		}
	}

	public void onMiss(K key) {
		if (!(keymisses.containsKey(key))) {
			keymisses.put((String) key, 1);
		} else {
			Set<Entry<String, Integer>> setHm = keymisses.entrySet();
			Iterator<Entry<String, Integer>> it = setHm.iterator();
			int nvalue = 0;
			while (it.hasNext()) {
				Entry<String, Integer> e = it.next();
				if ((e.getKey()).equals(key)) {
					nvalue = e.getValue() + 1;
					keymisses.put((String) key, nvalue);
				}
			}
		}
	}

	public void onPut(K key, V value) {
		if (!(keyupdates.containsKey(key))) {
			keyupdates.put((String) key, 1);
		} else {
			Set<Entry<String, Integer>> setHm = keyupdates.entrySet();
			Iterator<Entry<String, Integer>> it = setHm.iterator();
			int nvalue = 0;
			while (it.hasNext()) {
				Entry<String, Integer> e = it.next();
				if ((e.getKey()).equals(key)) {
					nvalue = e.getValue() + 1;
					keyupdates.put((String) key, nvalue);
				}
			}
		}
	}
}
