package observerPattern.classes;

import observerPattern.interfaces.CacheListener;
import java.util.LinkedList;

/**
 * The BroadcastListener broadcasts cache events to other listeners that have
 * been added to it. void onHit(K key); void onMiss(K key); void onPut(K key, V
 * value);
 */
public class BroadcastListener<K, V> implements CacheListener<K, V> {
	private LinkedList<CacheListener<K, V>> list;

	public BroadcastListener() {
		list = new LinkedList<CacheListener<K, V>>();
	}

	/**
	 * Add a listener to the broadcast list.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(CacheListener<K, V> listener) {
		/* Implementation of addListener */
		list.add(listener);
	}

	/* implementation of the CacheListener interface */

	public void onHit(K key) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).onHit(key);
		}
	}

	public void onMiss(K key) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).onMiss(key);
		}
	}

	public void onPut(K key, V value) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).onPut(key, value);
		}
	}
}
