package is.shapes.model;


import is.utility.Dimension2DC;
import is.utility.MyConstants;

import java.awt.geom.Point2D;

public final  class CircleObject extends AbstractGraphicObject {
	private static int  COUNTOB=0;
	private final String id;
	private Point2D position;

	private double radius;

	public CircleObject(Point2D pos, double r) {
		if (r <= 0)
			throw new IllegalArgumentException(MyConstants.ERR_NEG_VAL);
		position = new Point2D.Double(pos.getX(), pos.getY());
		radius = r;
		id="c"+COUNTOB;
		COUNTOB++;
	}
    @Override
	public String getId() {
		return id;
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
		radius *= factor;
		notifyListeners(new GraphicEvent(this));
	}
	@Override
	public double getArea(){
		return radius*radius*Math.PI;
	}
	@Override
	public double getPerimeter(){
		return 2*radius*Math.PI;
	}

	@Override
	public Dimension2DC getDimension() {
		Dimension2DC d = new Dimension2DC(0,0);
		d.setSize(2 * radius, 2 * radius);

		return d;
	}

	@Override
	public boolean contains(Point2D p) {
		return (position.distance(p) <= radius);

	}

	@Override
	public CircleObject clone() {
		CircleObject cloned = (CircleObject) super.clone();
		cloned.position = (Point2D) position.clone();
		return cloned;
	}

	@Override
	public String getType() {

		return "Circle";
	}

	public double getRadius() {
		return radius;
	}
	@Override
	public String toString() {
		return String.format("Id: %s Type: %s Position:(%.2f, %.2f)  Dimension:(%.2f, %.2f) Radius:(%.2f)\n", id, getType(), position.getX(), position.getY(), getDimension().getWidth(), getDimension().getHeight(),radius);
	}
}
