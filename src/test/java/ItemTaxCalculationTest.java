import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taxCalculation.Item;
import taxCalculation.ItemTaxCalculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTaxCalculationTest {
    private static final String IMPORT_DUTY = "IMPORT_DUTY";
    private static final String BASIC_TAX = "BASIC_TAX";
    private Item NOT_IMPORTED_FOOD_ITEM = new Item("Chocolates", 100.);
    private Item IMPORTED_FOOD_ITEM = new Item("Imported Chocolates", 100.);
    private Item NON_IMPORTED_NON_EXEMPTED_ITEM = new Item("music CD", 100.);
    private Item IMPORTED_NON_EXEMPTED_ITEM = new Item("imported music CD", 100.);
    ItemTaxCalculation itemTaxCalculation = new ItemTaxCalculation();

    @BeforeEach
    private void addTaxes() {
        itemTaxCalculation.addNewTax(BASIC_TAX, 10.);
        itemTaxCalculation.addNewTax(IMPORT_DUTY, 5.);
        addBasicTaxOnNonExemptedItem(IMPORTED_NON_EXEMPTED_ITEM);
        addImportDutyOnImportedItem(IMPORTED_NON_EXEMPTED_ITEM);
        addImportDutyOnImportedItem(IMPORTED_FOOD_ITEM);
        addBasicTaxOnNonExemptedItem(NON_IMPORTED_NON_EXEMPTED_ITEM);
    }

    @Test
    void noTaxAddedForExemptedNonImportedItem() {
        assertEquals(100., itemTaxCalculation.calculateTaxAddedValueForItem(NOT_IMPORTED_FOOD_ITEM));
    }

    @Test
    void addsImportDutyOnAnyImportedItem() {
        assertEquals(105., itemTaxCalculation.calculateTaxAddedValueForItem(IMPORTED_FOOD_ITEM));
    }

    @Test
    void addsBasicTaxOnNonExemptedNonImportedItem() {
        assertEquals(110., itemTaxCalculation.calculateTaxAddedValueForItem(NON_IMPORTED_NON_EXEMPTED_ITEM), 1e-6);
    }

    @Test
    void addsBasicTaxAndImportedDuty() {
        assertEquals(115., itemTaxCalculation.calculateTaxAddedValueForItem(IMPORTED_NON_EXEMPTED_ITEM), 1e-6);
    }

    @Test
    void totalTaxAndPriceCalculation() {
        addTaxes();
        itemTaxCalculation.addItem(IMPORTED_FOOD_ITEM);
        itemTaxCalculation.addItem(IMPORTED_NON_EXEMPTED_ITEM);
        itemTaxCalculation.addItem(NON_IMPORTED_NON_EXEMPTED_ITEM);
        itemTaxCalculation.addItem(NOT_IMPORTED_FOOD_ITEM);

        itemTaxCalculation.calculateTotalTax();

        assertEquals(30., itemTaxCalculation.getTotalTax(), 1e-6);
        assertEquals(430., itemTaxCalculation.getTotalValue(), 1e-6);
    }

    private void addImportDutyOnImportedItem(Item item) {
        item.addTax(IMPORT_DUTY);
    }

    private void addBasicTaxOnNonExemptedItem(Item item) {
        item.addTax(BASIC_TAX);
    }
}
