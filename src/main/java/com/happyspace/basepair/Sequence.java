package com.happyspace.basepair;

import exception.MalformedInputException;
import exception.UnknownEncoding;

import java.util.regex.Matcher;

/**
 * A class encapsulating an encoded representation of a sequence of base pairs.
 * Base pairs are encoded 8 per integer value for an saving of eight fold
 * in comparison to an array of integers with each integer representing a base pair.
 */
public class Sequence {

    /**
     * An array of integers encoding a sequence of base pairs.
     */
    private final int[] sequenceEncoded;


    /**
     * The number of base pairs.
     */
    private final int length;

    /**
     * A class that creates a memory efficient representation of a base pair sequence.
     *
     * @param sequence A string representing a sequence of base pairs.
     * @throws UnknownEncoding Thrown when the string contains a character other than A, T, C or G.
     * @throws MalformedInputException Thrown when the string does not have an even number of elements.
     */
    public Sequence(final String sequence) throws UnknownEncoding, MalformedInputException  {
        if (sequence.length() % 2 != 0) {
            throw new MalformedInputException("Unexpected length: sequence should be composed of pairs.");
        }
        Matcher matcher = BasePair.NEGATIVE_PATTERN.matcher(sequence);
        if (matcher.matches()) {
            throw new UnknownEncoding("Unexpected encoding: sequence may only be contain A, T, C or G");
        }
        this.length = sequence.length() / 2;
        this.sequenceEncoded = this.pack(sequence);
    }

    /**
     * Get the base pair found at a given position within the base pair sequence.
     *
     * @param position A zero based index into a sequence of base pairs.
     * @return The base pair found at the position.
     */
    public final BasePair get(final int position) {
        if (position < 0 || position >= length) {
            throw new IndexOutOfBoundsException("Position does not fall within the sequence.");
        }
        BasePair basePair = null;
        int segment = position / Layout.size();
        int se = sequenceEncoded[segment];

        int offset = position % Layout.size();
        Layout layout = Layout.getPosition(offset);

        basePair = Util.getBasePair(se, layout);
        return basePair;
    }

    /**
     * Create an array of packed integers representing a sequence of base pairs.
     *
     * @param basePairs A string representing a sequence of base pairs.
     * @return An array of packed integers representing the sequence of base pairs.
     *
     */
    private int[] pack(final String basePairs)  {
        int len = 0;
        int[] pack = new int[len];
        if (basePairs.length() > 0) {
            int ls = Layout.values().length;
            int bp = (basePairs.length() / 2);
            if (bp > ls) {
                len = bp / ls;
                if (bp % ls != 0) {
                    len++;
                }
            }
            else {
                len = 1;
            }

            pack = new int[len];
            Layout position = Layout.BP1;
            int p = 0;
            LayoutList layoutList = new LayoutList();
            for (int i = 0; i < basePairs.length(); i = i + 2) {
                String bps = basePairs.substring(i, i + 2);
                BasePair pair = BasePair.VALUEMAP.get(bps);
                layoutList.put(position, pair);

                if (position.isLast()) {
                    pack[p] = Util.packInt(layoutList);
                    layoutList = new LayoutList();
                    p++;
                }

                position = position.next();
            }
            // remainder
            if (layoutList.values().size() != 0) {
             pack[p] = Util.packInt(layoutList);
            }
        }

        return pack;
    }
}
