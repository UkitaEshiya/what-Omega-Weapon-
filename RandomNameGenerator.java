import java.io.*;
import java.util.*;

public class RandomNameGenerator {
    public static void main(String[] args) {
        // Prompt the user to enter the CSV file name
        Scanner scanner = new Scanner(System.in);
        System.out.print("请导入武器列表: ");
        String fileName = scanner.nextLine();

        // Read the CSV file
        ArrayList<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                names.add(parts[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Shuffle the list of names
        Collections.shuffle(names);

        // Choose a random name and provide the redraw and remove options
        Random rand = new Random();
        boolean redraw = true;
        while (redraw && !names.isEmpty()) {
            int index = rand.nextInt(names.size());
            String name = names.get(index);
            System.out.println("这周绝O武器换" + name);
            System.out.print("就换这把？（换/不换）");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("换")) {
                names.remove(index);
                try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
                    for (String n : names) {
                        pw.println(n);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                System.out.println("已兑换" + name + "武器！");
                return; // Exit the program if the user exchanges for the weapon
            }
            System.out.print("再抽一发？（Y/N): ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("N")) {
                redraw = false;
                System.out.println("下周再想换什么！");
            }
        }
    }
}
