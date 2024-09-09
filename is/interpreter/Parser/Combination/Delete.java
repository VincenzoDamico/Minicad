package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.shapes.model.GraphicObject;
import is.shapes.controller.ObjectRegister;
import is.shapes.specificcommand.DelObjectCmd;
import is.shapes.view.GraphicObjectPanel;
import is.utility.MyConstants;
import is.utility.SyntaxException;

import static is.interpreter.Parser.HelperParser.getObj;

public class Delete implements Combination{
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private final HistoryCommandHandler handler;
    private final GraphicObjectPanel gpanel;
    public Delete(GraphicObjectPanel gpanel, HistoryCommandHandler handler, LexicalAnalyzer lexer, ObjectRegister reg){
        this.handler=handler;
        this.lexer=lexer;
        this.reg=reg;
        this.gpanel=gpanel;
    }
    @Override
    public void interpret() {
        try {
            GraphicObject go = getObj(lexer,reg);
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
