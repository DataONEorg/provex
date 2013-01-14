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
 * Expiring Memory DtoCache uses LinkedHashMap to store the values (LRU cache).
 * The cached objects expires.
 */
public class ExpiringMemoryDtoCacheImpl<K,V> implements DtoCache<K,V> {

    protected final long expireMillis;
    protected final int maxSize;
    protected final LHM<K,Entry<V>> map;


    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    public ExpiringMemoryDtoCacheImpl( long expireMillis, int maxSize ) {
        this( expireMillis, maxSize, (maxSize < 100 ? maxSize : 100 ) * 4 / 3 + 1, 0.75f );
    }


    public ExpiringMemoryDtoCacheImpl( long expireMillis, int maxSize, int initialCapacity, float loadFactor ) {
        this.expireMillis = expireMillis;
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
        Entry<V> entry = map.get( key );

        if (entry == null) return null;

        if (entry.isExpired()) {
            map.remove( key );
            return null;
        }

        return entry.value;
    }


    /**
     * Puts the key/value pair into the cache.
     */
    public final synchronized void put( K key, V value ) {
        map.put( key, new Entry<V>( value, expireMillis ));
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

    protected LHM<K,Entry<V>> createMap( int initialCapacity, float loadFactor ) {
        return new LHM<K,Entry<V>>( initialCapacity, loadFactor );
    }


    ////////////////////////////////////////////////////////////////////////////
    // Inner
    ////////////////////////////////////////////////////////////////////////////

    protected class LHM<K,E> extends LinkedHashMap<K,E> {
        protected LHM( int initialCapacity, float loadFactor ) {
            super( initialCapacity, loadFactor, true );
        }

        @Override
        protected boolean removeEldestEntry( Map.Entry<K,E> eldest ) {
            return size() > maxSize;
        }
    }


    protected static class Entry<V> {
        protected long expire;
        protected V value;

        protected Entry( V value, long delta ) {
            this.value = value;
            this.expire = System.currentTimeMillis() + delta;
        }

        protected boolean isExpired() {
            return this.expire <= System.currentTimeMillis();
        }
    }
}


