package cachingSystem.classes;

import java.util.HashMap;

/**
 * The TimeAwareCache offers the same functionality as the LRUCache, but also
 * stores a timestamp for each element. The timestamp is updated after each get
 * / put operation for a key. This functionality allows for time based cache
 * stale policies (e.g. removing entries that are older than 1 second).
 */
public class TimeAwareCache<K, V> extends LRUCache<K, V> {

	private long millisexpire;
	/**
	 * map holds the mapping between keys and the cache nodes.
	 */
	private HashMap<K, LRUNode> map;

	/**
	 * head holds the most recent accessed element according the LRU cache logic.
	 */
	private LRUNode head;

	/**
	 * head holds the most recent accessed element according the LRU cache logic.
	 */
	private LRUNode tail;

	public TimeAwareCache() {
		map = new HashMap<K, LRUNode>();
	}

	/**
	 * Keep an entry on the LRUCache.
	 */
	class LRUNode {
		private LRUNode head;
		private LRUNode tail;
		private K key;
		private V value;
		private long timestamp;

		LRUNode(K key, V value) {
			this.key = key;
			this.value = value;
			this.timestamp = System.currentTimeMillis();
		}
	}

	/**
	 * Remove a LRUNode from the linkedlist.
	 */
	protected void removeNode(LRUNode node) {
		if (node == tail && node == head) {
			head = null;
			tail = null;
			return;
		}
		if (node == tail) {
			tail = node.head;
			return;
		}
		if (node == head) {
			head = node.tail;
			return;
		}
		node.tail.head = node.head;
		node.head.tail = node.tail;
	}

	/**
	 * Add a new node to the head of the linkedlist.
	 */
	protected void addToHead(LRUNode node) {
		if (head == null) {
			head = node;
			tail = node;
			return;
		}
		head.head = node;
		node.tail = head;
		head = node;
	}

	@Override
	public void put(K key, V value) {
		if (map.containsKey(key)) {
			onPut(key, value);
		}

		LRUNode node = new LRUNode(key, value);
		addToHead(node);
		map.put(key, node);
	}

	@Override
	public V get(K key) {
		LRUNode node = map.get(key);
		if (node != null) {
			if (millisexpire >= 0) {
				if (System.currentTimeMillis() - node.timestamp <= millisexpire) {
					onHit(key);
					removeNode(node);
					addToHead(node);
					return node.value;
				} else {
					map.remove(key);
					return null;
				}
			}
		}
		if (!(map.containsKey(key))) {
			onMiss(key);
		}
		node = map.get(key);
		removeNode(node);
		addToHead(node);
		return node.value;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return (size() == 0);
	}

	@Override
	public V remove(K key) {
		V value;
		map.remove(key);
		value = tail.value;
		removeNode(tail);
		return value;
	}

	@Override
	public void clearAll() {
		map.clear();
	}

	/* implementation of the CacheListener interface */

	public void onHit(K key) {
		cachelistener.onHit(key);
	}

	public void onMiss(K key) {
		cachelistener.onMiss(key);
	}

	public void onPut(K key, V value) {
		cachelistener.onPut(key, value);
	}

	/*
	 * ToDO: figure out which methods need to overridden in order to implement the
	 * timestamp functionality
	 */
	/**
	 * Get the timestamp associated with a key, or null if the key is not stored in
	 * the cache.
	 *
	 * @param key
	 *            the key
	 * @return the timestamp, or null
	 */
	public long getTimestampOfKey(K key) {
		LRUNode node = map.get(key);
		return node.timestamp;
	}

	/**
	 * Set a cache stale policy that should remove all elements older
	 * than @millisToExpire milliseconds. This is a convenience method for setting a
	 * time based policy for the cache.
	 *
	 * @param millisToExpire
	 *            the expiration time, in milliseconds
	 */
	public void setExpirePolicy(long millisToExpire) {
		this.millisexpire = millisToExpire;
	}
}
