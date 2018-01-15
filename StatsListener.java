package observerPattern.classes;

import observerPattern.interfaces.CacheListener;

/**
 * The StatsListener collects hit / miss / update stats for a cache.
 *
 * @param <K>
 * @param <V>
 */
public class StatsListener<K, V> implements CacheListener<K, V> {
	private static int hitsnb = 0;
	private static int missesnb = 0;
	private static int updatesnb = 0;

	/**
	 * Get the number of hits for the cache.
	 *
	 * @return number of hits
	 */
	public int getHits() {
		return (hitsnb);
	}

	/**
	 * Get the number of misses for the cache.
	 *
	 * @return number of misses
	 */
	public int getMisses() {
		return (missesnb);
	}

	/**
	 * Get the number of updates (put operations) for the cache.
	 *
	 * @return number of updates
	 */
	public int getUpdates() {
		return (updatesnb);
	}

	/* ToDO: Implement the CacheListener interface */

	public void onHit(K key) {
		hitsnb += 1;
	}

	public void onMiss(K key) {
		missesnb += 1;
	}

	public void onPut(K key, V value) {
		updatesnb += 1;
	}
}
