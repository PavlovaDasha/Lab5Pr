import javafx.util.Pair;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * класс для ввода команд
 */
public class IoHelper {
    /**
     * поле Scanner для ввода с клавиатуры
     */
    public static Scanner in;

    /**
     * метод, который спрашивает о продолжении ввода данных
     * @param message считываемая строка
     * @return true, если пользователь хочет продолжать ввод, иначе false
     */
    public static boolean getConfirmation(String message) {
        System.out.println(message + "(y/n):");
        String line = in.nextLine();

        while (true) {
            if (line.equals("y"))
                return true;
            if (line.equals("n"))
                return false;

            System.out.println(message + "(y/n):");
            line = in.nextLine();
        }
    }

    /**
     * метод, который проверяет правильность введеного числа типа long
     * @param prompt считываемая строка
     * @return результат проверки и число типа long, если результат проверки true; 0, если false
     */
    public static Pair<Boolean, Long> getLong(String prompt) {
        System.out.println(prompt + ": ");
        String line;

        long res;

        line = in.nextLine();
        while (true) {
            try {
                res = Long.parseLong(line);
            } catch (NumberFormatException ex) {
                if (getConfirmation("try again")) {
                    System.out.println(prompt + ": ");
                    line = in.nextLine();
                    continue;
                } else {
                    return new Pair<Boolean, Long>(false, 0L);
                }
            }

            return new Pair<Boolean, Long>(true, res);

        }
    }

    /**
     * метод, который проверяет правильность введеного числа типа int
     * @param prompt считываемая строка
     * @return результат проверки и число типа int, если результат проверки true; 0, если false
     */
    public static Pair<Boolean, Integer> getInt(String prompt) {
        System.out.println(prompt + ": ");
        String line;

        int res;

        line = in.nextLine();
        while (true) {
            try {
                res = Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                if (getConfirmation("try again")) {
                    System.out.println(prompt + ": ");
                    line = in.nextLine();
                    continue;
                } else {
                    return new Pair<Boolean, Integer>(false, 0);
                }
            }

            return new Pair<Boolean, Integer>(true, res);

        }
    }

    /**
     * метод, который вызывает метод getDouble(prompt, false), который проверяет правильность введеного числа типа double
     * @param prompt считываемая строка
     * @return метод getDouble(prompt, false)
     */
    public static Pair<Boolean, Double> getDouble(String prompt) {
        return getDouble(prompt, false);
    }

    /**
     * метод, который проверяет правильность введеного числа типа double
     * @param prompt считываемая строка
     * @param allowNull true, если число может быть null, иначе false
     * @return результат проверки и число типа double, если результат проверки true; 0, если false. Число будет null, если allowNull - true
     */
    public static Pair<Boolean, Double> getDouble(String prompt, Boolean allowNull) {
        System.out.println(prompt + ": ");
        String line;

        double res;

        line = in.nextLine();
        if (allowNull && line.isEmpty())
            return new Pair<>(true, null);

        while (true) {
            try {
                res = Double.parseDouble(line);
            } catch (NumberFormatException ex) {
                if (getConfirmation("try again")) {
                    System.out.println(prompt + ": ");
                    line = in.nextLine();

                    if (allowNull && line.isEmpty())
                        return new Pair<>(true, null);

                    continue;
                } else {
                    return new Pair<Boolean, Double>(false, 0d);
                }
            }

            return new Pair<Boolean, Double>(true, res);

        }
    }

    /**
     * метод, который проверяет правильность введеного числа типа float
     * @param prompt считываемая строка
     * @return результат проверки и число типа float, если результат проверки true; 0, если false
     */
    public static Pair<Boolean, Float> getFloat(String prompt) {
        System.out.println(prompt + ": ");
        String line;

        float res;

        line = in.nextLine();
        while (true) {
            try {
                res = Float.parseFloat(line);
            } catch (NumberFormatException ex) {
                if (getConfirmation("try again")) {
                    System.out.println(prompt + ": ");
                    line = in.nextLine();
                    continue;
                } else {
                    return new Pair<Boolean, Float>(false, 0f);
                }
            }

            return new Pair<Boolean, Float>(true, res);

        }
    }

    /**
     * метод, который проверяет правильность введеной даты
     * @param prompt считываемая строка
     * @return результат проверки и дату, если результат проверки true; null, если false
     */
    public static Pair<Boolean, LocalDate> getLocalDate(String prompt) {
        System.out.println(prompt + ": ");

        String line = in.nextLine();
        while (true) {
            if (line.isEmpty()) {
                return new Pair<>(true, null);
            }
            LocalDate res;
            try {
                res = LocalDate.parse(line, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException ex) {
                if (getConfirmation("try again")) {
                    System.out.println(prompt + ": ");
                    line = in.nextLine();
                    if (line.isEmpty()) {
                        return new Pair<>(true, null);
                    }
                    continue;
                } else {
                    return new Pair<>(false, null);
                }
            }

            return new Pair<>(true, res);
        }
    }
}
