package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.utility.MyConstants;


public class LsCmd implements Command {
    private final String ls;

    public LsCmd(String ls) {
        this.ls=ls;
    }
    public LsCmd(String ric , ObjectRegister reg) {
        this.ls=getString(ric,reg);
    }
    private String getString(String ric,ObjectRegister reg) {
        StringBuilder ret=new StringBuilder();
        for(GraphicObject obj: reg){
            if(ric.equals("all")||obj.getId().matches(ric)){
                ret.append(obj);
            }
        }
        return ret.toString();
    }
    @Override
    public boolean doIt() {
        if (ls.equals("")){
            System.out.println("Non è stato  inserito nulla");
        }else {
            System.out.println(ls);
        }
        return true; //permetto l'undo (anche se non fa niente) per non bloccarle la pila protocollare
    }
    @Override
    public boolean undoIt() {
        System.out.println(MyConstants.NO_UNDO);
        return  true;
    }
}
