package Week4_2;

public class Rechthoek extends Figuur {
    private int lengte;
    private int breedte;
    public Rechthoek(String kleur, int lengte, int breedte) {
        super(kleur);
    }
    public int getLengte() {
        return lengte;
    }

    public void setLengte(int lengte) {
        this.lengte = lengte;
    }

    public int getBreedte() {
        return breedte;
    }

    public void setBreedte(int breedte) {
        this.breedte = breedte;
    }
}


