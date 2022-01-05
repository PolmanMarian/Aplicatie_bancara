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

-- Vizualizare tranzactii

drop procedure if exists provideTransfers;
delimiter //
create procedure provideTransfers(
    in `IbanIN` varchar(40),
    in `NameIN` varchar(40)
)
begin
    select concat(u.nume,' ',u.prenume) as NumePrenume,
           tc.iban_cont_viraj,
           tc.iban_cont_plecare,
           tc.data
           from transferuri_bancare as tc
        join relatie_client_cont as rcc
            on tc.iban_cont_plecare=rcc.iban
        join clienti as c
            on rcc.cnp = c.cnp
        join users u on c.cnp = u.cnp
    where tc.iban_cont_plecare=IbanIN and concat(u.nume,' ',u.prenume)=NameIN;
end //


drop procedure if exists getTransfer;
delimiter //
create procedure getTransfer(
    nume varchar(20),
    prenume varchar(20)
)
begin

    select data, suma, iban_cont_plecare, iban_cont_viraj, numele_virant, status from transferuri_bancare
    where numele_titularului=concat(nume,' ',prenume) order by data;

end //



drop procedure if exists getFurnizori;
delimiter //
create procedure getFurnizori()
begin

    select cb.iban, curent_economii from relatie_client_cont
        join cont_bancar cb on cb.iban = relatie_client_cont.iban
    where cnp='0000000000000';
end//

use Aplicatie_bancara;

drop procedure if exists insertFullFlowClient;
delimiter //
create procedure insertFullFlowClient(

    cnpIn varchar(13),
    usernameIn varchar(13),
    passwordIn varchar(13),
    numeIn varchar(13),
    prenumeIn varchar(13),
    adresaIn varchar(13),
    nrTelIn varchar(10),
    nrCtrIn varchar(10),
    rankIn int,

    data_nasteriiIn date,
    sursaVenitIn varchar(40)


)
begin
    insert into users(cnp,username,password, nume, prenume, adresa, numar_de_telefon, numar_de_contract,`rank`)
    value(cnpIn,usernameIn,passwordIn,numeIn,prenumeIn,adresaIn,nrTelIn,nrCtrIn,rankIn);

    insert into clienti(data_nasterii, adresa, sursa_principala_de_venit, cnp)
    value(data_nasteriiIn,adresaIn,sursaVenitIn,cnpIn);
end //

call insertFullFlowClient('0000000000000','firma','firma','Furnizor','Servicii','-','-',123,3,'2020-06-26','-');

insert into cont_bancar(suma, curent_economii, iban) value (0,1,'RO11RZBR7599355624881527');
insert into relatie_client_cont(iban, cnp) value('RO11RZBR7599355624881527','0000000000000');

insert into cont_bancar(suma, curent_economii, iban) value (0,2,'TR11RZBR7599355624881527');
insert into relatie_client_cont(iban, cnp) value('TR11RZBR7599355624881527','0000000000000');

insert into cont_bancar(suma, curent_economii, iban) value (0,3,'HU11RZBR7599355624881527');
insert into relatie_client_cont(iban, cnp) value('HU11RZBR7599355624881527','0000000000000');

insert into cont_bancar(suma, curent_economii, iban) value (0,4,'FS11RZBR7599355624881527');
insert into relatie_client_cont(iban, cnp) value('FS11RZBR7599355624881527','0000000000000');

drop procedure if exists insertTransfer;
delimiter //
create procedure insertTransfer(
    sumaIN int,
    IbanPLecare varchar(40),
    IbanViraj varchar(40),
    NumeTitular varchar(20),
    NumeVirant varchar(20)
)
begin

    insert into transferuri_bancare(suma, iban_cont_plecare, iban_cont_viraj, numele_titularului, numele_virant, status, data)
        value (sumaIN,IbanPLecare,IbanViraj,NumeTitular,NumeVirant,'pending',current_date);
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