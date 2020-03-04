import java.util.Date;

public class TumgadCLI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {
        TumgadCLI cli = new TumgadCLI();
        cli.print(ANSI_PURPLE +
                "  _______ _    _ __  __  _____          _____  \n" +
                " |__   __| |  | |  \\/  |/ ____|   /\\   |  __ \\ \n" +
                "    | |  | |  | | \\  / | |  __   /  \\  | |  | |\n" +
                "    | |  | |  | | |\\/| | | |_ | / /\\ \\ | |  | |\n" +
                "    | |  | |__| | |  | | |__| |/ ____ \\| |__| |\n" +
                "    |_|   \\____/|_|  |_|\\_____/_/    \\_\\_____/" + ANSI_RESET);
    }

    private void say(String toSay) {
        System.out.println(new Date().toString() + ": " + toSay);
    }

    private void error(String errorText) {
        System.err.println(new Date().toString() + ": " + errorText);
    }

    private void print(String text) {
        System.out.println(text);
    }


}
