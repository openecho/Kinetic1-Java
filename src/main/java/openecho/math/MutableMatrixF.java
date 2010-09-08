/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.math;

/**
 *
 * @author jmarsden
 * @deprecated 
 */
public class MutableMatrixF extends MatrixF {

    public MutableMatrixF(int m, int n) {
        super(m,n);
    }

    public MutableMatrixF(Float[][] data) {
        super(data);
    }

    @Override
    public Float[][] getData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Float getData(int i, int j) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setData(Number[][] data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setData(int i, int j, Number data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Float[] getRow(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF add(Matrix b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF subtract(Matrix b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF multiply(Matrix b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF transpose() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF addScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF subtractScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF multiplyScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MatrixF divideScalar(Number v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isMutable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
