CREATE OR REPLACE FUNCTION dp_prov_cust
    ( pc_trace VARCHAR,
      pc_req    VARCHAR) 
RETURNS void AS $$
DECLARE

   
   vn_count        NUMERIC(10) := -1;
   vc_stageId      VARCHAR(10) := 2;
   
BEGIN

   -- Execute the lineage user requests
   PERFORM dp_prepare_lineage(pc_trace,pc_req);

    WHILE (vn_count !=0)
    LOOP
        PERFORM dp_apply_ur(pc_trace,pc_req,vc_stageId);
		vc_stageId := CAST(vc_stageId AS NUMERIC) + 1;
        PERFORM dp_check_pp(pc_trace,pc_req,vc_stageId);
        PERFORM dp_fix_pp(pc_trace,pc_req,vc_stageId);
        DELETE FROM tc;
        DELETE FROM tc_temp;
        
        SELECT COUNT(*)
           INTO vn_count 
          FROM policy_violation
        WHERE stageId = vc_stageId;

    END LOOP ;
        
END;
$$ LANGUAGE plpgsql;