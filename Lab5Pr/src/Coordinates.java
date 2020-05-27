import java.util.Locale;

/**
 * класс, описывающий координаты Music Band
 */
public class Coordinates {
    /**
     * поле координата x Music Band.
     */
    private long x; //Значение поля должно быть больше -785
    /**
     * поле координата y Music Band.
     */
    private Long y; //Значение поля должно быть больше -935, Поле не может быть null
    /**
     * поле минимальное значение x: -785
     */
    private static final long MINX = -785;
    /**
     * поле минимальное значение y: -935
     */
    private static final long MINY = -935;

    /**
     * конструктор, позволяющий задать координату x и координату y Music Band
     * @param x координата x
     * @param y координата y
     */
    public Coordinates (long x, Long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * метод для получения строки с координатами Music Band в формате csv
     * @return строка с координатами Music Band в формате csv
     */
    public String toCSVString() {
        return String.format(Locale.US, "%d;%d", x, y);
    }
    /**
     * метод для получения строки с координатами Music Band в формате (x, y)
     * @return строка с координатами Music Band в формате (x, y)
     */
    @Override
    public String toString() {
        return String.format(Locale.US, "(%d, %d)", x, y);
    }

    /**
     * метод, который проверяет правильность введенных координат
     * @return результат проверки и координаты, если результат проверки true; null, если false
     */
    public static Pair<Boolean, Coordinates> input() {
        long x, y;

        // x (not null)

        Pair<Boolean, Long> res = IoHelper.getLong("x");
        if (!res.getKey()) {
            System.out.println("input cancelled");
            return new Pair<Boolean, Coordinates>(false, null);
        }

        while (res.getValue() <= MINX) {
            System.out.println(String.format("error: x must be greater than %d", MINX));
            res = IoHelper.getLong("x");
            if (!res.getKey()) {
                System.out.println("input cancelled");
                return new Pair<Boolean, Coordinates>(false, null);
            }
        }

        x = res.getValue();

        // y (not null)

        res = IoHelper.getLong("y");
        if (!res.getKey()) {
            System.out.println("input cancelled");
            return new Pair<Boolean, Coordinates>(false, null);
        }

        while (res.getValue() <= MINY) {
            System.out.println(String.format("error: y must be greater than %d", MINY));
            res = IoHelper.getLong("y");
            if (!res.getKey()) {
                System.out.println("input cancelled");
                return new Pair<Boolean, Coordinates>(false, null);
            }
        }

        y = res.getValue();

        return new Pair<Boolean, Coordinates>(true,
                new Coordinates(x, y));
    }
}