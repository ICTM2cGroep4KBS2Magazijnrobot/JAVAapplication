import java.util.AbstractList;
import java.util.ArrayList;

public class BinPacking {

    ArrayList<ArrayList<Product>> order;

    public static ArrayList<Doos> binpacking(ArrayList<Product> Invoer) {
        Invoer.sort((Product p1, Product p2) -> (Integer.compare(p2.getGewicht(), p1.getGewicht()))); // sorteer descending
        ArrayList<Product> binpacker = new ArrayList<>(Invoer); // dupliceer de arraylist

        int max = 10; // max in doos

        ArrayList<Doos> Dozenlijst = new ArrayList<>(); // Maak nieuwe order arraylist

        while (binpacker.size() > 0) { //zolang er nog producten nog in te pakken zijn
            ArrayList<Product> doos = new ArrayList<>();
            int currentWeight = 0;
            for (int i = 0; i < binpacker.size(); i++) {
                Product product = binpacker.get(i);
                if (currentWeight + product.getGewicht() <= max) {
                    doos.add(product);
                    currentWeight += product.getGewicht();
                    binpacker.remove(i);
                    i--; // -1 want item is verwijderd
                }
            }
            Doos Nieuwdoos = new Doos(doos);
            Dozenlijst.add(Nieuwdoos);
        }
        return Dozenlijst;
    }
}