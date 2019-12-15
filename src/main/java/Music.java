import java.io.*;
import javazoom.jl.player.advanced.*;
import javazoom.jl.player.*;

public class Music {

    public static AdvancedPlayer explay;
    public static String muss = "C:\\Labs\\untitled\\example.mp3";

    public static void main(String[] args) {

        try{
            InputStream potok = new FileInputStream(muss);
            AudioDevice auDev = new JavaSoundAudioDevice();
            explay = new AdvancedPlayer(potok,auDev);
            explay.play();
        }catch(Exception err){err.printStackTrace();}
    }
}