drop schema Aplicatie_bancara;
create schema if not exists Aplicatie_bancara;
use Aplicatie_bancara;

create table if not exists user_rank (
    `id` integer primary key ,
    `rank` varchar(20) not null unique
);

insert into user_rank(user_rank.id , `rank`)
value
    (1 , 'client'),
    (2 , 'angajat'),
    (3 , 'administrator');

create table if not exists `users`(
    `username` varchar(250) not null,
    `password` varchar(250) not null,
    `cnp` char(13) not null,
    `nume` varchar(20) not null,
    `prenume` varchar(20) not null,
    `adresa` varchar(30) not null,
    `numar_de_telefon` varchar(10) not null,
    `numar_de_contract` integer,
    `rank` integer not null default 1,
    constraint fk_user_rank foreign key (`rank`) references user_rank (id),
    primary key (`cnp`)
);

create table if not exists `clienti` (
    `data_nasterii` date not null,
    `adresa` varchar(30) not null,
    `sursa_principala_de_venit` varchar(30) not null,
    `tranzactii_online` boolean,
    `cnp` char(13) not null,
    constraint fk_cnp_user_clienti foreign key (`cnp`) references users(`cnp`),
    primary key(`cnp`)
);

create table if not exists `cont_bancar` (
    `suma` integer not null,
    `curent_economii` boolean,
    `iban` char(24) not null,
    primary key (`iban`)
);

create table if not exists `relatie_client_cont` (
    `iban` char(24) not null,
    `cnp` char(13) not null,
    constraint fk_cnp_user_relatie foreign key (`cnp`) references users(`cnp`),
    constraint fk_iban_cont_bancar_relatie foreign key (`iban`) references cont_bancar(`iban`)
);


