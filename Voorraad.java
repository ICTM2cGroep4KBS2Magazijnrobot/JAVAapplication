import java.util.ArrayList;

public class Voorraad {
    private ArrayList<ArrayList<Product>> geheleVoorraad = new ArrayList<>(5);
    private ArrayList<Product> rij1 = new ArrayList<>(5);
    private ArrayList<Product> rij2 = new ArrayList<>(5);
    private ArrayList<Product> rij3 = new ArrayList<>(5);
    private ArrayList<Product> rij4 = new ArrayList<>(5);
    private ArrayList<Product> rij5 = new ArrayList<>(5);

    public Voorraad() {
        for (int i = 0; i < 5; i++) { //vul alle rijen met null
            rij1.add(null);
            rij2.add(null);
            rij3.add(null);
            rij4.add(null);
            rij5.add(null);
        }
        for (int i = 0; i < 5; i++) {// vull de rijen array met null
            geheleVoorraad.add(null);
        }
        for (int i = 0; i < 5; i++) {
            geheleVoorraad.set(0, rij1);
            geheleVoorraad.set(1, rij2);
            geheleVoorraad.set(2, rij3);
            geheleVoorraad.set(3, rij4);
            geheleVoorraad.set(4, rij5);
        }
    }

    public void setRijElement(int index1, int index2, Product product){
        geheleVoorraad.get(index1).set(index2, product);
    }

    public Product getRijElement(int index1, int index2){
        return geheleVoorraad.get(index1).get(index2);
    }
    public ArrayList<ArrayList<Product>> getGeheleVoorraad() {
        return geheleVoorraad;
    }
}
