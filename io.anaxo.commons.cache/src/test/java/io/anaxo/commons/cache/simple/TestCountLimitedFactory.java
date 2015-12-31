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
package io.anaxo.commons.cache.simple;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import io.anaxo.commons.cache.base.Cache;
import io.anaxo.commons.cache.base.CacheAcquireException;
import io.anaxo.commons.cache.base.CacheFactory;

/**
 * @author <a href="mailto:ralf DOT joachim AT syscon DOT eu">Ralf Joachim</a>
 * @version $Revision: 9041 $ $Date: 2011-08-16 11:51:17 +0200 (Di, 16 Aug 2011) $
 * @since 1.0
 */
public final class TestCountLimitedFactory extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite("CountLimitedFactory Tests");

        suite.addTest(new TestCountLimitedFactory("testConstructor"));
        suite.addTest(new TestCountLimitedFactory("testGetCacheType"));
        suite.addTest(new TestCountLimitedFactory("testGetCacheClassName"));
        suite.addTest(new TestCountLimitedFactory("testGetCache"));
        suite.addTest(new TestCountLimitedFactory("testShutdown"));

        return suite;
    }

    public TestCountLimitedFactory(final String name) { super(name); }

    public void testConstructor() {
        CacheFactory<String, String> cf = new CountLimitedFactory<String, String>();
        assertTrue(cf instanceof CountLimitedFactory);
    }

    public void testGetCacheType() {
        CacheFactory<String, String> cf = new CountLimitedFactory<String, String>();
        assertEquals("count-limited", cf.getCacheType());
    }

    public void testGetCacheClassName() {
        CacheFactory<String, String> cf = new CountLimitedFactory<String, String>();
        String classname = "io.anaxo.commons.cache.simple.CountLimited";
        assertEquals(classname, cf.getCacheClassName());
    }

    public void testGetCache() {
        CacheFactory<String, String> cf = new CountLimitedFactory<String, String>();
        try {
            Cache<String, String> c = cf.getCache(null);
            assertTrue(c instanceof CountLimited);
        } catch (CacheAcquireException ex) {
            fail("Failed to get instance of CountLimited from factroy");
        }
    }

    public void testShutdown() {
        CacheFactory<String, String> cf = new CountLimitedFactory<String, String>();
        cf.shutdown();
    }
}
