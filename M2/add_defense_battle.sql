create or replace procedure add_defense_battle(
    id_batalla in number,
    id_unit in number,
    inicio in number,
    resto in number,
    bando in number) as
begin
insert into battle_has_defenses(id_battle, id_defense, starting_amount, remaining_amount, side)
values (id_batalla, id_unit, inicio, resto, bando);
end;