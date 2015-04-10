package com.happyspace.basepair;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * An enumeration representing possible base pairs.
 */
public enum BasePair {
    /**
     * Padding.
     */
    // A T C G
    __("00", 0), // padding
    /**
     * AA.
     */
    AA("AA", 1),
    /**
     * AT.
     */
    AT("AT", 2),
    /**
     * AC.
     */
    AC("AC", 3),
    /**
     * AG.
     */
    AG("AG", 4),
    /**
     * TT.
     */
    TT("TT", 5),
    /**
     * CC.
     */
    CC("CC", 6),
    /**
     * GG.
     */
    GG("GG", 7),
    /**
     * TC.
     */
    TC("TC", 8),
    /**
     * TG.
     */
    TG("TG", 9),
    /**
     * CG.
     */
    CG("CG", 10);

    /**
     * The encoding for a base pair.
     */
    private final int encoding;
    /**
     * The string representation of a base pair.
     */
    private final String value;

    /**
     * Padding is zero.
     */
    public static final int PADDING_ENCODING = 0;
    /**
     * The value of padding needed to fill out the enum.
     */
    private static final String PADDING_NAME = "__";
    /**
     * Regex to test if a string has characters not used to represent base pairs.
     */
    public static final Pattern NEGATIVE_PATTERN = Pattern.compile("[^ATCG]*");

    /**
     * Look up of encoding to base pair.
     */
    public static final Map<Integer, BasePair> ENCODINGMAP = new HashMap<>();

    /**
     * Look up of value to base pair 'AA' -> BasePair.AA.
     */
    public static final Map<String, BasePair> VALUEMAP = new HashMap<>();

    /**
     * Set of base pairs excluding padding.
     */
    public static final EnumSet<BasePair> BASEPAIRSET = EnumSet.range(AA, CG);

    /**
     * Create base pairs with a string value and an encoding.
     *
     * @param value String value of base pair.
     * @param encoding encoding of base pair.
     */
    BasePair(final String value, final int encoding) {
        this.value = value;
        this.encoding = encoding;
    }
    // build look-ups
    static {
        for (BasePair basePair: BasePair.values()) {
            if (basePair.encoding != 0) {
                VALUEMAP.put(basePair.value, basePair);
                // put the reverse value AG == GA
                VALUEMAP.put(basePair.reverse(), basePair);
                ENCODINGMAP.put(basePair.encoding, basePair);
            }
        }
    }

    /**
     * Return encoding.
     *
     * @return encoding.
     */
    public int getEncoding() {
        return encoding;
    }

    /**
     * Return value.
     *
     * @return value.
     */
    public String getValue() {
        return value;
    }

    /**
     * @return reversed value. AG -> GA.
     */
    public String getValueReversed() {
        return this.reverse();
    }

    /**
     * @return value reversed.
     */
    private String reverse() {
        char[] v = this.value.toCharArray();
        char[] reverse = {v[1], v[0]};
        return reverse.toString();
    }
}
