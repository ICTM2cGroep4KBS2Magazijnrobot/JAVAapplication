public class Product {
    private String kleur;
    private int gewicht;

    public Product(String kleur, int gewicht) {
        setKleur(kleur);
        setGewicht(gewicht);
        System.out.println("haal deze zin weg");
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

    public String getKleur() {
        return kleur;
    }

    public int getGewicht() {
        return gewicht;
    }

    @Override
    public String toString() {
        return "Dit product heeft als kleur: " + kleur + " en als gewicht " + gewicht + " kg";
    }
}
