package is.test;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Parser.Combination.Move;
import is.interpreter.Parser.Combination.Moveoff;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoveTest {
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
        p=new Parser( gpanel,handler);
        StringReader sr = new StringReader("new circle (5.0) (12.0,12.0)");
        p.setReader(sr);
        sr = new StringReader("new circle (5.0) (20.0,24.0)");
        p.setReader(sr);
        sr = new StringReader("grp c0,c1");
        p.setReader(sr);
        sr = new StringReader("new circle (5.0) (40.0,12.0)");
        p.setReader(sr);
    }
    @Test
    public void moveTest(){
        ObjectRegister ob=p.getReg();
        assertEquals( new Point2D.Double(16.0,18.0),ob.getObj("g0").getPosition());
        assertEquals( new Point2D.Double(12.0,12.0),ob.getObj("c0").getPosition());
        StringReader sr  = new StringReader("mv c0 (12.0,24.0)");
        p.setReader(sr);
        assertEquals( new Point2D.Double(12.0,24.0),ob.getObj("c0").getPosition());
        assertEquals( new Point2D.Double(16.0,24.0),ob.getObj("g0").getPosition());
    }
    @Test
    public void moveOffTest(){
        ObjectRegister ob=p.getReg();
        assertEquals( new Point2D.Double(16.0,18.0),ob.getObj("g0").getPosition());
        assertEquals( new Point2D.Double(12.0,12.0),ob.getObj("c0").getPosition());
        StringReader sr  = new StringReader("mvoff c0 (12.0,24.0)");
        p.setReader(sr);
        assertEquals( new Point2D.Double(24.0,36.0),ob.getObj("c0").getPosition());
        assertEquals( new Point2D.Double(22.0,30.0),ob.getObj("g0").getPosition());
    }

    @Test
    public void errMoveTest(){
        //mv non accetta valori negativi
        ObjectRegister ob=p.getReg();
        StringReader sr  = new StringReader("c0 (-12.0,-24.0)");
        Move m = new Move( handler,  new LexicalAnalyzer(sr), ob);
        assertThrows( SyntaxException.class, ()->{
            m.interpret();
        });
        assertEquals( new Point2D.Double(12.0,12.0),ob.getObj("c0").getPosition());
        //con moveoff posso usare val negativi basta che la posizione finale non sia negativa
        sr  = new StringReader("c0 (-10.0,-2.0)");
        Moveoff moff = new Moveoff( handler,  new LexicalAnalyzer(sr), ob);
        moff.interpret();
        assertEquals( new Point2D.Double(2.0,10.0),ob.getObj("c0").getPosition());
        sr  = new StringReader("c0 (-10.0,-2.0)");
        //vado fuori non effetto il movimento
        Moveoff moff2 = new Moveoff( handler,  new LexicalAnalyzer(sr), ob);
        assertThrows(SyntaxException.class, ()->{
            moff2.interpret();
        });

        assertEquals( new Point2D.Double(2.0,10.0),ob.getObj("c0").getPosition());
    }


}
