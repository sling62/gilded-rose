package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    /**
     * Updates the quality of items at the end of each day
     */
    public void updateQuality() {
        //Loops over items
        for (int i = 0; i < items.length; i++) {
            //Shared validation of fields on all items
            validateItemFields(i);

            // Checks to make sure item name isn't "Sulfuras, Hand of Ragnaros"
            // If it isn't updates the quality of the item and sell by date
            // If it is, skips update of quality and sell by date
            if(items[i].name != "Sulfuras, Hand of Ragnaros") {
                //checks to make sure initial quality of item is not > 50, if it is throws exception
                if (items[i].quality > 50) {
                    throw new IllegalArgumentException("Item quality cannot be greater than 50");
                }

                //Updates quality based on category of item
                switch (items[i].name){
                    case "Aged Brie":
                        updateAgedBrieItemQuality(i);
                        break;
                    case "Backstage passes to a TAFKAL80ETC concert":
                        updateBackstagePassesItemQuality(i);
                        break;
                    case "Conjured Mana Cake":
                        updateConjuredItemQuality(i);
                        break;
                    default:
                        updateRegularItemQuality(i);
                        break;
                }

                //Updates sell by date to be one less day
                items[i].sellIn = items[i].sellIn - 1;
            } else {
                //Checks that "Sulfuras, Hand of Ragnaros" quality value is 80, if not throws exception
                if(items[i].quality != 80) {
                    throw new IllegalArgumentException("Item quality must be 80 for 'Sulfuras, Hand of Ragnaros'");
                }
            }
        }
    }

    /**
     * Validate initial field values of an item in items
     * @param itemIndex index of item in items
     */
    private void validateItemFields(int itemIndex) {
        if(items[itemIndex].name == null){
            throw new NullPointerException("Item name cannot be null");
        }
        if (items[itemIndex].name.isBlank() || items[itemIndex].name.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty");
        }
        if(items[itemIndex].quality <0) {
            throw new IllegalArgumentException("Item quality cannot be less than 0");
        }
    }

    /**
     * Update the quality of an aged brie
     * @param itemIndex index of aged brie in items
     */
    private void updateAgedBrieItemQuality(int itemIndex) {
        //This is purely for testing
        if(items[itemIndex].quality > 0) {
            items[itemIndex].quality = items[itemIndex].quality -1;
        }
    }

    /**
     * Update the quality of a backstage pass
     * @param itemIndex index of backstage pass in items
     */
    private void updateBackstagePassesItemQuality(int itemIndex) {
        //This is purely for testing
        if(items[itemIndex].quality > 0) {
            items[itemIndex].quality = items[itemIndex].quality -1;
        }
    }

    /**
     * Update the quality of a conjured item
     * @param itemIndex index of conjured item in items
     */
    private void updateConjuredItemQuality(int itemIndex) {
        //This is purely for testing
        if(items[itemIndex].quality > 0) {
            items[itemIndex].quality = items[itemIndex].quality -1;
        }
    }

    /**
     * Update the quality of a regular item
     * @param itemIndex index of regular item in items
     */
    private void updateRegularItemQuality(int itemIndex) {
        //This is purely for testing
        if(items[itemIndex].quality > 0) {
            items[itemIndex].quality = items[itemIndex].quality -1;
        }
    }
}