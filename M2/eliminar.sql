create or replace procedure drop_table as
eliminar varchar(255);

begin
eliminar := '
drop table battle_has_ships purge';
execute immediate eliminar;

eliminar := '
drop table BATTLE_HAS_DEFENSES purge';
execute immediate eliminar;

eliminar := '
drop table BATTLE purge';
execute immediate eliminar;

eliminar := '
drop table PLANET_HAS_SHIPS purge';
execute immediate eliminar;

eliminar := '
drop table PLANET_HAS_DEFENSES purge';
execute immediate eliminar;

eliminar := '
drop table PLANET purge';
execute immediate eliminar;

eliminar := '
drop table DEFENSE purge';
execute immediate eliminar;

eliminar := '
drop table SHIP purge';
execute immediate eliminar;
eliminar := '
drop table USU purge';
execute immediate eliminar;
end; 