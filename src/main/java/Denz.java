import java.util.Scanner;
public class Denz {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String logo = " ____  _____ _   _ _____ \n"
                + "|  _ \\| ____| \\ | |__  / \n"
                + "| | | |  _| |  \\| | / / \n"
                + "| |_| | |___| |\\  |/ /_| \n"
                + "|____/|_____|_| \\_|____|\n";
        String newLine = "--------------------------------------------------";
        System.out.println("Hello from\n" + logo);
        System.out.println(newLine);
        System.out.println("Hello, I'm Denz");
        System.out.println("What can I do for you?");
        System.out.println(newLine);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            if (line.equals("bye")){
                System.out.println(newLine);
                System.out.println("    Finally, time to take a break!");
                System.out.println(newLine);
                System.exit(0);
            }
            System.out.println(newLine);
            System.out.println("    " + line);
            System.out.println(newLine);
        }
        sc.close();
    }
}
