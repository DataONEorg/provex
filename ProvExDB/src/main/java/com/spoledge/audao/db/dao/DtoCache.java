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
 * This is an abstract cache used for caching DTOs.
 * A DTO can be cached by its primary key or by a query key.
 * The value can be a single DTO or an array/list of DTOs.
 */
public interface DtoCache<K,V> {

    /**
     * Returns the associated object or null.
     */
    public V get( K key );


    /**
     * Puts the key/value pair into the cache.
     */
    public void put( K key, V value );


    /**
     * Removes the associated object.
     */
    public void remove( K key );


    /**
     * Clears the cache.
     */
    public void clear();

}
