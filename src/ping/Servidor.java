package ping;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
/**
 * Clase para el servido
 */

public class Servidor {
    
    
    public static void main(String[] args) throws SocketException, IOException {
        
 
        
            // capacidad del paquete.
            byte[] mensaje_bytes = new byte[256];
            
            // Donde se enpaqueta el mensage rescivido del cliente
            DatagramPacket paquete = new DatagramPacket(mensaje_bytes,256);

            //Iniciamos el bucle
            do {
                //Creamos el socket UDP
                DatagramSocket socket = new DatagramSocket(6000);
                // Recibimos el paquete
                socket.receive(paquete);
                
                // Se genera el numero aleaatorio entre 1 y 10 
                int perdida= (int) Math.floor(Math.random()*10+1);
                
                System.out.println("Resivido_________"+perdida);
                if(perdida<5){ // si la perdida es mayor a 5 no se envia una respuesta al cliente.
                    // se pasa el mensage a mayusculas
                    String mensaje = new String(mensaje_bytes).trim().toUpperCase();
                
                    //Obtenemos IP Y PUERTO               
                    int puerto = paquete.getPort();
                    InetAddress IP = paquete.getAddress();
 
                    //formateamos el mensaje de salida
                    byte[] mensaje2_bytes = mensaje.getBytes();
 
                    //Preparamos el paquete que queremos enviar
                    DatagramPacket envpaquete = new DatagramPacket(mensaje2_bytes,mensaje.length(),IP,puerto);
                    // realizamos el envio
                    socket.send(envpaquete);
                    
                }
                socket.close(); // se cierra la conexión
            } while (true);
            
        
    }
}
