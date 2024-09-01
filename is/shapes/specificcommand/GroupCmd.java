package is.shapes.specificcommand;


import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;
import is.shapes.register.ObjectRegister;

public class GroupCmd  implements Command {
    private ObjectRegister obreg;
    private final GroupObject go;

    public GroupCmd(ObjectRegister obreg, GroupObject go) {
        this.obreg = obreg;
        this.go =go;

    }

    @Override
    public boolean doIt() {
        obreg.add(go);
        System.out.println("Creazione dell'oggetto Type: "+go.getType()+" con Id:"+go.getId());
        return true;
    }

    @Override
    public boolean undoIt() {
        obreg.remove(go.getId());
        System.out.println("Rimozione dell'oggetto Type: "+go.getType()+" con Id:"+go.getId());
        return true;
    }

}

