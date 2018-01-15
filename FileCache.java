package cachingSystem;

import cachingSystem.classes.ObservableCache;
import cachingSystem.classes.ObservableFIFOCache;
import cachingSystem.classes.LRUCache;
import cachingSystem.classes.TimeAwareCache;
import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;
import observerPattern.classes.BroadcastListener;
import observerPattern.interfaces.CacheListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class FileCache {

	public enum Strategy {
		FIFO, LRU,
	}

	public static cachingSystem.FileCache createCacheWithCapacity(cachingSystem.FileCache.Strategy strategy,
			int capacity) {
		ObservableCache<String, String> dataCache;

		switch (strategy) {

		case FIFO:
			dataCache = new ObservableFIFOCache<>();
			break;
		case LRU:
			dataCache = new LRUCache<>();
			break;
		default:
			throw new IllegalArgumentException("Unsupported cache strategy: " + strategy);
		}

		dataCache.setStalePolicy(new CacheStalePolicy<String, String>() {
			@Override
			public boolean shouldRemoveEldestEntry(Pair<String, String> entry) {
				return (dataCache.size() + 1) > capacity;
			}
		});

		return new cachingSystem.FileCache(dataCache);
	}

	public static cachingSystem.FileCache createCacheWithExpiration(long millisToExpire) {
		TimeAwareCache<String, String> dataCache = new TimeAwareCache<>();

		dataCache.setExpirePolicy(millisToExpire);

		return new cachingSystem.FileCache(dataCache);
	}

	private FileCache(ObservableCache<String, String> dataCache) {
		this.dataCache = dataCache;
		this.broadcastListener = new BroadcastListener<>();
		this.dataCache.setCacheListener(broadcastListener);

		broadcastListener.addListener(createCacheListener());
	}

	private CacheListener<String, String> createCacheListener() {
		/**
		 * implementation of a listener that loads a file in memory on a cache miss
		 * LoadListener<String, String> loadlistener = new LoadListener<>(); return
		 * loadlistener;
		 */
		return new CacheListener<String, String>() {
			@Override
			public void onMiss(String key) {
				String path = "../" + key;
				List<String> content;
				try {
					content = Files.readAllLines(Paths.get(key));
				} catch (IOException e) {
					throw new IllegalArgumentException("Invalid file path: " + path);
				}
				String cot = content.get(0);
				path = key;
				putFileContents(key, cot);
			}

			@Override
			public void onPut(String a, String b) {
			}

			@Override
			public void onHit(String a) {
			}
		};
	}

	public String getFileContents(String path) {

		String fileContents;

		do {
			fileContents = dataCache.get(path);
		} while (fileContents == null);

		return fileContents;
	}

	public void putFileContents(String path, String contents) {
		dataCache.put(path, contents);
	}

	public void addListener(CacheListener<String, String> listener) {
		broadcastListener.addListener(listener);
	}

	private ObservableCache<String, String> dataCache;
	private BroadcastListener<String, String> broadcastListener;
}
