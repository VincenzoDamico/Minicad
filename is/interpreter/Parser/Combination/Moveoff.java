package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.MoveCommand;
import is.utility.MyConstants;
import is.utility.SyntaxException;

import java.awt.geom.Point2D;

import static is.interpreter.Parser.HelperParser.getDupla;
import static is.interpreter.Parser.HelperParser.getObj;

public class Moveoff implements Combination {
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;

    private final HistoryCommandHandler handler;

    public Moveoff(HistoryCommandHandler handler, LexicalAnalyzer lexer, ObjectRegister reg) {
        this.handler = handler;
        this.lexer = lexer;
        this.reg = reg;
    }

    @Override
    public void interpret() {
        try {
            GraphicObject go =  getObj(   lexer,  reg);
            if (go != null) {
                Point2D position =getDupla(  lexer);
                double newX = position.getX() + go.getPosition().getX();
                double newY = position.getY() + go.getPosition().getY();
                position.setLocation(newX, newY);
                if (position.getX() < 0 || position.getY() < 0) {
                    throw new IllegalArgumentException(MyConstants.ERR_NEG_VAL_POS);
                }
                handler.handle(new MoveCommand(go, position));
            }
        } catch (RuntimeException s) {
            throw new SyntaxException(s + " "+MyConstants.ERR_NEG_MVOFF);
        }
    }
}