package Week3;

public class Medewerker {
    public String naam;
    private int leeftijd;


    public Medewerker(String naam, int leeftijd) {
        this.naam = naam;
        this.leeftijd = leeftijd;
    }

    @Override
    public String toString() {
        return  naam + leeftijd;
    }
}
