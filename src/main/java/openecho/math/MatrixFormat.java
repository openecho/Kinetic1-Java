/**
 * Copyright (C) 2010 openecho
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package openecho.math;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 *
 * @author openecho
 */
public class MatrixFormat extends Format {
    
    String rowPrefix;
    String rowSuffix;
    String cellSeparator;
    NumberFormat cellFormat;

    public MatrixFormat() {
        rowPrefix = "[";
        rowSuffix = " ]";
        cellSeparator = ", ";
        cellFormat = new DecimalFormat(" ####0.0000;-####0.0000");
    }

    public int[] getColumnCharacterWidths(ImmutableMatrixD a) {
        return null;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
//        if(obj instanceof ImmutableMatrixD) {
//            ImmutableMatrixD a = (ImmutableMatrixD) obj;
//            double[][] data = a.getData();
//            for(int i=0;i<a.getM();i++) {
//                toAppendTo.append(rowPrefix);
//                for(int j=0;j<a.getN();j++) {
//                    toAppendTo.append(cellFormat.format(data[i][j])).append((j<a.getN()-1) ? cellSeparator : "");
//                }
//                toAppendTo.append(rowSuffix).append("\r\n");
//            }
//        }
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String format(Object obj, String title) {
        MatrixFormat format = new MatrixFormat();
        return String.format("%s:\r\n%s", title, format.format(obj));
    }
}
