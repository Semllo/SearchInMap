package lib;

/**
 * La generación de los sucesores de un estado se puede realizar
 * implementando este interfaz.
 * @param <T> Tipo de los elementos para los que se aplica el operador.
 */
public interface Operador<T>
{
    /**
     * Aplica el operador a un objeto.
     * @param t Objeto donde aplicar el operador.
     * @return resultado del operador.
     */
    T run(T t);
}
