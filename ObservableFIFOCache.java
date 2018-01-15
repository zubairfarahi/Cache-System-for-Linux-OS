package cachingSystem.classes;

import dataStructures.classes.Pair;

/**
 * Class that adapts the FIFOCache class to the ObservableCache abstract class.
 */
public class ObservableFIFOCache<K, V> extends ObservableCache<K, V> {
	/* implementation of the methods from ObservableCache and Cache. */
	private FIFOCache<K, V> fcache;

	public ObservableFIFOCache() {
		fcache = new FIFOCache<K, V>();
	}

	@Override
	public V get(K key) {
		if (!(fcache.cache.containsKey(key))) {
			onMiss(key);
		}
		if (fcache.cache.containsKey(key)) {
			onHit(key);
			return fcache.get(key);
		}
		return fcache.get(key);
	}

	/*
	 * when adding a new key (the put method), don't forget to call
	 * clearStaleEntries
	 */

	@Override
	public void put(K key, V value) {
		clearStaleEntries(getEldestEntry());
		onPut(key, value);
		fcache.put(key, value);
	}

	@Override
	public int size() {
		return fcache.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public V remove(K key) {
		return fcache.remove(key);
	}

	@Override
	public void clearAll() {
		fcache.clearAll();
	}

	@Override
	public Pair<K, V> getEldestEntry() {
		return fcache.getEldestEntry();
	}

	/* implementation of the CacheListener interface. */
	public void onHit(K key) {
		cachelistener.onHit(key);
	}

	public void onMiss(K key) {
		cachelistener.onMiss(key);
	}

	public void onPut(K key, V value) {
		cachelistener.onPut(key, value);
	}
}
