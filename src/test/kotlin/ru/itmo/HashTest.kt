package ru.itmo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HashTest {
    private val size = 5
    private var hashTable: IntHashTable<Int, Int> = IntHashTable(size)
    @BeforeEach
    fun initTable() {
        hashTable = IntHashTable(size)
    }

    @Test
    fun `put and get data from hash table`() {
        hashTable.put(5, 5) // hash 5 % 5 = 0
        hashTable.put(11, 11) // hash 11 % 5 = 1

        assertEquals(5, hashTable.get(5))
        assertEquals( 11, hashTable.get(11))

        val expectedActions = listOf(
            "working with bucket 0", "insert on step 0",
            "working with bucket 1", "insert on step 0",
            "working with bucket 0", "found on step 0",
            "working with bucket 1", "found on step 0"
        )
        assertEquals(expectedActions, hashTable.log)
    }

    @Test
    fun `check data absence after delete from table`() {
        hashTable.put(5, 5)
        assertEquals(5, hashTable.get(5))
        hashTable.delete(5)
        assertEquals(null, hashTable.get(5))

        val expectedActions = listOf(
            "working with bucket 0", "insert on step 0",
            "working with bucket 0", "found on step 0",
            "working with bucket 0", "removed on step 0",
            "working with bucket 0", "not found in bucket"
        )
        assertEquals(expectedActions, hashTable.log)
    }

    @Test
    fun `check work of put and get with collision`() {
        val ints = listOf(1, 2, 3, 4, 5, 6).toList().toIntArray() // buckets: 1, 2, 3, 4, 0, 1
        ints.forEach { i -> hashTable.put(i, i) }
        ints.forEach { i -> assertEquals(i, hashTable.get(i)) }

        val expectedActions = listOf(
            "working with bucket 1", "insert on step 0",
            "working with bucket 2", "insert on step 0",
            "working with bucket 3", "insert on step 0",
            "working with bucket 4", "insert on step 0",
            "working with bucket 0", "insert on step 0",
            "working with bucket 1", "insert on step 1",
            "working with bucket 1", "found on step 0",
            "working with bucket 2", "found on step 0",
            "working with bucket 3", "found on step 0",
            "working with bucket 4", "found on step 0",
            "working with bucket 0", "found on step 0",
            "working with bucket 1", "found on step 1"
        )
        assertEquals(expectedActions, hashTable.log)
    }

    @Test
    fun `check work with collisions after delete`() {
        val intsToDelete = (1..size + 1).toList().toIntArray() // buckets: 1, 2, 3, 4, 0, 1
        val intsToCheck = (size+2..2*size).toList().toIntArray() // buckets: 2, 3, 4, 0

        val expectedActions = mutableListOf<String>()
        intsToDelete.plus(intsToCheck).forEach { s -> hashTable.put(s, s) }
        expectedActions.addAll(listOf(
            "working with bucket 1", "insert on step 0",
            "working with bucket 2", "insert on step 0",
            "working with bucket 3", "insert on step 0",
            "working with bucket 4", "insert on step 0",
            "working with bucket 0", "insert on step 0",
            "working with bucket 1", "insert on step 1",
            "working with bucket 2", "insert on step 1",
            "working with bucket 3", "insert on step 1",
            "working with bucket 4", "insert on step 1",
            "working with bucket 0", "insert on step 1"
        ))
        intsToDelete.forEach { s -> hashTable.delete(s) }
        expectedActions.addAll(listOf(
            "working with bucket 1", "removed on step 0",
            "working with bucket 2", "removed on step 0",
            "working with bucket 3", "removed on step 0",
            "working with bucket 4", "removed on step 0",
            "working with bucket 0", "removed on step 0",
            "working with bucket 1", "removed on step 0"
        ))
        intsToCheck.forEach { s -> assertEquals(s, hashTable.get(s)) }
        expectedActions.addAll(listOf(
            "working with bucket 2", "found on step 0",
            "working with bucket 3", "found on step 0",
            "working with bucket 4", "found on step 0",
            "working with bucket 0", "found on step 0"
        ))

        assertEquals(expectedActions, hashTable.log)
    }

    @Test
    fun `check correct delete in the middle of sequence`() {
        hashTable = IntHashTable(1)
        val ints = (1..size).toList().toIntArray()

        ints.forEach { i -> hashTable.put(i, i) }
        hashTable.delete(ints[2])
        assertEquals(ints[1], hashTable.get(ints[1]))
        assertEquals(null, hashTable.get(ints[2]))
        assertEquals(ints[3], hashTable.get(ints[3]))

        val expectedActions = listOf(
            "working with bucket 0", "insert on step 0",
            "working with bucket 0", "insert on step 1",
            "working with bucket 0", "insert on step 2",
            "working with bucket 0", "insert on step 3",
            "working with bucket 0", "insert on step 4",
            "working with bucket 0", "removed on step 2",
            "working with bucket 0", "found on step 1",
            "working with bucket 0", "not found in bucket",
            "working with bucket 0", "found on step 2"
        )

        assertEquals(expectedActions, hashTable.log)
    }
}