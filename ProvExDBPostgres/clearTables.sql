BEGIN;

DELETE FROM data;

DELETE FROM actor;

DELETE FROM user_request_master;

DELETE FROM user_request_detail;

DELETE FROM edge;

DELETE FROM provenance_stage;

DELETE FROM policy_violation;

DELETE FROM tc;

DELETE FROM tc_temp;


COMMIT;