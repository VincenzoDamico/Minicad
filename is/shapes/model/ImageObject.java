package is.shapes.model;

import is.utility.Dimension2DC;
import is.utility.MyConstants;


import java.awt.Image;

import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

public final class ImageObject extends AbstractGraphicObject {
	private double factor = 1.0;
	private static int  COUNTOB=0;
	private final Image image;
	private final String id;
	private Point2D position;

	public Image getImage() {
		return image;
	}

	public ImageObject(ImageIcon img, Point2D pos) {
		position = new Point2D.Double(pos.getX(), pos.getY());
		image = img.getImage();
		id="i"+COUNTOB;
		COUNTOB++;
	}
	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean contains(Point2D p) {
		double w = (factor * image.getWidth(null)) / 2;
		double h = (factor * image.getHeight(null)) / 2;
		double dx = Math.abs(p.getX() - position.getX());
		double dy = Math.abs(p.getY() - position.getY());
		return dx <= w && dy <= h;
	}

	@Override
	public void moveTo(Point2D p) {
		if(p.getX()<0 ||p.getY()<0)
			throw new IllegalArgumentException(MyConstants.ERR_NEG_VAL);
		position.setLocation(p);
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public ImageObject clone() {
		ImageObject cloned = (ImageObject) super.clone();
		cloned.position = (Point2D) position.clone();
		return cloned;

	}
	@Override
	public double getPerimeter(){
		return 2*factor * image.getWidth(null)+
				2*factor * image.getHeight(null);
	}
	@Override
	public double getArea(){
		return factor * image.getWidth(null)*
				factor * image.getHeight(null);
	}
	@Override
	public Point2D getPosition() {

		return new Point2D.Double(position.getX(), position.getY());
	}

	@Override
	public void scale(double factor) {
		if (factor <= 0)
			throw new IllegalArgumentException(MyConstants.ERR_NEG_VAL);
		this.factor *= factor;
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public Dimension2DC getDimension() {
		Dimension2DC dim = new Dimension2DC(0,0);
		dim.setSize(factor * image.getWidth(null),
				factor * image.getHeight(null));
		return dim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see is.shapes.GraphicObject#getType()
	 */
	@Override
	public String getType() {
		return "image";
	}

	@Override
	public String toString() {
		return String.format("Id: %s Type: %s Position:(%.2f, %.2f)  Dimension:(%.2f, %.2f)\n", id, getType(), position.getX(), position.getX(), getDimension().getWidth(), getDimension().getHeight());
	}

}
