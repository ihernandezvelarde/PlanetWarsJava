create or replace procedure add_planet (
    nombre in varchar, 
    tech_ataque in number, 
    tech_defensa in number, 
    cant_cristal in number,
    cant_metal in number,
    cant_deuterium in number,
    id_usu in number) as
id_planeta number;
begin
select max(id_planet) + 1 into id_planeta from planet;
if id_planeta is null then
id_planeta := 1;
end if;
insert into planet(id_planet, planet_name, technology_attack, technology_defense, crystal, metal, deuterium, id_user)
values (id_planeta, nombre, tech_ataque, tech_defensa, cant_cristal, cant_metal, cant_deuterium, id_usu);
end;