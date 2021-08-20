package taxCalculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTaxCalculation {
    private Map<String, Double> taxValuesByTypes = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private double totalTax;
    private double totalValue;

    public ItemTaxCalculation() {
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addNewTax(String taxName, double taxValue) {
        taxValuesByTypes.put(taxName, taxValue);
    }

    public double calculateTaxAddedValueForItem(Item item) {
        double taxAdded = 0.;
        for (String taxName : item.getTaxesApplicable()) {
            taxAdded += taxValuesByTypes.get(taxName);
        }
        return item.getPrice() * (1 + taxAdded / 100);
    }

    public void calculateTotalTax() {
        double totalValueWithoutTax = 0.;
        double totalValueWithTax = 0.;
        for (Item item : items) {
            totalValueWithoutTax += item.getPrice();
            totalValueWithTax += calculateTaxAddedValueForItem(item);
        }
        this.totalValue = totalValueWithTax;
        this.totalTax = totalValueWithTax - totalValueWithoutTax;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public double getTotalValue() {
        return totalValue;
    }
}
