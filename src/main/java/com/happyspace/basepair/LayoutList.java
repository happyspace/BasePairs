package com.happyspace.basepair;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;


/**
 * A wrapper class to provide a collection that is bounded by the Layout enum.
 */
public class LayoutList {

    /**
     * EnumMap defining a bucket per position in the layout.
     */
    private final EnumMap<Layout, BasePair> layoutMap;

    /**
     * Constructor.
     */
    public LayoutList() {
        layoutMap =  new EnumMap<Layout, BasePair>(Layout.class);
    }

    /**
     * Set a field to a base pair.
     *
     * @param layout a field from the Layout enum.
     * @param basePair a base pair from the BasePair enum.
     */
    public final void put(final Layout layout, final BasePair basePair) {
        layoutMap.put(layout, basePair);
    }

    /**
     * Get a base pair for a field.
     *
     * @param layout the field
     * @return base pair
     */
    public final BasePair get(final Layout layout){
        return layoutMap.get(layout);
    }

    /**
     * Returns the values in the EnumMap as a list.
     *
     * @return values in the collection as a list.
     */
    public final List<BasePair> values(){
        return new ArrayList<BasePair>(layoutMap.values());
    }
}
