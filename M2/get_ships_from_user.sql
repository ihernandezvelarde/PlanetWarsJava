create or replace procedure get_ships_from_user (
    id_usu in number,
    id_unit in number,
    num_fila in out number,
    cantidad out number,
    ataque out number,
    defensa out number) as
begin
select rownum, cant, lvl_attack, lvl_defense into num_fila, cantidad, ataque, defensa from planet_has_ships s
inner join planet p on p.id_planet = s.id_planet where p.id_user = id_usu and s.id_ship = id_unit and rownum = num_fila;

exception
when no_data_found then
num_fila := 0;
end;