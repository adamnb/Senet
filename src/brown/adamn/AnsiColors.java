package brown.adamn;

/**
 * Includes several constants and methods used for ANSI text formatting. They serve no purpose but as a visual aid.
 */
public class AnsiColors {
    public static final String RST = "\u001B[0m";

    public static final String INP = "\u001B[33m";
    public static final String ERROR = "\u001B[91m";

    public static final String P1 = "\u001B[35m";
    public static final String P2 = "\u001B[36m";

    public static final String GUARD1 = "\u001B[41m";
    public static final String GUARD2 = "\u001B[44m";

    public static final String NUMBER = "\u001B[92m";

    public static final String UNDERL = "\u001B[4m";

    /**
     * Attaches ANSI escape codes to a string and ends it with a reset escape code
     * @param prefix The escape code to prefix the string.
     * @param str The input string to be modified
     * @return The modified string
     */
    public static String format (String prefix, String str) {
        return prefix+str+RST;
    }
    public static String err (String str) {
        return format(ERROR, str);
    } // error message color
    public static String inp (String str) { // input query color????
        return format(INP, str);
    }
    public static String ul (String str){
        return format (UNDERL, str);
    } // underline
    public static String player (String str, int p) { // player color
        if (p==1)
            return format(P1, str);
        if (p==2)
            return format(P2, str);
        return str;
    }
}
