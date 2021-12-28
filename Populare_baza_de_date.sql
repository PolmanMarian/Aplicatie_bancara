use Aplicatie_bancara;


insert into users (username, password, cnp, nume, prenume, adresa, numar_de_telefon, numar_de_contract , `rank`)
value
    ('root' , 'root' ,      '1311210019001' , 'Fedora' , 'Costel' , 'Sub pod' , '0766435345' , 12 , 3),
    ('angajat' , 'angajat' ,'6211210123001' , 'Coste' , 'Andrei' , 'Sub celalalt pod' , '0733435345' , 13 , 2);

select users.`rank` from users where
`username` = 'root' and
`password` = 'root';

select * from users where
`username` = 'root' and `password` = 'root';
