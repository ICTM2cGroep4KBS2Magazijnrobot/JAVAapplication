import java.util.ArrayList;

public class Doos {
    private ArrayList<Product> inhoud;

    public Doos(){

    }



    public Doos(ArrayList<Product> products) {
        this.inhoud = products;
    }

    public Doos(Product eersteProduct, Product tweedeProduct, Product derdeProduct) {
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

    @Override
    public String toString() {
        if(inhoud.size() == 1){
            return inhoud.get(0).toString();
        }
        if (inhoud.size() == 2){
            return inhoud.get(0).toString() + " \n" + inhoud.get(1).toString();
        }
        if (inhoud.size() == 3){
            return inhoud.get(0).toString() + " \n" + inhoud.get(1).toString() + " \n " + inhoud.get(2).toString();
        }
        else return "";
    }
}
