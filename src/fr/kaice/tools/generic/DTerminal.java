package fr.kaice.tools.generic;

/**
 * Created by merkling on 28/08/16.
 */
public class DTerminal {

    private static final char COLOR_TERM_CHAR = 27;
    public static final String RESET    = COLOR_TERM_CHAR + "[0m";

    public static final String BLACK    = COLOR_TERM_CHAR + "[30m";
    public static final String RED      = COLOR_TERM_CHAR + "[31m";
    public static final String GREEN    = COLOR_TERM_CHAR + "[32m";
    public static final String YELLOW   = COLOR_TERM_CHAR + "[33m";
    public static final String BLUE     = COLOR_TERM_CHAR + "[34m";
    public static final String MAGENTA  = COLOR_TERM_CHAR + "[35m";
    public static final String CYAN     = COLOR_TERM_CHAR + "[37m";
    public static final String WHITE    = COLOR_TERM_CHAR + "[38m";

    public static final String BACK_BLACK   = COLOR_TERM_CHAR + "[40m";
    public static final String BACK_RED     = COLOR_TERM_CHAR + "[41m";
    public static final String BACK_GREEN   = COLOR_TERM_CHAR + "[42m";
    public static final String BACK_YELLOW  = COLOR_TERM_CHAR + "[43m";
    public static final String BACK_BLUE    = COLOR_TERM_CHAR + "[44m";
    public static final String BACK_MAGENTA = COLOR_TERM_CHAR + "[45m";
    public static final String BACK_CYAN    = COLOR_TERM_CHAR + "[47m";
    public static final String BACK_WHITE   = COLOR_TERM_CHAR + "[48m";
}
