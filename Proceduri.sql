use Aplicatie_bancara;

-- selecteza cate copnturi curente are
drop procedure if exists getAccountCount;
delimiter //
create procedure getAccountCount(
    in codnp varchar(30)
)
begin
    select count(*) from relatie_client_cont where cnp = codnp;
end //
delimiter ;
-- -----------------------------------------


-- selecteaza informatiile legate de conturi (suma din el , iban , curent / economii)
drop procedure if exists getAccountData;
delimiter //
create procedure getAccountData(
    in codnp varchar(30)
)
begin
    select suma , t.iban , curent_economii from (select * from relatie_client_cont where cnp = codnp) as t join cont_bancar where cont_bancar.iban = t.iban;
end //
delimiter ;
-- -----------------------------------------

-- Insert request Iban / card (iba , 0 -> Pending , 0)
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
-- -----------------------------------------

-- Stergere cont bancar
drop procedure if exists deleteIban;
delimiter //
create procedure deleteIban(
    in `iban_to_delete` varchar(40)
)
begin
    delete from `cont_bancar` where `iban` = `iban_to_delete`;
end //
delimiter ;
-- -----------------------------------------

-- Update Money
drop procedure if exists updateMoneyIban;
delimiter //
create procedure updateMoneyIban(
    `iban_to_update` varchar(40),
    `money` integer
)
begin
    update `cont_bancar` set suma = suma + `money` where `iban` = iban_to_update;
end //
delimiter ;
-- -----------------------------------------


-- daca exita un iban
drop procedure if exists existsIban;
delimiter //
create procedure existsIban(
    in `iban_to_select` varchar(40)
)
begin
    select * from cont_bancar where `iban_to_select` = `iban`;
end //
-- -----------------------------------------


-- adauga un cont nou
drop procedure if exists addNewBankAccount;
delimiter //
create procedure addNewBankAccount(
    in `newIban` varchar(40),
    in `tip` varchar(40)
)
begin
    insert ignore into `cont_bancar` (suma, iban , curent_economii)
        value (0 , newIban , tip);
end //
-- -----------------------------------------

-- face legatura cu iban cnp
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
-- -----------------------------------------


-- transgerurile facute de un client
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

drop procedure if exists getAllTransfer;
delimiter //
create procedure getAllTransfer(
)
begin
    select data, suma, iban_cont_plecare, iban_cont_viraj, numele_virant, status , id from transferuri_bancare order by data;
end //
-- -----------------------------------------

-- Furnizorii de servicii
drop procedure if exists getFurnizori;
delimiter //
create procedure getFurnizori()
begin
    select cb.iban, curent_economii from relatie_client_cont
        join cont_bancar cb on cb.iban = relatie_client_cont.iban
    where cnp='0000000000000';
end//
-- -----------------------------------------

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
insert into cont_bancar(suma, curent_economii, iban) value (0,'Furnizor gaz','RO11RZBR7599355624881527');
insert into relatie_client_cont(iban, cnp) value('RO11RZBR7599355624881527','0000000000000');

insert into cont_bancar(suma, curent_economii, iban) value (0,'Furnizor electricitate','TR11RZBR7599355624881527');
insert into relatie_client_cont(iban, cnp) value('TR11RZBR7599355624881527','0000000000000');

insert into cont_bancar(suma, curent_economii, iban) value (0,'Furnizor internet','HU11RZBR7599355624881527');
insert into relatie_client_cont(iban, cnp) value('HU11RZBR7599355624881527','0000000000000');

insert into cont_bancar(suma, curent_economii, iban) value (0,'Cotizatie biserica','FS11RZBR7599355624881527');
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

     DECLARE sumaCont integer;

     select suma into sumaCont
     from cont_bancar where iban=ibanPlecare;


     if(sumaIn<=sumaCont)
        then
        insert into transferuri_bancare(suma, iban_cont_plecare, iban_cont_viraj, numele_titularului, numele_virant, status, data)
        value (sumaIN,IbanPLecare,IbanViraj,NumeTitular,NumeVirant,'pending',current_date);
        update cont_bancar
            set suma=suma-sumaIn
        where iban=ibanPlecare;
    end if;
end //


drop procedure if exists insertDepozit;
delimiter //
create procedure insertDepozit(
    sumaIn integer,
    cnpIn char(13),
    dobandaIn integer
)
begin
    insert into depozite(suma, status, data, cnp, dobanda) value (sumaIn,'Pending',CURRENT_DATE,cnpIn,dobandaIn);
end //


drop procedure if exists lichidareDepozit;
delimiter //
create procedure lichidareDepozit(
    idDepozit int,
    ibanContCurent varchar(40)
)
begin

    DECLARE dobandaIn int;
    DECLARE dobandaProcente int;
    DECLARE dobandaZile int;
    DECLARE dataDobanda date;
    DECLARE zile int;

    DECLARE sumaIn int;

    set sumaIn=100;

    select data into dataDobanda
    from depozite where id=idDepozit;

    select suma into sumaIn
    from depozite where id=idDepozit;

    select dobanda into dobandaIn
    from depozite where id=idDepozit;

    select procent into dobandaProcente
    from dobanzi_depozite where id=dobandaIn;

    case dobandaIn
            when 1 then
        set dobandaZile=30;
            when 2 then
        set dobandaZile=90;
            when 3 then
        set dobandaZile=180;
    end case;

    set zile=datediff(current_date(),dataDobanda)-dobandaZile;

    if (zile>0)
        then
            set sumaIn=sumaIN+sumaIN*dobandaProcente/100;
    end if;

    update cont_bancar
        set suma=suma+sumaIn
    where iban = ibanContCurent;

    delete from depozite where id=idDepozit;
end//

# call lichidareDepozit(20,'0000 0000 0000 0000');

# insert into depozite(id, suma, status, data, cnp, dobanda) value(20,200,'pending','2000-10-12',5001210060437,2);
# insert into cont_bancar(suma, curent_economii, iban) value (2000,'curent','0000 0000 0000 0000');
# insert into relatie_client_cont(iban, cnp) value('0000 0000 0000 0000','5001210060437');

drop procedure if exists getAllAngajati;
delimiter //
create procedure getAllAngajati(
)
begin
    select concat(u.nume,' ',u.prenume) as nume , a.salariul, a.norma , a.sucursala , a.departament , a.iban_salariu from angajat as a
        join users as u where u.cnp = a.cnp;
end //


-- GetFavorite
drop procedure if exists getFavorite;
delimiter //
create procedure getFavorite(
    numele_titularuluiIn varchar(40)
)
begin
#     select * from transferuri_bancare group by numele_virant;
    select distinct numele_virant , iban_cont_viraj from transferuri_bancare
      where numele_titularului = numele_titularuluiIn
#     group by numele_virant
     order by numele_virant
    limit 5;
end //
call getFavorite('Furnizor Servicii');


drop procedure if exists plataSalariu;
delimiter //
create procedure plataSalariu(
    ibanAngajat varchar(40),
    salariu int,
    numeAngajat varchar(40)
)
begin

    insert into
        transferuri_bancare
        (suma, iban_cont_plecare, iban_cont_viraj, numele_titularului, numele_virant, status, data, responsabilNume) value
        (salariu,'B00S 1234 12345',ibanAngajat,'Administrator Banca',numeAngajat,'aproved',CURRENT_DATE,'Administrator Banca');
    update cont_bancar
        set suma=suma+salariu
    where iban=ibanAngajat;

end //

call plataSalariu('DF32 2311 3312 3123 1233' , 1000 , 'AltVasile Dijkstra');

-- GetAllUsers
drop procedure if exists getAllUsers;
delimiter //
create procedure getAllUsers()
begin
    select concat(nume,' ',prenume) as nume,adresa,numar_de_telefon from users
    where cnp != '0000000000000'
        order by cnp;
end //

drop procedure if exists getCereri;
delimiter //
create procedure getCereri()
begin
    select iban,aprobare_admin,aprobare_angajat as aprobare from solicitari_card
    order by aprobare;
end //

-- GetAllAcounts
drop procedure if exists getAllAcounts;
delimiter //
create procedure getAllAcounts()
begin
    select u.cnp,concat(u.nume,' ',u.prenume),cb.iban, cb.suma from cont_bancar as cb
        join relatie_client_cont rcc on cb.iban = rcc.iban
        join users u on u.cnp=rcc.cnp
    where u.cnp != '0000000000000'
    order by cnp;
end //

call getAllAcounts();


drop procedure if exists getTaxes;
delimiter //
create procedure getTaxes()
begin
    select * from taxe_comisioane;
end //

call getTaxes();

drop procedure if exists getAllDepozite;
delimiter //
create procedure getAllDepozite(
)
begin
    select * from depozite;
end //
-- GetAllUsersWithPasswords
drop procedure if exists getAllUsersWithPasswords;
delimiter //
create procedure getAllUsersWithPassWords()
begin
    select * from users
    where cnp != '0000000000000'
        order by cnp;
end //

-- GetAllUsers
drop procedure if exists getAllUsers;
delimiter //
create procedure getAllUsers()
begin
    select concat(nume,' ',prenume) as nume,adresa,numar_de_telefon from users
    where cnp != '0000000000000'
        order by cnp;
end //

call getAllUsersWithPassWords();
