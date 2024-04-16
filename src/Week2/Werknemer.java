package Week2;

public class Werknemer {
    private String naam;
    private String email;
    private String afdeling;
    private double salaris;

    public Werknemer(String naam,String email, String afdeling, double salaris) {
        this.email = email;
        this.naam = naam;
        this.afdeling = afdeling;
        this.salaris = salaris;
    }
    public Werknemer(String naam) {
        this(naam, "???", "???", -1);
    }

    public String getNaam() {
        return naam;
    }

    public double getSalaris() {
        if (salaris >= 0) {
            this.salaris = salaris;
        } else {
            System.out.println("mag niet");
            }
   return salaris; }

    public void verhoogsalaris(double salaris) {
        {
            if (salaris >= 0) {
                salaris = salaris;
            } else {
                System.out.println("MAG NIETTTTTTTTTTTT");
                salaris = 0;
            }
        }
    }

    public void getEmail() {
        if (this.email == "???"){
            this.email = email;
        } else {
            System.out.println("mag niet");
        }
    }

    @Override
    public String toString(){
        return naam+ " " +email+" "+afdeling+" "+salaris;
}}
