DROP FUNCTION IF EXISTS tcwithrecursive4aryintersectv2(text, text);

DROP TABLE IF EXISTS g;
DROP TABLE IF EXISTS g2;
DROP TABLE IF EXISTS temp;

CREATE FUNCTION array_union(anyarray, anyarray)
	RETURNS anyarray
	language sql
	as $FUNCTION$
    SELECT ARRAY(
        SELECT UNNEST($1)
        UNION
        SELECT UNNEST($2)
    );
	$FUNCTION$; 

CREATE AGGREGATE array_union (anyarray)
(
sfunc = array_union,
stype = anyarray,
initcond = '{}'
);


create aggregate array_intersect_agg(int[]) (
    sfunc = my_array_intersect,
    stype = int[]
);

CREATE FUNCTION tcwithrecursive4aryintersectv2(text, text) RETURNS
INTEGER
AS $$

DECLARE
	edgelabel ALIAS FOR $1;
	tcsymbol ALIAS FOR $2;
	t RECORD;
	r RECORD;
	rpq4edges int[];

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
	
	CREATE TABLE temp(
				compstart character varying(100),
 				label1 character varying(45),
  				compend character varying(100),
  				path int[]	);
	
	INSERT INTO g2(compstart, label1, compend, basestart, label2, baseend) 
		SELECT DISTINCT compstart, label1, compend, basestart, label2, baseend FROM g WHERE label1 = edgelabel;

	INSERT INTO temp(compstart, label1, compend, path)
	WITH RECURSIVE tc(compstart, label1, compend, path) AS (
			SELECT g2.compstart as compstart, edgelabel || tcsymbol as label1, g2.compend as compend, ARRAY[g2.id]
			FROM g2
		UNION ALL SELECT compstart, edgelabel || tcsymbol, compend, array_intersect_agg(path) FROM (
				SELECT tc.compstart as compstart, edgelabel || tcsymbol as label1, g2.compend as compend,
			    	array_union(tc.path, ARRAY[g2.id]) as path
				FROM g2, tc
				WHERE tc.compend = g2.compstart
		) as foo GROUP BY compstart, compend
	)
	SELECT * FROM tc;
	
	FOR t IN SELECT * FROM temp LOOP
		rpq4edges := t.path;
		IF array_upper(rpq4edges, 1) IS NOT NULL THEN
			FOR i IN array_lower(rpq4edges, 1) .. array_upper(rpq4edges, 1) LOOP
				IF t.path[i] <> -1 THEN	
					FOR r IN SELECT * FROM g2 WHERE id=t.path[i] LOOP
						INSERT INTO g values (
								t.compstart, t.label1, t.compend, r.basestart, r.label2, r.baseend); 
					END LOOP;
				END IF;
			END LOOP;
		END IF;
	END LOOP;
	
	RETURN 1;
END;

$$ LANGUAGE plpgsql;

