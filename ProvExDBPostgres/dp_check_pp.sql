CREATE OR REPLACE FUNCTION dp_check_pp
    (pc_trace VARCHAR,
     pc_req    VARCHAR,
     pc_stage VARCHAR) 
RETURNS void AS $$

DECLARE

      vcr_pp_tc CURSOR IS
           SELECT p.startNodeId, p.endNodeId
             FROM provenance_stage p,
                      data                     d1,
                      data                     d2
           WHERE  p.traceid = pc_trace
               AND  p.reqId=pc_req
               AND  p.stageId = pc_stage
               AND  p.startNodeId = d1.nodeId 
               AND  p.endNodeId = d2.nodeId
               UNION
           SELECT p.startNodeId, p.endNodeId
             FROM provenance_stage p,
                      actor                    a1,
                      actor                    a2
           WHERE  p.traceid = pc_trace
               AND  p.reqId=pc_req
               AND  p.stageId =  pc_stage
               AND  p.startNodeId = a1.nodeId 
               AND  p.endNodeId = a2.nodeId;      
               
      vcr_pp_wc CURSOR IS
           SELECT p1.startNodeId, p1.endNodeId
             FROM provenance_stage p1,
                      provenance_stage p2,
                      data                     d
           WHERE  p1.traceid = pc_trace
               AND  p1.reqId=pc_req
               AND  p1.stageId = pc_stage
               AND  p1.endNodeId = d.nodeId
               AND p2.traceid = pc_trace
               AND  p2.reqId=pc_req
               AND  p2.stageId = pc_stage
               AND  p2.endNodeId = d.nodeId
               AND  p1.endNodeId = p2.endNodeId
               AND  p1.startNodeId <> p2.startNodeId;                            

      vcr_pp_cc CURSOR IS
           SELECT  t.startNodeId, t.endNodeId
             FROM  tc_temp t
           WHERE  t.startNodeId = t.endNodeId;
               
BEGIN

   FOR t in vcr_pp_tc
   LOOP
      INSERT INTO policy_violation VALUES (pc_trace,pc_req,pc_stage,t.startNodeId,t.endNodeId,'tc');
   END LOOP;
   
   FOR w in vcr_pp_wc
   LOOP
      INSERT INTO policy_violation VALUES (pc_trace,pc_req,pc_stage,w.startNodeId,w.endNodeId,'wc');
   END LOOP;
   
   PERFORM dp_tc_i(pc_trace,pc_req,pc_stage);
   FOR c in vcr_pp_cc
   LOOP
      INSERT INTO policy_violation VALUES (pc_trace,pc_req,pc_stage,c.startNodeId,c.endNodeId,'cc');
   END LOOP;
        
END;

$$ LANGUAGE plpgsql;