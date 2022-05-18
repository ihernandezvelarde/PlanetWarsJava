create or replace procedure updatePlanet (id_usu in number,id_planetita in number,techATK in number,techDEF in number,Iron in number,Deute in number)
as
begin
update planet set technology_attack=techATK,technology_defense=TechDEF,metal=Iron,deuterium=Deute  where id_user=id_usu and id_planet=id_planetita;
end;