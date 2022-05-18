create or replace procedure getbattlestep(id_bat in number, id_paso in number, paso out varchar) as
begin
select descripcion into paso from battle_step where id_battle = id_bat and id_step = id_paso;
end;