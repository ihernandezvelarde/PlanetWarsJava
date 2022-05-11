create or replace procedure add_defense(
    id_planeta in number,
    id_unit in number,
    cantidad in number, 
    nivel in number) as
existe number;
begin
select id_unit into existe from planet_has_defenses where lvl = nivel;
update planet_has_defenses set cant = cant + cantidad where 
id_planet = id_planeta and id_defense = id_unit and lvl = nivel;
exception
when no_data_found then
insert into planet_has_defenses(id_planet, id_defense, cant, lvl)
values (id_planeta, id_unit, cantidad, nivel);
end;