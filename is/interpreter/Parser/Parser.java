package is.interpreter.Parser;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Parser.Combination.*;
import is.interpreter.Symbol;
import is.shapes.model.*;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.*;
import is.utility.MyConstants;
import is.utility.SyntaxException;
import is.shapes.view.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static is.interpreter.Parser.HelperParser.atteso;


public class Parser {
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private Symbol symbol;
    private final HistoryCommandHandler handler;
    private final GraphicObjectPanel gpanel;

    public Parser(GraphicObjectPanel gpanel, Reader in, HistoryCommandHandler handler) {
        this.handler = handler;
        this.gpanel = gpanel;
        lexer = new LexicalAnalyzer(in);
        reg = new ObjectRegister();
    }

    public void setReader(Reader sr) {
        try {
            lexer = new LexicalAnalyzer(sr);
            Combination().interpret();
            symbol = lexer.nextSymbol();
            atteso(symbol, Symbol.EOF);
        } catch (SyntaxException e) {
            throw new SyntaxException(e.toString());
        }
    }

    private Combination Combination() {
        symbol = lexer.nextSymbol();
        if (symbol == Symbol.NEW) {
            return new Creation(gpanel, handler, symbol, lexer, reg);

        }
        if (symbol == Symbol.DEL) {
            return new Delete(gpanel, handler, symbol, lexer, reg);
        }
        if (symbol == Symbol.MV) {
            return new Move(handler, symbol, lexer, reg);
        }
        if (symbol == Symbol.MVOFF) {
            return new Moveoff(handler, symbol, lexer, reg);
        }
        if (symbol == Symbol.SCALE) {
            return new Scale(handler, symbol, lexer, reg);
        }
        if (symbol == Symbol.GRP) {
            return new Group(handler, symbol, lexer, reg);
        }
        if (symbol == Symbol.UNGRP) {
            return new Ungroup(handler, symbol, lexer, reg);
        }
        if (symbol == Symbol.AREA) {
            return new Area(handler, symbol, lexer, reg);
        }
        if (symbol == Symbol.PERIMETER) {
            return new Perimeter(handler, symbol, lexer, reg);
        }
        if (symbol == Symbol.LS) {
            return new Ls(handler, symbol, lexer, reg);
        }
        if (symbol == Symbol.GRAMMAR) {
            return new Grammar(handler);
        }
        throw new SyntaxException(MyConstants.ERR_NEG_GEN);
    }
}
/*
    private void grammar() {
        handler.handle(new PrintCmd(MyConstants.GRAMMAR));
    }

    private void Ls() {
        try {
            symbol = lexer.nextSymbol();
            if (symbol == Symbol.WORD) {
                GraphicObject go = reg.getObj(lexer.getString());
                if (go != null) {
                    handler.handle(new LsCmd(go.toString()));
                    return;
                }
            }
            if (symbol == Symbol.CIRCLE || symbol == Symbol.RECTANGLE || symbol == Symbol.GROUPS || symbol == Symbol.IMG) {
                handler.handle(new LsCmd(lexer.getString().charAt(0) + "\\d+", reg));
                return;
            }
            if (symbol == Symbol.ALL) {
                handler.handle(new LsCmd("all", reg));
                return;
            }
        }catch(SyntaxException s){
            throw new SyntaxException(s+" "+MyConstants.ERR_NEG_LS);
        }
        throw new SyntaxException(MyConstants.ERR_NEG_LS);
    }


    private void perimeter() {
        try {
            symbol = lexer.nextSymbol();
            if (symbol == Symbol.WORD) {
                GraphicObject go = reg.getObj(lexer.getString());
                if (go != null) {
                    handler.handle(new PerimCmd(go.getPerimeter()));
                    return;
                }
            }
            if (symbol == Symbol.CIRCLE || symbol == Symbol.RECTANGLE || symbol == Symbol.GROUPS || symbol == Symbol.IMG) {
                handler.handle(new PerimCmd(lexer.getString().charAt(0) + "\\d+", reg));
                return;
            }
            if (symbol == Symbol.ALL) {
                handler.handle(new PerimCmd("all", reg));
                return;
            }
        } catch (SyntaxException s) {
            throw new SyntaxException(s + " "+MyConstants.ERR_NEG_PERIM);
        }
        throw new SyntaxException(MyConstants.ERR_NEG_PERIM);
    }

    private void area() {
        try {
            symbol = lexer.nextSymbol();
            if (symbol == Symbol.WORD) {
                GraphicObject go = reg.getObj(lexer.getString());
                if (go != null) {
                    handler.handle(new AreaCmd(go.getArea()));
                    return;
                }
            }
            if (symbol == Symbol.CIRCLE || symbol == Symbol.RECTANGLE || symbol == Symbol.GROUPS || symbol == Symbol.IMG) {
                handler.handle(new AreaCmd(lexer.getString().charAt(0) + "\\d+", reg));
                return;
            }
            if (symbol == Symbol.ALL) {
                handler.handle(new AreaCmd("all", reg));
                return;
            }
        } catch (SyntaxException s) {
            throw new SyntaxException(s + " "+ MyConstants.ERR_NEG_AREA);
        }
        throw new SyntaxException( MyConstants.ERR_NEG_AREA);
    }

    private void group() {
        try {
            List<String> listId=getListID();
            GroupObject go =new GroupObject(reg,listId);
            handler.handle(new GroupCmd(reg,go));
        }catch (SyntaxException s){
            throw new SyntaxException(s+" "+MyConstants.ERR_NEG_GRP);
        }

    }

    private void ungroup() {
        try {
            symbol = lexer.nextSymbol();
            atteso(Symbol.WORD);
            //controllo con la regex se è un ho l'id di un un gruppo
            String id=lexer.getString();
            if (id.matches("g\\d+")) {
                GroupObject go = (GroupObject)reg.getObj(id);
                        handler.handle(new UngroupCmd(reg,go));
                return;
            }
        }catch (SyntaxException s){
            throw new SyntaxException(s+" "+MyConstants.ERR_NEG_UNGRP);
        }
        throw new  SyntaxException(MyConstants.ERR_NEG_UNGRP);
    }


    private void  scale() {
        try {
            GraphicObject go = getObj();
            double ret = parseDouble();
            if (ret < 0) {
                throw new SyntaxException(MyConstants.ERR_NEG_VAL);
            }
            handler.handle(new ZoomCommand(go, ret));
        } catch (SyntaxException s) {
            throw new SyntaxException(s + " "+MyConstants.ERR_NEG_SCALE);
        }
    }

    private void moveOff() {
        try {
            GraphicObject go = getObj();
            if (go != null) {
                Point2D position = getDupla();
                double newX = position.getX() + go.getPosition().getX();
                double newY = position.getY() + go.getPosition().getY();
                position.setLocation(newX, newY);
                if (position.getX() < 0 || position.getY() < 0) {
                    throw new SyntaxException(MyConstants.ERR_NEG_VAL);
                }
                handler.handle(new MoveCommand(go, position));
            }
        } catch (SyntaxException s) {
            throw new SyntaxException(s + " "+MyConstants.ERR_NEG_MVOFF);
        }
    }

    private void move() {
        try {
            GraphicObject go = getObj();
            if (go != null) {
                Point2D position = getDupla();
                if (position.getX() < 0 || position.getY() < 0) {
                    throw new SyntaxException(MyConstants.ERR_NEG_VAL);
                }
                handler.handle(new MoveCommand(go, position));
            }
        } catch (SyntaxException s) {
            throw new SyntaxException(s + " "+MyConstants.ERR_NEG_MV);
        }
    }

    private void delete() {
        try {
            GraphicObject go = getObj();
            if (go != null) {
                handler.handle(new DelObjectCmd(gpanel, go, reg));
                return;
                //    deleteEl(go);
            }
        } catch (SyntaxException s) {
            throw new SyntaxException(s+" "+MyConstants.ERR_NEG_DEL);
        }
        throw new SyntaxException(MyConstants.ERR_NEG_DEL);
    }

    private void creation() {
        try {
            AbstractGraphicObject go = null;
            symbol = lexer.nextSymbol();
            if (symbol == Symbol.CIRCLE) {
                double radius = getRadius();
                Point2D position = getDupla();
                //controllo se non è negativo
                if (position.getX() < 0 || position.getY() < 0 || radius < 0) {
                    throw new SyntaxException(MyConstants.ERR_NEG_VAL);
                }
                go = new CircleObject(position, radius);
            } else if (symbol == Symbol.RECTANGLE) {
                Point2D dimension = getDupla();
                Point2D position = getDupla();
                if (position.getX() < 0 || position.getY() < 0 || dimension.getX() < 0 || dimension.getY() < 0) {
                    throw new SyntaxException(MyConstants.ERR_NEG_VAL);
                }
                go = new RectangleObject(position, dimension.getX(), dimension.getY());
            } else if (symbol == Symbol.IMG) {
                String path = getPath();
                Point2D position = getDupla();
                if (position.getX() < 0 || position.getY() < 0) {
                    throw new SyntaxException(MyConstants.ERR_NEG_VAL);
                }

                // vedo se un path totale o parziale
                if (!path.substring(0, 1).equals("C")) {
                    path = MyConstants.GEN_PATH+ path;
                }
                //verifico se il path esite
                if (Files.exists(Paths.get(path))) {
                    go = new ImageObject(new ImageIcon(path), position);
                } else {
                    throw new SyntaxException(MyConstants.ERR_PATH);
                }

            } else {
                throw new SyntaxException("");
            }

            CreateObjectAction action = new CreateObjectAction(go, gpanel, handler, reg);
            ActionEvent event = new ActionEvent(action, ActionEvent.ACTION_PERFORMED, "command");
            action.actionPerformed(event);
        }catch (SyntaxException s){
            throw new SyntaxException(s+" "+MyConstants.ERR_NEG_NEW);

        }
    }

    //Metodi di utilita per il parser

    private void atteso(Symbol s) {
        if (symbol != s) {
            String msg = "Trovato " + symbol + " mentre si attendeva " + s+".";
            throw new SyntaxException(msg);
        }
    }
    private GraphicObject getObj() {
        symbol = lexer.nextSymbol();
        atteso(Symbol.WORD);
        GraphicObject go= reg.getObj(lexer.getString());
        return go;
    }

    private List<String> getListID() {
        List<String> ret=new LinkedList<>();
        symbol = lexer.nextSymbol();
        atteso(Symbol.WORD);
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
            atteso(Symbol.WORD);
        }
        return ret;
    }
    private String getPath() {
        symbol = lexer.nextSymbol();
        atteso(Symbol.LEFT_PARENTHESIS);
        symbol = lexer.nextSymbol();
        atteso(Symbol.QUOTED_STRING);
        String ret=getQuotedString();
        symbol = lexer.nextSymbol();
        atteso(Symbol.QUOTED_STRING);
        symbol = lexer.nextSymbol();
        atteso(Symbol.RIGHT_PARENTHESIS);
        return ret;
    }
    private String getQuotedString(){
        StringBuilder ris=new StringBuilder();
        symbol = lexer.nextSymbol();
        while(symbol == Symbol.WORD ) {
            atteso(Symbol.WORD);
            ris.append(lexer.getString());
            symbol = lexer.nextSymbol();
            if ( symbol == Symbol.POINT ) {
                symbol = lexer.nextSymbol();
                atteso(Symbol.WORD);
                ris.append("."+lexer.getString());
                break;
            }else {
                if (symbol == Symbol.COLON ){
                    ris.append(":");
                    symbol = lexer.nextSymbol();
                }
            }
            atteso(Symbol.SLASH);
            ris.append("\\");
            symbol = lexer.nextSymbol();
        }
        return ris.toString();
    }

    private Point2D getDupla() {
        symbol = lexer.nextSymbol();
        atteso(Symbol.LEFT_PARENTHESIS);
        double puntoA =  parseDouble();
        symbol = lexer.nextSymbol();
        atteso(Symbol.COMA);
        double puntoB = parseDouble();
        symbol = lexer.nextSymbol();
        atteso(Symbol.RIGHT_PARENTHESIS);
        return new Point2D.Double(puntoA, puntoB);
    }
    private double getRadius() {
        symbol = lexer.nextSymbol();
        atteso(Symbol.LEFT_PARENTHESIS);
        double ret = parseDouble();
        symbol = lexer.nextSymbol();
        atteso(Symbol.RIGHT_PARENTHESIS);
        return ret;
    }
    private double parseDouble() {
        symbol = lexer.nextSymbol();
        StringBuilder ris = new StringBuilder();
        if(symbol == Symbol.MINUS){
            ris.append("-");
            symbol = lexer.nextSymbol();
        }
        atteso(Symbol.WORD);
        ris.append(lexer.getString());
        symbol = lexer.nextSymbol();
        atteso(Symbol.POINT);
        symbol = lexer.nextSymbol();
        atteso(Symbol.WORD);
        ris.append("."+lexer.getString());
        return Double.parseDouble(ris.toString());

    }
*/
