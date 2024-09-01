package is.shapes.specificcommand;


import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.shapes.view.GraphicObjectPanel;

public class DelObjectCmd implements Command {
    private ObjectRegister obreg;
    private final GraphicObjectPanel panel;
    private final GraphicObject go;

    public DelObjectCmd(GraphicObjectPanel panel, GraphicObject go,ObjectRegister obreg) {
        this.obreg=obreg;
        this.panel = panel;
        this.go = go;

    }

    @Override
    public boolean doIt() {
        if(go.getType().equals("Group"))
            obreg.remove(go.getId());
        else {
            obreg.remove(go.getId());
            panel.remove(go);
        }
        System.out.println("Rimozione dell'oggetto Type: "+go.getType()+" con Id:"+go.getId());

        return true;
    }

    @Override
    public boolean undoIt() {
        obreg.add(go);
        panel.add(go);

        System.out.println("Riaggiunta dell'oggetto Type: "+go.getType()+" con Id:"+go.getId());
        return true;
    }

}
