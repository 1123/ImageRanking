package org.benedetto.imageranking.adjancencylist;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;
import static org.benedetto.imageranking.util.FloatUtils.floatEqual;
import static org.benedetto.imageranking.TestUtil.gsonPrint;


/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 10/31/13
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class AcyclicWeightedGraphTest {

    @Test
    public void simpleTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1, 2, 0.2f);
        g.addEdge(2, 3, 0.3f);
        gsonPrint(g);
    }

    @Test
    public void doubleEdgeTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1, 2, 0.1f);
        g.addEdge(1, 2, 0.2f);
        gsonPrint(g);
        assertTrue(floatEqual(g.getF(1).get(2), 0.3f));
    }

    @Test
    public void simpleCycleTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 3, 0.2f);
        gsonPrint(g);
        List<Integer> cycle = g.search(1,3);
        gsonPrint(cycle);
        assertEquals(cycle, Arrays.asList(new Integer[]{1, 2, 3}));
    }

    @Test
    public void longCycleTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(0, 2, 0.3f);
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 3, 0.2f);
        g.addEdge(3, 4, 0.2f);
        g.addEdge(3, 5, 0.3f);
        List<Integer> cycle = g.search(1,5);
        gsonPrint(cycle);
        assertEquals(cycle, Arrays.asList(new Integer [] { 1, 2, 3, 5 }));
    }


    @Test
    public void simpleCylceRemovalTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 1, 0.2f);
        assertTrue(floatEqual(g.getF(2).get(1), 0.1f));
        assertFalse(g.getF(1).containsKey(2));
    }

    public static AcyclicWeightedGraph longCycleGraph() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(0, 2, 0.3f);
        g.addEdge(1, 2, 0.1f);
        g.addEdge(2, 3, 0.2f);
        g.addEdge(3, 4, 0.2f);
        g.addEdge(3, 5, 0.3f);
        g.addEdge(5, 1, 0.4f);
        return g;
    }

    @Test
    public void longCycleRemovalTest() {
        AcyclicWeightedGraph g = this.longCycleGraph();
        assertNull(g.getF(1).get(2));
        assertTrue(floatEqual(g.getF(2).get(3), 0.1f));
        assertTrue(floatEqual(g.getF(0).get(2), 0.3f));
        assertTrue(floatEqual(g.getF(5).get(1), 0.3f));
        assertTrue(floatEqual(g.getF(3).get(4), 0.2f));
    }

    @Test
    public void doubleCycleRemovalTest() {
        AcyclicWeightedGraph g = new AcyclicWeightedGraph();
        g.addEdge(1,2,0.1f);
        g.addEdge(1,2,0.1f);
        g.addEdge(2,1,0.1f);
        g.addEdge(2,3,0.2f);
        g.addEdge(1,4,0.3f);
        g.addEdge(4, 3, 0.4f);
        g.addEdge(3, 1, 0.3f);
        assertNull(g.getF(3).get(1));
        gsonPrint(g);
    }

}