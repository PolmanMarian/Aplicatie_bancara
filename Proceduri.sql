
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


#testing
# call getAccountCount('7610075035');
call getAccountData( '7610075035');

select * from users where cnp = '7610075035';