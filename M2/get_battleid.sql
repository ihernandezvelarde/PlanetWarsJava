create or replace procedure get_battleid(id_bat out number) as
begin
select max(id_battle) + 1 into id_bat from battle;
if id_bat is null then
id_bat := 1;
end if;
end;