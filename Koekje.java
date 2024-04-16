public class Koekje {
    private String naam;


    public Koekje(String naam) {
        setNaam(naam);
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return "Koekje heet: " + naam;
    }
}
