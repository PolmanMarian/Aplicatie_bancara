package Containers;

import java.util.Objects;

public class PersonalData {
    public String nume;
    public String prenume;
    public String cnp;
    public String adresa;
    public int numarContract;
    public String numarDeTelefon;

    public PersonalData() {
        nume = new String();
        prenume = new String();
        cnp = new String();
        adresa = new String();
        numarDeTelefon = new String();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalData that = (PersonalData) o;
        return Objects.equals(nume, that.nume) && Objects.equals(prenume, that.prenume) && Objects.equals(cnp, that.cnp) && Objects.equals(adresa, that.adresa) && Objects.equals(numarContract, that.numarContract);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, cnp, adresa, numarContract);
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", cnp='" + cnp + '\'' +
                ", adresa='" + adresa + '\'' +
                ", numarContract='" + numarContract + '\'' +
                '}';
    }
}
