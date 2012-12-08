DROP FUNCTION IF EXISTS tcwithrecursive4aryforall(text, text);

DROP TABLE IF EXISTS g;
DROP TABLE IF EXISTS g2;
DROP TABLE IF EXISTS temp1;

CREATE FUNCTION tcwithrecursive4aryforall(text, text) RETURNS
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
	
	CREATE INDEX ON g2 (id);
	
	CREATE TABLE temp1(
				compstart character varying(100),
 				label1 character varying(45),
  				compend character varying(100),
  				path int[]	);
	
	INSERT INTO g2(compstart, label1, compend, basestart, label2, baseend) 
		SELECT DISTINCT compstart, label1, compend, basestart, label2, baseend FROM g;
		
	INSERT INTO temp1(compstart, label1, compend, path)
	WITH RECURSIVE tc1(compstart, label1, compend, path) AS (
			SELECT g2.compstart as compstart, edgelabel || tcsymbol as label1, g2.compend as compend, ARRAY[g2.id]
			FROM g2
			WHERE label1 = edgelabel
		UNION ALL
			SELECT compstart, edgelabel || tcsymbol, compend, array_union(path) FROM (
				SELECT tc1.compstart as compstart, edgelabel || tcsymbol as label1, g2.compend as compend,
			    	array_union(tc1.path, ARRAY[g2.id]) as path
				FROM g2, tc1
				WHERE tc1.compend = g2.compstart AND g2.label1 = edgelabel
			) as foo GROUP BY compstart, compend
		)
	SELECT * from tc1;

	INSERT INTO g(
	
		WITH RECURSIVE tc2(compstart, label1, compend, path) AS (
			SELECT g2.compstart as compstart, 'FULL TC' as label1, g2.compend as compend, ARRAY[g2.id]
			FROM g2
		UNION ALL
			SELECT compstart, 'FULL TC', compend, array_union(path) FROM (
				SELECT tc2.compstart as compstart, 'FULL TC' as label1, g2.compend as compend,
			    	array_union(tc2.path, ARRAY[g2.id]) as path
				FROM g2, tc2
				WHERE tc2.compend = g2.compstart
			) as bar GROUP BY compstart, compend
		)
		SELECT DISTINCT v.compstart, v.label1, v.compend, g2.basestart, g2.label2, g2.baseend
		FROM (
			SELECT temp1.compstart, temp1.label1, temp1.compend, unnest(temp1.path) as path
			FROM temp1, tc2
			WHERE temp1.compstart = tc2.compstart AND temp1.compend = tc2.compend
				AND array_length(temp1.path, 1) = array_length(tc2.path, 1)
		) as v
		LEFT JOIN g2 ON v.path = g2.id
	);
	
	RETURN 1;
END;

$$ LANGUAGE plpgsql;

