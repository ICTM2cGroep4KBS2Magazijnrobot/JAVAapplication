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

    public void removeRijElement(int index1, int index2){
        getGeheleVoorraad().get(index1).set(index2, null);
    }

    public Product getRijElement(int index1, int index2){

        return geheleVoorraad.get(index1).get(index2);
    }

    public Product getArtikel(int artikelID){
        for (int i = 0; i < geheleVoorraad.size(); i++) {
            for (int j = 0; j < 5; j++) {
                if(geheleVoorraad.get(i).get(j).getArtikelID() == artikelID){
                    return geheleVoorraad.get(i).get(j);
                }
            }
        }
        return null;
    }

    public ArrayList<Integer> getCoordinaten(int artikelID){
        ArrayList<Integer> coordinatenlijst = new ArrayList<>();
        for (int i = 0; i < geheleVoorraad.size(); i++) {
            for (int j = 0; j < geheleVoorraad.get(i).size(); j++) {
                if (geheleVoorraad.get(i).get(j).getArtikelID() == artikelID){
                    coordinatenlijst.add(j);
                    coordinatenlijst.add(i);
                }
            }
        }
        if(coordinatenlijst.size() > 0){
            return coordinatenlijst;
        }
        else{
            return null;
        }

    }
    public ArrayList<ArrayList<Product>> getGeheleVoorraad() {
        return geheleVoorraad;
    }
}
