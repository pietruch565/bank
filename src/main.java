import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj pierwsze trzy cyfry numeru konta: ");
        String userInput = scanner.nextLine().trim();

        if (userInput.length() != 3 || !userInput.matches("\\d{3}")) {
            System.out.println("Błąd: Wprowadź dokładnie trzy cyfry.");
            return;
        }

        String url = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";

        try {
            List<String> lines = downloadFile(url);

            String bankInfo = findBankInfo(userInput, lines);

            if (bankInfo != null) {
                System.out.println("Informacje o banku: " + bankInfo);
            } else {
                System.out.println("Nie znaleziono informacji o banku dla podanych cyfr.");
            }

        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas przetwarzania pliku: " + e.getMessage());
        }
    }

    private static List<String> downloadFile(String fileUrl) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(fileUrl).openStream()))) {
            lines = reader.lines().collect(Collectors.toList());
        }

        return lines;
    }

    private static String findBankInfo(String prefix, List<String> lines) {
        for (String line : lines) {
            if (line.startsWith(prefix)) {
                return line;
            }
        }
        return null;
    }
}
