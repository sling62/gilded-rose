package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {
    private Item[] items;
    private GildedRose app;

    @Test(expected = NullPointerException.class)
    public void givenANullItemNameIsProvided_whenUpdateQualityIsCalled_ItMustThrowANullPointerException() {
        items = new Item[] { new Item("Aged Brie", 10, 16), new Item(null, 0, 0) };
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
        items = new Item[] { new Item("+5 Dexterity Vest", 0, -1) };
        app = new GildedRose(items);
        app.updateQuality();
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenARegularItemWithQualityHigherThanFiftyIsProvided_whenUpdateQualityIsCalled_ItMustThrowAIllegalArgumentException() {
        items = new Item[] { new Item("+5 Dexterity Vest", 0, 51) };
        app = new GildedRose(items);
        app.updateQuality();
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenASulfurasItemWithQualityNotEightyIsProvided_whenUpdateQualityIsCalled_ItMustThrowAIllegalArgumentException() {
        items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 50) };
        app = new GildedRose(items);
        app.updateQuality();
    }

    @Test
    public void whenASulfurasItemWithQualityofEightyIsProvided_ItShouldNotUpdateQualityOrSellInFields() {
        items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 20, 80) };
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, items[0].quality);
        assertEquals(20, items[0].sellIn);
    }

    @Test
    public void whenAgedBrieIsProvidedAndQualityIsGreaterThanZeroButLessThanFifty_ItShouldUpdateQualityAndSellInFields() {
        items = new Item[] { new Item("Aged Brie", 4, 2) };
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Aged Brie", items[0].name);
        assertEquals(1, items[0].quality);
        assertEquals(3, items[0].sellIn);
    }

    @Test
    public void whenABackstagePassIsProvidedAndQualityIsGreaterThanZeroButLessThanFifty_ItShouldUpdateQualityAndSellInFields() {
        items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Backstage passes to a TAFKAL80ETC concert", items[0].name);
        assertEquals(19, items[0].quality);
        assertEquals(14, items[0].sellIn);
    }

    @Test
    public void whenAConjuredItemIsProvidedAndQualityIsGreaterThanZeroButLessThanFifty_ItShouldUpdateQualityAndSellInFields() {
        items = new Item[] { new Item("Conjured Mana Cake", 3, 6) };
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Conjured Mana Cake", items[0].name);
        assertEquals(5, items[0].quality);
        assertEquals(2, items[0].sellIn);
    }

    @Test
    public void whenARegularItemIsProvidedAndQualityIsGreaterThanZeroButLessThanFifty_ItShouldUpdateQualityAndSellInFields() {
        items = new Item[] { new Item("+5 Dexterity Vest", 10, 20) };
        app = new GildedRose(items);
        app.updateQuality();
        assertEquals("+5 Dexterity Vest", items[0].name);
        assertEquals(19, items[0].quality);
        assertEquals(9, items[0].sellIn);
    }

    @Test
    public void whenARegularItemIsProvidedAndQualityIsZero_ItShouldNotUpdateQualityButUpdateSellInFields() {
        items = new Item[] { new Item("+5 Dexterity Vest", 10, 0) };
        app = new GildedRose(items);

        app.updateQuality();
        assertEquals("+5 Dexterity Vest", items[0].name);
        assertEquals(0, items[0].quality);
        assertEquals(9, items[0].sellIn);

        app.updateQuality();
        assertEquals("+5 Dexterity Vest", items[0].name);
        assertEquals(0, items[0].quality);
        assertEquals(8, items[0].sellIn);
    }

}