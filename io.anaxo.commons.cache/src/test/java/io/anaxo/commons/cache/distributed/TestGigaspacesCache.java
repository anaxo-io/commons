/*
 * Copyright 2005 Ralf Joachim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.anaxo.commons.cache.distributed;

import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import io.anaxo.commons.cache.base.Cache;
import io.anaxo.commons.cache.base.CacheAcquireException;

/**
 * JUnit test case for Gigaspaces cache 
 * @author <a href="mailto:werner DOT guttmann AT gmx DOT net">Werner Guttmann</a>
 * @version $Revision: 9041 $ $Date: 2011-08-16 11:51:17 +0200 (Di, 16 Aug 2011) $
 * @since 1.0.1
 */
public final class TestGigaspacesCache extends TestCase {
    private static final boolean DISABLE_LOGGING = true;
    
    public static Test suite() {
        TestSuite suite = new TestSuite("Gigaspaces Tests");

        suite.addTest(new TestGigaspacesCache("testStaticProperties"));
        suite.addTest(new TestGigaspacesCache("testConstructor"));
        suite.addTest(new TestGigaspacesCache("testGetType"));
        suite.addTest(new TestGigaspacesCache("testInitialize"));
        suite.addTest(new TestGigaspacesCache("testClose"));
//        suite.addTest(new TestGigaspacesCache("testGetOnEmptyCache"));
//        suite.addTest(new TestGigaspacesCache("testPutAndGet"));
//        suite.addTest(new TestGigaspacesCache("testPutAndGetAndRemove"));

        return suite;
    }

    public TestGigaspacesCache(final String name) { 
        super(name); 
    }

    public void testStaticProperties() {
        assertEquals("gigaspaces", GigaspacesCache.TYPE);
        assertEquals("com.j_spaces.map.CacheFinder", GigaspacesCache.IMPLEMENTATION);
    }

    public void testConstructor() {
        Cache<String, String> c = new GigaspacesCache<String, String>();
        assertTrue(c instanceof GigaspacesCache);
    }

    public void testGetType() {
        Cache<String, String> c = new GigaspacesCache<String, String>();
        assertEquals("gigaspaces", c.getType());
    }

    public void testInitialize() {
        Logger logger = Logger.getLogger(GigaspacesCache.class);
        Level level = logger.getLevel();
        
        GigaspacesCache<String, String> c = new GigaspacesCache<String, String>();
        int counter = DistributedCacheFactoryMock.getCounter();
        
        Properties params = new Properties();
        params.put(Cache.PARAM_NAME, "dummy cache");
        
        if (DISABLE_LOGGING) { logger.setLevel(Level.FATAL); }

        try {
            DistributedCacheFactoryMock.setException(new Exception("dummy"));
            c.initialize(DistributedCacheFactoryMock.class.getName(), params);
            fail("Failed to trow exception at initialize of CoherenceCache instance");
        } catch (CacheAcquireException ex) {
            assertEquals(counter, DistributedCacheFactoryMock.getCounter());
        }
        
        logger.setLevel(level);
        
        try {
            DistributedCacheFactoryMock.setException(null);
            c.initialize(DistributedCacheFactoryMock.class.getName(), params);
            assertEquals(counter + 1, DistributedCacheFactoryMock.getCounter());
            assertEquals("/./dummy cache?", DistributedCacheFactoryMock.getName());
            assertEquals("dummy cache", c.getName());
        } catch (CacheAcquireException ex) {
            fail("Failed to initialize GigaspacesCache instance");
        }
    }

    public void testClose() {
        Logger logger = Logger.getLogger(GigaspacesCache.class);
        Level level = logger.getLevel();
        
        GigaspacesCache<String, String> c = new GigaspacesCache<String, String>();
        int counter = DistributedCacheMock.getCounter();
        
        c.close();
        assertEquals(counter, DistributedCacheMock.getCounter());
        
        Properties params = new Properties();
        params.put(Cache.PARAM_NAME, "dummy gigaspaces cache");
        
        try {
            DistributedCacheFactoryMock.setException(null);
            c.initialize(DistributedCacheFactoryMock.class.getName(), params);
            assertEquals(counter, DistributedCacheMock.getCounter());
        } catch (CacheAcquireException ex) {
            fail("Failed to initialize GigaspacesCache instance");
        }
        
        if (DISABLE_LOGGING) { logger.setLevel(Level.FATAL); }
        
        DistributedCacheMock.setException(new Exception("dummy"));
        c.close();
        assertEquals(counter, DistributedCacheMock.getCounter());
        
        logger.setLevel(level);
        
        DistributedCacheMock.setException(null);
        c.close();
        assertEquals(counter, DistributedCacheMock.getCounter());
    }

//    private Cache initialize() throws CacheAcquireException {
//        
//        GigaspacesCacheFactory factory = new GigaspacesCacheFactory();
//        Cache c = factory.getCache(getClass().getClassLoader());
//        
//        Properties params = new Properties();
//        params.put(Cache.PARAM_NAME, "dummy gigaspaces cache");
//
//        c.initialize(params);
//        
//        return c;
//    }
//    
//    public void testGetOnEmptyCache() throws CacheAcquireException {
//        
//        Cache c = initialize();
//        
//        Object value = c.get("first key");
//        assertNull(value);
//    }    
//
//    public void testPutAndGet() throws CacheAcquireException {
//        
//        Cache c = initialize();
//        
//        c.put("first key", "first value");
//        Object value = c.get("first key");
//        assertNotNull(value);
//    }    
//
//    public void testPutAndGetAndRemove() throws CacheAcquireException {
//        
//        Cache c = initialize();
//        
//        c.put("first key", "first value");
//        
//        Object value = c.get("first key");
//        assertNotNull(value);
//        
//        c.remove("first key");
//        value = c.get("first key");
//        assertNull(value);
//        
//    }    
}
