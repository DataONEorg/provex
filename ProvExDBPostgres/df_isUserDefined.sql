CREATE OR REPLACE FUNCTION df_isUserDefined
    ( pc_trace      VARCHAR,
      pc_req         VARCHAR,
      pc_stage      VARCHAR,
      pc_nodeId    VARCHAR) 
RETURNS BOOLEAN AS $$

DECLARE
   
      vcr_ud CURSOR IS
            SELECT d.targetNodeId
              FROM user_request_master m, 
                        user_request_detail d
            WHERE m.traceid = pc_trace
                 AND m.reqId=pc_req
                 AND m.reqId = d.reqId
                 AND d.stageId = pc_stage
                 AND d.reqType = 'abstract'
                 AND d.targetNodeId = pc_nodeId;
                 
   vb_flag  boolean := false;                 
                 
BEGIN

   FOR n in vcr_ud
   LOOP
     vb_flag := true;
   END LOOP;
   
   RETURN vb_flag;
        
END;
$$ LANGUAGE plpgsql;