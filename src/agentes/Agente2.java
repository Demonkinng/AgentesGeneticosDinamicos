package agentes;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import modelo.Evaluar;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Clase que se encarga de la configuracion del Agente 2.
 * El agente 2 se encarga de recibir el resultado del agente 1 y mostrar el mensaje en pantalla.
 * @author Angel Chuncho
 * @version 1.0, 14/05/2024
 */
public class Agente2 extends Agent {
    // Cuerpo del agente usando la libreria
    /**
     * Configuracion del agente.
     * Se le asigna un comportamiento al agente como una instancia.
     */
    @Override
    protected void setup(){
        addBehaviour(new Comportamiento());
    }

    /**
     * Clase o subclase que se encarga del comportamiento del Agente 2.
     * Presenta metodos encargados de recibir el resultado del agente 1 y mostrar el mensaje en pantalla.
     * Hereda de la libreria pero especificando el tipo (simple o cyclic), parallel se configura en setup().
     */
    class Comportamiento extends Behaviour {
        /**
         * Metodo que se encarga de la accion del agente, lo que se desea que el agente haga.
         * En este caso, se encarga de recibir el resultado del agente 1, evaluar y mostrar el mensaje en pantalla.
         */
        @Override
        public void action(){
            // Bloquear la recepcion de mensajes a los agentes que no inician la conversacion
            ACLMessage aclmsj = blockingReceive();

            try {
                // Convertir el contenido del mensaje de objeto serializado a ArrayList<Evaluar>
                ByteArrayInputStream byteStream = new ByteArrayInputStream(aclmsj.getByteSequenceContent());
                ObjectInputStream objectStream = new ObjectInputStream(byteStream);
                ArrayList<Evaluar> ev = (ArrayList<Evaluar>) objectStream.readObject();

                // Mostrar cada elemento del ArrayList
                System.out.println("\nResultado recibido del Agente 1:");
                for (Evaluar evaluacion : ev) {
                    System.out.println("Iteración: " + evaluacion.getIteraccion());
                    System.out.println("X: " + evaluacion.getXy()[0]);
                    System.out.println("Y: " + evaluacion.getXy()[1]);
                    System.out.println("Coincidencias: " + evaluacion.getCoincidencias());
                    System.out.println("---------------------------");
                    if (evaluacion.getCoincidencias() >= 2) {
                        // Nota: considero que la iteraccion optima seria mostrar desde donde se obtiene la primera coincidencia
                        // sin embargo, para fines practicos se muestra la ultima iteraccion con una coincidencia de 3.
                        System.out.println("Iteracción optima: " + evaluacion.getIteraccion());
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        /**
         * Metodo que se encarga de la finalizacion del agente.
         * @return  True si el agente finalizo, False en caso contrario.
         */
        @Override
        public boolean done() {
            return true;
        }
    }
}
