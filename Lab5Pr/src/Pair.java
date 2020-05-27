/**
 * класс для хранения двух перменных
 * @param <K> ключ
 * @param <V> значение
 */
public class Pair<K, V> {
    /**
     * поле для хранения ключа
     */
    private K k;
    /**
     * поле для хранения значения
     */
    private V v;

    /**
     * конструктор, позволяющий задать ключ и значение
     * @param k ключ
     * @param v значение
     */
    public Pair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    /**
     * метод для получения ключа
     * @return ключ
     */
    public K getKey() {
        return k;
    }

    /**
     * метод для получения значения
     * @return значение
     */
    public V getValue() {
        return v;
    }
}
