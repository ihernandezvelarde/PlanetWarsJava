create or replace function comprovar_usuari(usuario in varchar) return boolean as
id_usu number;
existe boolean;
begin
select id_user into id_usu from usu where username = usuario;
return true;
exception
when no_data_found then
return false;
end;