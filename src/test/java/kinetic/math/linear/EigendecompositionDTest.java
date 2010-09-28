/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kinetic.math.linear;

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
        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 1D}, {6D, -1D, 0D}, {-1D, -2D, -1D}});
        EigendecompositionD instance = new EigendecompositionD(matrix);
        instance.decompose();
        boolean expResult = true;
        boolean result = instance.handleDecompose();
        assertEquals(expResult, result);
    }

    /**
     * 
     */
    @Test
    public void testGetD() {
        System.out.println("getD");
        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 1D}, {6D, -1D, 0D}, {-1D, -2D, -1D}});
        EigendecompositionD instance = new EigendecompositionD(matrix);
        MatrixD d = instance.getD();
        d.print(10, 4);

    }

    /**
     *
     */
    @Test
    public void testGetV() {
        System.out.println("getV");
        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 1D}, {6D, -1D, 0D}, {-1D, -2D, -1D}});
        EigendecompositionD instance = new EigendecompositionD(matrix);
        MatrixD v = instance.getV();
        v.print(10, 4);

    }

    /**
     *
     */
    @Test
    public void testGetEigenValues() {
        System.out.println("getEigenValues");
        //MatrixD matrix = MatrixD.create(new Double[][]{{3D, 0D, 1D}, {-4D, 1D, 2D}, {-6D, 0D, 2D}});
        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 1D}, {6D, -1D, 0D}, {-1D, -2D, -1D}});
        EigendecompositionD instance = new EigendecompositionD(matrix);
        double[] rE = instance.getRealEigenvalues();
        double[] iE = instance.getImagEigenvalues();
        for(int i=0;i<rE.length;i++) {
            System.out.println("[" + i + "]:" + rE[i] + " " + iE[i] + "i");
        }
    }

    /**
     *
     */
    @Test
    public void testReconstruction() {
        System.out.println("reconstruction");
        MatrixD matrix = MatrixD.create(new Double[][]{{1D, 2D, 1D}, {6D, -1D, 0D}, {-1D, -2D, -1D}});
        EigendecompositionD instance = new EigendecompositionD(matrix);

        MatrixD v = instance.getV();
        MatrixD d = instance.getD();
        MatrixD vTranspose = v.transpose();

        MatrixD matrixReconstruct = vTranspose.multiply(d).multiply(v);
        matrixReconstruct.print(10, 4);
    }
}