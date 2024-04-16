package Week1;

public class Auto {
    public boolean boost;
    public int prijs;
    public String merk;
    public int kilometerafstand;

    public Auto(boolean boost,int prijs, String merk, int kilometerafstand){
        this.boost = boost;
        this.prijs = prijs;
        this.merk = merk;
        this.kilometerafstand = kilometerafstand;
    }
    public Auto(boolean boost) {
        this.boost = boost;


    }
}

