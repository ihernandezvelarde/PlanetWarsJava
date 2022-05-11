create or replace procedure add_battle(
    id_batalla in number, 
    id_usuario in number,
    inicio_usuario in number,
    final_usuario in number,
    inicio_enemigo in number,
    final_enemigo in number) as
begin
insert into battle(id_battle, id_user, total_starting_units_user, total_remaining_units_user, total_starting_units_enemy, total_remaining_units_enemy)
values (id_batalla, id_usuario, inicio_usuario, final_usuario, inicio_enemigo, final_enemigo);
end;