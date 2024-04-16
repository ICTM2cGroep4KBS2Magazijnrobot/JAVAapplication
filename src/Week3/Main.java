package Week3;

public class Main {

    /*
    // vraag 1b
	public static void main(String[] args) {
        Winkel winkel = new Winkel("Supermarkt om de hoek");

        Medewerker m1 = new Medewerker("Huey", 17);
        winkel.zetMedewerkerIn(0, m1);

        Medewerker m2 = new Medewerker("Dewey", 18);
        winkel.zetMedewerkerIn(0, m2);                  // kassa 0 is al bezet
        winkel.zetMedewerkerIn(3029, m2);               // kassa 3029 bestaat niet
        winkel.zetMedewerkerIn(1, m2);
	}
	*/


    /*
    // vraag 1c
    public static void main(String[] args) {
        Winkel winkel = new Winkel("Supermarkt om de hoek");

        Medewerker m1 = new Medewerker("Huey", 17);
        winkel.zetMedewerkerIn(0, m1);

        Medewerker m2 = new Medewerker("Dewey", 18);
        winkel.zetMedewerkerIn(1, m2);

        Medewerker a = winkel.geefKassaMedewerker(1);
        System.out.println(a);                          // Dewey (18 jaar)

        Medewerker b = winkel.geefKassaMedewerker(2);
        System.out.println(b);                          // null
    }
     */


    // vraag 1d
    public static void main(String[] args) {
        Winkel winkel = new Winkel("Supermarkt om de hoek");

        Medewerker m1 = new Medewerker("Huey", 17);
        winkel.zetMedewerkerIn(0, m1);

        Medewerker m2 = new Medewerker("Dewey", 18);
        winkel.zetMedewerkerIn(1, m2);

        winkel.printKassaLijst();
                                                    // Kassabezetting - Supermarkt om de hoek:
                                                    // Kassa 0: Huey (17 jaar)
                                                    // Kassa 1: Dewey (18 jaar)
                                                    // Kassa 2: ---
    }



    /*
    // vraag 1e
    public static void main(String[] args) {
        Winkel winkel = new Winkel("Supermarkt om de hoek");

        Medewerker m1 = new Medewerker("Huey", 17);
        winkel.zetMedewerkerIn(0, m1);

        winkel.printKassaLijst();
                                                    // Kassabezetting - Supermarkt om de hoek:
                                                    // Kassa 0: Huey (17 jaar)
                                                    // Kassa 1: ---
                                                    // Kassa 2: ---

        Medewerker m2 = new Medewerker("Dewey", 18);
        int vrijeKassa = winkel.zetOpEersteVrijeKassa(m2);
        System.out.println(vrijeKassa);             // 1

        winkel.printKassaLijst();
                                                    // Kassabezetting - Supermarkt om de hoek:
                                                    // Kassa 0: Huey (17 jaar)
                                                    // Kassa 1: Dewey (18 jaar)
                                                    // Kassa 2: ---
    }

     */
}
