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


import org.dataone.daks.provexdb.DAO.models.dao.ActorDao;
import org.dataone.daks.provexdb.DAO.models.dto.Actor;


/**
 * This is the DAO imlementation class.
 *
 * @author generated
 */
public class ActorDaoImpl extends AbstractDaoImpl<Actor> implements ActorDao {

    private static final String TABLE_NAME = "actor";

    protected static final String SELECT_COLUMNS = "nodeId, nodeType, nodeDesc, valType, val, ActorId";

    private static final String SQL_INSERT = "INSERT INTO actor (nodeId,nodeType,nodeDesc,valType,val,ActorId) VALUES (?,?,?,?,?,?)";

    public ActorDaoImpl( Connection conn ) {
        super( conn );
    }

    /**
     * Finds a record.
     */
    public Actor findByNodeId( String nodeId ) {
        return findOne( "nodeId=?", nodeId);
    }

    /**
     * Inserts a new record.
     */
    public void insert( Actor dto ) throws DaoException {
        PreparedStatement stmt = null;

        debugSql( SQL_INSERT, dto );

        try {
            stmt = conn.prepareStatement( SQL_INSERT );

            if ( dto.getNodeId() == null ) {
                throw new DaoException("Value of column 'nodeId' cannot be null");
            }
            checkMaxLength( "nodeId", dto.getNodeId(), 32 );
            stmt.setString( 1, dto.getNodeId() );

            if ( dto.getNodeType() == null ) {
                throw new DaoException("Value of column 'nodeType' cannot be null");
            }
            checkMaxLength( "nodeType", dto.getNodeType(), 1 );
            stmt.setString( 2, dto.getNodeType() );

            if ( dto.getNodeDesc() == null ) {
                throw new DaoException("Value of column 'nodeDesc' cannot be null");
            }
            checkMaxLength( "nodeDesc", dto.getNodeDesc(), 100 );
            stmt.setString( 3, dto.getNodeDesc() );

            if ( dto.getValType() == null ) {
                throw new DaoException("Value of column 'valType' cannot be null");
            }
            checkMaxLength( "valType", dto.getValType(), 1 );
            stmt.setString( 4, dto.getValType() );

            if ( dto.getVal() != null ) {
                checkMaxLength( "val", dto.getVal(), 2000 );
            }
            stmt.setString( 5, dto.getVal() );

            if ( dto.getActorId() == null ) {
                throw new DaoException("Value of column 'ActorId' cannot be null");
            }
            checkMaxLength( "ActorId", dto.getActorId(), 32 );
            stmt.setString( 6, dto.getActorId() );

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

    protected Actor fetch( ResultSet rs ) throws SQLException {
        Actor dto = new Actor();
        dto.setNodeId( rs.getString( 1 ));
        dto.setNodeType( rs.getString( 2 ));
        dto.setNodeDesc( rs.getString( 3 ));
        dto.setValType( rs.getString( 4 ));
        dto.setVal( rs.getString( 5 ));
        dto.setActorId( rs.getString( 6 ));

        return dto;
    }

    protected Actor[] toArray(ArrayList<Actor> list ) {
        Actor[] ret = new Actor[ list.size() ];
        return list.toArray( ret );
    }

}
