package is.test;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Parser.Combination.Ls;
import is.interpreter.Parser.Parser;
import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.LsCmd;
import is.shapes.view.*;
import is.utility.SyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LsTest {
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
    public void LsTest(){
        ObjectRegister ob=p.getReg();
        LsCmd c=new LsCmd(ob.getObj("r0"));
        assertEquals( "Id: r0 Type: Rectangle Position:(12,00, 12,00)  Dimension:(12,40, 12,50)\n",c.getLs());
        StringReader sr = new StringReader("scale r0 2.0");
        p.setReader(sr);
        c=new LsCmd(ob.getObj("r0"));
        assertEquals( "Id: r0 Type: Rectangle Position:(12,00, 12,00)  Dimension:(24,80, 25,00)\n",c.getLs());
        c=new LsCmd("g\\d+",ob);
        assertEquals( "Id: g0 Type: Group {\n" +
                "\tId: c0 Type: Circle Position:(12,00, 12,00)  Dimension:(10,00, 10,00) Radius:(5,00)\n" +
                "\tId: c1 Type: Circle Position:(12,00, 12,00)  Dimension:(10,00, 10,00) Radius:(5,00)\n" +
                "}\n",c.getLs());
        c=new LsCmd("c\\d+",ob);
        assertEquals("Id: c0 Type: Circle Position:(12,00, 12,00)  Dimension:(10,00, 10,00) Radius:(5,00)\n" +
                "Id: c1 Type: Circle Position:(12,00, 12,00)  Dimension:(10,00, 10,00) Radius:(5,00)\n",c.getLs());

    }
    @ParameterizedTest  //word a caso id che non esiste
    @ValueSource(strings = {"allll","i0"})
    public void areaTestErr(String input){
        Ls l=new Ls(handler, new LexicalAnalyzer(new StringReader(input)),p.getReg());
        assertThrows(SyntaxException.class, ()->l.interpret());
    }
}
