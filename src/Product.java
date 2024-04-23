public class Product {
    private String kleur;
    private int gewicht;
    private int voorraadArtikel;
    private String artikelID;

    public Product(String kleur, int gewicht, int voorraadArtikel, String artikelID) {
        setKleur(kleur);
        setGewicht(gewicht);
        setVoorraadArtikel(voorraadArtikel);
        setArtikelID(artikelID);
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

    public void setArtikelID(String artikelID) {
        this.artikelID = artikelID;
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

    public String getArtikelID() {
        return artikelID;
    }

    @Override
    public String toString() {
        return "Dit product heeft als kleur: " + kleur + " en als gewicht " + gewicht + " kg";
    }
}
