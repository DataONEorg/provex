DROP FUNCTION IF EXISTS tcwithrecursive4aryintersect(text, text);

DROP TABLE IF EXISTS g;
DROP TABLE IF EXISTS g2;

CREATE FUNCTION tcwithrecursive4aryintersect(text, text) RETURNS
INTEGER
AS $$

DECLARE
	edgelabel ALIAS FOR $1;
	tcsymbol ALIAS FOR $2;

BEGIN
	
	CREATE SEQUENCE g2idseq;
	
	CREATE TABLE g2(
				id int NOT NULL DEFAULT NEXTVAL('g2idseq'),
				compstart character varying(100),
 				label1 character varying(45),
  				compend character varying(100),
  				basestart character varying(100),
 				label2 character varying(45),
  				baseend character varying(100) );
	
	ALTER SEQUENCE g2idseq OWNED BY g2.id;
	
	CREATE INDEX g2idx ON g2 (id);
	
	INSERT INTO g2(compstart, label1, compend, basestart, label2, baseend) 
		SELECT DISTINCT compstart, label1, compend, basestart, label2, baseend FROM g WHERE label1 = edgelabel;
	
	INSERT INTO g(
		WITH RECURSIVE tc(compstart, label1, compend, path) AS (
			SELECT g2.compstart as compstart, edgelabel || tcsymbol as label1, g2.compend as compend, ARRAY[g2.id]
			FROM g2
				UNION ALL
			SELECT compstart, edgelabel || tcsymbol, compend, array_intersect_agg(path) FROM (
				SELECT tc.compstart as compstart, edgelabel || tcsymbol as label1, g2.compend as compend,
			    	array_union(tc.path, ARRAY[g2.id]) as path
				FROM g2, tc
				WHERE tc.compend = g2.compstart
			) as foo GROUP BY compstart, compend
		)
		SELECT DISTINCT v.compstart, v.label1, v.compend, g2.basestart, g2.label2, g2.baseend
		FROM (
			SELECT compstart, label1, compend, unnest(path) as path
			FROM (
				SELECT compstart, edgelabel || tcsymbol as label1, compend, array_intersect_agg(path) as path
				FROM tc
				GROUP BY compstart, compend
			) as u
		) as v
		LEFT JOIN g2 ON v.path = g2.id
		WHERE path <> -1
	);
	
	RETURN 1;
END;

$$ LANGUAGE plpgsql;




