package brown.adamn;

/**
 * Includes several constants and methods used for ANSI text formatting. They serve no purpose but as a visual aid.
 */
public class AnsiColors {
    public static final String RST = "\u001B[0m";

    public static final String INP = "\u001B[33m";
    public static final String ERROR = "\u001B[91m";

    public static final String GUARD1 = "\u001B[41m";
    public static final String GUARD2 = "\u001B[44m";

    public static final String NUMBER = "\u001B[92m";

    public static final String UNDERL = "\u001B[4m";

    public static String format (String prefix, String str) {
        return prefix+str+RST;
    }
    public static String err (String str) {
        return format(ERROR, str);
    }
    public static String inp (String str) {
        return format(INP, str);
    }
    public static String ul (String str){
        return format (UNDERL, str);
    }
}
