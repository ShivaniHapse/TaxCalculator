package taxCalculation;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private final String name;
    private final double price;
    private List<String> taxesApplicable = new ArrayList<>();

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getTaxesApplicable() {
        return taxesApplicable;
    }

    public void addTax(String taxName) {
        if (taxesApplicable.contains(taxName)) {
            return;
        }
        taxesApplicable.add(taxName);
    }
}
