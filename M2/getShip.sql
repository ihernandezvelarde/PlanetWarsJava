create or replace procedure getShip(id_nave in number, nombre in out varchar, metal in out number, cristal in out number, deuterium in out number, inicial in out number, armadura in out number, base in out number, velocidad in out number, generar in out number)
as
begin
select ship_name into nombre from ship where id_ship = id_nave;
select metal_cost into metal from ship where id_ship = id_nave;
select crystal_cost into cristal from ship where id_ship = id_nave;
select deuterium_cost into deuterium from ship where id_ship = id_nave;
select initial_armor into inicial from ship where id_ship = id_nave;
select armor into armadura from ship where id_ship = id_nave;
select basedamage into base from ship where id_ship = id_nave;
select speed into velocidad from ship where id_ship = id_nave;
select generate_wastings into generar from ship where id_ship = id_nave;
end;