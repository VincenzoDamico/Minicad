package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.utility.MyConstants;

import java.awt.geom.Point2D;

public class MoveCommand implements Command {

	private final  Point2D oldPos;

    private  final Point2D newPos;

	private  final GraphicObject object;
	
	public MoveCommand(GraphicObject go, Point2D pos) {
		oldPos = new Point2D.Double(go.getPosition().getX(), go.getPosition().getY());
		newPos = pos;
		this.object = go;
		
		
	}

	@Override
	public boolean doIt() {
		object.moveTo(newPos);
		System.out.println(String.format("Movimento in posizione(%.2f, %.2f) dell'oggeto di id:%s",newPos.getX(),newPos.getY(),object.getId()));

		return true;
	}

	@Override
	public boolean undoIt() {
		object.moveTo(oldPos);
		
		return true;
	}

}
