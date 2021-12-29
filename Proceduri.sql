
use Aplicatie_bancara;

drop procedure if exists getAccountCount;
create procedure getAccountCount(
    in codnp varchar(30)
)
begin
    select count(*) from relatie_client_cont where cnp = codnp;
end

call getAccountCount('7610075035');


select * from users where cnp = '7610075035';