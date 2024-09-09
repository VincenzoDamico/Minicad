package is.test;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Parser.Combination.Creation;
import is.interpreter.Parser.Parser;
import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.controller.ObjectRegister;
import is.shapes.view.*;
import is.utility.SyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.geom.Point2D;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class NewTest {
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
    }

    @Test
    public void creationTest(){
        StringReader sr = new StringReader("new circle (5.0) (12.0,12.0)");
        p.setReader(sr);
        ObjectRegister ob =p.getReg();
        assertTrue(p.getCob() instanceof Creation);
        Creation c=(Creation) p.getCob();
        assertEquals( "c0",c.getGraphicObject().getId());
        assertEquals( "Circle",c.getGraphicObject().getType());
        assertEquals( new Point2D.Double(12.0,12.0),c.getGraphicObject().getPosition());
        assertEquals( 5.0*5.0*Math.PI,c.getGraphicObject().getArea());
        assertEquals( 5.0*2*Math.PI,c.getGraphicObject().getPerimeter());
        assertTrue(ob.contains(c.getGraphicObject().getId()));
    }


    @ParameterizedTest
    @ValueSource(strings = {"circle (-5.0) (12.0,12.0)","circle (5.0) (-12.0,12.0)","circle (5.0) (12.0,-12.0)","Circle (5.0) (12.0,12.0)","img (5.0) (12.0,12.0)","circle 5.0) (12.0,12.0","img (5.0) (12.0,12.0)","circle (5.0) (12.0,12.0/"})
    public void errNewCircleTest(String input){
        StringReader sr = new StringReader(input);
        ObjectRegister ob =p.getReg();
        Creation c= new Creation(gpanel,handler,new LexicalAnalyzer(sr),ob);
        assertThrows(SyntaxException.class,()->{
            c.interpret();
        });
        assertFalse(ob.contains("c0"));
    }
    @ParameterizedTest
    @ValueSource(strings = {"img (\"pathParziale.err\") (12.0,12.0)","img (\"NyaNYa.gif\") (-12.0,12.0)","img (\"C:\\Users\\Mariangela\\SoftwareEngineeringWS\\ProvaProgetto\\GraphicObjects\\src\\image\\Imgnonesiste.gie) (-12.0,12.0)"})
    public void errNewImgTest(String input){
        StringReader sr = new StringReader(input);
        ObjectRegister ob =p.getReg();
        Creation c= new Creation(gpanel,handler,new LexicalAnalyzer(sr),ob);
        assertThrows(SyntaxException.class,()->{
            c.interpret();
        });
        assertFalse(ob.contains("i0"));
    }
}
