Caching System (2017 - 2018)

## Input file format


The first line of the file contains the type of cache (FIFO, LRU, TIME) and a parameter.
The parameter represents either the capacity if it is FIFO / LRU or time
the expiration of the keys, if it is TimeAwareCache.

 The rest of the lines are commands such as `argument argument ', as
it follows:


  * delay -> adds a delay of `argument 'miliseconds. It is used for
    testing 'TimeAwareCache'.
  * get -> displays the contents of the `argument` file. If the file is not in
    cache, it will be loaded first, generating a miss and hit
  * top_hits / top_misses / top_updates -> displays the first `argument` values
    for hits / misses / updates. Values ​​and no caches will be displayed
    because output differences may occur for files with the same number
    hits / misses / updates. Use KeyStatsListener.
  * key_hits / key_misses / key_updates -> displays hits / misses / updates
    for the argument key. Use KeyStatsListener.
  * total_hits / total_misses / total_updates -> displays hits / misses / updates
    for the entire cache. Use StatsListener.

## Running checker

The theme will be tested using the `test.sh` script. It uses it in turn
Makefile in the archive.
