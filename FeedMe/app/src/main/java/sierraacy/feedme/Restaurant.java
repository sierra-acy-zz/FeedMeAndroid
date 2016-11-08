package sierraacy.feedme;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by sisis on 10/25/2016.
 */

public class Restaurant implements Serializable {
    String name;
    String style;
    String diningType;
    String priceRange;
    boolean hasMeal;
    boolean hasDessert;
    boolean hasDrinks;
    URL yelpSearch;

    public Restaurant(String name, String style, String diningType, String priceRange,
                      boolean hasMeal, boolean hasDessert, boolean hasDrinks) {
        this.name = name;
        this.style = style;
        this.diningType = diningType;
        this.priceRange = priceRange;
        this.hasMeal = hasMeal;
        this.hasDessert = hasDessert;
        this.hasDrinks = hasDrinks;
    }


}
