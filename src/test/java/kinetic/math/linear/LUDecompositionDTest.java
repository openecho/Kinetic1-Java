/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinetic.math.linear;

import kinetic.math.linear.LUDecompositionD;
import kinetic.math.MatrixD;
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
public class LUDecompositionDTest {

    public LUDecompositionDTest() {
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
     * Test of handleDecompose method, of class LUDecomposition.
     */
    @Test
    public void testHandleDecompose() {
        System.out.println("handleDecompose");
        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {10D, 5D, 1D}});
        LUDecompositionD instance = new LUDecompositionD(matrix);
        boolean expResult = true;
        boolean result = instance.handleDecompose();
        assertEquals(expResult, result);
    }

    /**
     * Test of getL method, of class LUDecomposition.
     */
    @Test
    public void testGetLU() {
        System.out.println("getLU");
        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {10D, 5D, 1D}});
        LUDecompositionD instance = new LUDecompositionD(matrix);
        MatrixD expLResult = MatrixD.create(new Double[][]{{1.0, 0.0, 0.0}, {0.1, 1.0, 0.0}, {0.3, 0.3333333333333333, 1.0}});
        MatrixD lResult = instance.getL();
        System.out.println(String.format("L:%s", lResult));
        assertTrue(expLResult.equals(lResult));

        MatrixD expUResult = MatrixD.create(new Double[][]{{10.0, 5.0, 1.0}, {0.0, 1.5, 2.9}, {0.0, 0.0, -0.2666666666666666}});
        MatrixD uResult = instance.getU();
        System.out.println(String.format("U:%s", uResult));
        assertTrue(expUResult.equals(uResult));
    }

    /**
     * Test of getPivot method, of class LUDecomposition.
     */
    @Test
    public void testGetPivot() {
        System.out.println("getPivot");
        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {10D, 5D, 1D}});
        LUDecompositionD instance = new LUDecompositionD(matrix);
        int[] expResult = new int[]{2, 0, 1};
        int[] result = instance.getPivot();
        for (int i = 0; i < expResult.length; i++) {
            assertEquals(expResult[i], result[i]);
        }
    }

    /**
     * Test of getPivot method, of class LUDecomposition.
     */
    @Test
    public void testDeterminant() {
        System.out.println("determinant");
        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 3D}, {3D, 2D, 1D}, {10D, 5D, 1D}});
        LUDecompositionD instance = new LUDecompositionD(matrix);
        double expResult = matrix.determinant();
        double result = instance.determinant();
        assertEquals(expResult, result, 0.00001);
    }
}
