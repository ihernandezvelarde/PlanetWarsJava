create or replace procedure get_step_amount (
    cod_bat in number,
    num_steps out number) as
begin
select count(*) into num_steps from battle_step where id_battle = cod_bat;
end;