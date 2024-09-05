package is.test;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Parser.Combination.Scale;
import is.interpreter.Parser.Parser;
import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.register.ObjectRegister;
import is.shapes.view.*;
import is.utility.Dimension2DC;
import is.utility.SyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScaleTest {
    static Parser p;
    static GraphicObjectPanel gpanel;
    static HistoryCommandHandler handler;

    @BeforeAll
    static void setUpAll() {
        gpanel = new GraphicObjectPanel();
        GraphicObjectViewFactory.FACTORY.installView(RectangleObject.class, new RectangleObjectView());
        GraphicObjectViewFactory.FACTORY.installView(CircleObject.class, new CircleObjectView());
        GraphicObjectViewFactory.FACTORY.installView(ImageObject.class, new ImageObjectView());
        handler = new HistoryCommandHandler();
        p = new Parser(gpanel, handler);
        StringReader sr = new StringReader("new circle (5.0) (12.0,12.0)");
        p.setReader(sr);
    }
    @Test
    public void scaleTest() {
        ObjectRegister ob = p.getReg();
        assertEquals(new Dimension2DC(10.0,10.0),ob.getObj("c0").getDimension());
        StringReader sr = new StringReader("scale c0 2.0");
        p.setReader(sr);
        assertEquals(new Dimension2DC(20.0,20.0),ob.getObj("c0").getDimension());
    }
    @Test
    public void errScaleTest() {
        ObjectRegister ob = p.getReg();
        assertEquals(new Dimension2DC(10.0,10.0),ob.getObj("c0").getDimension());
        StringReader sr = new StringReader("c0 -2.0");
        Scale s=new Scale(handler, new LexicalAnalyzer(sr),ob);
        assertThrows(SyntaxException.class,()->{s.interpret();});
        assertEquals(new Dimension2DC(10.0,10.0),ob.getObj("c0").getDimension());
    }
}
