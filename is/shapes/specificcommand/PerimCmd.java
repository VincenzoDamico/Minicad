package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.controller.ObjectRegister;
import is.utility.MyConstants;

public class PerimCmd implements Command {
    private final double perim;
    public PerimCmd(GraphicObject g) {
        this.perim=g.getPerimeter();
    }
    public PerimCmd(String ric , ObjectRegister reg) {
        this.perim=calPerim(ric,reg);
    }
    private double calPerim(String ric,ObjectRegister reg) {
        double ret=0d;
        for(GraphicObject obj: reg){
            if((ric.equals("all")&&!obj.getType().equals("Group"))||obj.getId().matches(ric)){
                ret+= obj.getPerimeter();
            }
        }
        return ret;
    }
    public double getPerim() {
        return perim;
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
