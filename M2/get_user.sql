create or replace procedure get_user(nombre_usu in out varchar, cod_usu out number, fecha_nac out date, contrasenya out varchar, existe out number) as
begin
select * into cod_usu, nombre_usu, fecha_nac, contrasenya from USU where username = nombre_usu;
existe := 1;
exception
when no_data_found then
existe := 0;
end;