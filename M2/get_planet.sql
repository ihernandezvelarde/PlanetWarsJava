create or replace procedure get_planet(
    id_planeta in out number, 
    nombre out varchar, 
    tech_ataque out number, 
    tech_defensa out number, 
    cant_cristal out number,
    cant_metal out number,
    cant_deuterium out number,
    id_usuario out number) as
begin
select * into id_planeta, nombre, tech_ataque, tech_defensa, cant_cristal, cant_metal, cant_deuterium, id_usuario from planet where id_planet = id_planeta;
end;