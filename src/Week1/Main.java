package Week1;

public class Main {public static void main(String[] args) {
Auto auto2 = new Auto(true,4,"merdeces",400);
    System.out.println(auto2.boost);


Auto auto3 = new Auto(false);
    System.out.println(auto3.boost);

    Hemellichaam hl1 = new Hemellichaam("Mercurius", "planeet");

    System.out.println("naam: " + hl1);

    Hemellichaam hl2 = new Hemellichaam("Mercurius");

    System.out.println(hl2);
}
}
