package sierraacy.feedme;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sisis on 11/22/2016.
 */

public class AppliedFilters implements Serializable{
    Set<String> styles;
    Set<String> dining;
    Set<String> price;
    Set<String> general;

    public AppliedFilters() {
        styles = new HashSet<String>();
        dining = new HashSet<String>();
        price = new HashSet<String>();
        general = new HashSet<String>();
    }
}
