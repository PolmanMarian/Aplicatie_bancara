package Containers;

import java.util.Objects;

public class PersonalData {
    public String lastName;
    public String firstName;
    public String cnp;
    public String adress;
    public int contractNumber;
    public String phoneNumber;

    public PersonalData() {
        lastName = "";
        firstName = "";
        cnp = "";
        adress = "";
        phoneNumber = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalData that = (PersonalData) o;
        return Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName) && Objects.equals(cnp, that.cnp) && Objects.equals(adress, that.adress) && Objects.equals(contractNumber, that.contractNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, cnp, adress, contractNumber);
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "nume='" + lastName + '\'' +
                ", prenume='" + firstName + '\'' +
                ", cnp='" + cnp + '\'' +
                ", adresa='" + adress + '\'' +
                ", numarContract='" + contractNumber + '\'' +
                '}';
    }
}
