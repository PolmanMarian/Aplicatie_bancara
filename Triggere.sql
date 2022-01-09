--  Prag transferuri bancare

drop trigger if exists pragTransferuri;
DELIMITER $$
CREATE TRIGGER pragTransferuri after insert on transferuri_bancare
FOR EACH ROW
BEGIN

    DECLARE prag int;
    set prag = 0;
    select procentaj into prag from taxe_comisioane where id=3;

    if(new.suma>prag)
        then update transferuri_bancare
                set status='pending'
        where id = new.id;
    else
#         set new.status = 'aproved';
        update transferuri_bancare
        set new.status = 'aproved'
        where id = new.id;
    end if;

end $$;

DELIMITER ;

-- Adauga dobanda la cont de economii

drop trigger if exists contEconomii;
DELIMITER $$
CREATE TRIGGER contEconomii after insert on cont_bancar
FOR EACH ROW
BEGIN

    DECLARE dobanda varchar(40);
    set dobanda=0;

    if new.curent_economii = 'De economii'
        then select procentaj into dobanda from taxe_comisioane where id=2;
    end if;

    update cont_bancar
        set suma=suma+suma*dobanda/100
    where iban = new.iban;
end $$;

DELIMITER ;


-- PRAGUL LA DEPOZITE

drop trigger if exists pragDepozite;
DELIMITER $$
CREATE TRIGGER pragDepozite after insert on depozite
FOR EACH ROW
BEGIN

    DECLARE prag int;
    set prag=0;

    select procentaj into prag from taxe_comisioane where id=3;

    if(new.suma>prag)
        then update depozite
                set status='pending'
        where id=new.id;
    else
        update depozite
        set status = 'aproved'
        where id=new.id;
    end if;

end $$;

DELIMITER ;


-- Trigger cand lichidezi un cont de economii

drop trigger if exists comisionEconomii;
DELIMITER $$
CREATE TRIGGER comisionEconomii before delete on cont_bancar
FOR EACH ROW
BEGIN

    DECLARE comision int;
    set comision=0;

    select procentaj into comision from taxe_comisioane where id=1;

    if(old.curent_economii='economii')
        then update cont_bancar
                set suma=suma+suma*comision/100
        where iban=old.iban;
    end if;

end $$;

DELIMITER ;

--  Trigger comision banca diferita

drop trigger if exists comisionBancaDiferita;
DELIMITER $$
CREATE TRIGGER comisionBancaDiferita before insert on transferuri_bancare
FOR EACH ROW
BEGIN

    DECLARE comision int;
    set comision=0;

    select procentaj into comision from taxe_comisioane where id=4;

    if(SUBSTRING(new.iban_cont_viraj, 1, 2)!=SUBSTRING(new.iban_cont_plecare,1,2))
        then update cont_bancar
                set suma=suma-new.suma*comision/100
        where iban = new.iban_cont_viraj;
    end if;

end $$;
DELIMITER ;


drop trigger if exists statusTranzactie;
DELIMITER $$
CREATE TRIGGER statusTranzactie after update on transferuri_bancare
FOR EACH ROW
BEGIN

    DECLARE ibanViraj varchar(40);
    DECLARE ibanPlecare varchar(40);

    select iban_cont_viraj into ibanViraj from transferuri_bancare
    where id = new.id;

    select iban_cont_plecare into ibanPlecare from transferuri_bancare
    where id = new.id;

    if(new.status = 'aproved')
        then update cont_bancar
            set suma=suma+new.suma
        where iban=ibanViraj;
    elseif(new.status = 'declined')
        then update cont_bancar
            set suma=suma+new.suma
        where iban=ibanPlecare;
    end if;
end $$
DELIMITER ;