/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.math;

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
public class Vector3FTest {

    public Vector3FTest() {
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
     * Test of initData method, of class Vector3F.
     */
    @Test
    public void testInitData_NumberArr() {
        System.out.println("initData");
        Number[] data = new Float[] {1F,2F,5F};
        Vector3F instance = new Vector3F(data);
        instance.initData(data);
        assertEquals(instance.y, 2F, 0);
    }

    /**
     * Test of initData method, of class Vector3F.
     */
    @Test
    public void testInitData_int_Number() {
        System.out.println("initData");
        int i = 1;
        Number[] data = new Float[] {1F,2F,5F};
        Vector3F instance = new Vector3F(data);
        instance.initData(i, 3F);
        assertEquals(instance.y, 3F, 0);
    }

    /**
     * Test of getX method, of class Vector3F.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Number[] data = new Float[] {1F,2F,5F};
        Vector3F instance = new Vector3F(data);
        float expResult = 1.0F;
        float result = instance.getX();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setX method, of class Vector3F.
     */
    @Test
    public void testSetX() {
        System.out.println("setX");
        float x = 22.0F;
        Number[] data = new Float[] {1F,2F,5F};
        Vector3F instance = new Vector3F(data, true);
        instance.setX(x);
        assertEquals(instance.x, x, 0.0);
    }

    /**
     * Test of getY method, of class Vector3F.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Number[] data = new Float[] {1F,2F,5F};
        Vector3F instance = new Vector3F(data,true);
        float expResult = 2.0F;
        float result = instance.getY();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setY method, of class Vector3F.
     */
    @Test
    public void testSetY() {
        System.out.println("setY");
        float y = 22.0F;
        Number[] data = new Float[] {1F,2F,5F};
        Vector3F instance = new Vector3F(data, true);
        instance.setY(y);
        assertEquals(instance.y, y, 0.0);
    }

    /**
     * Test of getZ method, of class Vector3F.
     */
    @Test
    public void testGetZ() {
        System.out.println("getZ");
        Number[] data = new Float[] {1F,2F,5F};
        Vector3F instance = new Vector3F(data);
        float expResult = 5.0F;
        float result = instance.getZ();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setZ method, of class Vector3F.
     */
    @Test
    public void testSetZ() {
        System.out.println("setZ");
        float z = 25.0F;
        Number[] data = new Float[] {1F,2F,5F};
        Vector3F instance = new Vector3F(data, true);
        instance.setZ(z);
        assertEquals(instance.z, z, 0.0);
    }

    /**
     * Test of getData method, of class Vector3F.
     */
    @Test
    public void testGetData_0args() {
        System.out.println("getData");
        Vector3F instance = new Vector3F(22.0F,43.0F,-33.0F);
        Float[] expResult = new Float[] {22.0F,43.0F,-33.0F};
        Float[] result = instance.getData();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getData method, of class Vector3F.
     */
    @Test
    public void testGetData_int() {
        System.out.println("getData");
        int i = 0;
        Vector3F instance = new Vector3F(22.0F,43.0F,-33.0F);
        Float expResult = 22F;
        Float result = instance.getData(i);
        assertEquals(expResult, result, 0);
        i=1;
        expResult = 43F;
        result = instance.getData(i);
        assertEquals(expResult, result, 0);
        i=2;
        expResult = -33.0F;
        result = instance.getData(i);
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of setData method, of class Vector3F.
     */
    @Test
    public void testSetData_NumberArr() {
        System.out.println("setData");
        Number[] data = new Float[] {2F,3F,4F};
        Vector3F instance = new Vector3F(data,true);
        data = new Float[] {-2F,33.1F,65.2F};
        instance.setData(data);
        assertEquals(instance.y, 33.1F, 0);
    }

    /**
     * Test of setData method, of class Vector3F.
     */
    @Test
    public void testSetData_int_Number() {
        System.out.println("setData");
        int i = 2;
        Number number = -52.3F;
        Number[] data = new Float[] {2F,3F,4F};
        Vector3F instance = new Vector3F(data,true);
        instance.setData(i, number);
        assertEquals(instance.z, -52.3F, 0);
    }

    /**
     * Test of negative method, of class Vector3F.
     */
    @Test
    public void testNegative() {
        System.out.println("negative");
        Vector3F instance = new Vector3F(-0.2F,33.2F,22.4F);
        Vector3F expResult = new Vector3F(0.2F,-33.2F,-22.4F);
        Vector3F result = instance.negative();
        assertEquals(expResult, result);
    }

    /**
     * Test of normalise method, of class Vector3F.
     */
    @Test
    public void testNormalise() {
        System.out.println("normalise");
        Vector3F instance = new Vector3F(3F,1F,2F);
        Vector3F expResult = new Vector3F(0.802F,0.267F,0.534F);
        Vector3F result = instance.normalise();
        assertEquals(expResult.x, result.x, 0.001);
        assertEquals(expResult.y, result.y, 0.001);
        assertEquals(expResult.z, result.z, 0.001);
    }

    /**
     * Test of add method, of class Vector3F.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Vector b = new Vector3F(3F,1F,2F);
        Vector3F instance = new Vector3F(3F,1F,2F);
        Vector3F expResult = new Vector3F(6F,2F,4F);
        VectorF result = instance.add(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of add3F method, of class Vector3F.
     */
    @Test
    public void testAdd3F() {
        System.out.println("add3F");
        Vector3F b = new Vector3F(3F,1F,2F);
        Vector3F instance = new Vector3F(3F,1F,2F);
        Vector3F expResult = new Vector3F(6F,2F,4F);
        Vector3F result = instance.add(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of subtract method, of class Vector3F.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        Vector b = new Vector3F(3F,1F,2F);
        Vector3F instance = new Vector3F(2F,4F,0.5F);
        Vector3F expResult = new Vector3F(-1F,3.0F,-1.5F);
        VectorF result = instance.subtract(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of subtract3F method, of class Vector3F.
     */
    @Test
    public void testSubtract3F() {
        System.out.println("subtract3F");
        Vector3F b = new Vector3F(3F,1F,2F);
        Vector3F instance = new Vector3F(2F,4F,0.5F);
        Vector3F expResult = new Vector3F(-1F,3.0F,-1.5F);
        Vector3F result = instance.subtract3F(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of cross method, of class Vector3F.
     */
    @Test
    public void testCross_Vector() {
        System.out.println("cross");
        Vector3F b = new Vector3F(3F,1F,2F);
        Vector3F instance = new Vector3F(2F,4F,0.5F);
        Vector3F expResult = new Vector3F(7.5F,-2.5F,-10F);
        VectorF result = instance.cross(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of cross method, of class Vector3F.
     */
    @Test
    public void testCross_Vector3F() {
        Vector3F b = new Vector3F(4F, 9F, 2F);
        Vector3F instance = new Vector3F(3F,-3F, 1F);
        Vector3F expResult = new Vector3F(-15F,-2F,39F);
        VectorF result = instance.cross3F(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of addScalar method, of class Vector3F.
     */
    @Test
    public void testAddScalar() {
        System.out.println("addScalar");
        Number v = 0.1D;
        Vector3F instance = new Vector3F(1.2F,2.3F,3.4F);
        Vector3F expResult = new Vector3F(1.3F,2.4F,3.5F);
        Vector3F result = (Vector3F) instance.addScalar(v);
        assertEquals(expResult.x, result.x, 0.001);
        assertEquals(expResult.y, result.y, 0.001);
        assertEquals(expResult.z, result.z, 0.001);
    }

    /**
     * Test of addScalar3F method, of class Vector3F.
     */
    @Test
    public void testAddScalar3F() {
        System.out.println("addScalar3F");
        float v = 0.1F;
        Vector3F instance = new Vector3F(1.2F,2.3F,3.4F);
        Vector3F expResult = new Vector3F(1.3F,2.4F,3.5F);
        Vector3F result = instance.addScalar3F(v);
        assertEquals(expResult.x, result.x, 0.001);
        assertEquals(expResult.y, result.y, 0.001);
        assertEquals(expResult.z, result.z, 0.001);
    }

    /**
     * Test of subtractScalar method, of class Vector3F.
     */
    @Test
    public void testSubtractScalar() {
        System.out.println("subtractScalar");
        float v = 0.1F;
        Vector3F instance = new Vector3F(1.2F,2.3F,3.4F);
        Vector3F expResult = new Vector3F(1.1F,2.2F,3.3F);
        Vector3F result = (Vector3F) instance.subtractScalar(v);
        assertEquals(expResult.x, result.x, 0.001);
        assertEquals(expResult.y, result.y, 0.001);
        assertEquals(expResult.z, result.z, 0.001);
    }

    /**
     * Test of subtractScalar3F method, of class Vector3F.
     */
    @Test
    public void testSubtractScalar3F() {
        System.out.println("subtractScalar3F");
        float v = 0.1F;
        Vector3F instance = new Vector3F(-1.2F,-2.3F,-3.4F);
        Vector3F expResult = new Vector3F(-1.3F,-2.4F,-3.5F);
        Vector3F result = instance.subtractScalar3F(v);
        assertEquals(expResult.x, result.x, 0.001);
        assertEquals(expResult.y, result.y, 0.001);
        assertEquals(expResult.z, result.z, 0.001);
    }

    /**
     * Test of multiplyScalar method, of class Vector3F.
     */
    @Test
    public void testMultiplyScalar() {
        System.out.println("multiplyScalar");
        float v = 10.0F;
        Vector3F instance = new Vector3F(-1.2F,-2.3F,-3.4F);
        Vector3F expResult = new Vector3F(-12F,-23F,-34F);
        Vector3F result = (Vector3F) instance.multiplyScalar(v);
        assertEquals(expResult.x, result.x, 0.001);
        assertEquals(expResult.y, result.y, 0.001);
        assertEquals(expResult.z, result.z, 0.001);
    }

    /**
     * Test of multiplyScalar3F method, of class Vector3F.
     */
    @Test
    public void testMultiplyScalar3F() {
        System.out.println("multiplyScalar3F");
        float v = -10.0F;
        Vector3F instance = new Vector3F(-1.2F,-2.3F,-3.4F);
        Vector3F expResult = new Vector3F(12F,23F,34F);
        Vector3F result = instance.multiplyScalar3F(v);
        assertEquals(expResult.x, result.x, 0.001);
        assertEquals(expResult.y, result.y, 0.001);
        assertEquals(expResult.z, result.z, 0.001);
    }

    /**
     * Test of divideScalar method, of class Vector3F.
     */
    @Test
    public void testDivideScalar() {
        System.out.println("divideScalar");
        float v = 2.0F;
        Vector3F instance = new Vector3F(44F,-30F,1F);
        Vector3F expResult = new Vector3F(22F,-15F,0.5F);
        Vector3F result = (Vector3F) instance.divideScalar(v);
        assertEquals(expResult.x, result.x, 0.001);
        assertEquals(expResult.y, result.y, 0.001);
        assertEquals(expResult.z, result.z, 0.001);
    }

    /**
     * Test of divideScalar3F method, of class Vector3F.
     */
    @Test
    public void testDivideScalar3F() {
        System.out.println("divideScalar3F");
        float v = -100.0F;
        Vector3F instance = new Vector3F(44F,-30F,1F);
        Vector3F expResult = new Vector3F(-0.44F,0.30F,-0.01F);
        Vector3F result = instance.divideScalar3F(v);
        assertEquals(expResult.x, result.x, 0.001);
        assertEquals(expResult.y, result.y, 0.001);
        assertEquals(expResult.z, result.z, 0.001);
    }

}