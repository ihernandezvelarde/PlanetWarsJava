create or replace procedure add_ship(
    id_planeta in number,
    id_unit in number,
    cantidad in number, 
    nivel in number) as
begin
insert into planet_has_ships(id_planet, id_ship, cant, lvl)
values (id_planeta, id_unit, cantidad, nivel);
end;