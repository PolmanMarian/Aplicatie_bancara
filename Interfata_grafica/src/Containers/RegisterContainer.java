package Containers;

public class RegisterContainer {
    public String nume , prenume , username , password , adresa, telefon , cnp;

    public RegisterContainer(String nume, String prenume, String username, String password, String adresa, String telefon , String cnp) {
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.password = password;
        this.adresa = adresa;
        this.telefon = telefon;
        this.cnp = cnp;
    }

    @Override
    public String toString() {
        return "RegisterContainer{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon='" + telefon + '\'' +
                ", cnp='" + cnp + '\'' +
                '}';
    }

    public void print() {
        System.out.println(toString());
    }
}
