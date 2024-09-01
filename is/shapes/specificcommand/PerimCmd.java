package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.utility.MyConstants;

public class PerimCmd implements Command {
    private final double perim;

    public PerimCmd(double perim) {
        this.perim=perim;
    }
    public PerimCmd(String ric , ObjectRegister reg) {
        this.perim=getPerim(ric,reg);
    }
    private double getPerim(String ric,ObjectRegister reg) {
        double ret=0d;
        for(GraphicObject obj: reg){
            if((ric.equals("all")&&!obj.getType().equals("Group"))||obj.getId().matches(ric)){
                ret+= obj.getPerimeter();
            }
        }
        return ret;
    }
    @Override
    public boolean doIt() {
        System.out.println(String.format("Il perimetro è: %.2f", perim));
        return true;
    }
    @Override
    public boolean undoIt() {
        System.out.println(MyConstants.NO_UNDO);
        return  true;
    }
}
