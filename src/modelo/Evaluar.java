package modelo;

import java.io.Serializable;

/**
 * Clase que se ideal para realizar la evaluación de las iteracciones.
 * @author Angel Chuncho
 * @version 1.0, 14/05/2024
 */
public class Evaluar implements Serializable {
    private int iteraccion;
    private int [] xy;
    private int coincidencias;

    public Evaluar() {
    }

    public Evaluar(int iteraccion, int[] xy, int coincidencias) {
        this.iteraccion = iteraccion;
        this.xy = xy;
        this.coincidencias = coincidencias;
    }

    public int getIteraccion() {
        return iteraccion;
    }

    public void setIteraccion(int iteraccion) {
        this.iteraccion = iteraccion;
    }

    public int[] getXy() {
        return xy;
    }

    public void setXy(int[] xy) {
        this.xy = xy;
    }

    public int getCoincidencias() {
        return coincidencias;
    }

    public void setCoincidencias(int coincidencias) {
        this.coincidencias = coincidencias;
    }
}
