drop schema Aplicatie_bancara;
create schema if not exists Aplicatie_bancara;
use Aplicatie_bancara;


-- rankurile pe care le poate avea un user
-- ----------------------------------------
create table if not exists user_rank (
    `id` integer unique primary key ,
    `rank` varchar(20) not null unique
);

insert into user_rank(user_rank.id , `rank`)
value
    (1 , 'client'),
    (2 , 'angajat'),
    (3 , 'administrator');
-- ----------------------------------------

-- tabela de useri din care se disting : clienti(cod:1) , angajati(cod:2), administartori(cod:3)
-- ----------------------------------------
create table if not exists `users`(
    `username` varchar(250) not null , -- nume de utilizator (login screen)
    `password` varchar(250) not null , -- parola de utilixator (login screen)
    `cnp` char(13) not null unique primary key , -- cheie primara
    `nume` varchar(20) not null , -- numele utilizatorului
    `prenume` varchar(20) not null, -- prenumele utilizatorului
    `adresa` varchar(40) not null, -- adresa fizica a utilizatorului
    `numar_de_telefon` varchar(10) not null , -- numarul de telefon al utilizatorului
    `numar_de_contract` integer , -- numarul de contract al utilizatorului
    `rank` integer not null default 1 , -- rankul din cadrul aplicatiei
    constraint fk_user_rank foreign key (`rank`) references user_rank(id) on delete cascade
);
-- ----------------------------------------


-- tabela clientilor
create table if not exists `clienti` (
    `data_nasterii` date not null , -- data nasterii utilizatorului
    `adresa` varchar(40) not null unique , -- adresa de email a clientului
    `sursa_principala_de_venit` varchar(40) not null, -- sursa principala de venit a clientului
    `tranzactii_online` boolean,
    `cnp` char(13) not null unique primary key ,
    constraint fk_cnp_user_clienti foreign key (`cnp`) references users(`cnp`) on delete cascade
);
-- ----------------------------------------


-- tabela conturilor bancare
-- ----------------------------------------
create table if not exists `cont_bancar` (
    `suma` integer not null , -- suma de bani din cont
    `curent_economii` varchar(40) , -- 0 = cont curent , 1 = cont de economii
    `iban` varchar(40) not null primary key unique
);
-- ----------------------------------------


-- tabela corespondentelor dintre client si conturi
-- ----------------------------------------
create table if not exists `relatie_client_cont` (
    `iban` varchar(40) not null , -- ibanul corespunde contului bancar
    `cnp` char(13) not null , -- cnpul corespunde unui client
    constraint fk_cnp_user_relatie foreign key (`cnp`) references users(`cnp`) on delete cascade,
    constraint fk_iban_cont_bancar_relatie foreign key (`iban`) references cont_bancar(`iban`) on delete cascade
);
-- ----------------------------------------


-- departamentele existente
-- ----------------------------------------
create table if not exists `nume_departamente` (
    `id` integer not null primary key unique ,
    `nume` varchar(15) not null unique
);

insert into `nume_departamente` (id, nume)
value
    (1 , 'HR'),
    (2 , 'IT'),
    (3 , 'Functionar');
-- ----------------------------------------


-- tabela departamentelor
-- ----------------------------------------
create table if not exists `departament` (
    `nume_departament` integer not null unique primary key ,
    constraint fk_nume_departament foreign key (`nume_departament`) references nume_departamente(`id`) on delete cascade
);
-- ----------------------------------------


-- permisiunile pe care le are un user care apartine de un anumit departament
-- ----------------------------------------
-- Conventie:
    -- 0 = Nu
    -- 1 = Da
    -- 2 = Doar persoane si clienti
create table if not exists `permisiuni_departament` (
    `nume_departament` integer not null unique primary key ,
    `activitati_bancare` integer ,
    `modificare_baza_de_date` integer,
    `activitati_legate_de_utilizatori` integer,
    constraint fk_nume_deparament_permisiuni foreign key (`nume_departament`) references nume_departamente(`id`) on delete cascade
);

insert into `permisiuni_departament` (nume_departament, activitati_bancare, modificare_baza_de_date, activitati_legate_de_utilizatori)
value
    (1 , 0 , 0 , 1),
    (2 , 0 , 1 , 0),
    (3 , 1 , 0 , 2);
-- ----------------------------------------


-- tabela angajatilor
-- ----------------------------------------
create table if not exists `angajat` (
    `norma` integer not null , -- numarul de ore de lucru pe zi
    `salariul` integer not null, -- salariul pe o luna
    `sucursala` integer ,
    `departament` integer , -- departamentul la care este asociat
    `cnp` char(13) not null unique primary key ,
    constraint fk_departament_angajat foreign key (`departament`) references nume_departamente(`id`) on delete cascade ,
    constraint fk_cnp_angajati foreign key (`cnp`) references users(`cnp`) on delete cascade
);
-- ----------------------------------------


-- tabela solicitarilor de card bancar
-- ----------------------------------------
create table if not exists `solicitari_card` (
    `iban` varchar(40) not null unique primary key,
    `aprobare_admin` boolean,
    `aprobare_angajat` boolean,
    constraint fk_cnp_solicitare_card foreign key (`iban`) references relatie_client_cont(`iban`) on delete cascade
);
-- ----------------------------------------


-- tabela transferurilor bancare dintre clienti
-- ----------------------------------------
drop table if exists `transferuri_bancare`;
create table if not exists `transferuri_bancare` (
    `suma` integer,
    `iban_cont_plecare` varchar(40) not null , -- contul din care pleaca banii
    `iban_cont_viraj` varchar(40) not null , -- contul in care ajung banii
    `numele_titularului` varchar(20) not null , -- numele titularului contului in care ajung banii
    `numele_virant` varchar(20) not null , -- numele virant contului in care ajung banii
    `id` integer unique primary key auto_increment ,
    `status` varchar(10), -- statusul transferului ("CREATED" , "SUCCESSFUL" , "ERROR")
    `data` date,
    `responsabilNume` varchar(40),
    constraint fk_iban_cont_plecare_transfer foreign key (`iban_cont_plecare`) references cont_bancar(`iban`) on delete cascade,
    constraint fk_iban_cont_viraj_transfer foreign key (`iban_cont_viraj`) references cont_bancar(`iban`) on delete cascade
);
-- ----------------------------------------


-- Dobanzi
-- -----------------------------------------
create table if not exists `dobanzi_depozite`(
    `id` integer not null unique primary key,
    `descirere` varchar(200),
    `procent` int
);
-- -----------------------------------------


-- Depozite
-- -----------------------------------------
create table if not exists `depozite` (
    `id` integer not null unique auto_increment primary key,
    `suma` integer not null,
    `status` varchar(40) not null,
    `data` datetime,

    `cnp` char(13),
    `dobanda` integer not null,
    constraint fk_cnp foreign key (`cnp`) references users(cnp),
    constraint fk_dobanda foreign key (`dobanda`) references dobanzi_depozite(id)
);
insert into dobanzi_depozite(id, descirere, procent) values(1,'30 zile depozit',5);
insert into dobanzi_depozite(id, descirere, procent) values(2,'90 zile depozit',10);
insert into dobanzi_depozite(id, descirere, procent) values(3,'180 zile depozit',15);
-- ----------------------------------------

-- Comisioane
-- ----------------------------------------
create table if not exists `taxe_comisioane` (
    `id` integer not null auto_increment primary key ,
    `descriere` varchar(100),
    `procentaj` integer
);

insert into `taxe_comisioane` (descriere, procentaj) values
('Lichidare cont economii' , 2),
('Dobanda creare cont economii' , 5),
('Prag' , 500000),
('Transfer alta banca' , 1);
-- ----------------------------------------
