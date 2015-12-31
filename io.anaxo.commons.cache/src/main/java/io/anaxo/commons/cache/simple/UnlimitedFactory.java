/*
 * Copyright 2005 Werner Guttmann, Ralf Joachim
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

import io.anaxo.commons.cache.base.AbstractCacheFactory;
import io.anaxo.commons.cache.base.Cache;
import io.anaxo.commons.cache.base.CacheFactory;

/**
 * Implements {@link CacheFactory} for the {@link Unlimited}
 * implementation of {@link Cache}.
 *
 * @param <K> the type of keys maintained by cache
 * @param <V> the type of cached values
 *
 * @author <a href="mailto:werner DOT guttmann AT gmx DOT net">Werner Guttmann</a>
 * @author <a href="mailto:ralf DOT joachim AT syscon DOT eu">Ralf Joachim</a>
 * @version $Revision: 9041 $ $Date: 2011-08-16 11:51:17 +0200 (Di, 16 Aug 2011) $
 * @since 1.0
 */
public final class UnlimitedFactory<K, V> extends AbstractCacheFactory<K, V> {
    /**
     * {@inheritDoc}
     */
    public String getCacheType() { return Unlimited.TYPE; }
    
    /**
     * {@inheritDoc}
     */
    public String getCacheClassName() { return Unlimited.class.getName(); }
}
