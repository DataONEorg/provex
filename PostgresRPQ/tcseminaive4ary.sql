DROP FUNCTION IF EXISTS tcseminaive4ary(text, text);

DROP TABLE IF EXISTS g;
DROP TABLE IF EXISTS temptc;
DROP TABLE IF EXISTS tcprev;
DROP TABLE IF EXISTS tcnew;

CREATE FUNCTION tcseminaive4ary(text, text) RETURNS
INTEGER
AS $$

DECLARE
	edgelabel ALIAS FOR $1;
	tcsymbol ALIAS FOR $2;

BEGIN
	
	CREATE TABLE temptc(
				compstart character varying(100),
 				label1 character varying(45),
  				compend character varying(100),
  				basestart character varying(100),
  				label2 character varying(45),
  				baseend character varying(100)
	);

	CREATE TABLE tcprev(
				compstart character varying(100),
 				label1 character varying(45),
  				compend character varying(100),
  				basestart character varying(100),
  				label2 character varying(45),
  				baseend character varying(100)
	);

	Create TABLE tcnew(
				compstart character varying(100),
 				label1 character varying(45),
  				compend character varying(100),
  				basestart character varying(100),
  				label2 character varying(45),
  				baseend character varying(100)
	);
	
	INSERT INTO temptc 
	  SELECT g.compstart, edgelabel || tcsymbol, g.compend, g.basestart, g.label2, g.baseend
	  FROM g 
	  WHERE g.label1 = edgelabel;
	  
	INSERT INTO tcprev SELECT * FROM temptc;
	
	LOOP

	/* Iterative step*/
		
		INSERT INTO tcnew
    	WITH t as (
                   SELECT tcprev.compstart as compstart, edgelabel || tcsymbol as label1,
                                   g.compend as compend,
		                           tcprev.basestart as tcbasestart, tcprev.label2 as tclabel2, 
		                           tcprev.baseend as tcbaseend,
		                           g.basestart as gbasestart, g.label2 as glabel2,
		                           g.baseend as gbaseend
		           FROM tcprev, g 
		           WHERE tcprev.compend = g.compstart and g.label1 = edgelabel )
        (SELECT DISTINCT compstart, label1, compend, tcbasestart, tclabel2, tcbaseend FROM t)
        UNION
        (SELECT DISTINCT compstart, label1, compend, gbasestart, glabel2, gbaseend FROM t);		

	/* Update with the main db */	
		
		IF NOT EXISTS(SELECT label2 FROM tcnew) THEN
			DROP TABLE tcprev;
			DROP TABLE tcnew;
			EXIT;
		ELSE
			DELETE FROM tcprev;
			INSERT INTO tcprev SELECT * FROM tcnew;
			INSERT INTO temptc SELECT * FROM tcnew;
			DELETE FROM tcnew;
		END IF;
		
	END LOOP;

	INSERT INTO g SELECT * FROM temptc;

	DROP TABLE temptc;
	RETURN 1;
	
END;

$$ LANGUAGE plpgsql;
