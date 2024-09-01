package is.shapes.view;

import is.shapes.model.GraphicObject;
import is.utility.Dimension2DC;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RectangleObjectView implements GraphicObjectView {
	@Override
	public void drawGraphicObject(GraphicObject go, Graphics2D g) {
		Point2D position = go.getPosition();
		Dimension2DC dim = go.getDimension();
		double x = position.getX() - dim.getWidth() / 2.0;
		double y = position.getY() - dim.getHeight() / 2.0;
		g.draw(new Rectangle2D.Double(x, y, dim.getWidth(), dim.getHeight()));

	}
}
