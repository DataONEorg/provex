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

