use Aplicatie_bancara;

insert ignore into cont_bancar (suma, curent_economii, iban)
values
(12 , 1 , 'ES88 4224 9908 1790 5066 4463'),
(12 , 1 , 'SM27 A681 9057 121C UKZ1 5WFS FRP'),
(12 , 1 , 'RS19 2619 7884 1322 3840 58'),
(12 , 1 , 'AE81 9875 7323 4623 7033 262'),
(12 , 1 , 'PL61 6062 8251 8912 3036 8436 3949');

insert ignore into relatie_client_cont (cnp, iban)
values
    ('7610075035' , 'ES88 4224 9908 1790 5066 4463'),
    ('7610075035' , 'SM27 A681 9057 121C UKZ1 5WFS FRP'),
    ('7610075035' , 'RS19 2619 7884 1322 3840 58'),
    ('7610075035' , 'AE81 9875 7323 4623 7033 262'),
    ('7610075035' , 'PL61 6062 8251 8912 3036 8436 3949');

