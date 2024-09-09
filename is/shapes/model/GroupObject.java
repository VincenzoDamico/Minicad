package is.shapes.model;
import is.shapes.controller.ObjectRegister;
import is.utility.Dimension2DC;
import is.utility.MyConstants;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class GroupObject extends AbstractGraphicObject{
    List<String> groups; //lista dei gruppi a cui il gruppo appartine il gruppo
    private static int  COUNTOB=0;

    private Point2D posAttuale=new Point2D.Double(0,0);
    private ObjectRegister reg;
    private final String id;//id identificativo del gruppo

    public  GroupObject(ObjectRegister reg, List<String> groups){
        this.groups=groups;
        this.reg=reg;
        updatePosition();
        id="g"+COUNTOB;
        COUNTOB++;
    }

    @Override
    public void moveTo(Point2D p) {
        if(p.getX()<0 ||p.getY()<0)
            throw new IllegalArgumentException(MyConstants.ERR_NEG_VAL_POS);
        //agiorno la posizione attuale nel caso si cia stao un movimento degli elementi facenti parte del gruppo
        updatePosition();
        Point2D diff = new Point2D.Double(p.getX()-posAttuale.getX(), p.getY()-posAttuale.getY());
        if(diff.getY()!=0 && diff.getX()!=0 && MovePos(diff)) {
            for (GraphicObject o : getListGroup()) {
                o.moveTo(o.getPosition().getX() + diff.getX(), o.getPosition().getY() + diff.getY());
            }
            posAttuale.setLocation(p.getX(),p.getY());
        }else{
            throw new IllegalArgumentException(MyConstants.ERR_POS_GRP);
        }
    }

    private boolean MovePos(Point2D diff) {
        for (GraphicObject o : getListGroup()) {
            if(o.getPosition().getX() + diff.getX()<0 || o.getPosition().getY() + diff.getY()<0)
                    return false;
        }
        return true;
    }

    @Override
    public double getArea() {
        double area = 0;
        for(GraphicObject o : getListGroup()) {
            area+=o.getArea();
        }
        return area;
    }
    @Override
    public double getPerimeter() {
        double per = 0;
        for(GraphicObject o : getListGroup()){
            per+=o.getPerimeter();
        }
        return per;
    }
    @Override
    public Point2D getPosition() {
        updatePosition();
        return posAttuale;
    }
    public void updatePosition() {
        Point2D pos=new Point2D.Double(0,0);
        int numObject = 0;
        for(GraphicObject o : getListGroup()) {
            pos.setLocation(pos.getX()+o.getPosition().getX(), pos.getY()+o.getPosition().getY());
            numObject++;
        }
        posAttuale.setLocation(pos.getX()/numObject, pos.getY()/numObject);
    }

    @Override
    public Dimension2DC getDimension() {
        Dimension2DC d = new Dimension2DC(0,0);
        for(GraphicObject o : getListGroup()) {
            d.setSize(d.getWidth()+o.getDimension().getWidth(),d.getHeight()+o.getDimension().getHeight());
        }

        return d;
    }
    @Override
    public void scale(double factor) {
        if (factor <= 0)
            throw new IllegalArgumentException(MyConstants.ERR_NEG_VAL_SCAl);
        for(GraphicObject o :  getListGroup() ){
            o.scale(factor);
        }
    }

    @Override //TO-DO
    public boolean contains(Point2D p) {
        for(GraphicObject o : getListGroup()) {
            if(o.contains(p)) return true;
        }
        return false;
    }
    @Override
    public GroupObject clone() {
        GroupObject cloned = (GroupObject) super.clone();
        cloned.groups =new LinkedList<>(groups);//funzione perchè le stringhe sono oggetti immutabili
        return cloned;

    }
    @Override
    public String getId() { return id; }
    @Override
    public String getType() {
        return "Group";
    }


    //l'idea è che l'oggetto stesso si renda conto al momento della chimate se alcuni suoi elementi sono stai eliminati e quindi si aggiorni da solo
    public List<GraphicObject> getListGroup() {
        List<GraphicObject> ret = new LinkedList<>();
        updateGrp();
        for (String idEl : groups) {
                ret.add(reg.getObj(idEl));
        }
        return ret;

    }

    private void updateGrp() {
        LinkedList<String> up = new LinkedList<>(groups);
        boolean newPos=false;
        for (String idEl : up) {
            if (!reg.contains(idEl)) {
                groups.remove(idEl);
                newPos=true;
            }
        }
        if(groups.isEmpty()){
            reg.remove(id);
            throw new RuntimeException("Id:"+id+". "+MyConstants.ERR_DEL_GRP);
        }
        if (newPos){
            updatePosition();
        }
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        childEl(this,res, "");
        return res.toString();
    }
    private void childEl(GroupObject grp,StringBuilder res, String child) {
        res.append(child+String.format("Id: %s Type: %s {\n", grp.getId(), grp.getType()));
        for (GraphicObject g : grp.getListGroup()) {
            if (g.getType().equals("Group")){
                childEl((GroupObject)g,res, child+"\t");
            }else{
                res.append(child+"\t"+g);
            }

        }
        res.append(child+"}\n");
    }

}
