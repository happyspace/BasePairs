package com.happyspace.basepair;

import exception.UnknownEncoding;

import java.util.ArrayList;

/**
 * A class containing utility methods work on base pair encoded ints.
 */
public final class Util {

    /**
     * Private constructor for utility class.
     */
    private Util() { }


    /**
     * A utility method to unpack an int. Protected to indicate that this
     * method should only be used in the context of this package.
     *
     * @param packed an int packed with base pair encodings.
     * @return List of base pairs encoded into the int.
     * @throws UnknownEncoding an encoding was found that does not map to a base pair.
     */
    protected static ArrayList<BasePair> unPackInt(final int packed) throws UnknownEncoding {
        ArrayList<BasePair> pairs = new ArrayList<>();

        for (Layout layout: Layout.values()) {
            int encoding = (packed >> layout.getOffset()) & Layout.MASK;

            if (encoding != BasePair.PADDING_ENCODING) {
                BasePair basePair = BasePair.ENCODINGMAP.get(encoding);
                if (basePair != null) {
                    pairs.add(basePair);
                }
                else {
                    throw new UnknownEncoding("Unexpected encoding: encoding contains illegal values.");
                }
            }
        }

        return pairs;
    }

    /**
     * Return the base pair found at a position within the packed int.
     * Protected to indicate that this method should only be used in the context of this package.
     *
     * @param packed an int packed with base pair encodings.
     * @param position the position within the layout to extract the base pair
     * @return base pair.
     */
    protected static BasePair getBasePair(final int packed, final Layout position) {
        BasePair basePair = null;
        if (position != null) {
            int encoding = (packed >> position.getOffset()) & Layout.MASK;
            if (encoding != 0) {
                basePair = BasePair.ENCODINGMAP.get(encoding);
            }
        }

        return basePair;
    }


    /**
     * Returns a packed int based on base pairs found in the layout list.
     * Note: Layout.values() are maintained in the natural order of the enumeration.
     *
     * @param basePairs a layout list of base pairs to pack
     * @return an int packed with base pair encodings
     *
     */
    protected static int packInt(final LayoutList  basePairs) {
        int pack = 0;
        // Values are in natural order for the enumeration.
        Layout[] ls = Layout.values();
        for (Layout layout: ls) {
            BasePair basePair = basePairs.get(layout);
            if (basePair != null) {
                int encoding = basePair.getEncoding();
                pack = pack | (encoding << layout.getOffset());
            }
        }
        return pack;
    }
}
