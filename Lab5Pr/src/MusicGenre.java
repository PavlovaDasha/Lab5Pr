/**
 * Набор музыкальных жанров, которые могут преподчитать музыкальные банды
 */
public enum MusicGenre {
    /**
     * контанта soul
     */
    SOUL,
    /**
     * контанта pop
     */
    POP,
    /**
     * контанта math rock
     */
    MATH_ROCK,
    /**
     * контанта post punk
     */
    POST_PUNK;

    /**
     * метод для получения поля жанр, который преподчитает музыкальная группа
     * @param s считываемая строка
     * @return музыкальный жанр из набора музыкальных жанров или null
     */
    public static MusicGenre getMusicGenre(String s) {
        switch (s) {
            case "SOUL":
                return MusicGenre.SOUL;
            case "POP":
                return MusicGenre.POP;
            case "MATH_ROCK":
                return MusicGenre.MATH_ROCK;
            case "POST_PUNK":
                return MusicGenre.POST_PUNK;
        }
        return null;
    }

    /**
     * метод, который проверяет правильность введенного музыкального жанра
     * @param prompt считываемая строка
     * @return true и музыкальный жанр, если введенная строка является музыкальным жанром
     */
    public static Pair<Boolean, MusicGenre> input(String prompt) {

        System.out.println(prompt + ": ");
        for (MusicGenre mg: MusicGenre.values()) {
            System.out.println(mg);
        }

        String line;
        line = IoHelper.in.nextLine();

        for (MusicGenre mg: MusicGenre.values()) {
            if (line.equals(mg.toString()))
                return new Pair<>(true, mg);
        }

        while (true) {

            System.out.println(prompt + ": ");
            for (MusicGenre mg: MusicGenre.values()) {
                System.out.println(mg);
            }

            line = IoHelper.in.nextLine();

            for (MusicGenre mg: MusicGenre.values()) {
                if (line.equals(mg.toString()))
                    return new Pair<>(true, mg);
            }
        }
    }
}