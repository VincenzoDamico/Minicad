package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.interpreter.LexicalAnalyzer;
import is.interpreter.Symbol;
import is.shapes.model.GroupObject;
import is.shapes.register.ObjectRegister;
import is.shapes.specificcommand.UngroupCmd;
import is.utility.MyConstants;
import is.utility.SyntaxException;

import static is.interpreter.Parser.HelperParser.atteso;

public class Ungroup implements Combination{
    private ObjectRegister reg;
    private LexicalAnalyzer lexer;
    private final HistoryCommandHandler handler;
    public Ungroup( HistoryCommandHandler handler, LexicalAnalyzer lexer, ObjectRegister reg){
        this.handler=handler;
        this.lexer=lexer;
        this.reg=reg;
    }
    @Override
    public void interpret() {
        try {
            Symbol symbol = lexer.nextSymbol();
            atteso(symbol,Symbol.WORD);
            //controllo con la regex se è un ho l'id di un un gruppo
            String id=lexer.getString();
            if (id.matches("g\\d+")) {
                GroupObject go = (GroupObject)reg.getObj(id);
                handler.handle(new UngroupCmd(reg,go));
                return;
            }
        }catch (SyntaxException s){
            throw new SyntaxException(s+" "+ MyConstants.ERR_NEG_UNGRP);
        }
        throw new  SyntaxException(MyConstants.ERR_NEG_UNGRP);
    }
}
