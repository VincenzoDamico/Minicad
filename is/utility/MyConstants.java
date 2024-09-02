package is.utility;

public class MyConstants {
    public static final String TITLE="Progetto Ingegneria del software";
    public static final String OPEN="Per conoscere i dettagli della grammatica  scrivi il comando: grammar. Per chiudere il programma scrivi: Exit";
    public static final String GRAMMAR="<cmd>::=<create>|<remove>|<move>|<scale>|<list>|<group>|<ungroup>|<area>|<perimeter>\n" +
            "<create>::= new <typeconstr> <pos>\n" +
            "<remove>::= del <objID>\n" +
            "<move>::= mv <objID> <pos> | mvoff <objID> <pos> \n" +
            "<scale>::= scale <objID> <posfloat>\n" +
            "<list>::= ls <objID>| ls <type> | ls all | ls groups\n" +
            "<group>::= grp <listID>\n" +
            "<ungroup>::= ungrp <objID>\n" +
            "<area>::= area <objID>| area <type> | area all\n" +
            "<perimeter>::= perimeter <objID>| perimeter <type> | perimeter all\n" +
            "<pos>::=( <posfloat> , <posfloat> )\n" +
            "<typeconstr>::= circle (<posfloat>) | rectangle <pos> | img (<path>)\n" +
            "<type>::= circle | rectangle | img\n" +
            "<listID>::= <objID> { , <objID> }\n" +
            "Il path dell'immagine deve essere assoluto a meno che l'immagine non si trovi nella cartella col il seguende path:\n"+
            MyConstants.GEN_PATH+"\n";
    public static final String ERR_POS_GRP="Posizione finale del gruppo non valida.";
    public static final String ERR_DEL_GRP="Il gruppo è risultato vuoto quindi è stato automaticamente eliminato";

    public static final String ERR_NEG_VAL="Non si accettano val negativi.";
    public static final String ERR_NEG_GEN="Comando inesistente.";
    public static final String ERR_NEG_LS="La sintassi del comando è errata. La corretta è ls <objID> o ls <type> o ls all o ls groups";
    public static final String ERR_NEG_PERIM="La sintassi del comando è errata. La corretta è perimeter <objID>| perimeter <type> | perimeter all";
    public static final String ERR_NEG_AREA="La sintassi del comando è errata. La corretta è area <objID>| area <type> | area all";
    public static final String ERR_NEG_GRP="La sintassi del comando è errata. La corretta è grp <listID>";
    public static final String ERR_NEG_UNGRP="Comando scoretto Ti ricordo la sitassi coretta è ungrp <objID>";
    public static final String ERR_NEG_SCALE="Comando scoretto Ti ricordo la sitassi coretta è scale <objID> <posfloat>";
    public static final String ERR_NEG_MV="Comando scoretto Ti ricordo la sitassi coretta è mv <objID> <pos>";
    public static final String ERR_NEG_MVOFF="Comando scoretto Ti ricordo la sitassi coretta è  mvoff <objID> <pos>";
    public static final String ERR_NEG_DEL="Comando scoretto Ti ricordo la sitassi coretta è del <objID>";
    public static final String GEN_PATH="C:\\Users\\Mariangela\\SoftwareEngineeringWS\\ProvaProgetto\\GraphicObjects\\src\\image\\";
    public static final String ERR_PATH="L'indirizzo dell'immagine è errata.";
    public static final String ERR_NEG_NEW="Comando scoretto Ti ricordo la sitassi coretta è <create>::= new <typeconstr> <pos>, <typeconstr> sono circle (<posfloat>) | rectangle <pos> | img (<path>)";
    public static final String NO_UNDO="Non è possibile effettuare l'undo.";
    private MyConstants() {
    }
}
