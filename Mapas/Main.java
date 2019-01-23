package mapau;

import java.io.*;

/**
 * Implementación de varios algoritmos para buscar caminos en mapas
 * simplificados.
 */
public class Main
{
private static long milisInicio;
private static int  nodosExplorados;

private static Estado busquedaAnchura(Estado inicial)
{
    throw new UnsupportedOperationException("Falta implementar");
}

private static Estado busquedaProfundidad(Estado inicial, int limite)
{
    throw new UnsupportedOperationException("Falta implementar");
}

private static Estado busquedaProfundidadIterativa(Estado inicial, int limite)
{
    throw new UnsupportedOperationException("Falta implementar");
}

private static Estado busquedaHeuristicaLineaRecta(Estado inicial)
{
    throw new UnsupportedOperationException("Falta implementar");
}

private static Estado busquedaHeuristicaDiagonal(Estado inicial)
{
    throw new UnsupportedOperationException("Falta implementar");
}

public static void main(String args[]) throws IOException
{
    String algoritmos[] = {
        "Búsqueda en anchura",
        "Búsqueda en profundidad",
        "Búsqueda en profundidad iterativa",
        "Búsqueda con heurística de distancia en línea recta",
        "Búsqueda con heurística de distancia en diagonal"
    };

    if(args.length != 6)
    {
        System.out.println("Parametros:  algoritmo ficheroMapa "+
                           "filaInicio columnaInicio filaFin columnaFin");
        System.exit(0);
    }

    int    ip       = 0,
           iAlg     = Integer.parseInt(args[ip++]);
    String fichMapa = args[ip++];
    int    filaIni  = Integer.parseInt(args[ip++]),
           colIni   = Integer.parseInt(args[ip++]),
           filaFin  = Integer.parseInt(args[ip++]),
           colFin   = Integer.parseInt(args[ip++]);

    Posicion pIni = Posicion.get(filaIni, colIni),
             pFin = Posicion.get(filaFin, colFin);
    Mapa     mapa = new Mapa(fichMapa, pIni, pFin);

    Estado inicial = new Estado(mapa),
           result  = null;

    if(iAlg < 0 || iAlg > 4)
        throw new IllegalArgumentException("Algoritmo erróneo: "+ iAlg);

    System.out.println(algoritmos[iAlg]);
    milisInicio = System.currentTimeMillis();

    switch(iAlg)
    {
        case 0:
            result = busquedaAnchura(inicial);
            break;
        case 1:
            result = busquedaProfundidad(inicial, 30);
            break;
        case 2:
            result = busquedaProfundidadIterativa(inicial, 30);
            break;
        case 3:
            result = busquedaHeuristicaLineaRecta(inicial);
            break;
        case 4:
            result = busquedaHeuristicaDiagonal(inicial);
            break;
        default:
            throw new RuntimeException("Algoritmo erróneo: "+ iAlg);
    }

    if(result == null)
        System.out.println("No se ha encontrado el camino.");
    else
        mapa.setCamino(result.getCamino());

    long milis = System.currentTimeMillis() - milisInicio;
    System.out.println("Nodos explorados: "+ nodosExplorados);
    System.out.println("    Milisegundos: "+ milis);
    System.out.println("       Distancia: "+ result.getProfundidad());
    mapa.mostrar();
}

} // Main
