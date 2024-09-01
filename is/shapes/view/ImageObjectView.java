package is.shapes.view;

import is.shapes.model.GraphicObject;
import is.shapes.model.ImageObject;
import is.utility.Dimension2DC;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;

public class ImageObjectView implements GraphicObjectView {
	@Override
	public void drawGraphicObject(GraphicObject go, Graphics2D g) {
		ImageObject io = (ImageObject) go;
		Dimension2DC dim = io.getDimension();
		Point2D position = io.getPosition();
		Image image = io.getImage();
		int w = (int) (dim.getWidth());
		int h = (int) (dim.getHeight());
		int x = (int) (position.getX()) - w / 2;
		int y = (int) (position.getY()) - h / 2;

		g.drawImage(image, x, y, w, h, null);

	}
}
