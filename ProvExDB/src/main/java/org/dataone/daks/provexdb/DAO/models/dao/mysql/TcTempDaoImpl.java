/*
 * This file was generated - do not edit it directly !!
 * Generated by AuDAO tool, a product of Spolecne s.r.o.
 * For more information please visit http://www.spoledge.com
 */
package org.dataone.daks.provexdb.DAO.models.dao.mysql;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;

import com.spoledge.audao.db.dao.AbstractDaoImpl;
import com.spoledge.audao.db.dao.DBException;
import com.spoledge.audao.db.dao.DaoException;


import org.dataone.daks.provexdb.DAO.models.dao.TcTempDao;
import org.dataone.daks.provexdb.DAO.models.dto.TcTemp;


/**
 * This is the DAO imlementation class.
 *
 * @author generated
 */
public class TcTempDaoImpl extends AbstractDaoImpl<TcTemp> implements TcTempDao {

    private static final String TABLE_NAME = "tc_temp";

    protected static final String SELECT_COLUMNS = "startNodeId, endNodeId, inRound";

    private static final String SQL_INSERT = "INSERT INTO tc_temp (startNodeId,endNodeId,inRound) VALUES (?,?,?)";

    public TcTempDaoImpl( Connection conn ) {
        super( conn );
    }

    /**
     * Inserts a new record.
     */
    public void insert( TcTemp dto ) throws DaoException {
        PreparedStatement stmt = null;

        debugSql( SQL_INSERT, dto );

        try {
            stmt = conn.prepareStatement( SQL_INSERT );

            if ( dto.getStartNodeId() == null ) {
                throw new DaoException("Value of column 'startNodeId' cannot be null");
            }
            checkMaxLength( "startNodeId", dto.getStartNodeId(), 32 );
            stmt.setString( 1, dto.getStartNodeId() );

            if ( dto.getEndNodeId() != null ) {
                checkMaxLength( "endNodeId", dto.getEndNodeId(), 32 );
            }
            stmt.setString( 2, dto.getEndNodeId() );

            if ( dto.getInRound() == null ) {
                stmt.setNull( 3, Types.INTEGER );
            }
            else {
                stmt.setInt( 3, dto.getInRound() );
            }

            int n = stmt.executeUpdate();
        }
        catch (SQLException e) {
            errorSql( e, SQL_INSERT, dto );
            handleException( e );
            throw new DBException( e );
        }
        finally {
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        }
    }

    /**
     * Returns the table name.
     */
    public String getTableName() {
        return TABLE_NAME;
    }

    protected String getSelectColumns() {
        return SELECT_COLUMNS;
    }

    protected TcTemp fetch( ResultSet rs ) throws SQLException {
        TcTemp dto = new TcTemp();
        dto.setStartNodeId( rs.getString( 1 ));
        dto.setEndNodeId( rs.getString( 2 ));
        dto.setInRound( rs.getInt( 3 ));

        if ( rs.wasNull()) {
            dto.setInRound( null );
        }


        return dto;
    }

    protected TcTemp[] toArray(ArrayList<TcTemp> list ) {
        TcTemp[] ret = new TcTemp[ list.size() ];
        return list.toArray( ret );
    }

}
