package cache;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LruCacheTest {

    @Test
    void testSingleInsertionCase() {
        var cache = new LruCache<Integer, Integer>(1);
        assertEquals(1, cache.getCapacity());
        assertEquals(0, cache.getCache().size());
        assertNull(cache.find(404));

        cache.put(42, 42);
        assertEquals(1, cache.getCache().size());
        assertEquals(42, cache.find(42));
    }

    @Test
    void testMultipleInsertionCase() {
        var cache = new LruCache<Integer, Integer>(20);
        cache.put(1, 2);
        cache.put(2, 3);
        cache.put(4, 4);
        cache.put(-100000, 1123);
        cache.put(-52134, 4445);
        assertEquals(5, cache.getCache().size());
        assertNull(cache.find(5));

        assertEquals(2, cache.find(1));
        assertEquals(4, cache.find(4));
        assertEquals(1123, cache.find(-100000));

        assertEquals(3, cache.find(2));
        cache.put(2, 334);
        assertEquals(334, cache.find(2));
    }

    @Test
    void testSingleReplacementCase() {
        var cache = new LruCache<Integer, Integer>(1);
        assertEquals(1, cache.getCapacity());
        assertEquals(0, cache.getCache().size());
        assertNull(cache.find(404));

        cache.put(29, 29);
        assertEquals(1, cache.getCache().size());
        assertEquals(29, cache.find(29));

        cache.put(28, 3);
        assertNull(cache.find(29));
        assertEquals(3, cache.find(28));
    }

    @Test
    void testMultipleReplacement() {
        var cache = new LruCache<Integer, Integer>(4);
        cache.put(1, 42);
        assertEquals(1, cache.getCache().size());
        assertNull(cache.find(2));
        cache.put(2, 256);
        cache.put(-42, -42);
        cache.put(0, -1000);
        assertEquals(4, cache.getCache().size());
        cache.put(-4242, 666);
        assertEquals(4, cache.getCache().size());
        assertNull(cache.find(1));
        assertEquals(256, cache.find(2));
        cache.put(3, 100500);
        assertNull(cache.find(-42));
    }

    @Test
    void testAllocation() {
        var cache = new LruCache<Integer, Integer>(1);
        assertEquals(1, cache.getCapacity());
        assertEquals(0, cache.getCache().size());

        cache.put(0, 3);
        assertEquals(1, cache.getCache().size());
        assertEquals(3, cache.find(0));

        cache.put(0, 4);
        assertEquals(1, cache.getCache().size());
        assertEquals(4, cache.find(0));
    }

    @Test
    void testRandomized() {
        var cache = new LruCache<Integer, Integer>(30);
        var expected = new LinkedHashMap<Integer, Integer>(30, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return this.size() > 30;
            }
        };

        var rand = new Random();
        var keys = new ArrayList<Integer>();

        for (int i = 0; i < 200; i++) {
            keys.add(rand.nextInt());
        }
        var insertionChance = 0.8;
        for (int i = 0; i < 1000000; i++) {
            Collections.shuffle(keys);
            var key = keys.get(0);
            if (rand.nextInt() <= insertionChance) {
                var value = rand.nextInt();
                cache.put(key, value);
                expected.put(key, value);
            } else {
                assertEquals(expected.get(key), cache.find(key));
            }
            assertEquals(expected.size(), cache.getCache().size());
        }
    }
}