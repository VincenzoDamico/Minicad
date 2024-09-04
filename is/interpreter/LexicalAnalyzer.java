package is.interpreter;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class LexicalAnalyzer {
        private StreamTokenizer input;
        private Symbol symbol;

        public LexicalAnalyzer(Reader in) {

            input = new StreamTokenizer(in);
            input.resetSyntax(); //resetto la Syntax-table
            input.eolIsSignificant(false);//indica che i caratteri di fine linea non sono significativi
            input.wordChars('a', 'z');
            input.wordChars('A', 'Z');
            input.wordChars('0', '9');
            input.whitespaceChars('\u0000', ' ');//caratteri di spazio
            input.ordinaryChar('(');
            input.ordinaryChar(')');
            input.ordinaryChar(',');
            input.ordinaryChar('\\'); //( ) , \\ sono caratteri ordinari, non fanno parte delle parole
            input.ordinaryChar('"');
            input.ordinaryChar('.');
            input.ordinaryChar(':');

        }

        public String getString() {
            return input.sval;
        }

        public Symbol nextSymbol() {//legge il prossimo token e lo mappa su un valore dell'enum SIMBOLI
            try {
                switch( input.nextToken() ) {
                    case StreamTokenizer.TT_EOF:// A constant indicating the end of the stream
                        symbol = Symbol.EOF;
                        break;
                    case StreamTokenizer.TT_WORD:
                        if( input.sval.equals("new") )
                            symbol = Symbol.NEW;
                        else if( input.sval.equals("all") )
                            symbol = Symbol.ALL;
                        else if( input.sval.equals("groups") )
                            symbol = Symbol.GROUPS;
                        else if( input.sval.equals("grp") )
                            symbol = Symbol.GRP;
                        else if( input.sval.equals("ungrp") )
                            symbol = Symbol.UNGRP;
                        else if( input.sval.equals("area") )
                            symbol = Symbol.AREA;
                        else if( input.sval.equals("perimeter") )
                            symbol = Symbol.PERIMETER;
                        else if( input.sval.equals("circle") )
                            symbol = Symbol.CIRCLE;
                        else if( input.sval.equals("rectangle") )
                            symbol = Symbol.RECTANGLE;
                        else if( input.sval.equals("img") )
                            symbol = Symbol.IMG;
                        else if( input.sval.equals("del") )
                            symbol = Symbol.DEL;
                        else if( input.sval.equals("mv") )
                            symbol = Symbol.MV;
                        else if( input.sval.equals("mvoff") )
                            symbol = Symbol.MVOFF;
                        else if( input.sval.equals("scale") )
                            symbol = Symbol.SCALE;
                        else if( input.sval.equals("ls") )
                            symbol = Symbol.LS;
                        else if( input.sval.equals("grammar") )
                            symbol = Symbol.GRAMMAR;
                        else if( input.sval.equals("\"") )
                            symbol = Symbol.QUOTED_STRING;
                        else
                            // parola normale - nome o numero
                            symbol = Symbol.WORD;
                        break;

                    case '"':
                        symbol = Symbol.QUOTED_STRING;
                        break;
                    case ')':
                        symbol = Symbol.RIGHT_PARENTHESIS;
                        break;
                    case '(':
                        symbol = Symbol.LEFT_PARENTHESIS;
                        break;
                    case '-':
                        symbol = Symbol.MINUS;
                        break;
                    case ':':
                        symbol = Symbol.COLON;
                        break;
                    case '.':
                        symbol = Symbol.POINT;
                        break;
                    case ',':
                        symbol = Symbol.COMA;
                        break;
                    case '\\':
                        symbol = Symbol.SLASH;
                        break;
                    default:
                        symbol = Symbol.INVALID_CHAR;
                }
            } catch( IOException e ) {
                symbol = Symbol.EOF;
            }
            return symbol;
        }
}

