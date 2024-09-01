package is.shapes.view;

import is.command.CommandHandler;
import is.shapes.model.AbstractGraphicObject;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.NewObjectCmd;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

//pattern flyweight
public class CreateObjectAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5399493440620423134L;
	ObjectRegister obreg;
	AbstractGraphicObject prototype;
	GraphicObjectPanel panel;
	CommandHandler ch;

	public CreateObjectAction(AbstractGraphicObject prototype,
			GraphicObjectPanel panel, CommandHandler ch,ObjectRegister obreg) {
		super();
		this.obreg=obreg;
		this.prototype = prototype;
		this.panel = panel;
		this.ch = ch;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		GraphicObject go = prototype.clone();
		ch.handle(new NewObjectCmd(panel, go,obreg));

	}

}
