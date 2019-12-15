import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
  //  private static BufferedReader in; // поток чтения из сокета
    private static DataOutputStream out; // поток записи в сокет

    public static void main(String[] args) throws IOException {
        try{
            server = new ServerSocket(4004);
            clientSocket = server.accept();
            System.out.println("Connected");
            //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new DataOutputStream(clientSocket.getOutputStream());
            BufferedInputStream file = new BufferedInputStream(new FileInputStream("C:\\Labs\\untitled\\example.mp3"));
            byte[] byteArray = new byte[1024];
            while (true){
                int local = file.read(byteArray);
                //System.out.println(byteArray);
                //System.out.println(local);
                if (local != -1) {
                    out.write(byteArray,0,local);
                }
                else{
                    break;
                }
            }
            out.flush();
            file.close();
            //out.close();


            //out.flush(); // выталкиваем все из буфера
            //String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
            //System.out.println(word);
            // не долго думая отвечает клиенту


        } catch (Exception e){
            System.out.println(e);
        }
        finally {
            server.close();
            //in.close();
            out.close();
        }
    }
}