create or replace procedure get_defense_battle (
    id_bat in number, 
    id_unit in number,
    bando in number,
    inicio out number,
    resto out number) as
begin
select starting_amount, remaining_amount into inicio, resto from battle_has_defenses
where id_battle = id_bat and id_defense = id_unit and side = bando;

exception
when no_data_found then
inicio := 0;
resto := 0;
end;