package is.test;

import is.command.HistoryCommandHandler;
import is.interpreter.Parser.Combination.*;
import is.interpreter.Parser.Parser;
import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.view.*;
import is.utility.SyntaxException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParserTest {

    static Parser p;
    @BeforeAll
    static void setUpAll() {
        final GraphicObjectPanel gpanel = new GraphicObjectPanel();
        GraphicObjectViewFactory.FACTORY.installView(RectangleObject.class, new RectangleObjectView());
        GraphicObjectViewFactory.FACTORY.installView(CircleObject.class, new CircleObjectView());
        GraphicObjectViewFactory.FACTORY.installView(ImageObject.class, new ImageObjectView());
        HistoryCommandHandler handler = new HistoryCommandHandler();
         p=new Parser( gpanel,handler);
    }


    @Order(1)
    @ParameterizedTest
    @ValueSource(strings = {"new circle (5.0) (12.0,12.0)","new rectangle (12.0,12.0) (12.0,12.0)","new img (\"NyaNYa.gif\") (12.0,12.0)","new circle (5.0) (12.0,12.0)","new circle (5.0) (12.0,12.0)"})
    void p_CrationTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Creation);
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(strings = {"grp c0,r0","grp g0,i0","grp c1,c2"})
    void p_GrpTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Group);
    }
    @Order(3)
    @ParameterizedTest
    @ValueSource(strings = {"ls c0","ls circle","ls all","ls groups"})
    void p_LsTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Ls);
    }
    @Order(4)
    @ParameterizedTest
    @ValueSource(strings = {"mv c0 (23.0,23.0)","mv g0 (23.0,23.0)"})
    void p_MvTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Move);
    }
    @Order(5)
    @ParameterizedTest
    @ValueSource(strings = {"mvoff c1 (23.0,23.0)","mvoff g1 (23.0,23.0)"})
    void p_MvOffTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Moveoff);
    }
    @Order(6)
    @ParameterizedTest
    @ValueSource(strings = {"area c0 ","area all","area circle"})
    void p_AreaTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Area);
    }
    @Order(7)
    @ParameterizedTest
    @ValueSource(strings ={"perimeter c0 ","perimeter all","perimeter circle"})
    void p_PeriTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Perimeter);
    }


    @Order(8)
    @ParameterizedTest
    @ValueSource(strings = {"ungrp g2"})
    void p_UngrpTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Ungroup);
    }



    @Order(9)
    @ParameterizedTest
    @ValueSource(strings = {"del g1"})
    void p_DelTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Delete);
    }
    @Order(10)
    @ParameterizedTest
    @ValueSource(strings = {"scale c1 2.0"})
    void p_ScaleTest(String comb){
        StringReader sr = new StringReader(comb);
        p.setReader(sr);
        assertTrue(p.getCob() instanceof Scale);
    }
    @Order(11)
    @ParameterizedTest
    @ValueSource(strings = {"scnhd", "scale c1 2.0 swsw" ,"mv c1 (12.0,12.0) dkkd","perimeter c1 sjs ","area c1 sjs ","mvoff c1 (12.0,12.0) dkkd","del c1 ffff","new rectangle (12.0,12.0) (12.0,12.0) ccvc, grp r0,r1 cdnj"})
    void p_SyntaxErrorTest(String comb){
        StringReader sr = new StringReader(comb);
        assertThrows(SyntaxException.class,() ->{
                p.setReader(sr);
        });
    }

}
