import org.junit.Test
import kotlin.test.assertEquals

internal class LRUCacheTest {
    private val cache = LRUCache<Int, Int>(2)

    @Test
    fun testGet() {
        val expected = 42
        val key = 1
        val value = 42
        cache.put(key, value)
        assertEquals(expected, cache.get(key))
    }

    @Test
    fun testSample0() {
        assertEquals(null, cache.get(2))
        cache.put(2, 6)
        assertEquals(null, cache.get(1))
        cache.put(1, 5)
        cache.put(1, 2)
        assertEquals(2, cache.get(1))
        assertEquals(6, cache.get(2))
    }

    @Test
    fun testSample1() {
        val cache = LRUCache<Int, Int>(2)
        cache.put(2, 1)
        cache.put(1, 1)
        cache.put(2, 3)
        assertEquals(3, cache.get(2))
        cache.put(4, 1)
        assertEquals(null, cache.get(1))
        assertEquals(3, cache.get(2))
    }

    @Test
    fun testSample2() {
        cache.put(1, 1)
        cache.put(2, 1)
        cache.put(3, 2)
        assertEquals(null, cache.get(1))
    }

    @Test
    fun testSample3() {
        cache.put(1, 0)
        cache.put(2, 2)
        assertEquals(0, cache.get(1))
        cache.put(3, 3)
        assertEquals(null, cache.get(2))
        cache.put(4, 4)
        assertEquals(null, cache.get(1))
        assertEquals(3, cache.get(3))
        assertEquals(4, cache.get(4))
    }

    @Test
    fun testSample4() {
        cache.put(2, 1)
        cache.put(1, 1)
        cache.put(2, 3)
        assertEquals(3, cache.get(2))
        cache.put(4, 1)
        assertEquals(null, cache.get(1))
        assertEquals(3, cache.get(2))
    }

    @Test
    fun testSample5() {
        cache.put(1, 1)
        cache.put(2, 2)
        assertEquals(1, cache.get(1))
        cache.put(3, 3)
        assertEquals(null, cache.get(2))
        cache.put(4, 4)
        assertEquals(null, cache.get(1))
        assertEquals(3, cache.get(3))
        assertEquals(4, cache.get(4))
    }

    @Test
    fun testSample6() {
        cache.put(2, 1)
        cache.put(1, 1)
        cache.put(2, 3)
        cache.put(4, 1)
        assertEquals(null, cache.get(1))
        assertEquals(3, cache.get(2))
    }

    @Test
    fun bigTestSample() {
        val cap = 25
        val cache = LRUCache<Int, Int>(cap)
        for (i in 0 until cap * 100) {
            cache.put(i, i)
            if (i >= cap) {
                assertEquals(null, cache.get(i - cap))
            }
        }
    }
}