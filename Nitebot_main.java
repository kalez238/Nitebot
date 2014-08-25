import org.jibble.pircbot.*;

public class Nitebot_main {
    
    public static void main(String[] args) throws Exception {
        
        // Now start our bot up.
        Nitebot bot = new Nitebot();
        
        // Enable debugging output.
        bot.setVerbose(true);

        // Connect to the IRC server.
        bot.connect("irc.snoonet.org");

        // Join the channel.
        bot.joinChannel("#anw");
        
    }
    
}
