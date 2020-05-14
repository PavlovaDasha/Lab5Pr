import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * класс, описывающий команды для управления кколлекцией
 */
public class BandList {
    /**
     * поле элемент коллекции
     */
    private ArrayList<MusicBand> musicBandList;
    /**
     * поле сгенерированный индентификационный номер
     */
    private int generatedId;
    /**
     * поле дата инициализации
     */
    private Date initDate;

    /**
     * конструктор, который создает объект коллекции с индентификационным номером 1 и дату инициализации
     */
    public BandList () {
        musicBandList = new ArrayList<MusicBand>();
        generatedId = 1;
        initDate = new Date();
    }

    /**
     * метод, который находит Music Band с введеным индентификационным номером
     * @param id индентификационный номер
     * @return Music Band с введеным индентификационным номером или null
     */
    public MusicBand findById(int id) {
        MusicBand found = null;
        for (MusicBand b : musicBandList) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    /**
     * метод для считывания файла и создания элемента коллекции
     * @param fileName имя файла
     * @return элемет коллекции
     * @throws IOException проверяет, не прервалась ли операция ввода/ввывода
     */
    public static BandList loadFile(String fileName) throws IOException {

        BandList res = new BandList();

        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String s;

        while ((s = br.readLine()) != null) {
            String[] fields = s.split(";");

            if (fields.length < 7) {
                System.out.println("в строке csv");
                System.out.println(s);
                System.out.println("меньше 7 обязательных полей");
                System.out.println("пропускаем эту банду");
                continue;
            }

            try {
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                long x = Long.parseLong(fields[2]);
                Long y = Long.parseLong(fields[3]);
                LocalDate date = LocalDate.parse(fields[4], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                int numberOfParticipants = Integer.parseInt(fields[5]);
                MusicGenre genre = MusicGenre.getMusicGenre(fields[6]);

                Person fm = null;
                if (fields.length > 7) {
                    String personName = fields[7];
                    LocalDate personBday = null;
                    Location loc = null;
                    if (fields.length > 8) {
                        personBday = LocalDate.parse(fields[8], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        if (fields.length > 9) {
                            if (fields.length < 12) {
                                System.out.println("в строке csv");
                                System.out.println(s);
                                System.out.println("задано frontman-location, но меньше 3 обязательных полей");
                                System.out.println("пропускаем эту банду");
                                continue;
                            }
                            double locX = Double.parseDouble(fields[9]);
                            float locY = Float.parseFloat(fields[10]);
                            int locZ = Integer.parseInt(fields[11]);
                            String locName = null;
                            if (fields.length == 13) {
                                locName = fields[12];
                            }
                            loc = new Location(locX, locY, locZ, locName);
                        }
                    }
                    fm = new Person(personName, personBday, loc);
                }
                res.add(new MusicBand(name, new Coordinates(x, y),
                        date, numberOfParticipants, genre, fm), id);
            } catch (NumberFormatException ex) {
                System.out.println("в строке csv");
                System.out.println(s);
                System.out.println("не получилось распарсить поле");
                System.out.println("пропускаем эту банду");
                System.out.println(ex);
                continue;
            }
        }

        br.close();
        fr.close();

        return res;
    }

    /**
     * метод для вывода в стандартный поток вывода информации о коллекции
     */
    public void info() {
        System.out.printf("Тип: MusicBand\nДата инициализации: %s\nКоличество элементов: %d\n", initDate.toString(), musicBandList.size());
    }

    /**
     * метод для вывода в стандартный поток всех элементов коллекции
     */
    public void show() {
        for (MusicBand m: musicBandList) {
            System.out.println(m);
        }
    }

    /**
     * метод для добавления нового элемента с генерацией индентификационного номера
     * @param band добавляемый элемент
     */
    public void add(MusicBand band) {
        band.setId(this.generatedId++);
        musicBandList.add(band);
    }

    /**
     * метод для добавления нового элемента
     * @param band добавляемый элемент
     * @param id индентификационный номер
     */
    public void add(MusicBand band, int id) {

        MusicBand found = findById(id);
        if (found != null) {
            System.out.println(band.getName());
            System.out.println("id банды конфликтует с существующей в списке бандой");
            System.out.println(String.format("%s id = %d", found.getName(), found.getId()));
            System.out.println("назначаем автоматически");
            int newId = this.generatedId++;
            System.out.println(newId);
            band.setId(newId);
        } else {
            band.setId(id);
            generatedId = Math.max(generatedId, band.getId() + 1);
        }

        musicBandList.add(band);
    }

    /**
     * метод для изменения элемента коллекции, индентификационный номер котрого равен заданному
     * @param id индентификационный номер
     */
    public void update(int id) {
        MusicBand found = null;
        for (MusicBand b : musicBandList) {
            if (b.getId() == id) {
                found = b;
                break;
            }
        }

        if (found == null) {
            System.out.println("id does not exist");
            return;
        }
    }

    /**
     * метод для удаления элемента из коллекции по его индентификационному номеру
     * @param id индентификационный номер
     */
    public void removeById(int id) {
        musicBandList.removeIf(m -> m.getId() == id);
    }

    /**
     * метод для очистки коллекции
     */
    public void clear(){
        musicBandList.clear();
    }

    /**
     * метод для сохранения коллекции в файл
     * @param fileName имя файла
     * @throws IOException проверяет, не прервалась ли операция ввода/вывода
     */
    public void save(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        for (MusicBand m: musicBandList) {
            bos.write(m.toCSVString().getBytes());
            bos.write("\n".getBytes());
        }
        bos.close();
        fos.close();
    }

    /**
     * метод, который удаляет элемент, находящийся в заданной позиции коллекции
     * @param index позиция коллекции
     */
    public void removeAt(int index) {
        if (index < musicBandList.size())
            musicBandList.remove(index);
        else {
            System.out.println("no such band");
        }
    }

    /**
     * метод для нахождения максимального элемента коллекции
     * @return максимальный элемент коллекции
     */
    public MusicBand findMax() {
        if (musicBandList.size() == 0)
            return null;
        MusicBand max = musicBandList.get(0);
        for (int i = 1; i != musicBandList.size(); ++i)
            if (musicBandList.get(i).compareTo(max) > 0)
                max = musicBandList.get(i);

        return max;
    }

    /**
     * метод, который добавляет новый элемент в коллекцию, если он больше наибольшего элемента этой коллекции
     * @param b элемент коллекции
     * @return true, если элемент добавлен, иначе false
     */
    public boolean addIfMax(MusicBand b) {
        if (b.compareTo(findMax()) > 0) {
            add(b);
            return true;
        } else
            return false;
    }

    /**
     * метод для сортировки элементов коллекции
     */
    public void sort() {
        musicBandList.sort(MusicBand::compareTo);
    }

    /**
     *метод, котрый выводит количество элементов коллекции, количество участников которых меньше заданного
     * @param numberOfParticipants количество участников в музыкальной банде
     */
    public void countLessThanNumberOfParticipants(int numberOfParticipants) {
        System.out.println(musicBandList.stream()
                .mapToInt(MusicBand::getNumberOfParticipants)
                .filter(x -> x < numberOfParticipants)
                .count());
    }

    /**
     * метод, котрый выводит количество элементов коллекции, поле frontMan которых больше заданного
     * @param frontMan человек, состоящий в музыкальной банде
     */
    public void countGreaterThanFrontMan(Person frontMan) {
        System.out.println(musicBandList.stream()
                .filter(x -> x.getFrontMan() != null)
                .map(MusicBand::getFrontMan)
                .filter(x -> x.compareTo(frontMan) < 0)
                .count());
    }

    /**
     *метод, котрый выводит элемент коллекции, количество участников которого равно заданному
     * @param numberOfParticipants количество участников в музыкальной банде
     */
    public void filterByNumberOfParticipants(int numberOfParticipants) {
        for (MusicBand m: musicBandList) {
            if (m.getNumberOfParticipants() == numberOfParticipants) {
                System.out.println(m);
            }
        }
    }
}