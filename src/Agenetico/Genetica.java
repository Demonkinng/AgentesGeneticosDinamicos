package Agenetico;

import modelo.Evaluar;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import java.util.ArrayList;

/**
 * Clase que se encarga de la configuracion del Algoritmo Genetico (AG), la creacion de la poblacion y la evolucion de la misma.
 * La especie esta dada por la longitud del cromosoma.
 * El cromosoma esta definido por los genes (funcion matematica) (representacion en binario, depende el caso).
 * El cromosoma y el gen necesitan saber la configuracion para poder ser creados y luego muten acorde a la misma.
 * La poblacion se conoce como genotipo (Genotype).
 * @author Angel Chuncho
 * @version 1.0, 14/05/2024
 */
public class Genetica {
    /**
     * Configura el Algoritmo Genetico (AG).
     * @param tamanioPoblacion  Tamaño de la poblacion.
     * @param longGene          Longitud del gen.
     * @return                  Configuracion del AG.
     */
    public Configuration configurarAG(int tamanioPoblacion, int longGene) {
        try {
            // Configuración por defecto:
            //      1. Seleccion de los padres de manera aleatoria.
            //      2. Cruce por punto, es decir, una del padre y una de la madre, aleatorio.
            //      3. Mutacion aleatoria de un solo gen.
            Configuration conf = new DefaultConfiguration();
            // Se setea la funcion de aptitud
            conf.setFitnessFunction(new FunAptitud());
            // Indicar el tamaño de la poblacion
            conf.setPopulationSize(tamanioPoblacion);
            // Se crea un cromosoma de ejemplo, el parametro 2 es un arreglo de genes
            Chromosome ejemplo = new Chromosome(conf, genesLong(longGene, conf));
            // Se setea el cromosoma de ejemplo
            conf.setSampleChromosome(ejemplo);
            return conf;
        } catch (InvalidConfigurationException e) {
            return null;
        }
    }

    /**
     * Crea una poblacion (Genotype) aleatoria.
     * @param conf  Configuracion del AG.
     * @return      Poblacion aleatoria.
     */
    public Genotype get_Poblacion(Configuration conf) {
        try {
            return Genotype.randomInitialGenotype(conf);
        } catch (InvalidConfigurationException e) {
            return null;
        }
    }

    /**
     * Evoluciona la poblacion un numero de veces dado.
     * @param poblacion         Poblacion a evolucionar.
     * @param numeroEvoluciones Numero de evoluciones.
     * @param numeroIteraccion  Numero de iteracciones.
     * @return                  Lista con la informacion de cada iteracion.
     */
    public ArrayList<Evaluar> evolucionar(Genotype poblacion, int numeroEvoluciones, int numeroIteraccion) {
        ArrayList<Evaluar> ev = new ArrayList<>();
        int[] xy = descomponerIndividuo(poblacion.getFittestChromosome());
        int coincidencias = 0;
        int x = xy[0];
        int y = xy[1];

        System.out.println("Poblacion Inicial: ");
        descomponerTodos(poblacion.getChromosomes());
        System.out.println("Mejor Inicial: " + xy[0] + " ; " + xy[1]);
        System.out.println("----------");

        for (int i = 0; i < numeroIteraccion; i++) {
            poblacion.evolve(numeroEvoluciones);
            xy = descomponerIndividuo(poblacion.getFittestChromosome());
            if (xy[0] == x && xy[1] == y) {
                coincidencias++;
            } else {
                x = xy[0];
                y = xy[1];
                coincidencias = 0;
            }
            // Se agrega la información de cada iteración a la lista
            Evaluar evaluacion = new Evaluar((i+1), xy, coincidencias);
            ev.add(evaluacion);
        }
        // Se descompone el mejor individuo de la poblacion
        xy = descomponerIndividuo(poblacion.getFittestChromosome());
        System.out.println("Mejor de Todo: " + xy[0] + " ; " + xy[1]);
        System.out.println("----------\n");
        return ev;
    }

    /**
     * Descompone cada individuo de la poblacion.
     * @param ics   Individuos de la poblacion.
     */
    private void descomponerTodos(IChromosome[] ics) {
        int [] xy;
        for (IChromosome ic : ics) {
            xy = descomponerIndividuo(ic);
            System.out.println("Individuo: " + xy[0] + " ; " + xy[1]);
        }
    }

    /**
     * Descompone un individuo de la poblacion en sus partes (acorde a la funcion de aptitud).
     * @param ic    Individuo de la poblacion.
     * @return      Arreglo con las partes del individuo.
     */
    private int[] descomponerIndividuo(IChromosome ic) {
        // Arreglo que contendra las partes del individuo
        int [] xy = new int[2];
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

        // Guardo los valores en el arreglo
        xy[0] = valX;
        xy[1] = valY;
        return xy;
    }

    /**
     * Crea un arrelo de genes de tipo entero, con genes binarios, es decir, 0 o 1.
     * Esto depende de como se quiere construir, en este caso se decidio por binarios.
     * @param longGene  Longitud del gen.
     * @param conf      Configuracion del AG.
     * @return          Arreglo de genes.
     */
    private Gene[] genesLong(int longGene, Configuration conf) {
        // Arreglo de genes
        Gene[] genes = new Gene[longGene];

        // Creacion de los genes
        for (int i = 0; i < longGene; i++) {
            try {
                // Cada gen es binario y deben llevar la configuracion del AG
                genes[i] = new IntegerGene(conf, 0, 1);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return genes;
    }
}