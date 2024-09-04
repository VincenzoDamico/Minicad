package is.test;

import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTest {
    private LexicalAnalyzer l;




    @Test
    @DisplayName("Test nextSymbol() type of word")
    public void testSimpleString()  {
        String inputString = "new all groups grp ungrp all area perimeter circle rectangle img del mv mvoff scale ls grammar \" parolagenerale";
        LexicalAnalyzer analyzer = new LexicalAnalyzer(new StringReader(inputString));
        assertEquals(Symbol.NEW, analyzer.nextSymbol());
        assertEquals(Symbol.ALL, analyzer.nextSymbol());
        assertEquals(Symbol.GROUPS, analyzer.nextSymbol());
        assertEquals(Symbol.GRP, analyzer.nextSymbol());
        assertEquals(Symbol.UNGRP, analyzer.nextSymbol());
        assertEquals(Symbol.ALL, analyzer.nextSymbol());
        assertEquals(Symbol.AREA, analyzer.nextSymbol());
        assertEquals(Symbol.PERIMETER, analyzer.nextSymbol());
        assertEquals(Symbol.CIRCLE, analyzer.nextSymbol());
        assertEquals(Symbol.RECTANGLE, analyzer.nextSymbol());
        assertEquals(Symbol.IMG, analyzer.nextSymbol());
        assertEquals(Symbol.DEL, analyzer.nextSymbol());
        assertEquals(Symbol.MV, analyzer.nextSymbol());
        assertEquals(Symbol.MVOFF, analyzer.nextSymbol());
        assertEquals(Symbol.SCALE, analyzer.nextSymbol());
        assertEquals(Symbol.LS, analyzer.nextSymbol());
        assertEquals(Symbol.GRAMMAR, analyzer.nextSymbol());
        assertEquals(Symbol.QUOTED_STRING, analyzer.nextSymbol());
        assertEquals(Symbol.WORD, analyzer.nextSymbol());
        assertEquals(Symbol.EOF, analyzer.nextSymbol());
    }

    @Test
    @DisplayName("Test nextSymbol() ordinary char")
    public void testKeywordsAndSymbols() {
        String inputString = "():.-,\\";
        LexicalAnalyzer analyzer = new LexicalAnalyzer(new StringReader(inputString));

        assertEquals(Symbol.LEFT_PARENTHESIS, analyzer.nextSymbol());
        assertEquals(Symbol.RIGHT_PARENTHESIS, analyzer.nextSymbol());
        assertEquals(Symbol.COLON, analyzer.nextSymbol());
        assertEquals(Symbol.POINT, analyzer.nextSymbol());
        assertEquals(Symbol.MINUS, analyzer.nextSymbol());
        assertEquals(Symbol.COMA, analyzer.nextSymbol());
        assertEquals(Symbol.SLASH, analyzer.nextSymbol());
        assertEquals(Symbol.EOF, analyzer.nextSymbol());
    }

    @Test
    @DisplayName("Test nextSymbol() with invalid character")
    public void testInvalidCharacter()  {
        String inputString = "invalid@character";
        LexicalAnalyzer analyzer = new LexicalAnalyzer(new StringReader(inputString));

        assertEquals(Symbol.WORD, analyzer.nextSymbol());
        assertEquals(Symbol.INVALID_CHAR, analyzer.nextSymbol());
        assertEquals(Symbol.WORD, analyzer.nextSymbol());
        assertEquals(Symbol.EOF, analyzer.nextSymbol());
    }
}
