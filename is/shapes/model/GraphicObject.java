package is.shapes.model;

import is.utility.Dimension2DC;

import java.awt.geom.Point2D;

public interface GraphicObject {

	void addGraphicObjectListener(GraphicObjectListener l); //aggiunge un ascoltatore per gli eventi dell'oggetto grafico

	void removeGraphicObjectListener(GraphicObjectListener l);//rimuove un ascoltatore

	void moveTo(Point2D p);//sposta l'oggetto grafico in una nuova posizione

	default void moveTo(double x, double y){
		moveTo(new Point2D.Double(x, y));
	} //restituisce la posizione dell'oggetto grafico

	Point2D getPosition();

	Dimension2DC getDimension();

	void scale(double factor);

	boolean contains(Point2D p);//verifica se un punto è contenuto nell'oggetto grafico

	String getType();

	String getId();
	double getPerimeter();
	double getArea();


}
