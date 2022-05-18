create or replace function getId(usuario in varchar) return number as
existe number;
begin
select id_user into existe from usu where username=usuario;
return existe;
end;
