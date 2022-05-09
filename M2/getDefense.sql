create or replace procedure getDefense(id_nave_defensa in number, nombre in out varchar, metal in out number, cristal in out number, deuterium in out number, inicial in out number, armadura in out number, base in out number, velocidad in out number, generar in out number)
as
begin
select defense_name into nombre from ship where id_defense = id_nave_defensa;
select metal_cost into metal from ship where id_defense = id_nave_defensa;
select crystal_cost into cristal from ship where id_defense = id_nave_defensa;
select deuterium_cost into deuterium from ship where id_defense = id_nave_defensa;
select initial_armor into inicial from ship where id_defense = id_nave_defensa;
select armor into armadura from ship where id_defense = id_nave_defensa;
select basedamage into base from ship where id_defense = id_nave_defensa;
select speed into velocidad from ship where id_defense = id_nave_defensa;
select generate_wastings into generar from ship where id_defense = id_nave_defensa;
end;