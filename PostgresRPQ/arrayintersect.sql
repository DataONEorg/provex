create function my_array_intersect(a1 int[], a2 int[]) 
returns int[] 
language plpgsql 
as $FUNCTION$
declare
    ret int[];
begin
    if a1 is null then
        return a2;
    elseif a2 is null then
        return a1;
    end if;
    select array_agg(e) into ret
    from (
        select unnest(a1)
        intersect
        select unnest(a2)
    ) as dt(e);
    
    if array_upper(ret, 1) is null then
    	return ARRAY[-1];
    else
    	return ret;
    end if;
end;
$FUNCTION$;

