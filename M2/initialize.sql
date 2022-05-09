create or replace procedure initialize 
as
no_existe exception;
contador number;
begin
select count(*) into contador from all_tables where table_name = 'USU' or table_name = 'PLANET' or table_name = 'DEFENSE' or table_name = 'SHIP' or table_name = 'PLANET_HAS_DEFENSES' or table_name = 'PLANET_HAS_SHIPS' or table_name = 'BATTLE' or table_name = 'BATTLE_HAS_DEFENSES'or table_name = 'BATTLE_HAS_SHIPS';
if contador != 9 then
raise no_existe;
end if;
EXCEPTION
when no_existe then
drop_table;
create_table;
insert_table;
end;