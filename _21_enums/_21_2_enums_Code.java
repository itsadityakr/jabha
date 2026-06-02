package _21_enums;

enum Laptop {
    Alienware(120000),
    Lenovo(100000),
    HP,
    Dell(130000);

    private int price;

    Laptop() {
        this.price = 110000;
    }

    Laptop(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

public class _21_2_enums_Code {
    public static void main(String[] args) {
        Laptop lap = Laptop.Alienware;
        System.out.println(lap + " : " + lap.getPrice());
        System.out.println(lap.ordinal());
        System.out.println(lap.name());
    }
}
