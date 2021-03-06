/*
 * This file was generated - do not edit it directly !!
 * Generated by AuDAO tool, a product of Spolecne s.r.o.
 * For more information please visit http://www.spoledge.com
 */
package org.dataone.daks.provexdb.DAO.models.dao.mysql;

import java.sql.Connection;

import org.dataone.daks.provexdb.DAO.models.dao.*;


/**
 * This is the main implementation class for obtaining DAO objects.
 * @author generated
 */
public class DaoFactoryImpl extends DaoFactory.Factory {
    public EdgeDao createEdgeDao( Connection conn ) {
        return new EdgeDaoImpl( conn );
    }

    public PolicyViolationDao createPolicyViolationDao( Connection conn ) {
        return new PolicyViolationDaoImpl( conn );
    }

    public ProvenanceStageDao createProvenanceStageDao( Connection conn ) {
        return new ProvenanceStageDaoImpl( conn );
    }

    public TcDao createTcDao( Connection conn ) {
        return new TcDaoImpl( conn );
    }

    public TcTempDao createTcTempDao( Connection conn ) {
        return new TcTempDaoImpl( conn );
    }

    public UserRequestDetailDao createUserRequestDetailDao( Connection conn ) {
        return new UserRequestDetailDaoImpl( conn );
    }

    public DataDao createDataDao( Connection conn ) {
        return new DataDaoImpl( conn );
    }

    public ActorDao createActorDao( Connection conn ) {
        return new ActorDaoImpl( conn );
    }

    public UserRequestMasterDao createUserRequestMasterDao( Connection conn ) {
        return new UserRequestMasterDaoImpl( conn );
    }

}
