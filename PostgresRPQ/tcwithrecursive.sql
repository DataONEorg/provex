DROP FUNCTION IF EXISTS tcwithrecursive(text, text);

DROP TABLE IF EXISTS g;

CREATE FUNCTION tcwithrecursive(text, text) RETURNS
INTEGER
AS $$

DECLARE
	edgelabel ALIAS FOR $1;
	tcsymbol ALIAS FOR $2;

BEGIN

	INSERT INTO g
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

	RETURN 1;
END;

$$ LANGUAGE plpgsql;
