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
public class Data extends AbstractDto {

    ////////////////////////////////////////////////////////////////////////////
    // Static
    ////////////////////////////////////////////////////////////////////////////

    public static final String TABLE = "data";

    ////////////////////////////////////////////////////////////////////////////
    // Attributes
    ////////////////////////////////////////////////////////////////////////////

    private String nodeId;
    private String nodeType;
    private String nodeDesc;
    private String valType;
    private String val;
    private String contId;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new empty DTO.
     */
    public Data() {
    }

    ////////////////////////////////////////////////////////////////////////////
    // Public
    ////////////////////////////////////////////////////////////////////////////

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId( String _val) {
        this.nodeId = _val;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType( String _val) {
        this.nodeType = _val;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc( String _val) {
        this.nodeDesc = _val;
    }

    public String getValType() {
        return valType;
    }

    public void setValType( String _val) {
        this.valType = _val;
    }

    public String getVal() {
        return val;
    }

    public void setVal( String _val) {
        this.val = _val;
    }

    public String getContId() {
        return contId;
    }

    public void setContId( String _val) {
        this.contId = _val;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Uses 'columns' equality type.
     */
    @Override
    public boolean equals( Object _other ) {
        if (_other == this) return true;
        if (_other == null || (!(_other instanceof Data))) return false;

        Data _o = (Data) _other;

        if ( nodeId == null ) {
            if ( _o.nodeId != null ) return false;
        }
        else if ( _o.nodeId == null || !nodeId.equals( _o.nodeId )) return false;

        if ( nodeType == null ) {
            if ( _o.nodeType != null ) return false;
        }
        else if ( _o.nodeType == null || !nodeType.equals( _o.nodeType )) return false;

        if ( nodeDesc == null ) {
            if ( _o.nodeDesc != null ) return false;
        }
        else if ( _o.nodeDesc == null || !nodeDesc.equals( _o.nodeDesc )) return false;

        if ( valType == null ) {
            if ( _o.valType != null ) return false;
        }
        else if ( _o.valType == null || !valType.equals( _o.valType )) return false;

        if ( val == null ) {
            if ( _o.val != null ) return false;
        }
        else if ( _o.val == null || !val.equals( _o.val )) return false;

        if ( contId == null ) {
            if ( _o.contId != null ) return false;
        }
        else if ( _o.contId == null || !contId.equals( _o.contId )) return false;

        return true;
    }

    /**
     * Returns a hash code value for the object.
     */
    @Override
    public int hashCode() {
        int _ret = 2122698; // = "Data".hashCode()
        _ret += nodeId == null ? 0 : nodeId.hashCode();
        _ret = 29 * _ret + (nodeType == null ? 0 : nodeType.hashCode());
        _ret = 29 * _ret + (nodeDesc == null ? 0 : nodeDesc.hashCode());
        _ret = 29 * _ret + (valType == null ? 0 : valType.hashCode());
        _ret = 29 * _ret + (val == null ? 0 : val.hashCode());
        _ret = 29 * _ret + (contId == null ? 0 : contId.hashCode());

        return _ret;
    }


    ////////////////////////////////////////////////////////////////////////////
    // Protected
    ////////////////////////////////////////////////////////////////////////////
		
    /**
     * Constructs the content for the toString() method.
     */
    protected void contentToString(StringBuffer sb) {
        append( sb, "nodeId", nodeId );
        append( sb, "nodeType", nodeType );
        append( sb, "nodeDesc", nodeDesc );
        append( sb, "valType", valType );
        append( sb, "val", val );
        append( sb, "contId", contId );
    }
}
