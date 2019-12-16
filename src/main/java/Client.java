import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.*;
import java.net.Socket;
import java.util.zip.GZIPInputStream;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static DataInputStream in;

    public static void main(String[] args) throws IOException {
        try{
            clientSocket = new Socket("localhost", 4004);
            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new DataInputStream(clientSocket.getInputStream());

            byte byteArray[] = new byte[1024];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int count = 0;
            while ((count=in.read(byteArray)) != -1){
                outputStream.write(byteArray,0,count);
            }
            System.out.println(outputStream.size());

            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            ByteArrayOutputStream tmpout = new ByteArrayOutputStream();
            count = 0;
            System.out.println("start decompressed");
            while ((count=gzipInputStream.read(byteArray)) != -1){
                tmpout.write(byteArray,0,count);
            }
            System.out.println("end decompressed");
            inputStream = new ByteArrayInputStream(tmpout.toByteArray());
            System.out.println("start play");
            InputStream potok = new BufferedInputStream(inputStream);
            AudioDevice auDev = new JavaSoundAudioDevice();
            AdvancedPlayer explay = new AdvancedPlayer(potok,auDev);
            explay.play();


        } catch (Exception e){
            System.out.println(e);
        }
        finally {
            clientSocket.close();
            in.close();
        }
    }
}