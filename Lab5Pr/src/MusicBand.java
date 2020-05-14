import javafx.util.Pair;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * классб описывающий Music Band
 */
public class MusicBand implements Comparable{
    /**
     * поле индентификационный номер музыкальной банды
     */
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**
     * поле название музыкальной банды
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * поле координаты музыкальной банды
     */
    private Coordinates coordinates; //Поле не может быть null
    /**
     * поле дата основания музыкальной банды
     */
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * поле количество участников музыкальной банды
     */
    private int numberOfParticipants; //Значение поля должно быть больше 0
    /**
     * поле жанр, который преподчитает музыкальная банда
     */
    private MusicGenre genre; //Поле не может быть null
    /**
     * поле человек, который состоит в музыкальной банде
     */
    private Person frontMan; //Поле может быть null

    /**
     * конструктор, позволяющий задать имя, координаты, дату основания, число участников, жанр и человека
     * @param name имя Music Band
     * @param coordinates координаты Music Band
     * @param creationDate дата основания Music Band
     * @param numberOfParticipants количество участников Music Band
     * @param genre жанр, который преподчитает Music Band
     * @param frontMan человек, состоящий в Music Band
     */
    public MusicBand (String name, Coordinates coordinates,
                      java.time.LocalDate creationDate, int numberOfParticipants,
                      MusicGenre genre, Person frontMan) {
        this.id = 0;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.frontMan = frontMan;
    }

    /**
     * метод, который проверяет правильность введенных полей у Music Band
     * @return результат проверки и Music Band, если результат проверки true; null, если false
     */
    public static Pair<Boolean, MusicBand> input() {
        String name;
        Coordinates coordinates;
        java.time.LocalDate creationDate;
        int numberOfParticipants;
        MusicGenre genre;
        Person frontMan;

        String line;

        // name (not null)
        System.out.print("name: ");
        line = IoHelper.in.nextLine();
        while (line.isEmpty()) {
            System.out.println("name not null");
            System.out.print("name: ");
            line = IoHelper.in.nextLine();
        }

        name = line;

        System.out.println("coordinates: ");
        Pair<Boolean, Coordinates> cp = Coordinates.input();
        if (!cp.getKey()) {
            return new Pair<>(false, null);
        }

        coordinates = cp.getValue();

        creationDate = LocalDate.now();

        Pair<Boolean, Integer> nopp = IoHelper.getInt("number of participants");
        if (!nopp.getKey()) {
            return new Pair<>(false, null);
        }

        while (nopp.getValue() <= 0) {
            nopp = IoHelper.getInt("number of participants");
            if (!nopp.getKey()) {
                return new Pair<>(false, null);
            }
        }

        numberOfParticipants = nopp.getValue();

        Pair<Boolean, MusicGenre> mgp = MusicGenre.input("genre");
        if (!mgp.getKey()) {
            return new Pair<>(false, null);
        }
        genre = mgp.getValue();

        Pair<Boolean, Person> fmp = Person.input("front man");
        if (!fmp.getKey()) {
            return new Pair<>(false, null);
        }
        frontMan = fmp.getValue();


        return new Pair<Boolean, MusicBand>(true, new MusicBand(name, coordinates, creationDate,
                numberOfParticipants, genre, frontMan));
        }

    /**
     * метод для редактирования Music Band
     * @return true, если Music Band был отредактирован
     */
    public boolean edit() {
        String name;
        Coordinates coordinates;
        java.time.LocalDate creationDate;
        int numberOfParticipants;
        MusicGenre genre;
        Person frontMan;

        boolean confirm;
        String line;


        // name (not null)
        System.out.println("name: ");
        System.out.println(String.format("%s", this.name));
        confirm = IoHelper.getConfirmation("update?");
        if (confirm) {
            line = IoHelper.in.nextLine();
            while (line.isEmpty()) {
                System.out.println("name not null");
                System.out.print("name: ");
                line = IoHelper.in.nextLine();
            }

            name = line;
        } else {
            name = this.name;
        }

        System.out.println("coordinates: ");
        System.out.println(String.format("%s", this.coordinates.toString()));
        confirm = IoHelper.getConfirmation("update?");

        if (confirm) {
            Pair<Boolean, Coordinates> cp = Coordinates.input();
            if (!cp.getKey()) {
                return false;
            }

            coordinates = cp.getValue();
        } else {
            coordinates = this.coordinates;
        }

        creationDate = LocalDate.now();

        System.out.println("numberOfParticipants: ");
        System.out.println(String.format("%d", this.numberOfParticipants));
        confirm = IoHelper.getConfirmation("update?");

        if (confirm) {
            Pair<Boolean, Integer> nopp = IoHelper.getInt("number of participants");
            if (!nopp.getKey()) {
                return false;
            }

            while (nopp.getValue() <= 0) {
                nopp = IoHelper.getInt("number of participants");
                if (!nopp.getKey()) {
                    return false;
                }
            }

            numberOfParticipants = nopp.getValue();
        } else {
            numberOfParticipants = this.numberOfParticipants;
        }



        System.out.println("genre: ");
        System.out.println(String.format("%s", this.genre));
        confirm = IoHelper.getConfirmation("update?");

        if (confirm) {
            Pair<Boolean, MusicGenre> mgp = MusicGenre.input("genre");
            if (!mgp.getKey()) {
                return false;
            }
            genre = mgp.getValue();
        } else {
            genre = this.genre;
        }

        System.out.println("front man: ");
        System.out.println(String.format("%s", this.frontMan));
        confirm = IoHelper.getConfirmation("update?");

        if (confirm) {
            Pair<Boolean, Person> fmp = Person.input("front man");
            if (!fmp.getKey()) {
                return false;
            }
            frontMan = fmp.getValue();
        } else {
            frontMan = this.frontMan;
        }


        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.frontMan = frontMan;

        return true;

    }

    /**
     * метод, для получения значения поля индентификационный номер музыкальной банды
     * @param id индентификационный номер музыкальной банды
     */
    public void setId(int id) {this.id = id;}
    public Integer getId () {
        return id;
    }

    /**
     * метод, для получения значения поля имя Music Band
     * @param name имя Music Band
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     * метод для задания значения поля имя Music Band
     * @return имя Music Band
     */
    public String getName () {
        return name;
    }

    /**
     * метод для получения значения поля координаты Music Band
     * @param coordinates координаты Music Band
     */
    public void setCoordinates (Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * метод для задания значения поля координаты Music Band
     * @return координаты Music Band
     */
    public Coordinates getCoordinates () {
        return coordinates;
    }

    /**
     * метод для получения значения поля дата основания Music Band
     * @param date дата основания Music Band
     */
    public void setDate (LocalDate date) {
        this.creationDate = date;
    }

    /**
     * метод для задания значения поля дата основания Music Band
     * @return дата основания Music Band
     */
    public LocalDate getDate () {
        return creationDate;
    }

    /**
     * метод для получения значения поля количество участников музыкальной банды
     * @param numberOfParticipants количество участников музыкальной банды
     */
    public void setNumberOfParticipants (int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * метод для задания значения поля количество участников музыкальной банды
     * @return количество участников музыкальной банды
     */
    public int getNumberOfParticipants () {
        return numberOfParticipants;
    }

    /**
     * метод для получения значения поля жанр, который преподчитает музыкальная банда
     * @param genre жанр, который преподчитает музыкальная банда
     */
    public void setGenre (MusicGenre genre) {
        this.genre = genre;
    }

    /**
     * метод для задания значения поля жанр, который преподчитает музыкальная банда
     * @return жанр, который преподчитает музыкальная банда
     */
    public MusicGenre getGenre () {
        return genre;
    }

    /**
     * метод для получения значения поля человек, который состоит в музыкальной банде
     * @param frontMan человек, который состоит в музыкальной банде
     */
    public void setFrontMan (Person frontMan) {
        this.frontMan = frontMan;
    }

    /**
     * метод для задания значения поля человек, который состоит в музыкальной банде
     * @return человек, который состоит в музыкальной банде
     */
    public Person getFrontMan () {
        return frontMan;
    }

    /**
     * метод для получения строки с именем, координатами, датой основания, числом участников, жанром и человеком музыкальной банды в формате csv
     * @return строка с  именем, координатами, датой основания, числом участников, жанром и человеком музыкальной банды в формате csv
     */
    public String toCSVString(){

        String fmCsv = "";
        if (frontMan != null)
            fmCsv = frontMan.toCSVString();
        return String.format("%d;%s;%s;%s;%d;%s;%s", id, name, coordinates.toCSVString(),
                creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                numberOfParticipants, genre, fmCsv);
    }
    /**
     * метод для получения строки с именем, координатами, датой основания, числом участников, жанром и человеком музыкальной банды
     * @return строка с  именем, координатами, датой основания, числом участников, жанром и человеком музыкальной банды
     */
    @Override
    public String toString() {
        return String.format("id: %d\nname: %s\ncoordinates: %s\ncreationDate: %s\nnumberOfParticipants: %d\ngenre: %s\nfrontMan: \n%s",
                id, name, coordinates.toString(), creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), numberOfParticipants, genre, frontMan);
    }
    /**
     * метод для сравнения Music Band
     * @param o Music Band
     * @return положительное число, если вызывающий объект больше объекта, переданного в качестве параметра; отрицательное число, если вызывающий объект меньше объекта, переданного в качестве параметра; нуль, если объекты равны
     */
    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((MusicBand) o).name);
    }
}

