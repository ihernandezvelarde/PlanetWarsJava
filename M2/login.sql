create or replace function login(nombre in varchar, contrasenya in varchar) return boolean as
cod number;
begin
select id_user into cod from usu where username = nombre and password = contrasenya;
return true;
exception
when no_data_found then
return false;
end;