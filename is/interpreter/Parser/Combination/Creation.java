package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.Parser.Combination.Combination;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.AbstractGraphicObject;
import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.register.ObjectRegister;
import is.shapes.view.CreateObjectAction;
import is.shapes.view.GraphicObjectPanel;
import is.utility.MyConstants;
import is.utility.SyntaxException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.nio.file.Files;
import java.nio.file.Paths;

import static is.interpreter.Parser.HelperParser.*;

public class Creation implements Combination {
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private Symbol symbol;
    private final HistoryCommandHandler handler;
    private final GraphicObjectPanel gpanel;
    public Creation( GraphicObjectPanel gpanel,HistoryCommandHandler handler, Symbol symbol,LexicalAnalyzer lexer, ObjectRegister reg){
        this.handler=handler;
        this.gpanel=gpanel;
        this.symbol=symbol;
        this.lexer=lexer;
        this.reg=reg;
    }
    @Override
    public void interpret() {
        try {
            AbstractGraphicObject go = null;
            symbol = lexer.nextSymbol();
            if (symbol == Symbol.CIRCLE) {
                double radius = getRadius(symbol,lexer);
                Point2D position = getDupla(symbol,lexer);
                //controllo se non è negativo
                if (position.getX() < 0 || position.getY() < 0 || radius < 0) {
                    throw new SyntaxException(MyConstants.ERR_NEG_VAL);
                }
                go = new CircleObject(position, radius);
            } else if (symbol == Symbol.RECTANGLE) {
                Point2D dimension = getDupla(symbol,lexer);
                Point2D position = getDupla(symbol,lexer);
                if (position.getX() < 0 || position.getY() < 0 || dimension.getX() < 0 || dimension.getY() < 0) {
                    throw new SyntaxException(MyConstants.ERR_NEG_VAL);
                }
                go = new RectangleObject(position, dimension.getX(), dimension.getY());
            } else if (symbol == Symbol.IMG) {
                String path = getPath(symbol,lexer);
                Point2D position = getDupla(symbol,lexer);
                if (position.getX() < 0 || position.getY() < 0) {
                    throw new SyntaxException(MyConstants.ERR_NEG_VAL);
                }

                // vedo se un path totale o parziale
                if (!path.substring(0, 1).equals("C")) {
                    path = MyConstants.GEN_PATH+ path;
                }
                //verifico se il path esite
                if (Files.exists(Paths.get(path))) {
                    go = new ImageObject(new ImageIcon(path), position);
                } else {
                    throw new SyntaxException(MyConstants.ERR_PATH);
                }

            } else {
                throw new SyntaxException("");
            }

            CreateObjectAction action = new CreateObjectAction(go, gpanel, handler, reg);
            ActionEvent event = new ActionEvent(action, ActionEvent.ACTION_PERFORMED, "command");
            action.actionPerformed(event);
        }catch (SyntaxException s){
            throw new SyntaxException(s+" "+MyConstants.ERR_NEG_NEW);

        }
    }
}
