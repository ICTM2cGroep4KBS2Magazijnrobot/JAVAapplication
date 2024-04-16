package Week2;

public class Artikel {
    private int nummer;
    private String naam;
    private double prijs;
    private static double btw = 1.19;
    private static int teller;
    public static int hoogste;
    public static int laagste;
    public Artikel(String naam, double prijs) {
        this.naam = naam;
        setPrijs(prijs);
        this.prijs = prijs;
        teller = teller + 1;
        this.nummer = teller;

    }
    public double getBTWprijs() {
        return prijs * btw;
    }

    public void setPrijs(double prijs) {
        if (prijs < 0 && prijs >= 1000){
            System.out.println("MAG NIET");
    } else { this.prijs = prijs;}
        if (this.prijs > hoogste){
            this.prijs = hoogste;}
        if (this.prijs < laagste){
            this.prijs = laagste;}
        }
        }



