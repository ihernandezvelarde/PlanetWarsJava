create or replace procedure initialize (numero in number := 0)
as
contador number;
begin

if numero = 1 then
    drop_table;
    create_table;
    insert_table;
else
    select count(*) into contador from all_tables where table_name = 'USU';
    if contador = 0 then
        create_table;
        insert_table;
    end if;
end if;
end;