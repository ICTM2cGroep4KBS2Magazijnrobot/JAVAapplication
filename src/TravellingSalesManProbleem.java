import java.util.ArrayList;

public class TravellingSalesManProbleem {


    public TravellingSalesManProbleem() {

    }

    public static Doos TSP(Voorraad voorraad, Doos doos) {
       // String kleur = voorraad.getRijElement(i, j).getKleur();
      //  Product eersteProduct = new Product(kleur, gewicht, voorraadArtikel, artikelID, naam);
       // Product tweedeProduct = new Product(kleur, gewicht, voorraadArtikel, artikelID, naam);
     //   Product derdeProduct = new Product(kleur, gewicht, voorraadArtikel, artikelID, naam);

        Doos nieuwe_doos = new Doos();

        if (doos.getInhoud().size() == 1) {
            return doos;
        }
        if (doos.getInhoud().size() == 2) {
            nieuwe_doos = InhoudIs2(voorraad, doos);

        }
        if (doos.getInhoud().size() == 3) {
            nieuwe_doos = InhoudIs3(voorraad, doos);

        }
        return nieuwe_doos;
       //  return new Doos( eersteProduct, tweedeProduct, derdeProduct);
    }

    private static Doos InhoudIs2(Voorraad voorraad, Doos doos) {
        int scoreProduct1 = 0;
        int scoreProduct2 = 0;

        int huidigeX = 0;
        int huidigeY = 0;

        int xWaardeProduct1 = 0;
        int yWaardeProduct1 = 0;

        int xWaardeProduct2 = 0;
        int yWaardeProduct2 = 0;

        Doos nieuwe_doos = new Doos();

        for (int i = 0; i < 2; i++) {
            int artikelID = doos.getProduct(i).getArtikelID();
            for (int j = 0; j < voorraad.getGeheleVoorraad().size(); j++) {
                for (int k = 0; k < voorraad.getGeheleVoorraad().get(j).size(); k++) {
                    if (artikelID == voorraad.getGeheleVoorraad().get(j).get(k).getArtikelID()) {
                        if (i == 0) {
                            xWaardeProduct1 = k;
                            yWaardeProduct1 = j;
                        } else {
                            xWaardeProduct2 = k;
                            yWaardeProduct2 = j;
                        }
                    }
                }
            }
        }


        scoreProduct1 = (huidigeX - xWaardeProduct1) + (huidigeY - yWaardeProduct1);
        scoreProduct2 = (huidigeY - xWaardeProduct2) + (huidigeY - yWaardeProduct2);

        if (scoreProduct1 < 0){
            scoreProduct1 = scoreProduct1 * -1;
        }
        if(scoreProduct2 < 0){
            scoreProduct2 = scoreProduct2 * -1;
        }
        if (scoreProduct1 <= scoreProduct2) {
            nieuwe_doos.voegToe(doos.getProduct(0));
            nieuwe_doos.voegToe(doos.getProduct(1));
        } else if (scoreProduct1 > scoreProduct2) {
            nieuwe_doos.voegToe(doos.getProduct(1));
            nieuwe_doos.voegToe(doos.getProduct(0));
        }



        return nieuwe_doos;
    }

    private static Doos InhoudIs3(Voorraad voorraad, Doos doos) {
        int scoreProduct1 = 0;
        int scoreProduct2 = 0;
        int scoreProduct3 = 0;

        int huidigeX = 0;
        int huidigeY = 0;

        int xWaardeProduct1 = 0;
        int yWaardeProduct1 = 0;

        int xWaardeProduct2 = 0;
        int yWaardeProduct2 = 0;

        int xWaardeProduct3 = 0;
        int yWaardeProduct3 = 0;


        Doos nieuwe_doos = new Doos();

        for (int i = 0; i < 3; i++) {
            int artikelID = doos.getProduct(i).getArtikelID();
            for (int j = 0; j < voorraad.getGeheleVoorraad().size(); j++) {
                for (int k = 0; k < voorraad.getGeheleVoorraad().get(j).size(); k++) {
                    if (artikelID == voorraad.getGeheleVoorraad().get(j).get(k).getArtikelID()) {
                        if (i == 0) {
                            xWaardeProduct1 = k;
                            yWaardeProduct1 = j;
                        } else if (i == 1) {
                            xWaardeProduct2 = k;
                            yWaardeProduct2 = j;
                        } else if (i == 2) {
                            xWaardeProduct3 = k;
                            yWaardeProduct3 = j;
                        }


                    }
                }
            }

        }

        scoreProduct1 = (huidigeX - xWaardeProduct1) + (huidigeY - yWaardeProduct1);
        scoreProduct2 = (huidigeY - xWaardeProduct2) + (huidigeY - yWaardeProduct2);
        scoreProduct3 = (huidigeX - xWaardeProduct3) + (huidigeY - yWaardeProduct3);

        if(scoreProduct1 < 0){
            scoreProduct1 = scoreProduct1 * -1;
        }
        if(scoreProduct2 < 0){
            scoreProduct2 = scoreProduct2 * -1;
        }
        if(scoreProduct3 < 0){
            scoreProduct3 = scoreProduct3 * -1;
        }

        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(scoreProduct1);
        scores.add(scoreProduct2);
        scores.add(scoreProduct3);


        int minimum = scores.get(0);
        int index = 0;

        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) < minimum) {
                minimum = scores.get(i);
                index = i;
            }
        }

        nieuwe_doos.voegToe(doos.getProduct(index)); //voeg product met laagste waarde toe aan doos
//        scores.remove(index); //verwijder dit product

        if (index == 0) {//als het dichtsbijzijnde product de eerste was doe dit
            huidigeX = xWaardeProduct1;
            huidigeY = yWaardeProduct1;

            scoreProduct2 = (huidigeY - xWaardeProduct2) + (huidigeY - yWaardeProduct2);
            scoreProduct3 = (huidigeX - xWaardeProduct3) + (huidigeY - yWaardeProduct3);

            if (scoreProduct2 <= scoreProduct3) {
                nieuwe_doos.voegToe(doos.getProduct(1));
                nieuwe_doos.voegToe(doos.getProduct(0));
            } else if (scoreProduct2 > scoreProduct3) {
                nieuwe_doos.voegToe(doos.getProduct(0));
                nieuwe_doos.voegToe(doos.getProduct(1));
            }

            return nieuwe_doos;

        } else if (index == 1) { //als de dichtsbijzijnde product de tweede was
            huidigeX = xWaardeProduct2;
            huidigeY = yWaardeProduct2;


            scoreProduct1 = (huidigeY - xWaardeProduct1) + (huidigeY - yWaardeProduct1);
            scoreProduct3 = (huidigeX - xWaardeProduct3) + (huidigeY - yWaardeProduct3);

            if (scoreProduct1 <= scoreProduct3) {
                nieuwe_doos.voegToe(doos.getProduct(1));
                nieuwe_doos.voegToe(doos.getProduct(0));

            } else if (scoreProduct1 > scoreProduct3) {
                nieuwe_doos.voegToe(doos.getProduct(0));
                nieuwe_doos.voegToe(doos.getProduct(1));

            }
        }
        else if (index == 2) {//als dichtsbijzijnde het derde product
            huidigeX = xWaardeProduct3;
            huidigeY = yWaardeProduct3;


            scoreProduct1 = (huidigeY - xWaardeProduct1) + (huidigeY - yWaardeProduct1);
            scoreProduct2 = (huidigeX - xWaardeProduct2) + (huidigeY - yWaardeProduct2);

            if (scoreProduct1 <= scoreProduct2) {
                nieuwe_doos.voegToe(doos.getProduct(1));
                nieuwe_doos.voegToe(doos.getProduct(0));

            } else if (scoreProduct1 > scoreProduct2) {
                nieuwe_doos.voegToe(doos.getProduct(0));
                nieuwe_doos.voegToe(doos.getProduct(1));
            }


        }
        return nieuwe_doos;
    }
}