create or replace procedure add_battle_step(id_bat in number, id_paso in number, desci in varchar) as
begin
insert into battle_step values(id_bat, id_paso, desci);
end;