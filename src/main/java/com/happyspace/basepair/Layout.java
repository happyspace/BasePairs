package com.happyspace.basepair;

/**
 * Enumeration defining the layout and offsets for fields encoded into an integer.
 */
public enum Layout {
    /**
     * Base pair 1.
     */
    BP1(0),
    /**
     * Base pair 2.
     */
    BP2(4),
    /**
     * Base pair 3.
     */
    BP3(8),
    /**
     * Base pair 4.
     */
    BP4(12),
    /**
     * Base pair 5.
     */
    BP5(16),
    /**
     * Base pair 6.
     */
    BP6(20),
    /**
     * Base pair 7.
     */
    BP7(24),
    /**
     * Base pair 8.
     */
    BP8(28);

    /**
     * Mask equal to the length of one field.
     */
    public static final int MASK = 0xF;
    /**
     * The values of this enum.
     */
    private static Layout[] vals = values();

    /**
     * Bit offset of field.
     */
    private final int offset;

    /**
     * @param offset bit offset of field
     */
    Layout(final int offset) {
        this.offset = offset;
    }

    /**
     * Fields are in sequence. Return the next field.
     *
     * @return the next field. Next wraps around.
     */
    public Layout next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    /**
     * @return if the field is the last field of a set.
     */
    public boolean isLast() {
        return this == Layout.BP8;
    }

    /**
     * @return the bit offset.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return the number of fields
     */
    public static int size() {
        return vals.length;
    }

    /**
     * Lookup a field based on a position (0-7).
     *
     * @param position the position for a field with the set.
     * @return field from enum.
     */
    protected static Layout getPosition(final int position) {
        Layout layout = null;
        if (position >= 0 && position < vals.length) {
            layout = vals[position];
        }
        return layout;
    }
}
