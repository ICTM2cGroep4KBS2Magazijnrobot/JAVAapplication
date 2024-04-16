package Week4_1;

public class Persoon {
    private String naam;
    private int leeftijd;

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setLeeftijd(int leeftijd) {
        this.leeftijd = leeftijd;
    }

    private void setNaam2(){
        if (naam.length() < 3){
            System.out.println("Naam moet langer");
        } else {this.naam = naam;}
    }
    private void setLeeftijd2(){
        if (leeftijd < 0 || leeftijd > 120){
            System.out.println("leeftijd moet tussen de 0 en 120");
        } else {leeftijd = leeftijd;}
    }

    // DIT MOETEN 2 SETTERS WORDEN TEUN







}
