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
    `cnp` char(13) not null,
    `nume` varchar(20) not null,
    `prenume` varchar(20) not null,
    `adresa` varchar(30) not null,
    `numar_de_telefon` varchar(10) not null,
    `iban` char(34) not null,
    `numar_de_contract` integer,
    `rank` integer not null default 1,
    constraint fk_user_rank foreign key (`rank`) references user_rank (id)
);

