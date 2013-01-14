BEGIN;

DROP TABLE IF EXISTS data CASCADE;

DROP TABLE IF EXISTS actor CASCADE;

DROP VIEW IF EXISTS node CASCADE;

DROP TABLE IF EXISTS user_request_master CASCADE;

DROP TABLE IF EXISTS user_request_detail CASCADE;

DROP TABLE IF EXISTS edge CASCADE;

DROP TABLE IF EXISTS provenance_stage CASCADE;

DROP TABLE IF EXISTS policy_violation CASCADE;

DROP TABLE IF EXISTS tc CASCADE;

DROP TABLE IF EXISTS tc_temp CASCADE;

CREATE TABLE data (
   nodeId             VARCHAR(32)       NOT NULL,
   nodeType        VARCHAR(1)         NOT NULL,
   nodeDesc        VARCHAR(100)      NOT NULL,
   valType           VARCHAR(1)         NOT NULL,
   val                  VARCHAR(2000)    NULL,
   contId              VARCHAR(32)      NULL
);

CREATE TABLE actor (
   nodeId             VARCHAR(32)       NOT NULL,
   nodeType        VARCHAR(1)         NOT NULL,
   nodeDesc        VARCHAR(100)      NOT NULL,
   valType           VARCHAR(1)         NOT NULL,
   val                  VARCHAR(2000)    NULL,
   ActorId            VARCHAR(32)       NOT NULL
);


CREATE VIEW node AS 
    SELECT nodeId, nodeType, nodeDesc, valType, val
      FROM data
     UNION  
     SELECT nodeId, nodeType, nodeDesc, valType, val
      FROM actor
;            

CREATE TABLE user_request_master (
   traceId               VARCHAR(32)       NOT NULL,
   reqId               VARCHAR(32)       NOT NULL,
   evalType           VARCHAR(1)        NOT NULL
);

CREATE TABLE user_request_detail (
   reqId               VARCHAR(32)       NOT NULL,
   stageId              VARCHAR(32)       NOT NULL,
   reqType           VARCHAR(32)         NOT NULL,   
   nodeId             VARCHAR(32)       NOT NULL,   
   targetNodeId    VARCHAR(32)       NULL
);

CREATE TABLE edge (
   traceId               VARCHAR(32)       NOT NULL,
   startNodeId        VARCHAR(32)       NOT NULL,   
   endNodeId         VARCHAR(32)       NOT NULL,
   edgeLabel         VARCHAR(32)       NOT NULL,
   edgeType         VARCHAR(32)
);

CREATE TABLE provenance_stage (
   traceId               VARCHAR(32)       NOT NULL,
   reqId                 VARCHAR(32)       NOT NULL,
   stageId              VARCHAR(32)       NOT NULL,
   startNodeId        VARCHAR(32)       NOT NULL,   
   endNodeId         VARCHAR(32)       NOT NULL,
   edgeLabel         VARCHAR(32)       NOT NULL
);

CREATE TABLE policy_violation (
   traceId               VARCHAR(32)       NOT NULL,
   reqId                 VARCHAR(32)       NOT NULL,
   stageId              VARCHAR(32)       NOT NULL,
   startNodeId        VARCHAR(32)       NOT NULL,   
   endNodeId         VARCHAR(32)       NOT NULL,
   policyType         VARCHAR(32)       NOT NULL
);

CREATE TABLE tc (
   startNodeId        VARCHAR(32)       NOT NULL,   
   endNodeId         VARCHAR(32)       NOT NULL
);

CREATE TABLE tc_temp (
   startNodeId        VARCHAR(32)       NOT NULL,   
   endNodeId         VARCHAR(32)       NOT NULL,
   inRound             NUMERIC (10)         NOT NULL
);

COMMIT;