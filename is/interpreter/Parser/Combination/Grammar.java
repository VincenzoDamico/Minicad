package is.interpreter.Parser.Combination;

import is.command.HistoryCommandHandler;
import is.shapes.specificcommand.GrammarCmd;
import is.utility.MyConstants;

public class Grammar implements Combination{
    private final HistoryCommandHandler handler;
    public Grammar(HistoryCommandHandler handler){
        this.handler=handler;
    }
    @Override
    public void interpret() {
        handler.handle(new GrammarCmd(MyConstants.GRAMMAR));
    }
}
