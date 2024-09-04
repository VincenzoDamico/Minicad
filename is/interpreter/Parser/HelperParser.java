package is.interpreter.Parser;

import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.GraphicObject;
import is.shapes.register.ObjectRegister;
import is.utility.SyntaxException;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class HelperParser {
    public static void atteso(Symbol symbol, Symbol sAsp) {
        if (symbol != sAsp) {
            String msg = "Trovato " + symbol + " mentre si attendeva " + sAsp+".";
            throw new SyntaxException(msg);
        }
    }
    public static GraphicObject getObj( LexicalAnalyzer lexer, ObjectRegister reg) {
        Symbol symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.WORD);
        GraphicObject go= reg.getObj(lexer.getString());
        return go;
    }

    public static List<String> getListID( LexicalAnalyzer lexer, ObjectRegister reg) {
        List<String> ret=new LinkedList<>();
        Symbol symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.WORD);
        while ( symbol == Symbol.WORD ) {
            String go=lexer.getString();
            if(reg.contains(go)) {
                ret.add(go);
            }else{
                throw new SyntaxException("Attenzione! Id: "+go+", non esiste.");
            }
            symbol = lexer.nextSymbol();
            if ( symbol != Symbol.COMA )
                break;
            symbol = lexer.nextSymbol();
            atteso(symbol,Symbol.WORD);
        }
        return ret;
    }
    public static String getPath(LexicalAnalyzer lexer) {
        Symbol symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.LEFT_PARENTHESIS);
        symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.QUOTED_STRING);
        String ret=getQuotedString(lexer);
        symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.QUOTED_STRING);
        symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.RIGHT_PARENTHESIS);
        return ret;
    }
    public static String getQuotedString(LexicalAnalyzer lexer){
        StringBuilder ris=new StringBuilder();
        Symbol symbol = lexer.nextSymbol();
        while(symbol == Symbol.WORD ) {
            atteso(symbol,Symbol.WORD);
            ris.append(lexer.getString());
            symbol = lexer.nextSymbol();
            if ( symbol == Symbol.POINT ) {
                symbol = lexer.nextSymbol();
                atteso(symbol,Symbol.WORD);
                ris.append("."+lexer.getString());
                break;
            }else {
                if (symbol == Symbol.COLON ){
                    ris.append(":");
                    symbol = lexer.nextSymbol();
                }
            }
            atteso(symbol,Symbol.SLASH);
            ris.append("\\");
            symbol = lexer.nextSymbol();
        }
        return ris.toString();
    }

    public  static Point2D getDupla(LexicalAnalyzer lexer) {
        Symbol symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.LEFT_PARENTHESIS);
        double puntoA =  parseDouble(lexer);
        symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.COMA);
        double puntoB = parseDouble( lexer);
        symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.RIGHT_PARENTHESIS);
        return new Point2D.Double(puntoA, puntoB);
    }
    public static double getRadius( LexicalAnalyzer lexer) {
        Symbol symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.LEFT_PARENTHESIS);
        double ret = parseDouble( lexer);
        symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.RIGHT_PARENTHESIS);
        return ret;
    }
    public static  double parseDouble(LexicalAnalyzer lexer) {
        Symbol symbol = lexer.nextSymbol();
        StringBuilder ris = new StringBuilder();
        if(symbol == Symbol.MINUS){
            ris.append("-");
            symbol = lexer.nextSymbol();
        }
        atteso(symbol,Symbol.WORD);
        ris.append(lexer.getString());
        symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.POINT);
        symbol = lexer.nextSymbol();
        atteso(symbol,Symbol.WORD);
        ris.append("."+lexer.getString());
        return Double.parseDouble(ris.toString());

    }
}


