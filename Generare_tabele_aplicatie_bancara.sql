/*
    Toate comisionele, dobanzile si posibilele taxe sau praguri sunt setate din interfata de catre admin. De exemplu,
pentru conturile de economii se scade o scade un comision de 2% cand este lichidat si se adauga o dobanda de
5% cand este creat. De asemenea exista depozite de 1, 3 si 6 luni pentru care se adauga o dobanda de 5%, 10%
respective 15% cand se indeplineste perioada pentru care a fost facut depozitul. Clientul isi poate lichida depozitul,
caz in care acesata este sters si banii sunt adaugati in contul current. Daca suma introdusa in depozit este mai mare
de 500000 lei, un angajat trebuie sa aprobe deschiderea depozitului, iar un admin lichidarea lui.
*/

/*
    Clientul poate solicita eliberarea unui card, care va fi conectat la contul curent, iar cererea sa trebuie sa fie aprobata
de un admin si de un angajat.
*/

/*
    Clientul poate face transferuri bancare intre catre alti client, caz in care trebuie sa menționeze numele titularului
contului si IBAN-ul. Transferul intre conturi are status de “CREATED” cand este inițiat de client si se realizează
doar când este aprobat de un angajat, atunci banii sunt transferați si statusul devine “SUCCESSUL”. Daca sumaeste prea mare, statusul devine “ERROR” si transferul este anulat. Daca IBAN-ul este către o alta banca, se
percepe un comision de 1%. Pentru a afla daca banca este diferita, verificați substring din IBAN (ex:
ROBTRL25235346 si ROING5363464764).
*/

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
    `adresa` varchar(30) not null, -- adresa fizica a utilizatorului
    `numar_de_telefon` varchar(10) not null , -- numarul de telefon al utilizatorului
    `numar_de_contract` integer , -- numarul de contract al utilizatorului
    `rank` integer not null default 1 , -- rankul din cadrul aplicatiei
    constraint fk_user_rank foreign key (`rank`) references user_rank(id)
);
-- ----------------------------------------


-- tabela clientilor
create table if not exists `clienti` (
    `data_nasterii` date not null , -- data nasterii utilizatorului
    `adresa` varchar(30) not null unique , -- adresa de email a clientului
    `sursa_principala_de_venit` varchar(30) not null, -- sursa principala de venit a clientului
    `tranzactii_online` boolean,
    `cnp` char(13) not null unique primary key ,
    constraint fk_cnp_user_clienti foreign key (`cnp`) references users(`cnp`)
);
-- ----------------------------------------


-- tabela conturilor bancare
-- ----------------------------------------
create table if not exists `cont_bancar` (
    `suma` integer not null , -- suma de bani din cont
    `curent_economii` boolean , -- 0 = cont curent , 1 = cont de economii
    `iban` char(24) not null primary key unique
);
-- ----------------------------------------


-- tabela corespondentelor dintre client si conturi
-- ----------------------------------------
create table if not exists `relatie_client_cont` (
    `iban` char(24) not null , -- ibanul corespunde contului bancar
    `cnp` char(13) not null , -- cnpul corespunde unui client
    constraint fk_cnp_user_relatie foreign key (`cnp`) references users(`cnp`),
    constraint fk_iban_cont_bancar_relatie foreign key (`iban`) references cont_bancar(`iban`)
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
    constraint fk_nume_departament foreign key (`nume_departament`) references nume_departamente(`id`)
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
    constraint fk_nume_deparament_permisiuni foreign key (`nume_departament`) references nume_departamente(`id`)
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
    constraint fk_departament_angajat foreign key (`departament`) references nume_departamente(`id`) ,
    constraint fk_cnp_angajati foreign key (`cnp`) references users(`cnp`)
);
-- ----------------------------------------


-- tabela solicitarilor de card bancar
-- ----------------------------------------
create table if not exists `solicitari_card` (
    `cnp` char(13) not null unique primary key,
    `aprobare_admin` boolean,
    `aprobare_angajat` boolean,
    constraint fk_cnp_solicitare_card foreign key (`cnp`) references clienti(`cnp`)
);
-- ----------------------------------------


-- tabela transferurilor bancare dintre clienti
-- ----------------------------------------
create table if not exists `transferuri_bancare` (
    `iban_cont_plecare` char(24) not null , -- contul din care pleaca banii
    `iban_cont_viraj` char(24) not null , -- contul in care ajung banii
    `numele_titularului` varchar(20) not null , -- numele titularului contului in care ajung banii
    `id` integer unique primary key auto_increment ,
    `status` varchar(10), -- statusul transferului ("CREATED" , "SUCCESSFUL" , "ERROR")
    constraint fk_iban_cont_plecare_transfer foreign key (`iban_cont_plecare`) references cont_bancar(`iban`),
    constraint fk_iban_cont_viraj_transfer foreign key (`iban_cont_viraj`) references cont_bancar(`iban`)
);
-- ----------------------------------------
