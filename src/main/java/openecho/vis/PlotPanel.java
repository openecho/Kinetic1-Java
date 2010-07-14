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
package openecho.vis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import openecho.geometry.Circle;
import openecho.geometry.Line;
import openecho.geometry.Point2D;
import openecho.geometry.Shape;
import openecho.geometry.Rectangle;
import openecho.math.Vector;

/**
 *
 * @author openecho
 */
public class PlotPanel extends JPanel {

    public ArrayList<Shape> shapes = new ArrayList<Shape>();

    public final int BORDER_BUFFER;

    public PlotPanel() {
        BORDER_BUFFER = 6;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintAxis(g);
        paintShapes(g);
    }

    public void paintAxis(Graphics g) {
        Dimension dimension = getSize();
        double contentHeight = dimension.height - BORDER_BUFFER;
        double contentWidth = dimension.width - BORDER_BUFFER;
        double xMax = contentWidth/2D;
        double yMax = contentHeight/2D;
        g.setColor(Color.LIGHT_GRAY);     
        g.drawRect(convertX(-xMax), convertY(yMax), (int) contentWidth, (int) contentHeight);
        g.drawLine(convertX(0), convertY(yMax), convertX(0) , convertY(-yMax));
        g.drawLine(convertX(-xMax), convertY(0), convertX(xMax) , convertY(0));
    }

    public void paintShapes(Graphics g) {
        Dimension dimension = getSize();
        int contentWidth = dimension.width - BORDER_BUFFER;
        int contentHeight = dimension.height - BORDER_BUFFER;
        int xMod = BORDER_BUFFER;
        int yMod = BORDER_BUFFER;
        double xOrigin = xMod+contentWidth/2D;
        double yOrigin = contentHeight/2D;

        double x,y,xWorld,yWorld;

        for(Shape shape: shapes) {
            if(shape instanceof Point2D) {
                g.setColor(Color.BLUE);
                Point2D point = shape.getOrigin();
                drawPoint(g, point);
            } else if(shape instanceof Line) {
                g.setColor(Color.GREEN);
                Point2D[] points = shape.getPoints();
                for(Point2D point: points) {
                    drawPoint(g, point);
                }
                drawLine(g,points[0],points[1]);
                drawCentrePoint(g, shape.getCentroid());
            } else if(shape instanceof Rectangle) {
                g.setColor(Color.RED);
                Point2D[] points = shape.getPoints();
                for(Point2D point: points) {
                    drawPoint(g, point);
                }
                drawLine(g,points[0],points[1]);
                drawLine(g,points[1],points[2]);
                drawLine(g,points[2],points[3]);
                drawLine(g,points[3],points[0]);

                drawCentrePoint(g, shape.getCentroid());
            } else if(shape instanceof Circle) {
                g.setColor(Color.BLACK);
                Point2D[] points = shape.getPoints();
                drawCircle(g, shape.getCentroid(), shape.getRadius());
                drawPoint(g, points[0]);
                drawLine(g,shape.getCentroid(),points[0]);
                drawCentrePoint(g, shape.getCentroid());
            }
        }
    }

    public void drawPoint(Graphics g, Point2D p) {
        Point2D centroid = p.getCentroid();
        g.fillOval(convertX(centroid.getX()-2), convertY(p.getY()+2), 4, 4);
    }

    public void drawCentrePoint(Graphics g, Point2D p) {
        double x,y,xWorld,yWorld;
        x = p.getX();
        y =  p.getY();
        drawPoint(g,p);
        g.drawOval(convertX(x-4), convertY(y+4), 8, 8);
    }

    public void drawLine(Graphics g, Point2D a, Point2D b) {
        double xA,yA,xB,yB;
        xA = a.getX();
        yA =  a.getY();
        xB = b.getX();
        yB = b.getY();
        g.drawLine(convertX(xA), convertY(yA), convertX(xB), convertY(yB));
    }

    public void drawLine(Graphics g, Line l) {
        drawLine(g,l.getStartPoint(),l.getEndPoint());
    }

    public void drawCircle(Graphics g, Point2D c, double r) {
        double x,y,n;
        x = c.getX() - r;
        y = c.getY() + r;
        n = r+r;
        g.drawOval((int) convertX(x), (int) convertY(y), (int) (n), (int) (n));
    }

    public int convertX(double x) {
        Dimension dimension = getSize();
        int contentWidth = dimension.width - BORDER_BUFFER;
        int xMod = BORDER_BUFFER/2;
        return (int) (xMod + contentWidth/2 + x);
    }

    public int convertY(double y) {
        Dimension dimension = getSize();
        int contentHeight = dimension.height - BORDER_BUFFER;
        int yMod = BORDER_BUFFER/2;
        return (int) (yMod + contentHeight/2 - y);
    }
}
