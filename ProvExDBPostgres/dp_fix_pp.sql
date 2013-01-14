CREATE OR REPLACE FUNCTION dp_fix_pp
    ( pc_trace VARCHAR,
      pc_req    VARCHAR,
      pc_stage VARCHAR) 
RETURNS void AS $$

DECLARE

      vcr_wc CURSOR IS
           SELECT startNodeId, endNodeId
             FROM policy_violation
           WHERE traceid = pc_trace
               AND  reqId=pc_req
               AND  stageId = pc_stage
               AND policyType = 'wc';
               
      vcr_tc CURSOR IS
           SELECT startNodeId, endNodeId
             FROM policy_violation
           WHERE traceid = pc_trace
               AND  reqId=pc_req
               AND  stageId = pc_stage
               AND policyType = 'tc';               

      vcr_cc CURSOR IS
       SELECT p.startNodeId, p.endNodeId
         FROM  (SELECT x.startNodeId, y.endNodeId
                       FROM (SELECT startNodeId
                                    FROM policy_violation
                                  WHERE traceid = pc_trace
                                       AND reqId=pc_req
                                       AND  stageId = pc_stage
                                       AND policyType = 'cc'
                                       AND startNodeId = endNodeId) x,
                                 (SELECT p.endNodeId
                                     FROM policy_violation p, 
                                              user_request_master m, 
                                              user_request_detail d
                                   WHERE p.traceid = pc_trace
                                       AND  p.reqId=pc_req
                                       AND  p.stageId = pc_stage
                                       AND p.policyType = 'cc' 
                                       AND p.startNodeId =  p.endNodeId
                                       AND m.traceid = pc_trace
                                       AND m.reqId=pc_req
                                       AND m.reqId = d.reqId
                                       AND d.stageId = pc_stage
                                       AND d.reqType = 'abstract'
                                       AND p.endNodeId = d.targetNodeId) y
               ) z,
              tc_temp p
   WHERE z.startNodeId = p.startNodeId 
       AND z.endNodeId = p.endNodeId;
    
BEGIN

   
   FOR w in vcr_wc
   LOOP
          INSERT INTO user_request_detail VALUES (pc_req,pc_stage, 'abstract', w.startNodeId, w.endNodeId);
   END LOOP;
     
   FOR t in vcr_tc
   LOOP
          IF (df_isUserDefined(pc_trace,pc_req,CAST (1 AS VARCHAR),t.startNodeId)) THEN 
             INSERT INTO user_request_detail VALUES (pc_req,pc_stage, 'abstract', t.endNodeId, t.startNodeId);
          ELSE 
             INSERT INTO user_request_detail VALUES (pc_req,pc_stage, 'abstract', t.startNodeId, t.endNodeId);
          END IF;
   END LOOP;

   FOR c in vcr_cc
   LOOP
          INSERT INTO user_request_detail VALUES (pc_req,pc_stage, 'abstract', c.startNodeId, c.endNodeId);
   END LOOP;
      
END;
$$ LANGUAGE plpgsql;
