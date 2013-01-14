/*
    -- these inserts are just to test if the tc procedure is working 
    -- correctly with a ditected acyclic graph.
    INSERT INTO provenance_stage VALUES (1,1,1,1,2,'a');
    INSERT INTO provenance_stage VALUES (1,1,1,1,3,'a');
    INSERT INTO provenance_stage VALUES (1,1,1,2,4,'a');
    INSERT INTO provenance_stage VALUES (1,1,1,3,5,'a');
    INSERT INTO provenance_stage VALUES (1,1,1,5,4,'a');
    INSERT INTO provenance_stage VALUES (1,1,1,4,6,'a');

    -- these inserts are just to test if the tc procedure is working 
    -- correctly with a ditected cyclic graph.
    INSERT INTO provenance_stage VALUES (1,1,2,1,2,'a');
    INSERT INTO provenance_stage VALUES (1,1,2,1,3,'a');
    INSERT INTO provenance_stage VALUES (1,1,2,2,4,'a');
    INSERT INTO provenance_stage VALUES (1,1,2,3,5,'a');
    INSERT INTO provenance_stage VALUES (1,1,2,5,4,'a');
    INSERT INTO provenance_stage VALUES (1,1,2,4,6,'a');
    INSERT INTO provenance_stage VALUES (1,1,2,4,3,'a');
    
*/

CREATE OR REPLACE FUNCTION dp_tc_i
    ( pc_trace VARCHAR,
      pc_req    VARCHAR,
      pc_stage VARCHAR) 
RETURNS void AS $$

DECLARE

      vcr_data CURSOR IS
           SELECT startNodeId, endNodeId 
             FROM provenance_stage 
           WHERE traceid = pc_trace
               AND  reqId=pc_req
               AND  stageId = pc_stage;
   
BEGIN

   FOR d in vcr_data
   LOOP
   
      INSERT INTO tc VALUES (d.startNodeId, d.endNodeId);
      INSERT INTO tc_temp VALUES (d.startNodeId, d.endNodeId,1);
                 
   END LOOP;
   
   PERFORM dp_tc_r(1);
        
END;

$$ LANGUAGE plpgsql;