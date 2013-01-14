/*
 * Copyright 2010 Spolecne s.r.o. (www.spoledge.com)
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
package com.spoledge.audao.db.dao;


/**
 * Chains caches.
 */
public class ChainedDtoCache<K,V> implements DtoCache<K,V> {

    private DtoCache<K,V> level1;
    private DtoCache<K,V> level2;


    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    public ChainedDtoCache( DtoCache<K,V> level1, DtoCache<K, V> level2 ) {
        this.level1 = level1;
        this.level2 = level2;
    }


    ////////////////////////////////////////////////////////////////////////////
    // DtoCache
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the associated object or null.
     */
    public V get( K key ) {
        V ret = level1.get( key );

        if (ret != null) return ret;

        ret = level2.get( key );

        if (ret != null) {
            level1.put( key, ret );
        }

        return ret;
    }


    /**
     * Puts the key/value pair into the cache.
     */
    public void put( K key, V value ) {
        level1.put( key, value );
        level2.put( key, value );
    }


    /**
     * Removes the associated object.
     */
    public void remove( K key ) {
        level1.remove( key );
        level2.remove( key );
    }


    /**
     * Clears the cache.
     */
    public void clear() {
        level1.clear();
        level2.clear();
    }

}

