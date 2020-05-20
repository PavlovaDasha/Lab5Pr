import javafx.util.Pair;
import java.io.*;
import java.util.*;

/**
 *класс для управления коллекцией
 */
public class Driver {
    /**
     *поле команды для управления коллекцией
     */
    private BandList mbList;
    /**
     * поле имя файла для записи введеных команд
     */
    private String commandLogFileName;
    /**
     * поле запись введеных комманд
     */
    private boolean writeCommandLog;
    /**
     * поле имя файла
     */
    private String fileName;

    /**
     * поле для запоминания вызванных скриптов
     */
    private Set<String> calledScripts;

    /*
    public Driver() {

        IoHelper.in = new Scanner(System.in);
        fileName = null;
        mbList = new BandList();

        commandLogFileName = "log.txt";
        writeCommandLog = true;
    }
    */

    /**
     * метод для считывания файла
     * @param fileName имя файла
     */
    public Driver(String fileName) {
        IoHelper.in = new Scanner(System.in);
        try {
            mbList = BandList.loadFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("указанного файла не существует, создаём пустой BandList");
            mbList = new BandList();
        } catch (IOException e) {
            System.out.println("IOException при попытке открытия файла");
            System.out.println("выберите другой файл, программа завершает работу");
            e.printStackTrace();
            System.exit(-2);
        }

        this.fileName = fileName;
        commandLogFileName = "log.txt";
        writeCommandLog = true;
        calledScripts = new TreeSet<>();
    }

    /**
     * метод для вывода справки по доступным командам
     */
    public void help() {
        System.out.println("help : вывести справку по доступным командам");
        System.out.println("info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        System.out.println("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.println("add {element} : добавить новый элемент в коллекцию");
        System.out.println("update id {element} : обновить значение элемента коллекции, id которого равен заданному");
        System.out.println("remove_by_id id : удалить элемент из коллекции по его id");
        System.out.println("clear : очистить коллекцию");
        System.out.println("save : сохранить коллекцию в файл");
        System.out.println("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.println("exit : завершить программу (без сохранения в файл)");
        System.out.println("remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)");
        System.out.println("add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        System.out.println("sort : отсортировать коллекцию в естественном порядке");
        System.out.println("count_less_than_number_of_participants numberOfParticipants : вывести количество элементов, значение поля numberOfParticipants которых меньше заданного");
        System.out.println("count_greater_than_front_man frontMan : вывести количество элементов, значение поля frontMan которых больше заданного");
        System.out.println("filter_by_number_of_participants numberOfParticipants : вывести элементы, значение поля numberOfParticipants которых равно заданному");
    }

    /**
     * метод для записи введеных команд
     * @param command введеная команда
     */
    public void writeLog(String command) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(commandLogFileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(command);
        printWriter.close();
    }

    /**
     *метод для считывания и выполнения скрипта из указанного файла
     * @param command введеная команда
     * @return false, если надо выходить (exit)
     */
    public boolean executeScript(String[] command) {

        if (command.length < 2){
            System.out.println("syntax: executeScript <filename>");
            return true;
        }

        String fileName = command[1];

        if (this.calledScripts.contains(fileName)) {
            System.out.println("infinite recursion detected");
            return false;
        } else {
            this.calledScripts.add(fileName);
        }

        FileReader fr;

        try {
            fr = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("не найден файл скрипта");
            e.printStackTrace();
            this.calledScripts.remove(fileName);
            return true;
        }
        
        BufferedReader br = new BufferedReader(fr);
        String s;

        Scanner temp = IoHelper.in;
        IoHelper.in = new Scanner(br);

        try {
            while (IoHelper.in.hasNextLine()) {
                s = IoHelper.in.nextLine();
                System.out.println(">>>" + s);
                if (!execution(s)) {
                    IoHelper.in = temp;
                    this.calledScripts.remove(fileName);
                    return false;
                }
            }

            br.close();
            fr.close();
        } catch (IOException ex) {
            System.out.println("error reading from file");
            IoHelper.in = temp;
            this.calledScripts.remove(fileName);
            return true;
        }

        IoHelper.in = temp;
        this.calledScripts.remove(fileName);
        return true;
    }

    /**
     * метод, который удаляет элемент, находящийся в заданной позиции коллекции
     * @param command введенная команда
     */
    public void removeAt(String[] command) {
        if (command.length < 2){
            System.out.println("syntax: remove_by_id <id>");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(command[1]);
        } catch (NumberFormatException ex) {
            System.out.println("wrong id parameter");
            return;
        }

        mbList.removeAt(id);
    }

    /**
     * метод для удаления элемента из коллекции по его индентификационному номеру
     * @param command введенная команда
     */
    public void removeById(String[] command) {
        if (command.length < 2){
            System.out.println("syntax: remove_by_id <id>");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(command[1]);
        } catch (NumberFormatException ex) {
            System.out.println("wrong id parameter");
            return;
        }

        mbList.removeById(id);
    }

    /**
     * метод для считывания и выполнения команд
     * @param inputCommand введенная комманда
     * @return true, пока не будет вызвана команда exit
     */
    public boolean execution(String inputCommand) {
        String[] command = inputCommand.split(" ", 2);

        if (writeCommandLog)
            writeLog(inputCommand);

        switch (command[0]) {
            case "help":
                help();
                break;
            case "info":
                mbList.info();
                break;
            case "show":
                mbList.show();
                break;
            case "add":
                add();
                break;
            case "update":
                updateById(command);
                break;
            case "remove_by_id":
                removeById(command);
                break;
            case "clear":
                mbList.clear();
                break;
            case "save":
                try {
                    mbList.save(fileName);
                } catch (IOException e) {
                    System.out.println("exception при сохранении файла");
                    e.printStackTrace();
                }
                break;
            case "execute_script":
            case "es":
                if (!executeScript(command)) {
                    return false;
                }
                break;
            case "exit":
                return false;
            case "remove_at":
                removeAt(command);
                break;
            case "add_if_max":
                addIfMax();
                break;
            case "sort":
                mbList.sort();
                break;
            case "count_less_than_number_of_participants":
                countLessThanNumberOfParticipants(command);
                break;
            case "count_greater_than_front_man":
                countGreaterThanFrontMan();
                break;
            case "filter_by_number_of_participants":
                filterByNumberOfParticipants(command);
                break;
        }
        return true;
    }

    /**
     * метод, котрый выводит количество элементов коллекции, поле frontMan которых больше заданного
     */
    private void countGreaterThanFrontMan() {
        Pair<Boolean, Person> fmp = Person.input("front man");
        if (!fmp.getKey() || fmp.getValue() == null) {
            System.out.println("cancelled");
            return;
        }
        Person frontMan = fmp.getValue();
        mbList.countGreaterThanFrontMan(frontMan);
    }

    /**
     *метод, котрый выводит элемент коллекции, количество участников которого равно заданному
     * @param command введенная команда
     */
    public void filterByNumberOfParticipants(String[] command) {
        if (command.length < 2){
            System.out.println("syntax: update <id>");
            return;
        }

        int nop;
        try {
            nop = Integer.parseInt(command[1]);
        } catch (NumberFormatException ex) {
            System.out.println("wrong id");
            return;
        }
        mbList.filterByNumberOfParticipants(nop);
    }

    /**
     *метод, котрый выводит количество элементов коллекции, количество участников которых меньше заданного
     * @param command введенная команда
     */
    public void countLessThanNumberOfParticipants(String[] command) {
        if (command.length < 2){
            System.out.println("syntax: update <id>");
            return;
        }

        int nop;
        try {
            nop = Integer.parseInt(command[1]);
        } catch (NumberFormatException ex) {
            System.out.println("wrong id");
            return;
        }
        mbList.countLessThanNumberOfParticipants(nop);
    }

    /**
     * метод для изменения элемента коллекции, индентификационный номер котрого равен заданному
     * @param command введенная команда
     */
    public void updateById(String[] command) {

        if (command.length < 2) {
            System.out.println("syntax: update <id>");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(command[1]);
        } catch (NumberFormatException ex) {
            System.out.println("wrong id");
            return;
        }

        MusicBand band = mbList.findById(id);

        if (band == null) {
            System.out.println("id does not exist");
            System.out.println("update cancelled");
            return;
        }

        System.out.println("found band with the requested id");
        System.out.println("proceeding to update");
        boolean updateRes = band.edit();

        if (updateRes) {
            System.out.println("updated successfully");
        } else {
            System.out.println("updated cancelled");
        }
    }

    /**
     * метод для добавления нового элемента
     */
    private void add() {
        System.out.println("input band data to be added:");
        Pair<Boolean, MusicBand> res = MusicBand.input();
        if (res.getKey()) {
            mbList.add(res.getValue());
            System.out.println("band added successfully");
        } else {
            System.out.println("adding band has been cancelled");
        }
    }

    /**
     * метод, который добавляет новый элемент в коллекцию, если он больше наибольшего элемента этой коллекции
     */
    private void addIfMax() {
        System.out.println("input band data to be added:");
        Pair<Boolean, MusicBand> res = MusicBand.input();
        if (res.getKey()) {
            if (mbList.addIfMax(res.getValue()))
                System.out.println("band added successfully");
            else
                System.out.println("band is less than the current max, adding cancelled");
        } else {
            System.out.println("adding band has been cancelled");
        }
    }

    /**
     * метод для считывания и выполнения введенных команд
     */
    public void run() {

        boolean isRun = true;
        String inputS;

        writeLog("");

        while(isRun) {
            inputS = IoHelper.in.nextLine();
            isRun = execution(inputS);
        }

    }

}
