import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MagazijnOverzicht extends JPanel{

    private Voorraad voorraad;
    private int xCordinateProduct1;
    private int yCordinateProduct1;
    private int xCordinateProduct2;
    private int yCordinateProduct2;
    private int xCordinateProduct3;
    private int yCordinateProduct3;
    private ArrayList<Doos> TSP_DozenLijst = new ArrayList<>();
    private Doos doos;
    private int ProductInDoosNummer = 0;

    private ArrayList<ArrayList<Product>> geheleVoorraad = new ArrayList<>(5);


    MagazijnOverzicht(Voorraad voorraad){ //in constructor object met waardes van dialoog meegeven
        setPreferredSize(new Dimension(1900, 780));
        setBackground(Color.BLACK);
        this.voorraad = voorraad;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("default", Font.PLAIN, 14));

        int xLinksboven = 10; //coordinaten voor linksboven
        int yLinksboven = 10;

        int xRechtsonder = getWidth() - 20; //coordinaten voor rechtsonder
        int yRechtsonder = getHeight() - 20;

        tekenMagazijn(g);
        tekenMagazijnRasters(g, xLinksboven, yLinksboven, xRechtsonder, yRechtsonder);

        tekenMagazijnInhoud(g);
        tekenRoute(g, ProductInDoosNummer);
//        System.out.println(getWidth() - 20);
//        System.out.println(getHeight()- 20);
    }

    private void tekenMagazijn(Graphics g){
        g.setColor(Color.GRAY);
        g.drawRect(10, 10, getWidth() - 20, getHeight()  - 20);
    }

    private void tekenMagazijnRasters(Graphics g, int xLinksboven, int yLinksboven, int xRechtsonder, int yRechtsonder){
        //breedte van magazijn op scherm is 623
        //hoogte van magazijn op scherm is 351
        int xAfstandVariabel = xLinksboven + 125;
        int yAfstandVariabel = yLinksboven + 75;

        for (int i = 0; i < 4; i++) { //teken verticale lijnen
            g.drawLine(xAfstandVariabel, yLinksboven, xAfstandVariabel, getHeight() - 10);
            xAfstandVariabel += 128;
        }

        for (int i = 0; i < 4; i++) { //teken horizontale lijnen
            g.drawLine(xLinksboven, yAfstandVariabel, getWidth() - 10, yAfstandVariabel);
            yAfstandVariabel += 72;
        }
    }

    // tekenBlokje tekent een blokje in het magazijn met de kleur, voorraad en artikelID
    private void tekenBlokje(Graphics g, int startX, int startY, int eindX, int eindY, String kleur, int voorraadArtikel, String artikelID){
        if (voorraadArtikel > 0) {
            if (kleur.equals("rood")) {
                g.setColor(Color.RED);
                g.fillRect(startX, startY, eindX, eindY);
                g.setColor(Color.WHITE);
                g.drawString("ArtikelID: " + artikelID, startX, startY + 28);
                g.drawString("Voorraad: " + voorraadArtikel, startX-25, startY + 42);
            } else if (kleur.equals("geel")) {
                g.setColor(Color.ORANGE);
                g.fillRect(startX, startY, eindX, eindY);
                g.setColor(Color.WHITE);
                g.drawString("ArtikelID: " + artikelID, startX, startY + 28);
                g.drawString("Voorraad: " + voorraadArtikel, startX-25, startY + 42);
            } else if (kleur.equals("blauw")) {
                g.setColor(Color.BLUE);
                g.fillRect(startX, startY, eindX, eindY);
                g.setColor(Color.WHITE);
                g.drawString("ArtikelID: " + artikelID, startX, startY + 28);
                g.drawString("Voorraad: " + voorraadArtikel, startX-25, startY + 42);
            }
        }
    }

    private void tekenMagazijnInhoud(Graphics g){
        int startX = 45;
        int startY = 35;

        int eindX = startX + 15;
        int eindY = startY + 10;

        for (int i = 0; i < 5; i++) {
            startX = 45;
            for (int j = 0; j < 5; j++) {
                if (voorraad.getRijElement(i, j) != null){
                    String kleur = voorraad.getRijElement(i, j).getKleur();
                    int artikelID = voorraad.getRijElement(i, j).getArtikelID();
                    if (!TSP_DozenLijst.isEmpty()){
                        if (TSP_DozenLijst.get(ProductInDoosNummer).getInhoud().size() == 2){
                            inhoudDoos2(artikelID, startX, startY, ProductInDoosNummer);

                        } else if (TSP_DozenLijst.size() == 3){
                            inhoudDoos3(artikelID, startX, startY, ProductInDoosNummer);
                        }
                    }
                    tekenBlokje(g, startX, startY, eindX, eindY, kleur, voorraad.getRijElement(i, j).getVoorraadArtikel(), Integer.toString(voorraad.getRijElement(i, j).getArtikelID()));
                }
                startX += 127;
            }
            startY += 72;
        }
    }

    public void inhoudDoos2 (int artikelID, int startX, int startY, int i){
        if(TSP_DozenLijst.get(i).getInhoud().get(0).getArtikelID() == artikelID ){
            xCordinateProduct1 = startX;
            yCordinateProduct1 = startY;
        }
        if(TSP_DozenLijst.get(i).getInhoud().get(1).getArtikelID() == artikelID ){
            xCordinateProduct2 = startX;
            yCordinateProduct2 = startY;
        }

    }

    public void inhoudDoos3 (int artikelID, int startX, int startY, int i){
        if(TSP_DozenLijst.get(i).getInhoud().get(0).getArtikelID() == artikelID ){
            xCordinateProduct1 = startX;
            yCordinateProduct1 = startY;
        }
        if(TSP_DozenLijst.get(i).getInhoud().get(1).getArtikelID() == artikelID ){
            xCordinateProduct2 = startX;
            yCordinateProduct2 = startY;
        }
        if(TSP_DozenLijst.get(i).getInhoud().get(2).getArtikelID() == artikelID){
            xCordinateProduct3 = startX;
            yCordinateProduct3 = startY;
        }
    }

    public void tekenRoute(Graphics g, int i){
        g.setColor(Color.MAGENTA);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));

        if (!TSP_DozenLijst.isEmpty()){
            if(TSP_DozenLijst.get(i).getInhoud().size() == 2){
                g2.drawLine(xCordinateProduct1 + 30, yCordinateProduct1 + 20, xCordinateProduct2 + 30, yCordinateProduct2 + 20);
            }
            if(TSP_DozenLijst.get(i).getInhoud().size() ==  3){
                g2.drawLine();
                g2.drawLine();
            }
        }
        repaint();
    }
    public void setTSP_DozenLijst(ArrayList<Doos> TSP_DozenLijst) {
        this.TSP_DozenLijst = TSP_DozenLijst;
    }
}
