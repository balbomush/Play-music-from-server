import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Client {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static DataInputStream in; // поток чтения из сокета
    //private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) throws IOException {
        try{
            clientSocket = new Socket("localhost", 4004);
            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new DataInputStream(clientSocket.getInputStream());
            byte byteArray[] = new byte[1024];
            //ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            //ArrayList<byte[]> arrayList = new ArrayList<>();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //String bytes = new String();
            int count = 0;
            while ((count=in.read(byteArray)) != -1){
                outputStream.write(byteArray,0,count);
            }
            //outputStream.close();
            System.out.println("start play");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            InputStream potok = new BufferedInputStream(inputStream);
            AudioDevice auDev = new JavaSoundAudioDevice();
            AdvancedPlayer explay = new AdvancedPlayer(potok,auDev);
            explay.play();
            //System.out.println(bytes);
            //InputStream stream = new ByteArrayInputStream(bytestring.getBytes(StandardCharsets.UTF_8));
            //AudioDevice auDev = new JavaSoundAudioDevice();
            //AdvancedPlayer explay = new AdvancedPlayer(stream,auDev);
            //explay.play();
            //out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            //String serverWord = in.readLine(); // ждём, что скажет сервер
            //System.out.println(serverWord);

            //System.out.println("Вы что-то хотели сказать? Введите это здесь:");
            // если соединение произошло и потоки успешно созданы - мы можем
            //  работать дальше и предложить клиенту что то ввести
            // если нет - вылетит исключение
            //String word = reader.readLine(); // ждём пока клиент что-нибудь
            // не напишет в консоль
           // out.write("word" + "\n"); // отправляем сообщение на сервер
            //out.flush();


        } catch (Exception e){
            System.out.println(e);
        }
        finally {
            clientSocket.close();
            in.close();
            //out.close();
        }
    }
}