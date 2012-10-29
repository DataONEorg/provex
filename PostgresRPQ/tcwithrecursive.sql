DROP FUNCTION IF EXISTS tcwithrecursive(text, text);

DROP TABLE IF EXISTS g;
DROP TABLE IF EXISTS temptc;

CREATE FUNCTION tcwithrecursive(text, text) RETURNS
INTEGER
AS $$

DECLARE
	edgelabel ALIAS FOR $1;
	tcsymbol ALIAS FOR $2;

BEGIN
	CREATE TABLE temptc(
				compstart character varying(100),
 				label1 character varying(45),
  				compend character varying(100)
	);

	INSERT INTO temptc
	WITH RECURSIVE tc(compstart, label1, compend) AS (
			SELECT g.compstart as compstart, g.label1 || tcsymbol as label1, g.compend as compend
			FROM g
			WHERE g.label1 = edgelabel
		UNION
			SELECT tc.compstart as compstart, g.label1 || tcsymbol as label1, g.compend as compend
			FROM g, tc
			WHERE g.label1 = edgelabel AND tc.compend = g.compstart
		)
	SELECT * from tc;

	INSERT INTO g SELECT * FROM temptc;
	RETURN 1;
END;

$$ LANGUAGE plpgsql;
