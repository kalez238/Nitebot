import java.util.TimerTask;

public class NiteTimerTask extends TimerTask {
    
    private Nitebot bot;
    private String nick;
    private String channel;
	//public boolean cancel();
	//public boolean run();
    
    public NiteTimerTask(Nitebot bot, String nick, String channel) {
        this.bot = bot;
        this.nick = nick;
        this.channel = channel;
    }
	
	//public boolean ticking = bot.isTicking(); causes NiteTimerTask to not work...
    
    public void run( ) {
		String groupy = bot.getGroupy();
		int duration = bot.getDuration();
		boolean waiting = bot.isWaiting();
		
		if (waiting) {
			if (!groupy.equals("")) { // If the ping list isn't empty, say them
				bot.sendMessage(channel, groupy + " and " + nick + ", your " + duration + " minute(s) start now!");
			}
			else { // If the ping list is empty, dont say them
				bot.sendMessage(channel, nick + ", your " + duration + " minute(s) start now!");
			}
		}
		else {
			if (!groupy.equals("")) {
				bot.sendMessage(channel, groupy + " and " + nick + ", pencils down!");
			}
			else {
				bot.sendMessage(channel, nick + ", pencils down!");
			}
		}
		bot.setTicking(false);
		bot.setGroupy("");
		bot.setWaiting(false);
    }
    
}
