/**************************************************************
 * Name: 
 * Date: 
 * Author:
 * Description:
 *    
 * Modification Log: 
 * (1) Date: 
 * (1) Change:
 * (2) Date: 
 * (2) Change:
 *    
 *    
 *************************************************************/

CREATE OR REPLACE FUNCTION dp_tc_r
    (pn_round  NUMERIC) 
RETURNS void AS $$

DECLARE

      vcr_data CURSOR IS
           SELECT t1.startNodeId, t2.endNodeId
             FROM tc_temp t1, tc t2
            WHERE t1.inRound = pn_round
              AND t1.endNodeId = t2.startNodeId
           EXCEPT  
           SELECT t1.startNodeId, t1.endNodeId
             FROM tc_temp t1;
   
     vn_round  NUMERIC(10);
     vn_count  NUMERIC(10);

BEGIN
   
   vn_round := pn_round+1;
   vn_count := 0;
   
   FOR d in vcr_data
   LOOP
      INSERT INTO tc_temp VALUES (d.startNodeId, d.endNodeId,vn_round);
      vn_count := vn_count + 1;          
   END LOOP;
   
   IF vn_count > 0 THEN
      PERFORM dp_tc_r(vn_round);
   END IF;
       
END;
$$ LANGUAGE plpgsql;