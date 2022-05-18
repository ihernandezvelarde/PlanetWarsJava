create or replace procedure updateDefenses (id_planetita in number,techATK in number,techDEF in number,id_def in number,canti in number)
as
existe number;
begin
select count(id_planet) into existe from planet_has_defenses where id_planet=id_planetita and id_defense=id_def and lvl_attack=techATK and lvl_defense=techDef;
if canti>0 then
    if existe>0 then
        update planet_has_defenses set CANT=canti  where id_planet=id_planetita and id_defense=id_def and lvl_attack=techATK and lvl_defense=techDef;
    else
        insert into planet_has_defenses (id_planet,id_defense,cant,lvl_attack,lvl_defense) values (id_planetita,id_def,canti,techATK,techDEF);
    end if;
end if;
end;