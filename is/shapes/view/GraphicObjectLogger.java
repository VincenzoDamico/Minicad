package is.shapes.view;

import is.shapes.model.GraphicEvent;
import is.shapes.model.GraphicObject;
import is.shapes.model.GraphicObjectListener;

public class GraphicObjectLogger implements GraphicObjectListener {

	public void graphicChanged(GraphicEvent e) {
		GraphicObject go = e.getSource();
		System.out.printf("[%s] pos=[%f,%f] dim=[%f,%f]%n", go.getType(), go
				.getPosition().getX(), go.getPosition().getY(), go
				.getDimension().getWidth(),
				go.getDimension().getHeight());
	}

}
