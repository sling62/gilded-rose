package com.gildedrose;

class GildedRose {
    //Minimum quality value a item can have
    private static final int MINIMUM_QUALITY_VALUE = 0;
    //Maximum quality value a item can have
    private static final int MAXIMUM_QUALITY_VALUE = 50;

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
                if (items[i].quality > MAXIMUM_QUALITY_VALUE) {
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
        if(items[itemIndex].quality <MINIMUM_QUALITY_VALUE) {
            throw new IllegalArgumentException("Item quality cannot be less than 0");
        }
    }

    /**
     * Update the quality of an aged brie
     * @param itemIndex index of aged brie in items
     */
    private void updateAgedBrieItemQuality(int itemIndex) {
        //Only increases quality of Aged Brie if the quality is not already at 50
        if(items[itemIndex].quality != MAXIMUM_QUALITY_VALUE) {
            items[itemIndex].quality = items[itemIndex].quality + 1;
        }
    }

    /**
     * Update the quality of a backstage pass
     * @param itemIndex index of backstage pass in items
     */
    private void updateBackstagePassesItemQuality(int itemIndex) {
        //Calculates the difference between the max quality an item can have (50), and the item quality, to see how much quality that can be added to not exceed 50
        int qualityDifference = MAXIMUM_QUALITY_VALUE - items[itemIndex].quality;;

        //Checks to see if sellIn date is between 6-10 days or 0-5 days
        if(items[itemIndex].sellIn > 5 && items[itemIndex].sellIn <= 10) {
            //if it is between 6-10 days, checks the quality of item to see if its 49 or 50, to make sure it doesn't exceed max quality when increased by 2
            if(qualityDifference < 2) {
                //if quality is 49 or 50 updates quality by difference (ie. 0 or 1) to reach max quality
                items[itemIndex].quality = items[itemIndex].quality + qualityDifference;
            }
            else {
                //else if 48 or less, increases quality by 2
                items[itemIndex].quality = items[itemIndex].quality + 2;
            }
        }
        else if(items[itemIndex].sellIn >= 0 && items[itemIndex].sellIn <= 5) {
            //if it is between 0-5 days, checks the quality of item to see if its 48, 49 or 50, to make sure it doesn't exceed max quality when increased by 3
            if(qualityDifference < 3) {
                //if quality is 48, 49 or 50 updates quality by difference (ie. 0, 1 or 2) to reach max quality
                items[itemIndex].quality = items[itemIndex].quality + qualityDifference;
            }
            else {
                //else if 47 or less, increases quality by 3
                items[itemIndex].quality = items[itemIndex].quality + 3;
            }
        }
    }

    /**
     * Update the quality of a conjured item
     * Conjured items degrade in quality twice as fast as normal/regular items hence,
     * If a conjured item has passed its sellIn date will decrease in quality twice as fast as a regular item which had passed its sellIn date (ie. conjured items will decrease by 4)
     * If a conjured item has not passed its sellIn then it will just decrease twice as fast as a regular item that has not passed its sellIn date (ie. conjured items will decrease by 2)
     * @param itemIndex index of conjured item in items
     */
    private void updateConjuredItemQuality(int itemIndex) {
        //Checks to see if item has passed its sellIn date
        if(items[itemIndex].sellIn < 0) {
            //if so, checks to see if item quality value is less than 4 (if so will decrease quality value to 0, so it is not negative)
            if((items[itemIndex].quality - 4) < MINIMUM_QUALITY_VALUE) {
                items[itemIndex].quality = MINIMUM_QUALITY_VALUE;
            }
            else {
                //if item quality value is 4 or greater will deduct the usual quality amount (4) for a conjured item that passed its sell by date
                items[itemIndex].quality = items[itemIndex].quality - 4;
            }
        }
        else {
            //if it hasn't passed its sellIn date, checks to see if item quality value is greater than 2 (so that when decreased by 2 is not negative)
            if((items[itemIndex].quality - 2) < MINIMUM_QUALITY_VALUE) {
                //if the quality value is not greater than 2 (ie. if quality is 0 or 1) then sets quality to 0
                items[itemIndex].quality = MINIMUM_QUALITY_VALUE;
            }
            else {
                // else decreases quality by 2
                items[itemIndex].quality = items[itemIndex].quality - 2;
            }
        }
    }

    /**
     * Update the quality of a regular item
     * If sellIn has passed, quality will degrade twice as fast (ie. quality will be decreased by 2 not 1)
     * If not passed its sellIn will just decrease quality by usual amount (ie. 1)
     * @param itemIndex index of regular item in items
     */
    private void updateRegularItemQuality(int itemIndex) {
        //Checks to see if item has passed its sellIn date
        if(items[itemIndex].sellIn < 0) {
            //if it has, checks to see if item quality value is 1 (so it will only decrease quality by 1 not 2 so that quality is not negative)
            if(items[itemIndex].quality == 1) {
                items[itemIndex].quality = items[itemIndex].quality - 1;
            }
            else if(items[itemIndex].quality >=2) {
                //if item quality value is 2 or greater will deduct the usual quality amount (2) for a regular item that passed its sell by date
                items[itemIndex].quality = items[itemIndex].quality - 2;
            }
        }
        else {
        //If it hasn't passed its sellIn date, it checks to see if item quality value is greater than 0 (so that when decreased by 1 is not negative)
          if(items[itemIndex].quality > MINIMUM_QUALITY_VALUE) {
              items[itemIndex].quality = items[itemIndex].quality - 1;
          }
        }
    }
}