create or replace procedure create_table as
crear varchar(32767);
begin
crear := 'create table USU (
ID_User int primary key not null,
Username varchar(255) not null unique,
Birth_Date date not null,
Contrasenya varchar(255) not null)';
execute immediate crear;

crear := 'create table PLANET (
ID_Planet int primary key not null,
Planet_name varchar(255) unique not null,
Technology_Attack int not null,
Technology_Defense int not null,
Crystal int not null,
Metal int not null,
Deuterium int not null,
ID_User int not null,
foreign key(ID_User) references USU(ID_User))';
execute immediate crear;

crear := '
create table DEFENSE (
ID_Defense int primary key not null,
Defense_Name varchar(255) unique not null,
Metal_Cost int not null,
Crystal_Cost int not null,
Deuterium_Cost int not null,
Initial_Armor int not null,
Armor int not null,
BaseDamage int not null,
Speed int not null,
Generate_Wastings int not null)';
execute immediate crear;

crear := '
create table SHIP (
ID_Ship int primary key not null ,
Ship_Name varchar(255) unique not null,
Metal_Cost int not null,
Crystal_Cost int not null,
Deuterium_Cost int not null,
Initial_Armor int not null,
Armor int not null,
BaseDamage int not null,
Speed int not null,
Generate_Wastings int not null)';
execute immediate crear;

crear := '
create table PLANET_HAS_DEFENSES (
ID_Planet int not null,
foreign key (ID_Planet) references PLANET(ID_Planet),
ID_Defense int not null,
foreign key (ID_Defense) references DEFENSE(ID_Defense),
cant int not null,
Lvl int not null,
constraint pk_Planet_Defenses primary key (ID_Planet, ID_Defense))';
execute immediate crear;

crear := '
create table PLANET_HAS_SHIPS (
ID_Planet int not null,
foreign key (ID_Planet) references PLANET(ID_Planet),
ID_Ship int not null,
foreign key (ID_Ship) references SHIP(ID_Ship),
cant int not null,
Lvl int not null,
constraint pk_Planet_Ship primary key (ID_Planet, ID_Ship))';
execute immediate crear;

crear := '
create table BATTLE (
ID_Battle int not null,
ID_User int not null,
Total_Starting_Units_User int not null,
Total_Remaining_Units_User int not null,
Total_Starting_Units_Enemy int not null,
Total_Remaining_Units_Enemy int not null,
constraint pk_Battle primary key (ID_Battle),
foreign key (ID_User) references USU(ID_User))';
execute immediate crear;

crear := '
create table BATTLE_HAS_DEFENSES (
ID_Battle int not null,
foreign key (ID_Battle) references BATTLE(ID_Battle),
ID_Defense int not null,
foreign key (ID_Defense) references DEFENSE(ID_Defense),
Starting_Amount int not null,
Remaining_Amount int not null,
Side int,
constraint pk_battle_defense primary key (ID_Battle, ID_Defense))';
execute immediate crear;

crear := 'create table BATTLE_HAS_SHIPS (
ID_Battle int not null,
foreign key (ID_Battle) references BATTLE(ID_Battle),
ID_Ship int not null,
foreign key (ID_Ship) references SHIP(ID_Ship),
Starting_Amount int not null,
Remaining_Amount int not null,
Side int,
constraint pk_battle_ship primary key (ID_Battle, ID_Ship))';
execute immediate crear;
end;