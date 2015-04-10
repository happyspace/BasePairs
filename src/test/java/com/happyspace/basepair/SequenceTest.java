package com.happyspace.basepair;

import exception.MalformedInputException;
import exception.UnknownEncoding;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;


/**
 * Test Sequence class and Util class
 */
public class SequenceTest {

    /**
     * Test that an UnknownEncoding exception is thrown for input that has an odd number of characters.
     *
     * @throws Exception Junit
     */
    @Test(expected = UnknownEncoding.class)
    public final void testMalformedInputException() throws Exception {
        Sequence sequence = new Sequence("MooMoo");
    }

    /**
     * Test that an MalformedInputException is thrown for strings containing
     * illegal characters.
     *
     * @throws Exception Junit
     */
    @Test(expected = MalformedInputException.class)
    public final void testUnknownEncodingException() throws Exception
    {
        Sequence sequence = new Sequence("Moo");
    }

    /**
     * Test a sequence of exactly one base pair.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testSequence1() throws Exception {
        Sequence sequence = new Sequence("AA");
        BasePair pair = sequence.get(0);
        assertEquals(BasePair.AA, pair);
    }

    /**
     * Test a sequence of exactly eight base pairs.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testSequence8() throws Exception {
        Sequence sequence = new Sequence("AAACAGGGAAACAGGG");
        BasePair pair = sequence.get(0);
        assertEquals(BasePair.AA, pair);

        pair = sequence.get(7);
        assertEquals(BasePair.GG, pair);
    }

    /**
     * Test a sequence of exactly eight CG base pairs.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testSequence8CG() throws Exception {
        Sequence sequence = new Sequence("CGCGCGCGCGCGCGCG");
        BasePair pair = sequence.get(0);
        assertEquals(BasePair.CG, pair);

        pair = sequence.get(7);
        assertEquals(BasePair.CG, pair);
    }

    /**
     * Test a sequence of ten base pairs.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testSequence10() throws Exception {
        String os = this.generateOrderedString(10);
        Sequence sequence = new Sequence(os);
        BasePair pair = sequence.get(0);
        assertEquals(BasePair.AA, pair);

        pair = sequence.get(9);
        assertEquals(BasePair.CG, pair);
    }

    /**
     * Test a sequence of 100 base pairs.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testSequence100() throws Exception {
        String os = this.generateOrderedString(100);
        Sequence sequence = new Sequence(os);
        BasePair pair = sequence.get(0);
        assertEquals(BasePair.AA, pair);

        pair = sequence.get(99);
        assertEquals(BasePair.CG, pair);
    }

    /**
     * Test a sequence of 1,000,000 base pairs.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testSequence1000000() throws Exception {
        String os = this.generateOrderedString(1000000);
        Sequence sequence = new Sequence(os);
        BasePair pair = sequence.get(0);
        assertEquals(BasePair.AA, pair);

        pair = sequence.get(999999);
        assertEquals(BasePair.CG, pair);
    }

    /**
     * Test index out bounds exception.
     *
     * @throws Exception Junit
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public final void testSequenceOutOfBounds() throws Exception {
        Sequence sequence = new Sequence("AA");
        BasePair pair = sequence.get(1);
    }

    /**
     * Test utility method get base pair with eight base pairs populated.
     * @throws Exception Junit
     */
    @Test
    public final void testGetBasePair8() throws Exception {
        LayoutList list = new LayoutList();
        list.put(Layout.BP1, BasePair.AA);
        list.put(Layout.BP2, BasePair.AC);
        list.put(Layout.BP3, BasePair.AG);
        list.put(Layout.BP4, BasePair.AT);
        list.put(Layout.BP5, BasePair.CC);
        list.put(Layout.BP6, BasePair.CG);
        list.put(Layout.BP7, BasePair.TC);
        list.put(Layout.BP8, BasePair.TG);
        int pack = Util.packInt(list);

        BasePair pair = Util.getBasePair(pack, Layout.BP1);
        assertEquals(BasePair.AA, pair);

        pair = Util.getBasePair(pack, Layout.BP2);
        assertEquals(BasePair.AC, pair);

        pair = Util.getBasePair(pack, Layout.BP3);
        assertEquals(BasePair.AG, pair);

        pair = Util.getBasePair(pack, Layout.BP4);
        assertEquals(BasePair.AT, pair);

        pair = Util.getBasePair(pack, Layout.BP5);
        assertEquals(BasePair.CC, pair);

        pair = Util.getBasePair(pack, Layout.BP6);
        assertEquals(BasePair.CG, pair);

        pair = Util.getBasePair(pack, Layout.BP7);
        assertEquals(BasePair.TC, pair);

        pair = Util.getBasePair(pack, Layout.BP8);
        assertEquals(BasePair.TG, pair);
    }

    /**
     * Test utility method get base pair with three base pairs populated.
     * @throws Exception Junit
     */
    @Test
    public final void testGetBasePair3() throws Exception {
        LayoutList list = new LayoutList();
        list.put(Layout.BP1, BasePair.AA);
        list.put(Layout.BP2, BasePair.AC);
        list.put(Layout.BP3, BasePair.AG);
        int pack = Util.packInt(list);

        BasePair pair = Util.getBasePair(pack, Layout.BP1);
        assertEquals(BasePair.AA, pair);

        pair = Util.getBasePair(pack, Layout.BP4);
        assertNull(pair);
        pair = Util.getBasePair(pack, Layout.BP5);
        assertNull(pair);
        pair = Util.getBasePair(pack, Layout.BP6);
        assertNull(pair);
        pair = Util.getBasePair(pack, Layout.BP7);
        assertNull(pair);
        pair = Util.getBasePair(pack, Layout.BP8);
        assertNull(pair);
    }

    /**
     * Test utility methods pack and unpack no base pairs.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testPackUnPackEmpty() throws Exception {
        LayoutList list = new LayoutList();
        int pack = Util.packInt(list);
        assertEquals(pack, 0);
    }

    /**
     * Test utility methods pack and unpack 1 base pairs.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testPackUnPack1() throws Exception {
        LayoutList list = new LayoutList();
        list.put(Layout.BP1, BasePair.AA);
        int pack = Util.packInt(list);

        // System.out.println(Integer.toBinaryString(pack));

        ArrayList<BasePair> pairs = Util.unPackInt(pack);
        assertEquals(1, pairs.size());

        List<BasePair> values = list.values();
        int i = 0;
        for (BasePair basePair: values) {
            assertEquals(basePair, pairs.get(i));
            i++;
        }
    }

    /**
     * Test utility methods pack and unpack 3 base pairs.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testPackUnPack3() throws Exception {
        LayoutList list = new LayoutList();
        list.put(Layout.BP1, BasePair.AA);
        list.put(Layout.BP2, BasePair.AC);
        list.put(Layout.BP3, BasePair.AG);
        int pack = Util.packInt(list);

        // System.out.println(Integer.toBinaryString(pack));

        ArrayList<BasePair> pairs = Util.unPackInt(pack);
        assertEquals(3, pairs.size());

        List<BasePair> values = list.values();
        int i = 0;
        for (BasePair basePair: values) {
            assertEquals(basePair, pairs.get(i));
            i++;
        }
    }

    /**
     * Test utility methods pack and unpack eight base pairs.
     *
     * @throws Exception Junit
     */
    @Test
    public final void testPackUnPack8() throws Exception {
        LayoutList list = new LayoutList();
        list.put(Layout.BP1, BasePair.AA);
        list.put(Layout.BP2, BasePair.AC);
        list.put(Layout.BP3, BasePair.AG);
        list.put(Layout.BP4, BasePair.AT);
        list.put(Layout.BP5, BasePair.CC);
        list.put(Layout.BP6, BasePair.CG);
        list.put(Layout.BP7, BasePair.TC);
        list.put(Layout.BP8, BasePair.TG);
        int pack = Util.packInt(list);

        // System.out.println(Integer.toBinaryString(pack));

        ArrayList<BasePair> pairs = Util.unPackInt(pack);
        assertEquals(8, pairs.size());

        List<BasePair> values = list.values();
        int i = 0;
        for (BasePair basePair: values) {
            assertEquals(basePair, pairs.get(i));
            i++;
        }
    }

    /**
     * Create a string in the order of the BasePair enum.
     *
     * @param length Number of base pairs to encode.
     * @return String representation of a sequence of base pairs.
     */
    private String generateOrderedString(final int length) {
        StringBuilder sb = new StringBuilder();
        ArrayList<BasePair> pairs = new ArrayList<>(BasePair.BASEPAIRSET);

        for (int i = 0; i < length; i++) {
            BasePair pair = pairs.get(i % pairs.size());
            sb.append(pair.getValue());
        }
        return sb.toString();
    }
}