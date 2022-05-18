create or replace procedure add_user(nombre in varchar, fecha in varchar, contr in varchar) as
id_usu number;
fecha_ins date;
begin
select max(id_user) + 1 into id_usu from usu;
if id_usu is null then
id_usu := 1;
end if;
insert into usu(id_user, username, birth_date, contrasenya) values (id_usu, nombre, fecha, contr);
end;