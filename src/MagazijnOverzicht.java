import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MagazijnOverzicht extends JPanel{
    //breedte van magazijn op scherm is 623
    //hoogte van magazijn op scherm is 351
    private Voorraad voorraad;

    private ArrayList<Doos> TSP_DozenLijst = new ArrayList<>();
    private Doos doos;
    private int ProductInDoosNummer = 2;

    private ArrayList<ArrayList<Product>> geheleVoorraad = new ArrayList<>(5);
    private int robotstatus = 2;
    private int robotXcord = 580;
    private int robotYcord = 340;



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
        tekenStatus(g, robotstatus);
        tekenMagazijnInhoud(g);
        tekenRobot(g);

    }

    private void tekenMagazijn(Graphics g){
        g.setColor(Color.GRAY);
        g.drawRect(10, 10, getWidth() - 20, getHeight()  - 20);
    }

    private void tekenMagazijnRasters(Graphics g, int xLinksboven, int yLinksboven, int xRechtsonder, int yRechtsonder){

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

    private void tekenStatus(Graphics g, int status){
        g.setColor(Color.WHITE);
        switch (status){
            case 1: //handmatig - groen
                g.drawString(": Handmatig", 25, 18);
                g.setColor(Color.GREEN);
                break;
            case 2: //automatisch - oranje
                g.drawString(": Automatisch", 25, 18);
                g.setColor(Color.ORANGE);
                break;
            case 3:
                g.drawString(": Noodstop!", 25, 18);
                g.setColor(Color.RED);
                break;
        }

        g.fillOval(5, 3, 20, 20);
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
//                    tekenBlokje(g, startX, startY, eindX, eindY, kleur, voorraad.getRijElement(i, j).getVoorraadArtikel(), Integer.toString(voorraad.getRijElement(i, j).getArtikelID()));

                    if (!TSP_DozenLijst.isEmpty()){
                        ArrayList<Product> alleProducten = new ArrayList<>(); //dit is the definitieve volgorde lijst
                        for (int k = 0; k < TSP_DozenLijst.size(); k++) {
                            for (int l = 0; l < TSP_DozenLijst.get(k).getInhoud().size(); l++) {
                                alleProducten.add(TSP_DozenLijst.get(k).getInhoud().get(l));
                            }
                        }
                        for (int k = 0; k < alleProducten.size(); k++) { //hier alle lijntjes tekenen
                            if(artikelID == alleProducten.get(k).getArtikelID()){
                                g.setColor(Color.GREEN);
                                g.drawString(Integer.toString(k + 1), startX +25, startY - 5);
                                Graphics2D g2 = (Graphics2D) g;
                                g2.setStroke(new BasicStroke(5));
                                g2.drawRect(startX, startY, eindX, eindY);
                            }
                        }

                    }
                    tekenBlokje(g, startX, startY, eindX, eindY, kleur, voorraad.getRijElement(i, j).getVoorraadArtikel(), Integer.toString(voorraad.getRijElement(i, j).getArtikelID()));
                }
                startX += 127;
            }
            startY += 72;
        }
    }

    private void tekenRobot(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(robotXcord, robotYcord, 50, 40);//robothoofd

        g.setColor(Color.orange);
        g.fillRect(robotXcord + 15, robotYcord - 5, 20, 5); //robot hoedje

        g.setColor(Color.YELLOW);
        g.fillRect(robotXcord + 5, robotYcord + 5, 15, 10); //robot ogen
        g.fillRect(robotXcord + 30, robotYcord + 5, 15, 10); //robot ogen

        g.setColor(Color.WHITE);//robot mond
        g.fillRect(robotXcord + 10, robotYcord + 20, 30, 10);

        g.setColor(Color.RED); //teken oren
        g.fillRect(robotXcord - 7, robotYcord + 10, 7, 15);
        g.fillRect(robotXcord + 50, robotYcord + 10, 7, 15);

        g.setColor(Color.GRAY);
        g.drawRect(robotXcord + 50, robotYcord + 10, 7, 15);
        g.drawRect(robotXcord - 7, robotYcord + 10, 7, 15);

//        g.setColor(Color.GRAY); //teken tanden
//////        for (int i = 0; i < 5; i++) {
//////            g.drawLine(robotXcord + 15, robotYcord+ 20, robotXcord + 15, robotYcord + 20 + 20);
//////            robotXcord += 5;
//////        }
//        g.drawLine(robotXcord + 15, robotYcord+ 20, robotXcord + 15, robotYcord + 20 + 20);
//        g.drawLine(robotXcord + 20, robotYcord+ 20, robotXcord + 20, robotYcord + 20 + 20);
//        g.drawLine(robotXcord + 25, robotYcord+ 20, robotXcord + 25, robotYcord + 20 + 20);
//        g.drawLine(robotXcord + 30, robotYcord+ 20, robotXcord + 30, robotYcord + 20 + 20);
//        g.drawLine(robotXcord + 35, robotYcord+ 20, robotXcord + 35, robotYcord + 20 + 20);

    }

    public void setTSP_DozenLijst(ArrayList<Doos> TSP_DozenLijst, int doosnummer) {
        this.TSP_DozenLijst = TSP_DozenLijst;
        setProductInDoosNummer(doosnummer);
    }

    public void setProductInDoosNummer(int productInDoosNummer) {
        ProductInDoosNummer = productInDoosNummer;
    }

    public void setRobotstatus(int robotstatus) {
        this.robotstatus = robotstatus;
    }

    public int getRobotstatus() {
        return robotstatus;
    }

    public void setRobotCords(int xCord, int yCord){
        if (xCord < 0){
            xCord += 255;
        }
        if(yCord < 0){
            yCord += 255;
        }
        this.robotXcord = xCord;
        this.robotYcord = yCord;
    }
}
