package ping;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 * Clase para el Cliente
 */
public class Cliente {
    
    public static void main(String[] args) {
        
        
        String DireccionServidor="172.30.12.41"; //dirección servidor
        int puerto=6000;//Puerto del servidor
        
        //se crea un soket UDP
        DatagramSocket socket;
        InetAddress IpServidor;//direccion
        
        String mensaje="ping"; // mensage que va a ser enviado
        byte[] mensaje_bytes=mensaje.getBytes();// Conversion del mensage a bytes
        
        //Paquete
        DatagramPacket paquete;
 
        DatagramPacket servPaquete;
 
        byte[] RecogerServidor_bytes = new byte[256];
 
        try {
            //se crea la conexión con el servidor
            socket = new DatagramSocket();
            socket.setSoTimeout(1000);// se define el tiempo de espera maximo
            
            IpServidor=InetAddress.getByName(DireccionServidor);//combierte en ip la dirección
            
            for (int i = 0; i < 10; i++) { // envia los 10 Ping al servidor
                //Se enpaqueta el mensaje
                paquete = new DatagramPacket(mensaje_bytes,mensaje.length(),IpServidor,puerto);
                //Se toma el tiempo en que se envio el paquete
                double time= System.currentTimeMillis();
                // se envia el paquete
                socket.send(paquete);
                
                RecogerServidor_bytes = new byte[256];
 
                //Esperamos a recibir un paquete
                servPaquete = new DatagramPacket(RecogerServidor_bytes,256);
                try{
                socket.receive(servPaquete);//Se recibe el paquete del servidor
                //Calculamos el tiempo transcurrido
               time= System.currentTimeMillis()- time;
                
                //Convertimos el mensaje recibido en un string
                String mensajeRecibido = new String(RecogerServidor_bytes).trim();
 
                //Imprimimos el paquete recibido con el tiempo trancurrido
                System.out.println((i+1)+" "+mensajeRecibido+ " time:"+ (time/1000)+"s");
                }catch(Exception e){
                    //Si se termina el tiempo de espera, se da por perdido el paquete
                    System.out.println("Paquete "+(i+1)+" Perdido");
                }
            }
 
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
