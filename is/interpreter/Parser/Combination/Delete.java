package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.DelObjectCmd;
import is.shapes.view.GraphicObjectPanel;
import is.utility.MyConstants;
import is.utility.SyntaxException;

import static is.interpreter.Parser.HelperParser.getObj;

public class Delete implements Combination{
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private Symbol symbol;
    private final HistoryCommandHandler handler;
    private final GraphicObjectPanel gpanel;
    public Delete(GraphicObjectPanel gpanel, HistoryCommandHandler handler, Symbol symbol,LexicalAnalyzer lexer, ObjectRegister reg){
        this.handler=handler;
        this.symbol=symbol;
        this.lexer=lexer;
        this.reg=reg;
        this.gpanel=gpanel;
    }
    @Override
    public void interpret() {
        try {
            GraphicObject go = getObj(symbol,lexer,reg);
            if (go != null) {
                handler.handle(new DelObjectCmd(gpanel, go, reg));
                return;
            }
        } catch (SyntaxException s) {
            throw new SyntaxException(s+" "+ MyConstants.ERR_NEG_DEL);
        }
        throw new SyntaxException(MyConstants.ERR_NEG_DEL);
    }
}
//da cpapire se deco eliminare tutto
    /*private void deleteEl(GraphicObject go) {
            if(!go.getType().equals("Group")) {

            }else {
                GroupObject grp=(GroupObject)go;
                deleteGrp(grp);
            }
    }

    private void deleteGrp(GroupObject grp) {
        for(GraphicObject ob:grp.getListGroup()){
            deleteEl(ob);
        }
    }*/