package is.test;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Parser.Combination.Creation;
import is.interpreter.Parser.Combination.Delete;
import is.interpreter.Parser.Parser;
import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.controller.ObjectRegister;
import is.shapes.view.*;
import is.utility.SyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class DelTest {
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
    public void DelTest() {
        StringReader sr = new StringReader("new circle (5.0) (12.0,12.0)");
        p.setReader(sr);
        ObjectRegister ob = p.getReg();
        assertTrue(p.getCob() instanceof Creation);
        Creation c = (Creation) p.getCob();
        assertEquals( "c0",c.getGraphicObject().getId());
        assertEquals("Circle",c.getGraphicObject().getType() );
        assertEquals( new Point2D.Double(12.0, 12.0),c.getGraphicObject().getPosition());
        assertEquals(5.0 * 5.0 * Math.PI,c.getGraphicObject().getArea() );
        assertEquals(5.0 * 2 * Math.PI,c.getGraphicObject().getPerimeter() );
        assertTrue(ob.contains(c.getGraphicObject().getId()));
        sr = new StringReader("del c0");
        p.setReader(sr);
        assertFalse(ob.contains(c.getGraphicObject().getId()));
    }

    @Test
    public void DelGrpTest() {
        StringReader sr = new StringReader("new circle (5.0) (12.0,12.0)");
        ObjectRegister ob = p.getReg();
        p.setReader(sr);
        sr = new StringReader("new circle (5.0) (12.0,12.0)");
        p.setReader(sr);
        sr = new StringReader("grp c0,c1");
        p.setReader(sr);
        assertTrue(ob.contains("c0"));
        assertTrue(ob.contains("c1"));
        assertTrue(ob.contains("g0"));
        sr = new StringReader("del g0");
        p.setReader(sr);
        assertFalse(ob.contains("c0"));
        assertFalse(ob.contains("c1"));
        assertFalse(ob.contains("g0"));
    }

    @Test
    public void DeLError() {
        StringReader sr = new StringReader("new circle (5.0) (12.0,12.0)");
        ObjectRegister ob = p.getReg();
        p.setReader(sr);
        assertTrue(ob.contains("c0"));
        StringReader del = new StringReader("c1");
        Delete d=new Delete(gpanel,handler,new LexicalAnalyzer(del),ob);
        assertThrows(SyntaxException.class, () -> {
            d.interpret();
        });
        assertTrue(ob.contains("c0"));

    }
}