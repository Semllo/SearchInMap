package mapau;

import static mapau.Mapa.*;

/**
 * Clase para almacenar una posición del mapa.
 */
public class Posicion implements Comparable<Posicion>
{
private final int m_fila, m_columna;
private static final int f_lado2 = LADO * LADO;

/**
 * Crea una posición a partir de las coordenadas de una celda
 * @param filaCelda    Fila de la celda.
 * @param columnaCelda Columna de la celda.
 * @return Posicion.
 */
public static Posicion get(int filaCelda, int columnaCelda)
{
    return new Posicion(filaCelda    * LADO + LADO / 2,
                        columnaCelda * LADO + LADO / 2);
}

/**
 * Construye una posición a partir de su fila y columna.
 * @param fila    Fila de la posición.
 * @param columna Columna de la posición.
 */
public Posicion(int fila, int columna)
{
    m_fila    = fila;
    m_columna = columna;
}

/**
 * Construye un objeto a partir de su representación como cadena.
 * @param filaComaColumna Fila y columna separadas por una coma.
 */
public Posicion(String filaComaColumna)
{
    int coma = filaComaColumna.indexOf(',');
    m_fila    = Integer.parseInt(filaComaColumna.substring(0, coma));
    m_columna = Integer.parseInt(filaComaColumna.substring(coma + 1));
}

/**
 * Obtiene la fila de la posición.
 * @return Fila de la posición.
 */
public int getFila()
{
    return m_fila;
}

/**
 * Obtiene la columna de la posición.
 * @return Columna de la posición.
 */
public int getColumna()
{
    return m_columna;
}

/**
 * Comprueba si una posición es vecina de esta posición.
 * @param p Posición que se desea comprobar si es vecina.
 * @return <code>true</code> si la posición es vecina.
 */
public boolean vecina(Posicion p)
{
    return distancia2(p) <= f_lado2 + 1;
}

/**
 * Comprueba si una posición está en la misma celda.
 * @param p Posición a comprobar.
 * @return
 */
public boolean mismaCelda(Posicion p)
{
    return m_fila / LADO == p.m_fila / LADO &&
           m_columna / LADO == p.m_columna / LADO;
}

/**
 * Calcula la distancia en línea recta entre esta posición y otra.
 * @param p Posición con la que hay que calcular la distancia.
 * @return Distancia calculada en número de pasos.
 */
public int distancia(Posicion p)
{
    return (int)Math.sqrt(distancia2(p));
}

private int distancia2(Posicion p)
{
    int df = (m_fila - p.m_fila) / LADO,
        dc = (m_columna - p.m_columna) / LADO;

    return df*df + dc*dc;
}

/**
 * Calcula la distancia de la diagonal entre dos posiciones.
 * @param p Posición con la que hay que calcular la distancia.
 * @return Distancia calculada en número de pasos.
 */
public int diagonal(Posicion p)
{
    int f = Math.abs(m_fila - p.m_fila),
        c = Math.abs(m_columna - p.m_columna),
        d = Math.min(f, c),
        r = Math.max(f-d, c-d);

    return d / DIAGONAL + r / LADO;
}

/**
 * Comprueba si dos posiciones son iguales.
 * @param obj Posición que hay que comparar.
 * @return <code>true</code> si las posiciones son iguales.
 */
@Override public boolean equals(Object obj)
{
    if(!(obj instanceof Posicion))
        return false; //............................................RETURN

    Posicion p = (Posicion)obj;
    return m_fila == p.m_fila && m_columna == p.m_columna;
}

/**
 * Calcula el código hash de la posición para que pueda ser usada
 * como clave en clases como HashMap.
 * @return Código hash calculado.
 */
@Override public int hashCode()
{
    return 13 * (13 * 7 + m_fila) + m_columna;
}

/**
 * Compara esta posición con otra.
 * @param p Posición a comparar.
 * @return Un número negativo, cero, o positivo dependiendo de si esta
 *         posición es menor, igual o mayor que la indicada.
 */
@Override public int compareTo(Posicion p)
{
    int r = m_fila - p.m_fila;
    return r == 0 ? m_columna - p.m_columna : r;
}

/**
 * Convierte la posición en cadena.
 * @return Fila y columna separados por una coma.
 */
@Override public String toString()
{
    return m_fila +","+ m_columna;
}

public static void main(String args[])
{
    Posicion p = Posicion.get(1,1),
             q = Posicion.get(100,20);

    System.out.println("Distancia en línea recta: "+ p.distancia(q));
    System.out.println("   Distancia en diagonal: "+ p.diagonal(q));
}

} // Posicion
