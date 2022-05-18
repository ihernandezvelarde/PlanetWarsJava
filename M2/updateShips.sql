create or replace procedure updateShips (id_planetita in number,techATK in number,techDEF in number,id_nave in number,canti in number)
as
existe number;
begin
select count(id_planet) into existe from planet_has_ships where id_planet=id_planetita and id_ship=id_nave and lvl_attack=techATK and lvl_defense=techDef;
if canti>0 then
    if existe>0 then
        update planet_has_ships set CANT=canti  where id_planet=id_planetita and id_ship=id_nave and lvl_attack=techATK and lvl_defense=techDef;
    else
        insert into planet_has_ships (id_planet,id_ship,cant,lvl_attack,lvl_defense) values (id_planetita,id_nave,canti,techATK,techDEF);
    end if;
end if;
end;