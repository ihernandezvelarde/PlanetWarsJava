create or replace procedure getDefense(id_nave_defensa in out number, nombre in out varchar, metal in out number, cristal in out number, deuterium in out number, inicial in out number, armadura in out number, base in out number, velocidad in out number, generar in out number)
as
begin
select * into id_nave_defensa,nombre,metal,cristal,deuterium,inicial,armadura,base,velocidad,generar from defense where id_defense = id_nave_defensa;
end;