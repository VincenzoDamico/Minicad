package is.shapes.specificcommand;


import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.GroupObject;
import is.shapes.register.ObjectRegister;
import is.shapes.view.GraphicObjectPanel;

import java.util.LinkedList;
import java.util.List;

public class DelObjectCmd implements Command {
    private final ObjectRegister obreg;
    private final GraphicObjectPanel panel;
    private final GraphicObject go;
    private List<GraphicObject> grpDel;

    public DelObjectCmd(GraphicObjectPanel panel, GraphicObject go,ObjectRegister obreg) {
        this.obreg=obreg;
        this.panel = panel;
        this.go = go;
        if(go.getType().equals("Group"))
            grpDel=new LinkedList<>();
    }

    @Override
    public boolean doIt() {
        if(go.getType().equals("Group")){
            System.out.println("Rimozione dell'oggetto Type: "+go.getType()+" con Id:"+go.getId()+" e di tutti i  suoi elementi:");
            GroupObject grp=(GroupObject) go;
            remGrp(grp,"\t");
            grpDel.add(go);
            obreg.remove(go.getId());
        }
        else {
            System.out.println("Rimozione dell'oggetto Type: "+go.getType()+" con Id:"+go.getId());
            obreg.remove(go.getId());
            panel.remove(go);
        }

        return true;
    }
// funziona ricorsivamente mi memorizzo gli elemnti che cancello poichè nell'ogetto gruppo ho solo la lista di id non degli oggetti
     private void remGrp(GroupObject grp,String space) {
        for (GraphicObject el: grp.getListGroup()){
            if (el.getType().equals("Group")){
                System.out.println(space+"Rimozione dell'oggetto: "+el.getType()+" con Id:"+el.getId()+" e di tutti i  suoi elementi:");
                remGrp((GroupObject) el,space+"\t");
                grpDel.add(el);
                obreg.remove(el.getId());
            }else{
                grpDel.add(el);
                obreg.remove(el.getId());
                panel.remove(el);
                System.out.println(space+"Rimozione dell'oggetto Type: "+el.getType()+" con Id:"+el.getId());

            }
        }
    }

    @Override
    public boolean undoIt() {
        if (go.getType().equals("Group")){
            System.out.println("Ricreazione di tutti gli lementi appartenti al gruppo:");
            reAdded();

        }else{
            obreg.add(go);
            panel.add(go);
        }
        System.out.println("Riaggiunta dell'oggetto Type: "+go.getType()+" con Id:"+go.getId());
        return true;
    }

    private void reAdded() {
        for (GraphicObject el : grpDel){
            if (el.getType().equals("Group")){
                obreg.add(el);
            }else{
                obreg.add(el);
                panel.add(el);
            }
            System.out.println("Riaggiunta dell'oggetto Type: "+el.getType()+" con Id:"+el.getId());
        }

    }

}
