create or replace procedure insert_table as
insertar varchar(255);
begin
insertar := 'insert into ship values (1, ''Light Hunter'',3000,0,50,400,400,80,3,20)';
execute immediate insertar;

insertar := '
insert into ship
values (2, ''Heavy Hunter'',6500,0,50,1000,1000,150,7,30)';
execute immediate insertar;

insertar := '
insert into ship 
values (3, ''Battle Ship'',45000,0,7000,6000,6000,1000,45,60)';
execute immediate insertar;

insertar := '
insert into ship 
values (4, ''Armored Ship'',30000,0,15000,8000,8000,700,700,75)';
execute immediate insertar;

insertar := '
insert into defense 
values (1, ''Missile Launcher'',2000,0,0,200,200,80,5,10)';
execute immediate insertar;

insertar := '
insert into defense 
values (2, ''Ion Cannon'',4000,0,500,1200,1200,250,12,25)';
execute immediate insertar;

insertar := '
insert into defense 
values (3, ''Plasma Cannon'',50000,0,5000,7000,7000,2000,30,50)';
execute immediate insertar;
end; 