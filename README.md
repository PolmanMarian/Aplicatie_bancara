# Detalii legate de implementari

** Client **
1) Un client nu poate avea mai mult de 5 conturi
2) Clientul poate solicita eliberarea unui card si trebuie aprobata de admin si de un angajat
3) Clientul poate face transferuri bancare intre catre alti client, caz in care trebuie sa menționeze numele titularului
contului si IBAN-ul. Transferul intre conturi are status de “CREATED” cand este inițiat de client si se realizează
doar când este aprobat de un angajat, atunci banii sunt transferați si statusul devine “SUCCESSUL”. Daca sumaeste prea mare, statusul devine “ERROR” si transferul este anulat. Daca IBAN-ul este către o alta banca, se
percepe un comision de 1%. Pentru a afla daca banca este diferita, verificați substring din IBAN (ex:
ROBTRL25235346 si ROING5363464764).

4) Clientul isi poate salva o lista de contacte favorite catre care sa faca transferuri mai usor, fara sa mai specifice
IBAN-ul.
5) Clienții pot sa plătească facturi folosind un cont special, prin alegerea unui furnizor si introducerea codului
facturii. Se va genera o factura cu detaliile tranzacției.


** Angajati **
1) Angajații pot sa vada toate tranzacțiile si sa extraga rapoarte despre statusul lor (filtrate după data, status, angajat
responsabil sau client). De asemenea, pot sa vadă toate conturile unui client.

2) La logare, clienții isi vad contul curent si un meniu principal din care pot naviga spre toate activitățile bancare, iar
angajații vad o lista cu operațiunile care trebuie autorizate de ei.


** Admin **
Adminul poate efectua plata unor salarii specificând suma, un cont sursa si o lista de IBAN-uri destinatie.

** Comisioane si dobanzi **
1) Toate comisioanele, dobanzile si posibilele taxe sau praguri sunt setate din interfata de catre admin. De exemplu, pentru conturile de economii se scade o scade un comision de 2% cand este lichidat si se adauga o dobanda de 5% cand este creat. De asemenea exista depozite de 1, 3 si 6 luni pentru care se adauga o dobanda de 5%, 10% respective 15% cand se indeplineste perioada pentru care a fost facut depozitul. Clientul isi poate lichida depozitul, caz in care acesata este sters si banii sunt adaugati in contul current. Daca suma introdusa in depozit este mai mare de 500000 lei, un angajat trebuie sa aprobe deschiderea depozitului, iar un admin lichidarea lui.

2)
