import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

public class Server {

    private static Socket clientSocket;
    private static ServerSocket server;
    private static DataOutputStream out;

    public static void main(String[] args) throws IOException {
        try{
            server = new ServerSocket(4004);
            clientSocket = server.accept();
            System.out.println("Connected");
            out = new DataOutputStream(clientSocket.getOutputStream());
            BufferedInputStream file = new BufferedInputStream(new FileInputStream("src/main/resources/example.mp3"));
            ByteArrayOutputStream outtmp = new ByteArrayOutputStream();
            GZIPOutputStream compression = new GZIPOutputStream(outtmp);
            byte[] byteArray = new byte[1024];
            int len;
            while ((len=file.read(byteArray)) != -1){
                compression.write(byteArray,0,len);
            }
            compression.close();
            System.out.println(outtmp.size());
            ByteArrayInputStream intmp = new ByteArrayInputStream(outtmp.toByteArray());
            while (true){
                int local = intmp.read(byteArray);
                if (local != -1) {
                    out.write(byteArray,0,local);
                }
                else{
                    break;
                }
            }
            out.flush();
            file.close();

        } catch (Exception e){
            System.out.println(e);
        }
        finally {
            server.close();
            out.close();
        }
    }
}