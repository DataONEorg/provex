CREATE OR REPLACE FUNCTION dp_prepare_lineage
    (pc_trace VARCHAR,
     pc_req  VARCHAR) 
RETURNS void AS $$

DECLARE

	  vcr_edge CURSOR IS
		SELECT traceId, startnodeId, endnodeId, edgeLabel
		FROM edge
		WHERE traceId = pc_trace;

      vcr_lin CURSOR IS 
		
		WITH RECURSIVE rqNode(reqId, stageId, traceId, startnodeId, endnodeId, edgeLabel)
		
		AS(
		   -- Non-recursive term
			SELECT reqId, stageId, traceId, startnodeId, endnodeId, edgeLabel 
			FROM provenance_stage
			WHERE traceId = pc_trace AND
			endnodeId IN (SELECT nodeId FROM user_request_detail WHERE reqType='lineage' AND reqId=pc_req)
			
		   
		   -- Recursive term
			UNION ALL 
		   
			SELECT d.reqId, d.stageId, d.traceId, d.startnodeId, d.endnodeId, d.edgeLabel 
			FROM provenance_stage AS d,  rqNode AS rd
			WHERE d.endnodeid = rd.startnodeid
		)
		SELECT DISTINCT * FROM rqNode;
   
BEGIN
   FOR e in vcr_edge
   LOOP
      INSERT INTO provenance_stage VALUES (pc_trace, pc_req, 1, e.startnodeid, e.endnodeid, e.edgelabel);
   END LOOP;

   FOR l in vcr_lin
   LOOP
      INSERT INTO provenance_stage VALUES (l.traceId, l.reqId, 2, l.startnodeId, l.endnodeId, l.edgeLabel);
   END LOOP;
        
END;

$$ LANGUAGE plpgsql;