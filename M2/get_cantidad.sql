create or replace procedure get_cantidad(ship_id in number,planet_id in number, nivel_ataque in number, nivel_defensa in number, cantidad in out number)
as
begin
select cant into cantidad from planet_has_ships where id_ship = ship_id and id_planet = planet_id and Lvl_attack = nivel_ataque and Lvl_defense = nivel_defensa;
end;