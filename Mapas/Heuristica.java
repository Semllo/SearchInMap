package mapau;

import java.util.*;

/**
 * Interfaz para implementar heurísticas.
 */
public abstract class Heuristica implements Comparator<Estado>
{
/**
 * Compara dos estados según su heurística.
 * @param a Primer estado a comparar.
 * @param b Segundo estado a comparar.
 * @return Un número negativo, cero, o positivo si el primer estado
 *         tiene una heurística menor, igual o mayor que el segundo.
 */
@Override public int compare(Estado a, Estado b)
{
    int h = getH(a) - getH(b),
        f = getG(a) - getG(b) + h;

    return f == 0 ? h : f;
}

/**
 * Calcula la evaluación de un estado.
 * @param e Estado sobre el que calcular.
 * @return Evaluación del estado.
 */
public int getF(Estado e)
{
    return getG(e) + getH(e);
}

/**
 * Devuelve la distancia recorrida hasta el estado.
 * @param e Estado sobre el que calcular.
 * @return Distancia recorrida hasta el estado.
 */
public int getG(Estado e)
{
    return e.getProfundidad();
}

/**
 * Calcula la distancia estimada hasta la solución.
 * @param e Estado sobre el que calcular.
 * @return Distancia estimada hasta la solución.
 */
public abstract int getH(Estado e);

} // Heuristica
