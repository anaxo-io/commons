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
 * @author <a href="mailto:ralf DOT joachim AT syscon DOT eu">Ralf Joachim</a>
 * @version $Revision: 9041 $ $Date: 2011-08-16 11:51:17 +0200 (Di, 16 Aug 2011) $
 * @since 1.0
 */
public final class TestJCache extends TestCase {
    private static final boolean DISABLE_LOGGING = true;
    
    public static Test suite() {
        TestSuite suite = new TestSuite("JCache Tests");

        suite.addTest(new TestJCache("testStaticProperties"));
        suite.addTest(new TestJCache("testConstructor"));
        suite.addTest(new TestJCache("testGetType"));
        suite.addTest(new TestJCache("testInitialize"));
        suite.addTest(new TestJCache("testClose"));

        return suite;
    }

    public TestJCache(final String name) { super(name); }

    public void testStaticProperties() {
        assertEquals("jcache", JCache.TYPE);
        assertEquals("javax.util.jcache.CacheAccessFactory", JCache.IMPLEMENTATION);
    }

    public void testConstructor() {
        Cache<String, String> c = new JCache<String, String>();
        assertTrue(c instanceof JCache);
    }

    public void testGetType() {
        Cache<String, String> c = new JCache<String, String>();
        assertEquals("jcache", c.getType());
    }

    public void testInitialize() {
        Logger logger = Logger.getLogger(JCache.class);
        Level level = logger.getLevel();
        
        JCache<String, String> c = new JCache<String, String>();
        int counter = DistributedCacheFactoryMock.getCounter();
        
        Properties params = new Properties();
        params.put(Cache.PARAM_NAME, "dummy jcache");
        
        if (DISABLE_LOGGING) { logger.setLevel(Level.FATAL); }

        try {
            DistributedCacheFactoryMock.setException(new Exception("dummy"));
            c.initialize(DistributedCacheFactoryMock.class.getName(), params);
            fail("Failed to trow exception at initialize of JCache instance");
        } catch (CacheAcquireException ex) {
            assertEquals(counter, DistributedCacheFactoryMock.getCounter());
        }
        
        logger.setLevel(level);
        
        try {
            DistributedCacheFactoryMock.setException(null);
            c.initialize(DistributedCacheFactoryMock.class.getName(), params);
            assertEquals(counter + 2, DistributedCacheFactoryMock.getCounter());
            assertEquals("dummy jcache", DistributedCacheFactoryMock.getName());
            assertEquals("dummy jcache", c.getName());
        } catch (CacheAcquireException ex) {
            fail("Failed to initialize JCache instance");
        }
    }

    public void testClose() {
        Cache<String, String> c = new JCache<String, String>();
        int counter = DistributedCacheMock.getCounter();
        
        c.close();
        assertEquals(counter, DistributedCacheMock.getCounter());
    }
}
