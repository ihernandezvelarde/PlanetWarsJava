create or replace procedure add_user(nombre in varchar, contrasenya in varchar, fecha in date) as
id_usu number;
begin
select max(id_user) + 1 into id_usu from usu;
insert into usu values(id_usu, nombre, contrasenya, fecha);
end;