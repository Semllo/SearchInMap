package mapau;

import java.util.*;

/**
 * Estado en la búsqueda del mapa.
 */
public class Estado implements Comparable<Estado>
{
/**
 * Construye el estado inicial de un mapa.
 * @param mapa Mapa al que hace referencia el estado.
 */
public Estado(Mapa mapa)
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Devuelve la profundidad del estado.
 * @return profundidad
 */
public int getProfundidad()
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Obtiene el mapa asociado al estado.
 * @return Mapa asociado al estado.
 */
public Mapa getMapa()
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Obtiene la posición referenciada por el estado.
 * @return Posición referenciada por el estado.
 */
public Posicion getPosicion()
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Indica si el estado es el objetivo.
 * @return {@code true} si el estado es el objetivo.
 */
public boolean esObjetivo()
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Obtiene el camino recorrido hasta el estado.
 * @return Camino recorrido hasta el estado.
 */
public ArrayList<Posicion> getCamino()
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Devuelve el código hash de la posición de este estado.
 * @return Código hash de la posición de este estado.
 */
@Override public int hashCode()
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Comprueba si la posición de este estado es igual a la de otro.
 * @param obj Estado a comparar.
 * @return {@code true} si los estados tienen la misma posición.
 */
@Override public boolean equals(Object obj)
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Compara la posición de este estado con la de otro.
 * <p>Aparentemente esta comparación no tiene sentido,
 * pero es necesaria para utilizar la cola de prioridad.
 * @param e estado
 * @return un número negativo, cero, o positivo si este estado
 *         es menor, igual, o mayour que el estado indicado.
 */
@Override public int compareTo(Estado e)
{
    throw new UnsupportedOperationException("Falta implementar");
}

} // Estado
