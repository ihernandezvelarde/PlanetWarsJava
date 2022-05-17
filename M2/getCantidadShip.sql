create or replace function get_cantidadShip(ship_id in number,planet_id in number, nivel_ataque in number, nivel_defensa in number)
return number as
cantidad number;
begin
select cant into cantidad from planet_has_ships where id_ship = ship_id and id_planet = planet_id and Lvl_attack = nivel_ataque and Lvl_defense = nivel_defensa;
return cantidad;

exception
when no_data_found then
return 0;

end;