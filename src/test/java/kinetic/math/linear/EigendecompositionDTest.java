/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kinetic.math.linear;

import kinetic.math.MatrixD;
import kinetic.math.Matrix;
import kinetic.math.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jmarsden
 */
public class EigendecompositionDTest {

    public EigendecompositionDTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of handleDecompose method, of class EigendecompositionD.
     */
    @Test
    public void testHandleDecompose() {
        System.out.println("handleDecompose");

        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {10D, 5D, 1D}});
        EigendecompositionD instance = new EigendecompositionD(matrix);
        instance.decompose();
        boolean expResult = false;
        boolean result = instance.handleDecompose();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


}