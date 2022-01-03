use Aplicatie_bancara;

drop procedure if exists getAccountCount;
delimiter //
create procedure getAccountCount(
    in codnp varchar(30)
)
begin
    select count(*) from relatie_client_cont where cnp = codnp;
end //

delimiter ;

drop procedure if exists getAccountData;
delimiter //
create procedure getAccountData(
    in codnp varchar(30)
)
begin
    select suma , t.iban , curent_economii from (select * from relatie_client_cont where cnp = codnp) as t join cont_bancar where cont_bancar.iban = t.iban;
end //
delimiter ;

drop procedure if exists adaugareRequestIban;
delimiter //
create procedure adaugareRequestIban(
    in iban varchar(40)
)
begin
    insert ignore into `solicitari_card` (iban, aprobare_admin, aprobare_angajat)
        value (iban , 0 , 0);
end //
delimiter ;

drop procedure if exists deleteIban;
delimiter //
create procedure deleteIban(
    in `iban_to_delete` varchar(40)
)
begin
    delete from `cont_bancar` where `iban` = `iban_to_delete`;
end //


drop procedure if exists existsIban;
delimiter //
create procedure existsIban(
    in `iban_to_select` varchar(40)
)
begin
    select * from cont_bancar where `iban_to_select` = `iban`;
end //

drop procedure if exists addNewBankAccount;
delimiter //
create procedure addNewBankAccount(
    in `newIban` varchar(40),
    in `tip` varchar(40)
)
begin
    insert ignore into `cont_bancar` (suma, curent_economii, iban)
        value (0 , newIban , tip);
end //

drop procedure if exists associateCnpIban;
delimiter //
create procedure associateCnpIban(
    in `@CNP` char(13),
    in `@IBAN` varchar(40)
)
begin
    insert `relatie_client_cont` (iban, cnp)
        value (`@IBAN` , `@CNP`);
end //





call addNewBankAccount('hwsdfkajdsgfahsdfasd' , 'jfasdfj')

# update transferuri_bancare set status = 'Pending' where id = 1;
#
# update transferuri_bancare set status = 'ceva' where id = 1;
#
# select * from transferuri_bancare where
# concat(iban_cont_plecare , iban_cont_viraj , numele_titularului , id , `status`) like '%"+criter+"%';
#
# insert into users (username, password, cnp, nume, prenume, adresa, numar_de_telefon)
# values ('c' , 'c' , '6211217017662' , 'George' , 'Dumitru' , 'In deal' , '4673676674');

#testing
# call getAccountCount('7610075035');
# call getAccountData( '7610075035');
# select * from users where cnp = '7610075035';