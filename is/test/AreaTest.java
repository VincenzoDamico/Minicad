package is.test;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Parser.Combination.Area;
import is.interpreter.Parser.Parser;
import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.AreaCmd;
import is.shapes.view.*;
import is.utility.SyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AreaTest {
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
        StringReader sr = new StringReader("new rectangle  (12.4,12.5) (12.0,12.0)");
        p.setReader(sr);
        sr = new StringReader("new circle (5.0) (12.0,12.0)");
        p.setReader(sr);
        sr = new StringReader("new circle (5.0) (12.0,12.0)");
        p.setReader(sr);
        sr = new StringReader("grp c0,c1");
        p.setReader(sr);
    }
    @Test
    public void areaTest(){
        ObjectRegister ob=p.getReg();
        AreaCmd c=new AreaCmd(ob.getObj("r0"));
        assertEquals(12.4*12.5,c.getArea() );
        StringReader sr = new StringReader("scale r0 2.0");
        p.setReader(sr);
        c=new AreaCmd(ob.getObj("r0"));
        assertEquals(12.4*12.5*4,c.getArea() );
        c=new AreaCmd("g\\d+",ob);
        assertEquals(5*5*Math.PI*2,c.getArea() );
        c=new AreaCmd("c\\d+",ob);
        assertEquals(5*5*Math.PI*2, c.getArea());

    }
    @ParameterizedTest  //word a caso id che non esiste
    @ValueSource(strings = {"allll","i0"})
    public void areaTestErr(String input){
        Area a=new Area(handler, new LexicalAnalyzer(new StringReader(input)),p.getReg());
        assertThrows(SyntaxException.class, ()->a.interpret());
    }

}
