import java.util.ArrayList;

public class Doos {
    private ArrayList<Product> inhoud;



    public Doos(ArrayList<Product> products) {
        this.inhoud = products;
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
