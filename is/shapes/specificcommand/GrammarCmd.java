package is.shapes.specificcommand;

import is.command.Command;
import is.utility.MyConstants;


public class GrammarCmd implements Command {
    private final String s;

    public GrammarCmd(String s) {
        this.s=s;
    }
    @Override
    public boolean doIt() {
        System.out.println(s);
        return true;//permetto l'undo (anche se non fa niente) per non bloccarle la pila protocollare
    }
    @Override
    public boolean undoIt() {
        System.out.println(MyConstants.NO_UNDO);
        return  true;
    }
}
