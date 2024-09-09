package is.shapes.controller;

import is.shapes.model.GraphicObject;
import is.utility.SyntaxException;


import java.util.*;


public class ObjectRegister implements Iterable<GraphicObject>  {
 private Map<String, GraphicObject> reg;
public ObjectRegister(){
    reg=new HashMap<>();
}
 public String add (GraphicObject g){
     String id =g.getId();
     reg.put(id,g);
     return id;
 }
    public void remove (String id){
        if(reg.containsKey(id)){
            /*if (reg.get(id).getType().equals("Group")){
                GroupObject lid= (GroupObject)reg.get(id);
                for(String i:lid.getListId() )  {
                    if (reg.containsKey(i)) {
                        if (reg.get(id).getType().equals("Group")) {
                            remove(id);
                        } else {
                            reg.remove(id);
                        }
                    }
                }
            }else {*/
                reg.remove(id);


        }else{
            throw new SyntaxException("ID:"+id+" inserito non esiste.");
        }
    }

    public GraphicObject getObj (String id) {
        if (reg.containsKey(id))
            return reg.get(id);
        throw new SyntaxException("ID:"+id+" inserito non esiste.");
    }

    public boolean contains(String id) {
        return reg.containsKey(id);
    }
    @Override
    public Iterator<GraphicObject> iterator() {
        return reg.values().iterator();
    }
}
  /*  public void degroup (String id){
        if(reg.containsKey(id)){
            reg.remove(id);
        }else{
            System.out.println("Id inserito non esiste");
        }
  }*/




