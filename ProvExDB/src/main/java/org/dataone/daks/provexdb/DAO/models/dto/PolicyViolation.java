/*
 * This file was generated - do not edit it directly !!
 * Generated by AuDAO tool, a product of Spolecne s.r.o.
 * For more information please visit http://www.spoledge.com
 */
package org.dataone.daks.provexdb.DAO.models.dto;

import com.spoledge.audao.db.dto.AbstractDto;

/**
 * This is a DTO class.
 *
 * @author generated
 */
public class PolicyViolation extends AbstractDto {

    ////////////////////////////////////////////////////////////////////////////
    // Static
    ////////////////////////////////////////////////////////////////////////////

    public static final String TABLE = "policy_violation";

    ////////////////////////////////////////////////////////////////////////////
    // Attributes
    ////////////////////////////////////////////////////////////////////////////

    private String traceId;
    private String reqId;
    private String stageId;
    private String startNodeId;
    private String endNodeId;
    private String policyType;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new empty DTO.
     */
    public PolicyViolation() {
    }

    ////////////////////////////////////////////////////////////////////////////
    // Public
    ////////////////////////////////////////////////////////////////////////////

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId( String _val) {
        this.traceId = _val;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId( String _val) {
        this.reqId = _val;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId( String _val) {
        this.stageId = _val;
    }

    public String getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId( String _val) {
        this.startNodeId = _val;
    }

    public String getEndNodeId() {
        return endNodeId;
    }

    public void setEndNodeId( String _val) {
        this.endNodeId = _val;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType( String _val) {
        this.policyType = _val;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Uses 'columns' equality type.
     */
    @Override
    public boolean equals( Object _other ) {
        if (_other == this) return true;
        if (_other == null || (!(_other instanceof PolicyViolation))) return false;

        PolicyViolation _o = (PolicyViolation) _other;

        if ( traceId == null ) {
            if ( _o.traceId != null ) return false;
        }
        else if ( _o.traceId == null || !traceId.equals( _o.traceId )) return false;

        if ( reqId == null ) {
            if ( _o.reqId != null ) return false;
        }
        else if ( _o.reqId == null || !reqId.equals( _o.reqId )) return false;

        if ( stageId == null ) {
            if ( _o.stageId != null ) return false;
        }
        else if ( _o.stageId == null || !stageId.equals( _o.stageId )) return false;

        if ( startNodeId == null ) {
            if ( _o.startNodeId != null ) return false;
        }
        else if ( _o.startNodeId == null || !startNodeId.equals( _o.startNodeId )) return false;

        if ( endNodeId == null ) {
            if ( _o.endNodeId != null ) return false;
        }
        else if ( _o.endNodeId == null || !endNodeId.equals( _o.endNodeId )) return false;

        if ( policyType == null ) {
            if ( _o.policyType != null ) return false;
        }
        else if ( _o.policyType == null || !policyType.equals( _o.policyType )) return false;

        return true;
    }

    /**
     * Returns a hash code value for the object.
     */
    @Override
    public int hashCode() {
        int _ret = -301216781; // = "PolicyViolation".hashCode()
        _ret += traceId == null ? 0 : traceId.hashCode();
        _ret = 29 * _ret + (reqId == null ? 0 : reqId.hashCode());
        _ret = 29 * _ret + (stageId == null ? 0 : stageId.hashCode());
        _ret = 29 * _ret + (startNodeId == null ? 0 : startNodeId.hashCode());
        _ret = 29 * _ret + (endNodeId == null ? 0 : endNodeId.hashCode());
        _ret = 29 * _ret + (policyType == null ? 0 : policyType.hashCode());

        return _ret;
    }


    ////////////////////////////////////////////////////////////////////////////
    // Protected
    ////////////////////////////////////////////////////////////////////////////
		
    /**
     * Constructs the content for the toString() method.
     */
    protected void contentToString(StringBuffer sb) {
        append( sb, "traceId", traceId );
        append( sb, "reqId", reqId );
        append( sb, "stageId", stageId );
        append( sb, "startNodeId", startNodeId );
        append( sb, "endNodeId", endNodeId );
        append( sb, "policyType", policyType );
    }
}
