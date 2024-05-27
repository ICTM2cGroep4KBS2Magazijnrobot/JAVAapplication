public class Product {
    private String kleur;
    private int gewicht;
    private int voorraadArtikel;
    private int artikelID;
    private String naam;
    private int prijs;

    public Product(String kleur, int gewicht, int voorraadArtikel, int artikelID, String naam, int prijs) {
        setKleur(kleur);
        setGewicht(gewicht);
        setVoorraadArtikel(voorraadArtikel);
        setArtikelID(artikelID);
        setNaam(naam);
        setPrijs(prijs);
    }

    private void setPrijs(int prijs) {this.prijs = prijs;}

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public void setVoorraadArtikel(int voorraadArtikel) {
        this.voorraadArtikel = voorraadArtikel;
    }

    public void setArtikelID(int artikelID) {
        this.artikelID = artikelID;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getPrijs(){return  prijs;}


    public String getKleur() {
        return kleur;
    }

    public int getGewicht() {
        return gewicht;
    }

    public int getVoorraadArtikel() {
        return voorraadArtikel;
    }

    public int getArtikelID() {
        return artikelID;
    }
    public String getNaam() {
        return naam;
    }

    @Override
    public String toString() {
        return "Artikel: " + artikelID +" Kleur: " + kleur + " Gewicht: " + gewicht + " Naam: " + naam;
    }
}
