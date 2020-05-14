/**
 * класс для исполнения программы
 */
public class Main {
    /**
     * метод для исполнения программы
     * @param args входные значения
     */
    public static void main (String[] args) {

        if (args.length < 1) {
            System.out.println("отсутствует обязательный аргумент командной строки");
            System.out.println("имя файла для открытия");
            System.exit(-1);
        }

        Driver d = new Driver(args[0]);
        d.run();
    }
}