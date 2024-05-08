import java.util.ArrayList;

public class Doos {
    private ArrayList<Product> inhoud = new ArrayList<>();


    public Doos(Product product) {
        inhoud.add(product);
    }


    public Doos(Product product, Product product2) {
        inhoud.add(product);
        inhoud.add(product2);
    }

    public Doos(Product product, Product product2, Product product3) {
        inhoud.add(product);
        inhoud.add(product2);
        inhoud.add(product3);
    }

    public ArrayList<Product> getInhoud() {
        return inhoud;
    }

    public Product getProduct(int num) {
        return inhoud.get(num);
    }

    public void setInhoud(int num, Product product) {
        inhoud.set(num,product);
    }

    public void voegToe(Product product){
        inhoud.add(product);
    }
}
