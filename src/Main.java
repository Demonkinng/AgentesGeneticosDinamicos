/**
 * Clase principal que inicia el contenedor
 * @author Angel Chuncho
 * @version 1.0, 14/05/2024
 */
public class Main {
    public static void main(String[] args) {
        // Crear un contenedor con el puerto 1099, los valores no especificados se asignan automaticamente
        new contenedor.Contenedor().crearContenedor(null, 1099, null);
    }
}