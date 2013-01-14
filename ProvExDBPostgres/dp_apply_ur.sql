BEGIN;
CREATE OR REPLACE FUNCTION dp_apply_ur
    (pc_req  VARCHAR,
     pc_trace VARCHAR,
     pc_stage VARCHAR) 
RETURNS void AS $$

DECLARE
      vcr_ur CURSOR IS
           SELECT nodeId, targetNodeId 
             FROM user_request_detail 
           WHERE reqType='abstract' 
               AND reqId=pc_req
               AND stageId = pc_stage;

      vcr_abstract_node CURSOR IS
           SELECT DISTINCT targetNodeId 
             FROM user_request_detail 
           WHERE reqType='abstract' 
               AND reqId=pc_req
               AND stageId = pc_stage;               
 
      vcr_lin CURSOR IS
           SELECT p.*
             FROM provenance_stage p
           WHERE  p.traceid = pc_trace
               AND  p.reqId=pc_req
               AND  p.stageId = pc_stage;
                 
BEGIN

   FOR l in vcr_lin
   LOOP  
      INSERT INTO provenance_stage VALUES (l.traceid, l.reqId, CAST(l.stageId AS NUMERIC)+1, l.startnodeid, l.endnodeid, l.edgelabel);
   END LOOP;
   
   FOR u in vcr_ur
   LOOP
   
      UPDATE provenance_stage
           SET startNodeId = u.targetNodeId
      WHERE  traceid = pc_trace
           AND  reqId=pc_req
           AND  stageId = CAST(pc_stage+1 AS VARCHAR)
           AND startNodeId = u.nodeId;
           
      UPDATE provenance_stage
           SET endNodeId = u.targetNodeId
      WHERE  traceid = pc_trace
           AND  reqId=pc_req
           AND  stageId = CAST(pc_stage+1 AS VARCHAR)
           AND endNodeId = u.nodeId;

   END LOOP;

   FOR a in vcr_abstract_node
   LOOP 

       INSERT INTO actor VALUES (a.targetNodeId, 'i', a.targetNodeId, 'v', 'No value for now', a.targetNodeId);
       
       DELETE FROM provenance_stage
          WHERE  traceid = pc_trace
           AND  reqId=pc_req
           AND  stageId = CAST(pc_stage+1 AS VARCHAR)
           AND endNodeId =startNodeId
           AND endNodeId = a.targetNodeId;   
              
   END LOOP;       

END;

$$ LANGUAGE plpgsql;

COMMIT;