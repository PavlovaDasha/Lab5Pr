import java.util.Locale;

/**
 * класс, описывающий месторасположение человека
 */
public class Location {
    /**
     * поле координата x месторасположения человека
     */
    private double x;
    /**
     * поле координата y месторасположения человека
     */
    private float y;
    /**
     * поле координата z месторасположения человека
     */
    private int z;
    /**
     * поле название месторасположения человека
     */
    private String name; //Поле может быть null

    /**
     * конструтор, позволяющий задать координаты и название месторасположения человека
     * @param x координата x
     * @param y координата y
     * @param z координата z
     * @param name название месторасположения
     */
    public Location (double x, float y, int z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
    /**
     * метод для получения строки с координатами и названием месторасположения человека в формате csv
     * @return строка с координатами и названием месторасположения человека в формате csv
     */
    public String toCSVString() {
        return String.format(Locale.US, "%f;%f;%d;%s", x, y, z, name);
    }
    /**
     * метод для получения строки с координатами и названием месторасположения человека
     * @return строка с координатами и названием месторасположения человека
     */
    @Override
    public String toString() {
        return String.format(Locale.US, "(%f, %f, %d)\nname: %s", x, y, z, name);
    }

    /**
     * метод, который проверяет правильность введенных полей у месторасположения человека
     * @param prompt считываемая строка
     * @return результат проверки и месторасположение, если результат проверки true; null, если false
     */
    public static Pair<Boolean, Location> input(String prompt) {
        System.out.println(prompt + ": ");

        double x;
        float y;
        int z;
        String name;

        Pair<Boolean, Double> px = IoHelper.getDouble("x", true);
        if (!px.getKey())
            return new Pair<>(false, null);

        if (px.getValue() == null)
            return new Pair<>(true, null);

        x = px.getValue();

        Pair<Boolean, Float> py = IoHelper.getFloat("y");
        if (!py.getKey())
            return new Pair<>(false, null);
        y = py.getValue();

        Pair<Boolean, Integer> pz = IoHelper.getInt("z");
        if (!pz.getKey())
            return new Pair<>(false, null);
        z = pz.getValue();

        System.out.println("name: ");
        name = IoHelper.in.nextLine();

        return new Pair<>(true, new Location(x, y, z, name));

    }
}