package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.AreaCmd;
import is.shapes.specificcommand.MoveCommand;
import is.utility.MyConstants;
import is.utility.SyntaxException;

import java.awt.geom.Point2D;

import static is.interpreter.Parser.HelperParser.getDupla;
import static is.interpreter.Parser.HelperParser.getObj;

public class Move implements Combination {
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private final HistoryCommandHandler handler;

    public Move(HistoryCommandHandler handler, LexicalAnalyzer lexer, ObjectRegister reg) {
        this.handler = handler;
        this.lexer = lexer;
        this.reg = reg;
    }

    @Override
    public void interpret() {
        try {
            GraphicObject go = getObj( lexer,  reg);
            if (go != null) {
                Point2D position = getDupla(  lexer);
                if (position.getX() < 0 || position.getY() < 0) {
                    throw new SyntaxException(MyConstants.ERR_NEG_VAL);
                }
                handler.handle(new MoveCommand(go, position));
            }
        } catch (SyntaxException s) {
            throw new SyntaxException(s + " "+MyConstants.ERR_NEG_MV);
        }
    }
}