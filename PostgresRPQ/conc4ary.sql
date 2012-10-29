DROP FUNCTION IF EXISTS conc4ary(text, text, text);

DROP TABLE IF EXISTS g;

CREATE FUNCTION conc4ary(text, text, text) RETURNS
INTEGER
AS $$

DECLARE
	s1 ALIAS FOR $1;
	s2 ALIAS FOR $2;
	s3 ALIAS FOR $3;

BEGIN
	
	INSERT INTO g
    	WITH gt as (
                     SELECT g1.compstart as compstart, s1 as label1, g2.compend as compend,
                     g1.basestart as g1basestart, g1.label2 as g1label2, g1.baseend as g1baseend,
                     g2.basestart as g2basestart, g2.label2 as g2label2, g2.baseend as g2baseend
                     FROM g as g1, g as g2 
                     WHERE g1.label1 = s2 and g1.compend = g2.compstart and g2.label1 = s3
                )
        (SELECT gt.compstart, gt.label1, gt.compend,
                gt.g1basestart, gt.g1label2, gt.g1baseend
         FROM gt)
        UNION
        (SELECT gt.compstart, gt.label1, gt.compend,
                gt.g2basestart, gt.g2label2, gt.g2baseend
         FROM gt);
	
	RETURN 1;
END;

$$ LANGUAGE plpgsql;
