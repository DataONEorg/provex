CREATE OR REPLACE FUNCTION dp_ins_ur() RETURNS void AS $$
BEGIN

    INSERT INTO user_request_master VALUES (1,1, 'h');
    
    INSERT INTO user_request_detail VALUES (1,1, 'lineage', 'axg', NULL);
    INSERT INTO user_request_detail VALUES (1,1, 'lineage', 'ayg', NULL);
    INSERT INTO user_request_detail VALUES (1,1, 'abstract', 'sm1', 'g1');
    INSERT INTO user_request_detail VALUES (1,1, 'abstract', 'ah', 'g1');    
    INSERT INTO user_request_detail VALUES (1,1, 'abstract', 'sl1', 'g1');    
        
END;

$$ LANGUAGE plpgsql;