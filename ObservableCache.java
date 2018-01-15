package cachingSystem.classes;

import cachingSystem.interfaces.Cache;
import cachingSystem.interfaces.CacheStalePolicy;
import observerPattern.interfaces.CacheListener;
import dataStructures.classes.Pair;

/**
 * Abstract class that adds support for listeners and stale element policies to
 * the Cache interface.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public abstract class ObservableCache<K, V> implements Cache<K, V> {
	protected CacheListener<K, V> cachelistener;
	protected CacheStalePolicy<K, V> stalepolicy;

	/**
	 * Set a policy for removing stale elements from the cache.
	 *
	 * @param stalePolicy
	 */
	public void setStalePolicy(CacheStalePolicy<K, V> stalePolicy) {

		this.stalepolicy = stalePolicy;
	}

	/**
	 * Set a listener for the cache.
	 *
	 * @param cacheListener
	 */
	public void setCacheListener(CacheListener<K, V> cacheListener) {
		/* implementation of setCacheListener */
		this.cachelistener = cacheListener;
	}

	/**
	 * Clear the stale elements from the cache. This method must make use of the
	 * stale policy.
	 *
	 */
	public void clearStaleEntries(Pair<K, V> entry) {
		/* : implementation of clearStaleEntries */
		if (this.stalepolicy.shouldRemoveEldestEntry(entry)) {
			this.remove(entry.getKey());
		}
	}
}
