package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GroupObject;
import is.shapes.controller.ObjectRegister;

public class UngroupCmd  implements Command {
    private final ObjectRegister obreg;
    private final GroupObject go;

    public UngroupCmd(ObjectRegister obreg, GroupObject go) {
        this.obreg = obreg;
        this.go =go;

    }

    @Override
    public boolean doIt() {
        obreg.remove(go.getId());
        System.out.println("Smantellamento del gruppo con Id:"+go.getId());

        return true;
    }

    @Override
    public boolean undoIt() {
        obreg.add(go);
        System.out.println("Ricreazione del gruppo con Id:"+go.getId());

        return true;
    }

}

