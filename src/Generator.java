import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Scanner;

public class Generator {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        l("Welcome to LC3 asm subroutine generator.");
        l("Enter the name of subroutine:");
        String name = n();
        l("Enter the number of local variables: ");
        int numLv = nextInt();
        l("Enter the number of arguments: ");
        int numArgs = nextInt();
        String subroutine = buildSubroutine(name, numLv, numArgs);
        l(subroutine);
        copyToClipboard(subroutine);
        l(" \u001b[32mCopied to clipboard.");
    }

    public static void copyToClipboard(String s) {
        StringSelection stringSelection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static String buildSubroutine(String name, int localVars, int args) {
        StringBuilder subroutine = new StringBuilder();
        int spaceLVAndSR = localVars + 5 - 1; // Always save R0 - R4
        subroutine.append(name).append(" ADD\tR6, R6, -4\t; Allocate space rv,ra,fp,lv1\n")
                .append("STR\tR7, R6, 2\t; Save Ret Addr\n")
                .append("STR\tR5, R6, 1\t; Save Old FP\n")
                .append("ADD\tR5, R6, 0\t; Copy SP to FP\n")
                .append("ADD\tR6, R6, -")
                .append(spaceLVAndSR)
                .append("\t; Make room for R0-R4 and local vars\n");
        subroutine.append("; Save R0 - R4\n");
        for (var i = 0; i < 5; i++) {
            subroutine.append("STR\tR").append(i)
                    .append(", R5, ")
                    .append(-(i + localVars))
                    .append("\t; Save R")
                    .append(i)
                    .append("\n");
        }
        subroutine.append("; ============== BEGIN YOUR WORK ==============\n\n")
                .append("; Uncomment the following to access arguments\n");
        for (var i = 0; i < args && i < 5; i++) {
            subroutine.append("; LDR\tR").append(i)
                    .append(", R5, ")
                    .append(4 + i)
                    .append("\t; Load ")
                    .append(i + 1)
                    .append(" argument (from left) into ")
                    .append("R")
                    .append(i)
                    .append("\n");
        }
        subroutine.append("\n; Uncomment the following to use local variables\n");
        for (var i = 0; i < localVars && i < 5; i++) {
            subroutine.append("; STR\tX")
                    .append(", R5, ")
                    .append(i)
                    .append("\t; Store value of X into LV ")
                    .append(i + 1)
                    .append("\n");
        }
        subroutine.append("\n; Uncomment the following to set return value\n")
                .append("; STR\tX, R5, 3\t; Set return value to memory addr specified by X\n");
        subroutine.append("; ============== END YOUR WORK ==============\n");
        subroutine.append("; Restore R0 - R4\n");
        for (var i = 0; i < 5; i++) {
            subroutine.append("LDR\tR").append(i)
                    .append(", R5, ")
                    .append(-(i + localVars))
                    .append("\t; Restore R")
                    .append(i)
                    .append("\n");
        }
        subroutine.append("ADD\tR6, R5, 0\t; Pop local vars & saved regs\n")
                .append("LDR\tR7, R5, 2\t; R7 = RA\n")
                .append("LDR\tR5, R5, 1\t; FP = OldFP\n")
                .append("ADD\tR6, R6, 3\t; Pop 3 wds, R6 now rests on RV\n")
                .append("RET");

        return subroutine.toString();
    }

    public static int nextInt() {
        return Integer.parseInt(n());
    }

    public static String n() {
        return scan.nextLine();
    }

    public static void l(Object o) {
        System.out.println(o);
    }
}
