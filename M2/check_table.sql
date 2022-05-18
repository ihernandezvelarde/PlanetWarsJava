create or replace procedure check_battle(cod_bat in number, existe out number) as
begin
select count(*) into existe from battle where id_battle = cod_bat;
end;