package agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que se encarga de la comunicacion entre agentes.
 * Se encarga de enviar mensajes entre agentes.
 * El mensaje se envia a un agente receptor, tiene un id y puede ser de tipo REQUEST o INFORM.
 * El contenido del mensaje puede ser un objeto serializable o un string.
 * @author Angel Chuncho
 * @version 1.0, 08/05/2024
 */
public class Comunicacion {
    /**
     * Envia un mensaje entre agentes.
     * En el receptor solo me interesa su alias por eso no se usa tipo Agent como en el receptor.
     * @param tipoMSJ           Tipo de mensaje (REQUEST, INFORM).
     * @param emisor            Agente emisor.
     * @param receptor          Alias del agente receptor.
     * @param contenidoStr      Contenido del mensaje en string.
     * @param contenidoObj      Contenido del mensaje en objeto serializable.
     * @param conversationId    Identificador de la conversacion.
     */
    public static void msj(int tipoMSJ, Agent emisor, String receptor, String contenidoStr, Serializable contenidoObj, String conversationId){
        ACLMessage acl = new ACLMessage(tipoMSJ);
        AID receptorID = new AID();
        receptorID.setLocalName(receptor);
        acl.addReceiver(receptorID);
        acl.setSender(emisor.getAID());
        acl.setLanguage(FIPANames.ContentLanguage.FIPA_SL);

        // No se puede enviar dos mensajes de diferentes tipos al mismo tipo
        if(contenidoStr == null){
            try {
                acl.setContentObject(contenidoObj);
            } catch (IOException ex) {
                Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            acl.setContent(contenidoStr);
        }
        acl.setConversationId(conversationId);
        // Se envia el mensaje
        emisor.send(acl);
    }
}
