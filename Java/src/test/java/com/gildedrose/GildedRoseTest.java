package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    private final String AGED_BRIE_ITEM = "Aged Brie";
    private final String REGULAR_ITEM = "+5 Dexterity Vest";
    private final String SULFURAS_ITEM = "Sulfuras, Hand of Ragnaros";
    private final String BACKSTAGE_PASSES_ITEM = "Backstage passes to a TAFKAL80ETC concert";
    private final String CONJURED_ITEM = "Conjured Mana Cake";
    private Item[] items;
    private GildedRose app;

    @Test(expected = NullPointerException.class)
    public void givenANullItemNameIsProvided_whenUpdateQualityIsCalled_ItMustThrowANullPointerException() {
        items = new Item[] { new Item(AGED_BRIE_ITEM, 10, 16), new Item(null, 0, 0) };
        app = new GildedRose(items);
        app.updateQuality();
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAEmptyItemNameIsProvided_whenUpdateQualityIsCalled_ItMustThrowAIllegalArgumentException() {
        items = new Item[] { new Item(" ", 0, 0) };
        app = new GildedRose(items);
        app.updateQuality();
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenANegativeQualityIsProvided_whenUpdateQualityIsCalled_ItMustThrowAIllegalArgumentException() {
        items = new Item[] { new Item(REGULAR_ITEM, 0, -1) };
        app = new GildedRose(items);
        app.updateQuality();
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenARegularItemWithQualityHigherThanFiftyIsProvided_whenUpdateQualityIsCalled_ItMustThrowAIllegalArgumentException() {
        items = new Item[] { new Item(REGULAR_ITEM, 0, 51) };
        app = new GildedRose(items);
        app.updateQuality();
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenASulfurasItemWithQualityNotEightyIsProvided_whenUpdateQualityIsCalled_ItMustThrowAIllegalArgumentException() {
        items = new Item[] { new Item(SULFURAS_ITEM, 0, 50) };
        app = new GildedRose(items);
        app.updateQuality();
    }

    @Test
    public void whenASulfurasItemWithQualityofEightyIsProvided_ItShouldNotUpdateQualityOrSellInFields() {
        items = new Item[] { new Item(SULFURAS_ITEM, 20, 80) };
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, items[0].quality);
        assertEquals(20, items[0].sellIn);
    }

    @Test
    public void whenAgedBrieIsProvidedAndQualityIsLessThanFifty_ItShouldIncreaseQualityAndDecreaseSellInFieldsEndOfEachDay() {
        items = new Item[] { new Item(AGED_BRIE_ITEM, 6, 4) };
        app = new GildedRose(items);

        //first update of quality and sellIn
        app.updateQuality();
        assertEquals(AGED_BRIE_ITEM, items[0].name);
        assertEquals(5, items[0].quality);
        assertEquals(5, items[0].sellIn);

        //second update of quality and sellIn
        app.updateQuality();
        assertEquals(AGED_BRIE_ITEM, items[0].name);
        assertEquals(6, items[0].quality);
        assertEquals(4, items[0].sellIn);
    }

    @Test
    public void whenAgedBrieIsProvidedAndQualityIsFifty_ItShouldNotIncreaseQualityAtEndOfEachDay() {
        items = new Item[] { new Item(AGED_BRIE_ITEM, -10, 50) };
        app = new GildedRose(items);

        //First update
        app.updateQuality();
        assertEquals(AGED_BRIE_ITEM, items[0].name);
        assertEquals(50, items[0].quality);
        assertEquals(-11, items[0].sellIn);

        //Second update
        app.updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    public void whenABackstagePassWithSellInBetweenZeroAndFiveIsProvided_ItShouldIncreaseQualityByThreeUntilValueIsFifty() {
        items = new Item[] {new Item(BACKSTAGE_PASSES_ITEM, 5, 45)};
        assertEquals(BACKSTAGE_PASSES_ITEM, items[0].name);

        app = new GildedRose(items);
        //First update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will increase by 3
        assertEquals(48, items[0].quality);
        assertEquals(4, items[0].sellIn);

        //Second update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will increase by 2 to 50
        assertEquals(50, items[0].quality);
        assertEquals(3, items[0].sellIn);

        //Third update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will stay at 50
        assertEquals(50, items[0].quality);
        assertEquals(2, items[0].sellIn);
    }

    @Test
    public void whenABackstagePassWithSellInBetweenSixAndTenIsProvided_ItShouldIncreaseQualityByTwoUntilFifty() {
        items = new Item[] {
                new Item(BACKSTAGE_PASSES_ITEM, 10, 47),
        };
        assertEquals(BACKSTAGE_PASSES_ITEM, items[0].name);

        app = new GildedRose(items);
        //First update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will increase by 2 to 49
        assertEquals(49, items[0].quality);
        assertEquals(9, items[0].sellIn);

        //Second update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will increase by 1 to 50
        assertEquals(50, items[0].quality);
        assertEquals(8, items[0].sellIn);

        //Third update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will stay at 50
        assertEquals(50, items[0].quality);
        assertEquals(7, items[0].sellIn);
    }

    @Test
    public void whenABackstagePassWithSellInLessThanZeroOrGreaterThanTenIsProvided_ItShouldNotUpdateQuality() {
        items = new Item[]{new Item(BACKSTAGE_PASSES_ITEM, -1, 48), new Item(BACKSTAGE_PASSES_ITEM, 12, 30)};
        assertEquals(BACKSTAGE_PASSES_ITEM, items[0].name);

        app = new GildedRose(items);

        app.updateQuality();
        //Items sellIn date is expected to decrease and quality will stay the same
        assertEquals(48, items[0].quality);
        assertEquals(-2, items[0].sellIn);
        assertEquals(30, items[1].quality);
        assertEquals(11, items[1].sellIn);
    }

    @Test
    public void whenAConjuredItemWithSellInLessThanZeroIsProvidedAndQualityIsGreaterThanZero_ItShouldDecreaseQualityByFourUntilZero() {
        items = new Item[] { new Item(CONJURED_ITEM, -1, 6) };
        app = new GildedRose(items);

        //First update
        app.updateQuality();
        assertEquals(CONJURED_ITEM, items[0].name);
        //Item sellIn date is expected to decrease and quality will decrease by 4
        assertEquals(2, items[0].quality);
        assertEquals(-2, items[0].sellIn);

        //Second update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will decrease to 0
        assertEquals(0, items[0].quality);
        assertEquals(-3, items[0].sellIn);

        //Third update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will stay the same
        assertEquals(0, items[0].quality);
        assertEquals(-4, items[0].sellIn);
    }

    @Test
    public void whenAConjuredItemWithSellInGreaterThanOrEqualToZeroIsProvided_ItShouldDecreaseQualityByTwoUntilZero() {
        items = new Item[] { new Item(CONJURED_ITEM, 4, 3) };
        app = new GildedRose(items);

        //First update
        app.updateQuality();
        assertEquals(CONJURED_ITEM, items[0].name);
        //Item sellIn date is expected to decrease and quality will decrease by 2
        assertEquals(1, items[0].quality);
        assertEquals(3, items[0].sellIn);

        //Second update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will decrease to 0
        assertEquals(0, items[0].quality);
        assertEquals(2, items[0].sellIn);

        //Third update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will stay the same
        assertEquals(0, items[0].quality);
        assertEquals(1, items[0].sellIn);
    }

    @Test
    public void whenARegularItemWithSellInLessThanZeroIsProvided_ItShouldDecreaseQualityByTwoUntilZero() {
        items = new Item[] { new Item(REGULAR_ITEM, -1, 3) };
        app = new GildedRose(items);

        //First update
        app.updateQuality();
        assertEquals(REGULAR_ITEM, items[0].name);
        assertEquals(1, items[0].quality);
        assertEquals(-2, items[0].sellIn);

        //Second update
        app.updateQuality();
        assertEquals(0, items[0].quality);
        assertEquals(-3, items[0].sellIn);

        //Third update
        app.updateQuality();
        assertEquals(0, items[0].quality);
        assertEquals(-4, items[0].sellIn);
    }

    @Test
    public void whenARegularItemWithSellInGreaterThanOrEqualToZeroIsProvided_ItShouldDecreaseQualityByOneUntilZero() {
        items = new Item[] { new Item(REGULAR_ITEM, 5, 1) };
        app = new GildedRose(items);

        //First update
        app.updateQuality();
        //Items sellIn date is expected to decrease and quality will decrease by 1
        assertEquals(REGULAR_ITEM, items[0].name);
        assertEquals(0, items[0].quality);
        assertEquals(4, items[0].sellIn);

        //Second update
        app.updateQuality();
        //Items sellIn date is expected to decrease and quality will stay the same
        assertEquals(0, items[0].quality);
        assertEquals(3, items[0].sellIn);
    }
}