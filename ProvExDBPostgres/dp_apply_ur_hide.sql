CREATE OR REPLACE FUNCTION dp_apply_ur_hide
    (pc_req  VARCHAR,
     pc_trace VARCHAR,
     pc_stage VARCHAR) 
RETURNS void AS $$

DECLARE

      vcr_hide CURSOR IS
           SELECT nodeId 
             FROM user_request_detail 
           WHERE reqType='hide' 
               AND reqId=pc_req
               AND stageId = pc_stage;

-- one optimization: create base table for tc computation by using only the edges
-- using "in" and "out" of the selected nodes to be hidden
-- and then compute tc on this set instead of entire  provenance_stage table          
      vcr_dep CURSOR IS
            SELECT t.startNodeId,  t.endNodeId
                FROM (SELECT p.startNodeId, r.nodeId 
                             FROM user_request_detail r, provenance_stage p 
                           WHERE r.reqType='hide' 
                               AND r.reqId=pc_req
                               AND r.stageId = pc_stage
                               AND p.traceId = pc_trace
                               AND p.reqId = pc_req
                               AND p.stageId = pc_stage
                               AND r.nodeId = p.endNodeId ) inSet,
                          ( SELECT r.nodeId, p.endNodeId 
                             FROM user_request_detail r, provenance_stage p 
                           WHERE r.reqType='hide' 
                               AND r.reqId=pc_req
                               AND r.stageId = pc_stage
                               AND p.traceId = pc_trace
                               AND p.reqId = pc_req
                               AND p.stageId = pc_stage
                               AND r.nodeId = p.startNodeId ) outSet,
                           tc_temp t
              WHERE  t.startNodeId = inSet.startNodeId
                  AND  t.endNodeId = outSet.endNodeId; 
                                               
BEGIN
   
   FOR h in vcr_hide
   LOOP
   
      DELETE  FROM provenance_stage
      WHERE  traceid = pc_trace
           AND  reqId=pc_req
           AND  stageId = pc_stage
           AND startNodeId = h.nodeId;
           
      DELETE  FROM provenance_stage
      WHERE  traceid = pc_trace
           AND  reqId=pc_req
           AND  stageId = pc_stage
           AND endNodeId = h.nodeId;

   END LOOP;
   
   FOR d in vcr_dep
   LOOP
      INSERT INTO provenance_stage VALUES (pc_trace, pc_req, pc_stage, d.startnodeid, d.endnodeid, 'a');
   END LOOP;
   
END;

$$ LANGUAGE plpgsql;