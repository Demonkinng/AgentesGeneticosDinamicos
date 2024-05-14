package contenedor;

import agentes.Agente1;
import agentes.Agente2;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que crea el contenedor y los agentes.
 * @author Angel Chuncho
 * @version 1.0, 08/05/2024
 */
public class Contenedor {
    /**
     * Metodo para crear el contenedor.
     * @param host direccion IP del host.
     * @param port puerto.
     * @param name nombre del contenedor.
     */
    public void crearContenedor(String host, int port, String name) {
        // Crea un nuevo proceso
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        // Perfil del contenedor
        Profile profile = new ProfileImpl(host, port, name);
        // Para pasar como objeto el contenedor al metodo que crea los agentes
        // Se crea un contenedor principal
        AgentContainer contenedorPrincipal = runtime.createMainContainer(profile);
        crearAgentes(contenedorPrincipal);
    }

    /**
     * Metodo para crear los agentes.
     * @param contenedorPrincipal contenedor principal donde se alojan los agentes.
     */
    public void crearAgentes(AgentContainer contenedorPrincipal){
        try {
            // Inicio los hilos, los agentes se crean en orden inverso al proceso de comunicacion de la arquitectura
            contenedorPrincipal.createNewAgent("Ag2", Agente2.class.getName(), null).start();
            contenedorPrincipal.createNewAgent("Ag1", Agente1.class.getName(), null).start();
        } catch (StaleProxyException ex) {
            Logger.getLogger(Contenedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
