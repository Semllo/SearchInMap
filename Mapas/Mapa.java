package mapau;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Clase que gestiona los mapas.
 */
public final class Mapa
{
// Si descomponemos cuada cuadrícula en 8119 posiciones,
// el avance en diagonal supondrá 5741 posiciones en cada eje.
// Estos valores minimizan el error de esta relación:
//
//      diagonal = lado / sqrt(2)
//
// Usamos coordenadas enteras para que la comparación de
// posiciones sea más sencilla.

public static final int LADO     = 8119,
                        DIAGONAL = 5741;

private String m_fichero;
private ArrayList<String> m_mapa = new ArrayList<>();
private Posicion m_inicio, m_objetivo;
private ArrayList<Posicion> m_camino;

/**
 * Construye un mapa para aplicar los algoritmos de búsqueda.
 * @param ficheroMapa Fichero que contiene el mapa.
 * @param inicio      Posición inicial.
 * @param objetivo    Posición objetivo.
 * @throws IOException
 */
public Mapa(String ficheroMapa, Posicion inicio, Posicion objetivo)
    throws IOException
{
    leerMapa(ficheroMapa);

    if(!posicionValida(inicio.getFila(), inicio.getColumna()))
    {
        throw new IllegalArgumentException(
                "La posición inicial no es válida.");
    }

    if(!posicionValida(objetivo.getFila(), objetivo.getColumna()))
    {
        throw new IllegalArgumentException(
                "La posición del objetivo no es válida.");
    }

    m_inicio   = inicio;
    m_objetivo = objetivo;
}

/**
 * Construye un mapa con un camino para visualizarlo por pantalla.
 * @param ficheroMapa Fichero que contiene el mapa.
 * @param ficheroCamino Fichero que contiene el camino.
 * @throws IOException
 */
public Mapa(String ficheroMapa, String ficheroCamino) throws IOException
{
    leerMapa(ficheroMapa);

    if(ficheroCamino != null)
        leerCamino(ficheroCamino);
}

private void leerMapa(String fichero) throws IOException
{
    int columnas = 0;
    m_fichero = fichero;
    BufferedReader in = new BufferedReader(new FileReader(fichero));
    String line;
    int iFila = 0;

    while((line = in.readLine()) != null)
    {
        line = line.trim();
        leerFila(line);

        if(columnas == 0)
            columnas = line.length();
        else if(columnas != line.length())
            throw new IOException("Las filas no tienen el mismo tamaño.");
    }

    in.close();
}

//------------------------------------------------------------------------
private void leerFila(String linea) throws IOException
{
    int length = linea.length();

    for(int j = 0; j < length; j++)
    {
        char c = linea.charAt(j);

        if(c != '.' && c != '#')
            throw new IOException("Celda errónea: "+ c);
    }

    m_mapa.add(linea);
}

/**
 * Indica un camino para dibujarlo.
 * @param camino Camino para dibujar.
 */
public void setCamino(ArrayList<Posicion> camino)
{
    m_camino = camino;
}

private void leerCamino(String fichero) throws IOException
{
    m_camino = new ArrayList<>();
    BufferedReader in = new BufferedReader(new FileReader(fichero));
    String line;

    while((line = in.readLine()) != null)
    {
        line = line.trim();

        if(line.length() > 0)
            m_camino.add(new Posicion(line));
    }

    in.close();
}

/**
 * Obtiene la posicion inicial de la búsqueda.
 * @return Posición inicial de la búsqueda.
 */
public Posicion getInicio()
{
    return m_inicio;
}

/**
 * Obtiene la posición objetivo de la búsqueda.
 * @return Posición objetivo de la búsqueda.
 */
public Posicion getObjetivo()
{
    return m_objetivo;
}

/**
 * Indica si una posición es válida, es decir, si está dentro de los
 * límites del mapa y no hay un obstáculo en ella.
 * @param fila    Fila de la posición.
 * @param columna Columna de la posición.
 * @return {@code true} si se trata de una posición válida.
 */
public boolean posicionValida(int fila, int columna)
{
    if(fila < 0 || columna < 0)
        return false;

    int filaCelda    = fila / LADO,
        columnaCelda = columna / LADO;

    if(filaCelda >= m_mapa.size())
        return false;

    String f = m_mapa.get(filaCelda);

    return columnaCelda >= 0 && columnaCelda < f.length()
           && f.charAt(columnaCelda) == '.';
}

/**
 * Convierte el mapa en texto.
 * @return Representación del mapa como texto.
 */
@Override public String toString()
{
    StringBuilder sb = new StringBuilder();

    for(String fila : m_mapa)
    {
        sb.append(fila);
        sb.append('\n');
    }

    return sb.toString();
}

private void dibujar(Graphics2D g, int width, int height)
{
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);

    g.setColor(Color.white);
    g.fillRect(0, 0, width, height);

    int numFilas    = m_mapa.size(),
        numColumnas = m_mapa.get(0).length();

    int d = 32,
        w = numColumnas * d,
        h = numFilas * d;

    double sx = (double)width / (double)(w+1),
           sy = (double)height / (double)(h+1),
           scale = Math.min(sx, sy);

    double tx = sy > sx ? 0 : (width - sy * w) / 2,
           ty = sx > sy ? 0 : (height - sx * h) / 2;

    g.transform(AffineTransform.getTranslateInstance(tx, ty));
    g.transform(AffineTransform.getScaleInstance(scale, scale));
    g.setColor(Color.gray);

    for(int i = 0; i < numFilas; i++)
    {
        String fila = m_mapa.get(i);

        for(int j = 0; j < numColumnas; j++)
        {
            char c = fila.charAt(j);

            if(c == '#')
            {
                int y = i * d,
                    x = j * d;

                g.fillRect(x, y, d, d);
            }
        }
    }

    g.setColor(Color.lightGray);
    g.setStroke(new BasicStroke(1));

    for(int i = 0; i <= numFilas; i++)
    {
        for(int j = 0; j <= numColumnas; j++)
        {
            int y = i * d,
                x = j * d;

            g.drawLine(x, 0, x, h);
            g.drawLine(0, y, w, y);
        }
    }

    if(m_camino != null && !m_camino.isEmpty())
    {
        Iterator<Posicion> it = m_camino.iterator();
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.red);
        Posicion p = it.next();
        dibujarPunto(g, p, d);

        while(it.hasNext())
        {
            Posicion q = it.next();
            dibujarPunto(g, q, d);
            dibujarLinea(g, p, q, d);
            p = q;
        }
    }
}

private void dibujarPunto(Graphics2D g, Posicion p, int d)
{
    int x = p.getColumna() * d / LADO,
        y = p.getFila()    * d / LADO;

    g.fillOval(x-8, y-8, 16, 16);
}

private void dibujarLinea(Graphics2D g, Posicion p, Posicion q, int d)
{
    g.drawLine(p.getColumna() * d / LADO,
               p.getFila()    * d / LADO,
               q.getColumna() * d / LADO,
               q.getFila()    * d / LADO);
}

/**
 * Muestra el mapa en una ventana.
 */
public void mostrar()
{
    EventQueue.invokeLater(() ->
    {
        JFrame f = new JFrame(m_fichero);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JComponent c = new JComponent()
        {@Override public void paint(Graphics g)
        {
            Dimension d = getSize();
            dibujar((Graphics2D)g, d.width, d.height);
        }};

        f.getContentPane().add(c);
        f.setSize(1000, 700);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    });
}

/**
 * Muestra un mapa y opcionalmente un camino guardados en ficheros.
 * @param args Parametros.
 * @throws java.io.IOException
 */
public static void main(String args[]) throws IOException
{
    if(args.length == 0)
    {
        System.out.println("Parámetros: mapa [camino]");
        System.exit(0);
    }

    String mapa   = args[0],
           camino = args.length==2 ? args[1] : null;

    Mapa m = new Mapa(mapa, camino);
    System.out.println(m);
    m.mostrar();
}

} // Mapa
