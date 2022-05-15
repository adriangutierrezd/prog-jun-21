package prog.jun.prog_jun_2021;

public class ModelTable {
    String nif, name, surname, date, price;

    public ModelTable(String nif, String name, String surname, String date, String price) {
        this.nif = nif;
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.price = price;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
