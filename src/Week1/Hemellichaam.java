package Week1;

public class Hemellichaam {
    public String planeet;
    public String type;

    public Hemellichaam(String planeet,String type){
        this.planeet = planeet;
        this.type = type;
    }
    public Hemellichaam(String planeet) {
        this.planeet = planeet;
    }

    public String toString(){if (type != null){
        return type + " " + planeet;} else {return planeet + " onbekend";}
    }}
