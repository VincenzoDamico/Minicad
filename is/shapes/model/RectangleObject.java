package is.shapes.model;

import is.utility.Dimension2DC;
import is.utility.MyConstants;

import java.awt.geom.Point2D;

public final class RectangleObject extends AbstractGraphicObject {
	private static int  COUNTOB=0;
	private final String id;
	private Point2D position;
	private Dimension2DC dim;


	public RectangleObject(Point2D pos, double w, double h) {
		if (w <= 0 || h <= 0)
			throw new IllegalArgumentException(MyConstants.ERR_NEG_VAL);
		dim = new Dimension2DC(0,0);
		dim.setSize(w, h);
		position = new Point2D.Double(pos.getX(), pos.getY());
		id="r"+COUNTOB;
		COUNTOB++;
	}
	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean contains(Point2D p) {
		double w = dim.getWidth() / 2;
		double h = dim.getHeight() / 2;
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
	public Point2D getPosition() {
		return new Point2D.Double(position.getX(), position.getY());
	}

	@Override
	public void scale(double factor) {
		if (factor <= 0)
			throw new IllegalArgumentException(MyConstants.ERR_NEG_VAL);
		dim.setSize(dim.getWidth() * factor, dim.getHeight() * factor);

		notifyListeners(new GraphicEvent(this));
	}
	@Override
	public double getArea(){
		return dim.getHeight()*dim.getWidth();
	}
	@Override
	public double getPerimeter(){

		return dim.getHeight()*2+2*dim.getWidth();
	}

	@Override
	public Dimension2DC getDimension() {
		return dim.clone();
	}

	@Override
	public RectangleObject clone() {
		RectangleObject cloned = (RectangleObject) super.clone();
		cloned.position = (Point2D) position.clone();
		cloned.dim =  dim.clone();
		return cloned;
	}

	@Override
	public String getType() {
		return "Rectangle";
	}
	@Override
	public String toString() {
		return String.format("Id: %s Type: %s Position:(%.2f, %.2f)  Dimension:(%.2f, %.2f)\n", id, getType(), position.getX(), position.getX(), dim.getWidth(), dim.getHeight());
	}

}
