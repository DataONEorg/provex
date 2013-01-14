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

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Memory DtoCache uses LinkedHashMap to store the values (LRU cache).
 * The cached objects never expires - this implementation saes the memory.
 */
public class MemoryDtoCacheImpl<K,V> implements DtoCache<K,V> {

    protected final int maxSize;
    protected final LHM<K,V> map;


    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    public MemoryDtoCacheImpl( int maxSize ) {
        this( maxSize, (maxSize < 100 ? maxSize : 100 ) * 4 / 3 + 1, 0.75f );
    }


    public MemoryDtoCacheImpl( int maxSize, int initialCapacity, float loadFactor ) {
        this.maxSize = maxSize;
        this.map = createMap( initialCapacity, loadFactor );
    }


    ////////////////////////////////////////////////////////////////////////////
    // DtoCache
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the associated object or null.
     */
    public final synchronized V get( K key ) {
        return map.get( key );
    }


    /**
     * Puts the key/value pair into the cache.
     */
    public final synchronized void put( K key, V value ) {
        map.put( key, value );
    }


    /**
     * Removes the associated object.
     */
    public final synchronized void remove( K key ) {
        map.remove( key );
    }


    /**
     * Clears the cache.
     */
    public final synchronized void clear() {
        map.clear();
    }


    ////////////////////////////////////////////////////////////////////////////
    // Protected
    ////////////////////////////////////////////////////////////////////////////

    protected LHM<K,V> createMap( int initialCapacity, float loadFactor ) {
        return new LHM<K,V>( initialCapacity, loadFactor );
    }


    ////////////////////////////////////////////////////////////////////////////
    // Inner
    ////////////////////////////////////////////////////////////////////////////

    protected class LHM<K,V> extends LinkedHashMap<K,V> {
        protected LHM( int initialCapacity, float loadFactor ) {
            super( initialCapacity, loadFactor, true );
        }

        @Override
        protected boolean removeEldestEntry( Map.Entry<K,V> eldest ) {
            return size() > maxSize;
        }
    }
}

