package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.GroupObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.GroupCmd;
import is.utility.MyConstants;
import is.utility.SyntaxException;

import java.util.List;

import static is.interpreter.Parser.HelperParser.getListID;

public class Group implements Combination{
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private final HistoryCommandHandler handler;
    public Group( HistoryCommandHandler handler,LexicalAnalyzer lexer, ObjectRegister reg){
        this.handler=handler;
        this.lexer=lexer;
        this.reg=reg;
    }


    @Override
    public void interpret() {
        try {
            List<String> listId = getListID( lexer, reg);
            GroupObject go = new GroupObject(reg, listId);
            handler.handle(new GroupCmd(reg, go));
        } catch (
                SyntaxException s) {
            throw new SyntaxException(s + " " + MyConstants.ERR_NEG_GRP);
        }
    }
}
