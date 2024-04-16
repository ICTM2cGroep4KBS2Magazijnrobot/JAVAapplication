package Week3;

public class Winkel {
    private String naam;
    private Medewerker[] kassamedewerkers;

    public Winkel(String naam) {
        this.naam = naam;
        this.kassamedewerkers = new Medewerker[3];

    }

    public void zetMedewerkerIn(int kassa, Medewerker medewerker) {
        if (kassa < 0 || kassa >= kassamedewerkers.length) {
            System.out.println("kassa " + kassa + " bestaat niet");
        } else {
            if (this.kassamedewerkers[kassa] == null) {
                this.kassamedewerkers[kassa] = medewerker;
            } else {
                System.out.println(kassa + "is bezet");
            }
        }
    }
    public Medewerker Geefhkassamedewerker(int kassa){
        return this.kassamedewerkers[kassa];
    }
    public void printKassaLijst(){
        for (int i = 0; i < kassamedewerkers.length; i++) {
            System.out.println();
        }

    }

}




