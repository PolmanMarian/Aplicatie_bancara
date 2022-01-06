use Aplicatie_bancara;


insert ignore into users (username, password, cnp, nume, prenume, adresa, numar_de_telefon)
value ('client' , 'client' , '6220103089429' , 'Vasile' , 'Prenume' , 'Undeva' , '0744234342');

insert ignore into cont_bancar (suma, curent_economii, iban)
values
(12 , 'Curent' , 'ES88 4224 9908 1790 5066 4463'),
(100 , 'Curent' , 'SM27 A681 9057 121C UKZ1 5WFS FRP'),
(233 , 'Curent' , 'RS19 2619 7884 1322 3840 58'),
(132 , 'Curent' , 'AE81 9875 7323 4623 7033 262'),
(112 , 'Curent' , 'PL61 6062 8251 8912 3036 8436 3949');

insert ignore into relatie_client_cont (cnp, iban)
values
    ('6220103089429' , 'ES88 4224 9908 1790 5066 4463'),
    ('6220103089429' , 'SM27 A681 9057 121C UKZ1 5WFS FRP'),
    ('6220103089429', 'RS19 2619 7884 1322 3840 58'),
    ('6220103089429', 'AE81 9875 7323 4623 7033 262'),
    ('6220103089429' , 'PL61 6062 8251 8912 3036 8436 3949');

