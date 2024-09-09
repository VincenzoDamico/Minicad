package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.controller.ObjectRegister;
import is.utility.MyConstants;

public class AreaCmd implements Command {
    private final double area;
    public AreaCmd(GraphicObject g) {
        this.area=g.getArea();
    }
    public AreaCmd(String ric , ObjectRegister reg) {
        this.area=calArea(ric,reg);
    }
    private double calArea(String ric,ObjectRegister reg) {
        double ret=0d;
        for(GraphicObject obj: reg){
            if((ric.equals("all")&&!obj.getType().equals("Group"))||obj.getId().matches(ric)){
                ret+= obj.getArea();
            }
        }
        return ret;
    }
    public double getArea() {
        return area;
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
