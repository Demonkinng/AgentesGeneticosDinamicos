package Agenetico;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 * Clase que se encarga de la funcion de aptitud del Algoritmo Genetico (AG).
 * Importante graficar la longitud del gen para una mejor visualizacion.
 * La descomposicion me lleva a algo.
 * Ojo que la ecuacion puede ser un insumo de otra funcion (ejm: numeros primos del score).
 * @author Angel Chuncho
 * @version 1.0, 08/05/2024
 */
public class FunAptitud extends FitnessFunction {
    /**
     * Crea y evalua la funcion de aptitud del AG (funcion euristica).
     * @param ic    Cromosoma a evaluar.
     * @return      Puntaje de la funcion de aptitud.
     */
    @Override
    protected double evaluate(IChromosome ic) {
        // Puntaje de la funcion de aptitud
        double score = 0;
        // Del individuo obtengo el gen de la posicion cero, en este caso el signo de x
        int sx = (int) ic.getGene(0).getAllele();
        // Del individuo obtengo el gen de la posicion seis, en este caso el signo de y
        int sy = (int) ic.getGene(6).getAllele();

        // Despedazo el cromosoma en partes para obtener algo
        String valorX = (int) ic.getGene(1).getAllele() + "" +
                        (int) ic.getGene(2).getAllele() + "" +
                        (int) ic.getGene(3).getAllele() + "" +
                        (int) ic.getGene(4).getAllele() + "" +
                        (int) ic.getGene(5).getAllele();
        String valorY = (int) ic.getGene(7).getAllele() + "" +
                        (int) ic.getGene(8).getAllele() + "" +
                        (int) ic.getGene(9).getAllele() + "" +
                        (int) ic.getGene(10).getAllele() + "" +
                        (int) ic.getGene(11).getAllele();

        // Convierto el valor binario, de x e y, a entero
        int valX = Integer.parseInt(valorX, 2);
        int valY = Integer.parseInt(valorY, 2);

        // Si los signos son 0, entonces los valores son negativos
        if(sx == 0) valX = valX * -1;
        if(sy == 0) valY = valY * -1;

        // Se calcula el puntaje acorde a la funcion de aptitud X^3 + Y^4
        score = (valX * valX * valX) + (valY * valY * valY * valY);
        // Si el puntaje es negativo, se asigna cero
        if (score < 0) score = 0;
        return score;
    }
}