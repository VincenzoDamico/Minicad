package is.shapes.view;

import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
//pattern observer
public class CircleObjectView implements GraphicObjectView {
	@Override
	public void drawGraphicObject(GraphicObject go, Graphics2D g) {
		CircleObject co = (CircleObject) go;
		Point2D position = co.getPosition();
		double r = co.getRadius();
		double x = position.getX() - r;
		double y = position.getY() - r;
		g.draw(new Ellipse2D.Double(x, y, r * 2.0, r * 2.0));

	}
}