public class Product {
    private String kleur;
    private int gewicht;
    private int voorraadArtikel;
    private int artikelID;
    private String naam;

    public Product(String kleur, int gewicht, int voorraadArtikel, int artikelID, String naam) {
        setKleur(kleur);
        setGewicht(gewicht);
        setVoorraadArtikel(voorraadArtikel);
        setArtikelID(artikelID);
        setNaam(naam);
    }

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
        return "Dit product heeft als kleur: " + kleur + " en als gewicht " + gewicht + " kg";
    }
}
