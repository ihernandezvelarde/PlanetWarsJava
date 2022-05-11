create or replace function login(nombre in varchar, contr in varchar) return boolean as
cod number;
begin
select id_user into cod from usu where username = nombre and contrasenya = contr;
return true;
exception
when no_data_found then
return false;
end;