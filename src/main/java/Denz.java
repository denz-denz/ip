import java.util.Scanner;
import java.util.ArrayList;
public class Denz {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String logo = " ____  _____ _   _ _____ \n"
                + "|  _ \\| ____| \\ | |__  / \n"
                + "| | | |  _| |  \\| | / / \n"
                + "| |_| | |___| |\\  |/ /_| \n"
                + "|____/|_____|_| \\_|____|\n";
        String newLine = "--------------------------------------------------";
        ArrayList<String> userInputs = new ArrayList<>();
        System.out.println("Hello from\n" + logo);
        System.out.println(newLine);
        System.out.println("Hello, I'm Denz");
        System.out.println("What can I do for you?");
        System.out.println(newLine);
        while (sc.hasNextLine()){
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("bye")){
                System.out.println(newLine);
                System.out.println("    Finally, time to take a break!");
                System.out.println(newLine);
                System.exit(0);
            }
            else if (line.equalsIgnoreCase("list")){
                for (int i = 0; i < userInputs.size(); i ++){
                    System.out.println(i + ": " + userInputs.get(i));
                }
            }
            else if (line.equals("")){
                continue;
            }
            else{
                System.out.println("added: " + newLine);
                System.out.println("    " + line);
                System.out.println(newLine);
                userInputs.add(line);
            }

        }
        sc.close();
    }
}
