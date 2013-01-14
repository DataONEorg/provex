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
 * This exception is thrown when DAO detects an application error.
 * For example when the minimum/maximum field length is reached (insert/update)
 * or when an integrity constraint error is detected.
 */
public class DaoException extends Exception {

    /**
     * GWT needs this.
     */
    DaoException() {
    }

    public DaoException(String message) {
        super( message );
    }

    public DaoException(String message, Throwable cause) {
        super( message, cause );
    }

    public DaoException(Throwable cause) {
        super( cause );
    }

}
