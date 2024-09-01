package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.shapes.view.GraphicObjectPanel;

public class NewObjectCmd implements Command {
	private ObjectRegister obreg;
	private final GraphicObjectPanel panel;
	private final GraphicObject go;

	public NewObjectCmd(GraphicObjectPanel panel, GraphicObject go,ObjectRegister obreg) {
		this.obreg=obreg;
		this.panel = panel;
		this.go = go;
		
	}

	@Override
	public boolean doIt() {
		obreg.add(go);
		panel.add(go);
		System.out.println("Creazione dell'oggetto Type: "+go.getType()+" con Id:"+go.getId());

		return true;
	}

	@Override
	public boolean undoIt() {
		obreg.remove(go.getId());
		panel.remove(go);
		System.out.println("Rimozione dell'oggetto Type: "+go.getType()+" con Id:"+go.getId());

		return true;
	}

}
