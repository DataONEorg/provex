BEGIN;
-- Load the trace
SELECT dp_ins_trace();

SELECT * FROM data;

SELECT * FROM actor;

SELECT * FROM node;

SELECT * FROM edge;

-- Load user requests

SELECT  dp_ins_ur();

SELECT * FROM user_request_detail;

SELECT * FROM user_request_master;

-- Customize

SELECT dp_prov_cust('1','1');

SELECT * FROM provenance_stage;

SELECT * FROM provenance_stage WHERE stageId = '3';

SELECT * FROM policy_violation WHERE stageId = '2';

SELECT * FROM tc;

SELECT * FROM tc_temp;

COMMIT;
