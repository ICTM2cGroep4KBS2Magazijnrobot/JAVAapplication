import javax.swing.*;
import java.awt.*;

public class MagazijnOverzicht extends JPanel{

    private Voorraad voorraad;
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
        System.out.println(getWidth() - 20);
        System.out.println(getHeight()- 20);
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

    private void tekenBlokje(Graphics g, int startX, int startY, int eindX, int eindY, String kleur){
        if (kleur.equals("rood")){
            g.setColor(Color.RED);
            g.fillRect(startX, startY, eindX, eindY);
        }
    }
    private void tekenMagazijnInhoud(Graphics g){
        int startX = 45;
        int startY = 35;

        int eindX = startX + 15;
        int eindY = startY + 10;

//        for (int i = 0; i < 5; i++) {
//            if (voorraad.get(i) != null){
//                String kleur = Voorraad.get(i).getKleur();
//                tekenBlokje(g, startX, startY, eindX, eindY, kleur);
//                startX += 127;
//
//            }
//            else{
//                startX += 127;
//            }
//        }

    }

}
