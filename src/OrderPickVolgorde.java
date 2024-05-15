import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderPickVolgorde extends JPanel {

    private String pickvolgordeHeader = "Geen order geselecteerd ";
    private int huidigeOrder;
    private MagazijnOverzicht panel;
    ArrayList<Product> invoer = new ArrayList<Product>();
    private Voorraad voorraad;
    private ArrayList<Integer> stockitemids = new ArrayList<>();

    private ArrayList<Doos> TSP_Dozenlijst = new ArrayList<>();

    private int Doosnummer;



    OrderPickVolgorde(Voorraad voorraad, MagazijnOverzicht panel){ //in constructor object met waardes van dialoog meegeven
        setPreferredSize(new Dimension(1900, 780));
        this.panel = panel;
        setBackground(Color.WHITE);
        this.voorraad = voorraad;

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("default", Font.PLAIN, 30));

        g.drawString(pickvolgordeHeader, 100, 40);
        stockitemids.clear();

        if (huidigeOrder > 0){ //hier alle orderlines printen van een order.
            g.setFont(new Font("default", Font.PLAIN,15));
            DB_connectie.getOrderlines(stockitemids, huidigeOrder);


            ArrayList<Product> producten = new ArrayList<>();

            for (int i = 0; i < stockitemids.size(); i++) { //voeg aan de hand van stockitemid's de producten toe aan producten lijst
                for (int j = 0; j < voorraad.getGeheleVoorraad().size(); j++) {
                    for (int k = 0; k < voorraad.getGeheleVoorraad().get(j).size(); k++) {
                        if (stockitemids.get(i) == voorraad.getRijElement(j, k).getArtikelID()){
                            producten.add(voorraad.getRijElement(j,k));
                        }
                    }
                }
            }
            System.out.println(producten + "\n");
            ArrayList<Doos> Dozenlijst = BinPacking.binpacking(producten);

            System.out.println("dozenlijst na BinPacking: \n");
            for (Doos doos : Dozenlijst){
                System.out.println(doos);
                System.out.println("\n");
            }
            System.out.println("\n\n\n\n\n Dozenlijst na TSP: \n");

            for (int i = 0; i < Dozenlijst.size(); i++) {
                Doos oude_doos = new Doos();
                oude_doos = Dozenlijst.get(i);
                oude_doos = TravellingSalesManProbleem.TSP(voorraad, oude_doos);
                Dozenlijst.set(i, oude_doos);
            }
            TSP_Dozenlijst = Dozenlijst;
            panel.setTSP_DozenLijst(Dozenlijst, Doosnummer);

            for (Doos doos: Dozenlijst){
                System.out.println(doos + "\n");
            }
//
            for (int i = 0; i < Dozenlijst.size(); i++) {
                System.out.println("Doos " + (i+1) + ": \n" + Dozenlijst.get(i));
            }


            int startX = 10;
            int startY = 70;



//            for (int i = 0; i < stockitemids.size(); i++) {
//                g.drawString("-----------------------------------------------------------------------------------------------------------------------",
//                        startX, startY + 10);
//
//                Product product = voorraad.getArtikel(stockitemids.get(i));
//                g.drawString(i+ 1 + ". Artikel " + product.getArtikelID() + ": " + product.getNaam() + ". Gewicht: " + product.getGewicht() + "kg", startX, startY);
//
//                startY+= 30;
//            }
            for (int i = 0; i < Dozenlijst.size(); i++) {
                g.drawString("Doos " +(i+1) + ":", startX, startY);
                startY+= 20;
                for (int j = 0; j < Dozenlijst.get(i).getInhoud().size(); j++) {
                    Product product = Dozenlijst.get(i).getInhoud().get(j);
                    g.drawString("    " + (j + 1) + ". Artikel " + product.getArtikelID() + ": " + product.getNaam() + ". Gewicht: " + product.getGewicht() + "kg", startX, startY);
                    startY += 20;

                }
                startY += 20;
            }
        }
    }
    public void setPickvolgordeHeader(String pickvolgordeHeader) {
        this.pickvolgordeHeader = pickvolgordeHeader;
    }

    public void setHuidigeOrder(int huidigOrder) {
        setPickvolgordeHeader("Pickvolgorde Order: " + huidigOrder);
        this.huidigeOrder = huidigOrder;


    }

    public void setDoosnummer(int doosnummer) {
        Doosnummer = doosnummer;
    }

    public ArrayList<Doos> getTSP_Dozenlijst() {
        return this.TSP_Dozenlijst;
    }


}

