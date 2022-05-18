create or replace procedure get_planetusu(
    nombre in varchar,
    id_planeta out number) as
begin
select id_planet into id_planeta from planet p
inner join usu u on u.id_user = u.id_user
where u.username = nombre;
end;