package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.Parser.Combination.Combination;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.AreaCmd;
import is.utility.MyConstants;
import is.utility.SyntaxException;

public class Area implements Combination {
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private Symbol symbol;
    private final HistoryCommandHandler handler;
    public Area( HistoryCommandHandler handler, Symbol symbol,LexicalAnalyzer lexer, ObjectRegister reg){
        this.handler=handler;
        this.symbol=symbol;
        this.lexer=lexer;
        this.reg=reg;
    }
    @Override
    public void interpret() {
        try {
            symbol = lexer.nextSymbol();
            if (symbol == Symbol.WORD) {
                GraphicObject go = reg.getObj(lexer.getString());
                if (go != null) {
                    handler.handle(new AreaCmd(go.getArea()));
                    return;
                }
            }
            if (symbol == Symbol.CIRCLE || symbol == Symbol.RECTANGLE || symbol == Symbol.GROUPS || symbol == Symbol.IMG) {
                handler.handle(new AreaCmd(lexer.getString().charAt(0) + "\\d+", reg));
                return;
            }
            if (symbol == Symbol.ALL) {
                handler.handle(new AreaCmd("all", reg));
                return;
            }
        } catch (SyntaxException s) {
            throw new SyntaxException(s + " "+ MyConstants.ERR_NEG_AREA);
        }
        throw new SyntaxException( MyConstants.ERR_NEG_AREA);
    }
}
