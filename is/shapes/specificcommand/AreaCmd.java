package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.utility.MyConstants;

public class AreaCmd implements Command {
    private final double area;

    public AreaCmd(double area) {
        this.area=area;
    }
    public AreaCmd(String ric , ObjectRegister reg) {
        this.area=getArea(ric,reg);
    }
    private double getArea(String ric,ObjectRegister reg) {
        double ret=0d;
        for(GraphicObject obj: reg){
            if((ric.equals("all")&&!obj.getType().equals("Group"))||obj.getId().matches(ric)){
                ret+= obj.getArea();
            }
        }
        return ret;
    }
    @Override
    public boolean doIt() {
        System.out.println(String.format("L'area è: %.2f", area));
        return true;//permetto l'undo (anche se non fa niente) per non bloccarle la pila protocollare
    }
    @Override
    public boolean undoIt() {
        System.out.println(MyConstants.NO_UNDO);
        return  true;
    }
}
