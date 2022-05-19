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
    public void whenAgedBrieIsProvidedAndQualityIsLessThanFifty_ItShouldIncreaseQualityAndDecreaseSellInFieldsEndOfEachDay() {
        items = new Item[] { new Item("Aged Brie", 6, 4) };
        app = new GildedRose(items);

        //first update of quality and sellIn
        app.updateQuality();
        assertEquals("Aged Brie", items[0].name);
        assertEquals(5, items[0].quality);
        assertEquals(5, items[0].sellIn);

        //second update of quality and sellIn
        app.updateQuality();
        assertEquals("Aged Brie", items[0].name);
        assertEquals(6, items[0].quality);
        assertEquals(4, items[0].sellIn);
    }

    @Test
    public void whenAgedBrieIsProvidedAndQualityIsFifty_ItShouldNotIncreaseQualityAtEndOfEachDay() {
        items = new Item[] { new Item("Aged Brie", -10, 50) };
        app = new GildedRose(items);

        //First update
        app.updateQuality();
        assertEquals("Aged Brie", items[0].name);
        assertEquals(50, items[0].quality);
        assertEquals(-11, items[0].sellIn);

        //Second update
        app.updateQuality();
        assertEquals(50, items[0].quality);
    }

    @Test
    public void whenABackstagePassWithSellInBetweenZeroAndFiveIsProvidedAndQualityIsGreaterThanFortySevenButLessThanFifty_ItShouldUpdateQualityToBeFiftyThenStop() {
        items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", 2, 48)};
        assertEquals("Backstage passes to a TAFKAL80ETC concert", items[0].name);

        app = new GildedRose(items);
        //First update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will increase by 2 to 50
        assertEquals(50, items[0].quality);
        assertEquals(1, items[0].sellIn);

        //Second update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will stay at 50
        assertEquals(50, items[0].quality);
        assertEquals(0, items[0].sellIn);

    }

    @Test
    public void whenABackstagePassWithSellInBetweenZeroAndFiveIsProvidedAndQualityIsLessThanFortyEight_ItShouldUpdateQualityToBeFiftyThenStop() {
        items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", 5, 45)};
        assertEquals("Backstage passes to a TAFKAL80ETC concert", items[0].name);

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
    public void whenABackstagePassWithSellInBetweenSixAndTenIsProvidedAndQualityIsGreaterThanFourtyEightButLessThanFifty_ItShouldUpdateQualityToBeFiftyThenStop() {
        items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 3, 49),
        };
        assertEquals("Backstage passes to a TAFKAL80ETC concert", items[0].name);

        app = new GildedRose(items);
        //First update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will increase by 1 to 50
        assertEquals(50, items[0].quality);
        assertEquals(2, items[0].sellIn);

        //Second update
        app.updateQuality();
        //Item sellIn date is expected to decrease and quality will stay at 50
        assertEquals(50, items[0].quality);
        assertEquals(1, items[0].sellIn);
    }

    @Test
    public void whenABackstagePassWithSellInBetweenSixAndTenIsProvidedAndQualityIsLessThanFourtyNine_ItShouldUpdateQualityToBeFiftyThenStop() {
        items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 47),
        };
        assertEquals("Backstage passes to a TAFKAL80ETC concert", items[0].name);

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
    public void whenABackstagePassWithSellInLessThanZeroOrGreaterThanTenIsProvidedAndQualityIsLessThanFifty_ItShouldNotUpdateQuality() {
        items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", -1, 48), new Item("Backstage passes to a TAFKAL80ETC concert", 12, 30)};
        assertEquals("Backstage passes to a TAFKAL80ETC concert", items[0].name);

        app = new GildedRose(items);

        app.updateQuality();
        //Items sellIn date is expected to decrease and quality will stay the same
        assertEquals(48, items[0].quality);
        assertEquals(-2, items[0].sellIn);
        assertEquals(30, items[1].quality);
        assertEquals(11, items[1].sellIn);
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