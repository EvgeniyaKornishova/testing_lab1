package ru.itmo

class IntHashTable<K, V>(
    private val size: Int,
) {
    private val storage: Array<Bucket<K, V>?> = Array(size) { null }
    var log: MutableList<String> = mutableListOf()

    data class Bucket<K, V>(
        val key: K,
        var value: V,
        var next: Bucket<K, V>? = null,
    )

    private fun getBucketId(key: K) = kotlin.math.abs(key.hashCode() % size)

    fun get(key: K): V? {
        val bucketId = getBucketId(key)
        var bucket = storage[bucketId]
        log.add("working with bucket $bucketId")
        var counter = 0
        while (bucket !== null) {
            if (bucket.key == key) {
                log.add("found on step $counter")
                return bucket.value
            }
            counter++
            bucket = bucket.next
        }
        log.add("not found in bucket")
        return null
    }

    fun put(key: K, value: V) {
        val bucketId = getBucketId(key)
        var bucket = storage[bucketId]
        log.add("working with bucket $bucketId")
        var counter = 0
        while (bucket !== null) {
            if (bucket.next == null) {
                log.add("insert on step ${counter + 1}")
                bucket.next = Bucket(key, value)
                return
            }
            counter++
            bucket = bucket.next
        }
        log.add("insert on step $counter")
        storage[bucketId] = Bucket(key, value)
        return
    }

    fun delete(key: K): V? {
        val bucketId = getBucketId(key)
        var bucket = storage[bucketId]
        log.add("working with bucket $bucketId")
        var counter = 0
        var prev: Bucket<K, V>? = null
        while (bucket !== null) {
            if (bucket.key == key) {
                if (prev != null) {
                    log.add("removed on step $counter")
                    prev.next = bucket.next
                } else {
                    log.add("removed on step $counter")
                    storage[bucketId] = bucket.next
                }
                return bucket.value
            }
            prev = bucket
            counter++
            bucket = bucket.next
        }
        log.add("not found")
        return null
    }


}