package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.ZoomCommand;
import is.utility.MyConstants;
import is.utility.SyntaxException;

import static is.interpreter.Parser.HelperParser.getObj;
import static is.interpreter.Parser.HelperParser.parseDouble;

public class Scale implements Combination{
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private final HistoryCommandHandler handler;
    public Scale( HistoryCommandHandler handler,LexicalAnalyzer lexer, ObjectRegister reg){
        this.handler=handler;
        this.lexer=lexer;
        this.reg=reg;
    }

    @Override
    public void interpret() {
        try {
            GraphicObject go = getObj(   lexer,  reg);
            double ret = parseDouble(lexer);
            if (ret < 0) {
                throw new SyntaxException(MyConstants.ERR_NEG_VAL);
            }
            handler.handle(new ZoomCommand(go, ret));
        } catch (SyntaxException s) {
            throw new SyntaxException(s + " " + MyConstants.ERR_NEG_SCALE);
        }
    }
}
