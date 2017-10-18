package sprinttva;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    private ArrayList<Person> persons = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    Path outfilePath = Paths.get("C:\\Users\\Andreas\\Repositories\\Gymmet\\src\\sprinttva\\VaritOchTränat.txt");

    public static void main(String[] args) throws IOException {
        Main program = new Main();
        program.run();
    }

    private void init() {
        ArrayList<String> formattedData = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get("C:\\Users\\Andreas\\Repositories\\Gymmet\\src\\sprinttva\\Gymkunder.txt"))) {
            final String[] firstLine = new String[1];
            final String[] secondLine = new String[1];
            final int[] counter = {0};

            stream.forEach(line -> {
                if (counter[0] == 0) {
                    firstLine[0] = line;
                    counter[0]++;
                } else if (counter[0] == 1) {
                    secondLine[0] = line;
                    formattedData.add(firstLine[0] + ", " + secondLine[0]);
                    counter[0] = 0;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        formattedData.forEach(data -> {
            String[] info = data.trim().split(",");

            Person person = new Person();
            person.setPersonNumber(info[0]);
            person.setName(info[1].trim());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ITALY);
            person.setLastPayed(LocalDate.parse(info[2].trim(), formatter));

            persons.add(person);
        });
    }

    private void run() throws IOException {
        init();
        search();
    }

    private void search() throws IOException {

        try  {

            LocalDate today = LocalDate.now();
            System.out.println("1. Sök på person");
            System.out.println("2. Avsluta");
            int input = sc.nextInt();
            sc.nextLine();

            if (input == 1) {
                System.out.println("1. Genom personnummer");
                System.out.println("2. Genom namn");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    System.out.println("Ange personnummer");
                    String personNumber = sc.nextLine();

                    Person result = persons.stream()
                            .filter(p -> p.getPersonNumber().equals(personNumber))
                            .findFirst()
                            .orElse(null);

                    if (null == result) {
                        System.out.println("Ingen matchning!");
                    } else {
                        LocalDate expires = result.getLastPayed().plusYears(1);

                        if (expires.equals(LocalDate.now()) || expires.isAfter(LocalDate.now())) {

                            PrintWriter pw = new PrintWriter(Files.newBufferedWriter(outfilePath));
                            pw.println(result.getName() + ", " + result.getPersonNumber() + ", " + today);
                            pw.close();
                            System.out.println("Person funnen: " + result.getName() +  "\nAktivt medlemskap");

                        } else {
                            System.out.println("Inte längre aktivt medlemskap");
                        }
                    }
                } else if (choice == 2) {
                    System.out.println("Ange namn");
                    String name = sc.nextLine();

                    Person result = persons.stream()
                            .filter(p -> p.getName().equals(name))
                            .findFirst()
                            .orElse(null);

                    if (null == result) {
                        System.out.println("Ingen matchning.");
                    } else {
                             LocalDate expires = result.getLastPayed().plusYears(1);
                        if (persons.contains(result) && expires.equals(LocalDate.now()) || expires.isAfter(LocalDate.now())) {
                            System.out.println("Person funnen: " + result.getName() + "\nAktivt Medlemskap");
                            PrintWriter pw = new PrintWriter(Files.newBufferedWriter(outfilePath));
                            pw.println(result.getName() + ", " + result.getPersonNumber() + ", " + today);
                            pw.close();

                        }
                        else {
                            if (persons.contains(result)) {
                                System.out.println("Person funnen: " + result.getName() + "\nInte längre aktivt medlemskap");
                            }
                        }
                    }
                }
            } else if (input == 2) {
                sc.close();
                System.exit(0);
            }
            search();

        }
        catch (Exception e) {
            System.out.println("Daamn");
        }
    }
}
