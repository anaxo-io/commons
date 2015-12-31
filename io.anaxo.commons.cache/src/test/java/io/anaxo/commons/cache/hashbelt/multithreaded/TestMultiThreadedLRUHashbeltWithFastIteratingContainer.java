package io.anaxo.commons.cache.hashbelt.multithreaded;

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

import io.anaxo.commons.cache.base.Cache;
import io.anaxo.commons.cache.base.CacheAcquireException;
import io.anaxo.commons.cache.hashbelt.AbstractHashbelt;
import io.anaxo.commons.cache.hashbelt.LRUHashbelt;
import io.anaxo.commons.cache.hashbelt.container.FastIteratingContainer;

/**
 * Test multithreaded LRUHashbelt with FastIteratingContainer.
 */
public final class TestMultiThreadedLRUHashbeltWithFastIteratingContainer
        extends AbstractTestMultiThreadedHashbelt {

    public static Test suite() {
        TestSuite suite = new TestSuite("MultiThreadedLRUHashbeltWithFastIteratingContainer Tests");

        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testPutThenGet"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testGetThenPut"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testContainsKey"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testContainsValue"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testClear"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testSize"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testIsEmpty"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testRemove"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testPutAll"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testKeySet"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testValues"));
        suite.addTest(new TestMultiThreadedLRUHashbeltWithFastIteratingContainer(
                "testEntrySet"));

        return suite;
    }

    public TestMultiThreadedLRUHashbeltWithFastIteratingContainer(final String name) {
        super(name);
    }

    @Override
    protected Cache<String, String> initialize() throws CacheAcquireException {
        Cache<String, String> cache = new LRUHashbelt<String, String>();
        Properties params = new Properties();
        params.put(Cache.PARAM_NAME, "dummy1");
        params.put(AbstractHashbelt.PARAM_CONTAINER_CLASS, FastIteratingContainer.class.getName());
        cache.initialize(params);

        return cache;
    }

}
