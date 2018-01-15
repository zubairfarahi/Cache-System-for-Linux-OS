package cachingSystem.classes;

import dataStructures.classes.Pair;

import java.util.HashMap;

/**
 * This cache is very similar to the FIFOCache, but guarantees O(1) complexity
 * for the get, put and remove operations.
 */
public class LRUCache<K, V> extends ObservableCache<K, V> {

	/*
	 * implementation of the methods from ObservableCache and Cache.
	 */

	// head holds the most recent accessed element according the LRU cache logic.
	private LRUNode head;
	// head holds the most recent accessed element according the LRU cache logic.
	private LRUNode tail;
	private LRUNode node;
	// map holds the mapping between keys and the cache nodes.
	private HashMap<K, LRUNode> map;

	/**
	 * Keep an entry on the LRUCache.
	 */
	class LRUNode {
		private LRUNode head;
		private LRUNode tail;
		private K key;
		private V value;

		LRUNode(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	public LRUCache() {
		map = new HashMap<>();
	}

	// Remove a LRUNode from the linkedlist.
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
		onPut(key, value);
		clearStaleEntries(getEldestEntry());

		LRUNode node = new LRUNode(key, value);
		addToHead(node);
		map.put(key, node);
	}

	@Override
	public V get(K key) {
		node = null;
		if (!(map.containsKey(key))) {
			onMiss(key);
		}

		if (map.containsKey(key)) {
			onHit(key);
			node = map.get(key);
			removeNode(node);
			addToHead(node);
			return node.value;
		}

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

	@Override
	public Pair<K, V> getEldestEntry() {
		if (isEmpty()) {
			return null;
		}
		return new Pair<K, V>(tail.key, tail.value);
	}
	/*
	 * implementation of the CacheListener interface .
	 */

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
