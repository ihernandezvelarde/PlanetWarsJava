create or replace procedure add_ship(
    id_planeta in number,
    id_unit in number,
    cantidad in number, 
    nivel in number) as
existe number;
begin
select id_unit into existe from planet_has_ships where lvl = nivel;
update planet_has_ships set cant = cant + cantidad where 
id_planet = id_planeta and id_ship = id_unit and lvl = nivel;
exception
when no_data_found then
insert into planet_has_ships(id_planet, id_ship, cant, lvl)
values (id_planeta, id_unit, cantidad, nivel);
end;