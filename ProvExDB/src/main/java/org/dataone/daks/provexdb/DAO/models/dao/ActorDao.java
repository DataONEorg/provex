/*
 * This file was generated - do not edit it directly !!
 * Generated by AuDAO tool, a product of Spolecne s.r.o.
 * For more information please visit http://www.spoledge.com
 */
package org.dataone.daks.provexdb.DAO.models.dao;

import java.sql.Date;
import java.sql.Timestamp;

import com.spoledge.audao.db.dao.AbstractDao;
import com.spoledge.audao.db.dao.DaoException;

import org.dataone.daks.provexdb.DAO.models.dto.Actor;


/**
 * This is the DAO.
 *
 * @author generated
 */
public interface ActorDao extends AbstractDao {

    /**
     * Finds a record.
     */
    public Actor findByNodeId( String nodeId );

    /**
     * Inserts a new record.
     */
    public void insert( Actor dto ) throws DaoException;

}
