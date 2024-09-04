package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.Parser.Combination.Combination;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.PerimCmd;
import is.utility.MyConstants;
import is.utility.SyntaxException;

public class Perimeter implements Combination {
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private final HistoryCommandHandler handler;
    public Perimeter( HistoryCommandHandler handler,LexicalAnalyzer lexer, ObjectRegister reg){
        this.handler=handler;
        this.lexer=lexer;
        this.reg=reg;
    }
    @Override
    public void interpret() {
        try {
            Symbol symbol = lexer.nextSymbol();
            if (symbol == Symbol.WORD) {
                GraphicObject go = reg.getObj(lexer.getString());
                if (go != null) {
                    handler.handle(new PerimCmd(go.getPerimeter()));
                    return;
                }
            }
            if (symbol == Symbol.CIRCLE || symbol == Symbol.RECTANGLE || symbol == Symbol.GROUPS || symbol == Symbol.IMG) {
                handler.handle(new PerimCmd(lexer.getString().charAt(0) + "\\d+", reg));
                return;
            }
            if (symbol == Symbol.ALL) {
                handler.handle(new PerimCmd("all", reg));
                return;
            }
        } catch (SyntaxException s) {
            throw new SyntaxException(s + " "+ MyConstants.ERR_NEG_PERIM);
        }
        throw new SyntaxException(MyConstants.ERR_NEG_PERIM);
    }
}
