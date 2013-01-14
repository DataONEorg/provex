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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


/**
 * This is the parent of all DAO implementation classes.
 * It uses all common generic methods and utilities.
 * The implementation is not thread safe - we assume
 * that the client creates one DAO impl per thread.
 */
public abstract class AbstractDaoImpl<T> extends RootDaoImpl {

    /**
     * The assigned connection.
     */
    protected Connection conn;


    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new DAO implementation.
     * @throws NullPointerException whe the passed connection is null.
     */
    protected AbstractDaoImpl(Connection conn) {
        if (conn == null) {
            throw new NullPointerException("The connection is null");
        }

        this.conn = conn;
    }


    ////////////////////////////////////////////////////////////////////////////
    // Protected
    ////////////////////////////////////////////////////////////////////////////

    protected T findOne( String cond, Object... params) {
        Statement stmt = null;
        ResultSet rs = null;
        String sql = null;

        try {
            sql = getSelectSql( cond );

            debugSql( sql, params );

            if (params != null && params.length > 0) {
                PreparedStatement pstmt = conn.prepareStatement( sql );
                stmt = pstmt;

                stmt.setFetchSize( 1 );

                rs = query( pstmt, params);
            }
            else {
                stmt = conn.createStatement();

                stmt.setFetchSize( 1 );

                rs = stmt.executeQuery( sql );
            }

            return rs.next() ? fetch( rs ) : null;
        }
        catch (SQLException e) {
            errorSql( e, sql, params );

            throw new DBException( e );
        }
        finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        }
    }


    protected T[] findManyArray( String cond, int offset, int count, Object... params) {
        return toArray( findManyImpl( cond, offset, count, params ));
    }


    protected ArrayList<T> findManyList( String cond, int offset, int count, Object... params) {
        return findManyImpl( cond, offset, count, params );
    }


    protected ArrayList<T> findManyImpl( String cond, int offset, int count, Object[] params) {
        Statement stmt = null;
        ResultSet rs = null;
        String sql = null;

        try {
            sql = getSelectSql( cond );

            debugSql( sql, params );

            if (params != null && params.length > 0) {
                PreparedStatement pstmt = conn.prepareStatement( sql );
                stmt = pstmt;

                if (count > 0) {
                    stmt.setFetchSize( offset + count );
                }

                rs = query( pstmt, params);
            }
            else {
                stmt = conn.createStatement();

                if (count > 0) {
                    stmt.setFetchSize( offset + count );
                }

                rs = stmt.executeQuery( sql );
            }

            return fetchList( rs, offset, count );
        }
        catch (SQLException e) {
            errorSql( e, sql, params );

            throw new DBException( e );
        }
        finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        }
    }


    /**
     * New counter - SQL count().
     */
    protected int count( String cond, Object... params) {
        return selectInt( getSelectCountSql() + getSqlCondition( cond ), params );
    }


    protected boolean updateOne( String setstring, String cond, Object... params) throws DaoException {
        int ret = executeUpdate( getUpdateSql( setstring, cond ), params );

        if (ret > 1) {
            throw new DaoException("More than one record updated");
        }

        return ret == 1;
    }


    protected int updateMany( String setstring, String cond, Object... params) throws DaoException {
        return executeUpdate( getUpdateSql( setstring, cond ), params );
    }


    protected boolean deleteOne( String cond, Object... params) throws DaoException {
        String sql = getDeleteSql( cond );
        int ret = executeUpdate( sql, params );

        if (ret > 1) {
            String err = "More than one record deleted";
            log.error( err + " for " + sqlLog( sql, params ));

            throw new DaoException( err );
        }

        return ret == 1;
    }


    protected int deleteMany( String cond, Object... params) throws DaoException {
        return executeUpdate( getDeleteSql( cond ), params );
    }


    protected int executeUpdate( String sql, Object... params) throws DaoException {
        Statement stmt = null;

        debugSql( sql, params );

        try {
            if (params != null && params.length > 0) {
                PreparedStatement pstmt = conn.prepareStatement( sql );
                stmt = pstmt;

                params( pstmt, params);

                return pstmt.executeUpdate();
            }
            else {
                stmt = conn.createStatement();

                return stmt.executeUpdate( sql );
            }
        }
        catch (SQLException e) {
            errorSql( e, sql, params );
            handleException( e );
            throw new DBException( e );
        }
        finally {
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        }
    }


    protected String getSelectSql(String cond) {
        return getSelectSql() + getSqlCondition( cond );
    }


    protected String getSelectSql() {
        return "SELECT " + getSelectColumns() + " FROM " + getTableName();
    }


    protected String getSelectCountSql() {
        return "SELECT count(*) FROM " + getTableName();
    }


    protected String getUpdateSql(String setstring, String cond) {
        return getUpdateSql(setstring) + getSqlCondition( cond );
    }


    protected String getUpdateSql(String setstring) {
        return "UPDATE " + getTableName() + " SET " + setstring;
    }


    protected String getDeleteSql(String cond) {
        return getDeleteSql() + getSqlCondition( cond );
    }


    protected String getDeleteSql() {
        return "DELETE FROM " + getTableName();
    }


    protected String getTruncateSql() {
        return "TRUNCATE TABLE " + getTableName();
    }


    protected String getInsertSelect( String target, String cond ) {
        return "INSERT INTO " + target + " SELECT * FROM " + getTableName() + getSqlCondition( cond );
    }


    /**
     * Returns the condition starting with " WHERE " or an empty string.
     */
    protected String getSqlCondition(String cond) {
        return cond != null && cond.length() > 0 ? (" WHERE " + cond) : "";
    }


    protected abstract String getSelectColumns();
    protected abstract T fetch( ResultSet rs ) throws SQLException;
    protected abstract T[] toArray( ArrayList<T> list );


    protected T[] fetchArray( ResultSet rs, int offset, int count ) throws SQLException {
        return toArray( fetchList( rs, offset, count ));
    }


    protected ArrayList<T> fetchList( ResultSet rs, int offset, int count ) throws SQLException {
        // next must be called at least once:
        while (offset-- >= 0) {
            if (!rs.next()) {
                return new ArrayList<T>( 0 );
            }
        }

        if (count < 1) {
            count = Integer.MAX_VALUE;
        }

        ArrayList<T> ret = new ArrayList<T>( count < 100 ? count : 100 );

        do {
            ret.add( fetch( rs ));
        }
        while (rs.next() && --count > 0);

        return ret;
    }


    protected Short selectShort( String sql, Object... params) {
        Integer i = selectInt( sql, params );
        return i != null ? i.shortValue() : null;
    }


    protected Integer selectInt( String sql, Object... params) {
        Statement stmt = null;
        ResultSet rs = null;

        debugSql( sql, params );

        try {
            if (params != null && params.length > 0) {
                PreparedStatement pstmt = conn.prepareStatement( sql );
                stmt = pstmt;

                rs = query( pstmt, params);
            }
            else {
                stmt = conn.createStatement();
                rs = stmt.executeQuery( sql );
            }

            return rs.next() ? rs.getInt( 1 ) : null;
        }
        catch (SQLException e) {
            errorSql( e, sql, params );
            throw new DBException( e );
        }
        finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        }
    }


    protected Long selectLong( String sql, Object... params) {
        Statement stmt = null;
        ResultSet rs = null;

        debugSql( sql, params );

        try {
            if (params != null && params.length > 0) {
                PreparedStatement pstmt = conn.prepareStatement( sql );
                stmt = pstmt;

                rs = query( pstmt, params);
            }
            else {
                stmt = conn.createStatement();
                rs = stmt.executeQuery( sql );
            }

            return rs.next() ? rs.getLong( 1 ) : null;
        }
        catch (SQLException e) {
            errorSql( e, sql, params );
            throw new DBException( e );
        }
        finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        }
    }


    protected void handleException( SQLException e ) throws DaoException {
        String sqlState = e.getSQLState();

        if (sqlState == null) {
            throw new DBException( e );
        }

        if (sqlState.startsWith("23")) {
            throw new DaoException("Integrity constraint violation: " + e.getMessage());
        }

        throw new DBException( e );
    }


    ////////////////////////////////////////////////////////////////////////////
    // Private
    ////////////////////////////////////////////////////////////////////////////

    private ResultSet query( PreparedStatement pstmt, Object[] params) throws SQLException {
        params( pstmt, params );

        return pstmt.executeQuery();
    }


    /**
     * We skip null values because our XSL works so.
     */
    private void params( PreparedStatement pstmt, Object[] params) throws SQLException {
        int i = 1;
        for (Object param : params) {
            if (param != null) {
                pstmt.setObject( i++, param );
            }
        }
    }


}
