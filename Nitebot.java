import org.jibble.pircbot.*;
import java.util.Timer;

// any /*sendMessage()*/ found are for testing purposes to check variables at any given calculation

public class Nitebot extends PircBot {
	
    private Timer timer = new Timer(true);
	
	public String channel;
	public int timey = 0;
	public int wpmy = 0;
	public long checky; // for !check
	public long clocky; // Initial current time grabber for !check
	public long clocky2; // calc variable for after duration, wait, and seconds are calc'ed for !check
	public long clocky3; // New current timer grabber for !check
	public long tocky; // calc variable for !check
	public long ticky; // calc variable for !check
	public int waity = 1; // standard wait time
	public String groupy = ""; // ping list starts out empty
	public int duration; // duration for NTT;
	public String kalez; // for when only I should call a command
	public String joiny = "no"; // for grabbing the name of the person who just entered
	
	public boolean ticking = false; // is a timed session running? false = no
	public boolean waiting = false; // is a waiting session running? false = no
	
	public boolean kchaty = false; // is Poad accepting pms right now? false=no true=yes. need to be changed
	public String kwhoy; // who am i talking to through Poad? channel or person
	public int quity; // did i tell Poad to quit? 1=yes. doesnt work...

	public String timeSender; // person who started the timer
	public Long ctmo; // get current original time
	public Long rtTick; // reduce that time to seconds and add duration in seconds
	public Long ctmn; // get new current time
	public Long rtTock; //
	public Long rtTime;
	
	public String nicky;
	
    public Nitebot() {
        this.setName("Poad_"); // bot's name
		sendMessage("NickServ", "identify poad2000");
    }
	// public final void changeNick(String Poad, String message) {
		// message = message.trim( ).toLowerCase( );
		// if (message.equalsIgnoreCase("!nick")) {
			// changeNick(Poad);
		// }
	// }
	// protected void onDisconnect() {
		// String allnightwriters = "#allnightwriters";
		// if (quity == 1) {
		// }
		// else {
			// reconnect(IOException, IrcException, NickAlreadyInUseException);
		// }
	// }
		
	public void onJoin(String channel, String sender, String login, String hostname) {
		joiny = login; // grab the name of the person who just entered chat
	}
	
	protected void onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel) {
		sendMessage("kalez",sourceNick + " has invited me to " + channel);
		sendMessage(sourceNick, "Thanks for the invite. I will inform my controller.");
	}
	
    public void onPrivateMessage(String sender, String login, String hostname, String message) {

		String messageIC = message.trim( ).toLowerCase( );

		if (sender.equals("kalez") || login.equals("failsoft")) {
		
			if (message.equalsIgnoreCase("!chat ")) {
				String chatty = message.substring(6);
				if (chatty.equals("on")) {
					kchaty = true;
				}
				if (chatty.equals("off")) {
					kchaty = false;
				}
			}

			if (messageIC.startsWith("!who ")) {
				kwhoy = message.substring(5);
			}
			
			if (messageIC.startsWith("!say ")) {
				String ksayy = message.substring(5);
				sendAction("kalez","<To " + kwhoy + "> " + ksayy);
				sendMessage(kwhoy,ksayy);
			}

			if (messageIC.startsWith("!act ")) {
				String kacty = message.substring(5);
				sendAction("kalez","<Mo " + kwhoy + "> " + kacty);
				sendAction(kwhoy,kacty);
			}
			
			if (messageIC.startsWith("!join ")) {
				String kjoiny = message.substring(6);
				joinChannel(kjoiny);
			}
			
			if (messageIC.startsWith("!part ")) {
				String kparty = message.substring(6);
				partChannel(kparty);
			}
			
			if (messageIC.startsWith("!quit ")) {
				String kquity = message.substring(6);
				quity = 1;
				quitServer(kquity);
			}
		} // kalez if
		else {
			if (kchaty == false) {
				sendMessage("kalez", "<OFF>< " + sender + "> " + message);
				sendMessage(sender, "I am not responding to private messages at this time.");
			}
			else {
				sendMessage("kalez","<" + sender + "> " + message);
			}
		}
	} // onPM

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
		
		channel = channel;
			
		//String startsWithIgnoreCase = startsWith.trim( ).toLowerCase( );
		//String containsIgnoreCase = contains.trim( ).toLowerCase( );
		String messageIC = message.trim( ).toLowerCase( );
		
		if (messageIC.startsWith("!quit ")) {
			if (login.equals("failsoft") || login.equals("Seigio") || login.equals("Penguinatio")) {
				String kquity = message.substring(6);
				quity = 1;
				quitServer(kquity);
			}
			else {
				sendMessage(channel, "I don't think you have permission to do that. (Ops must be signed in)");
			}
		} // quit if
		
		if (message.equalsIgnoreCase("!login")) {
			sendMessage("NickServ", "identify poad2000");
		}

        if (message.equalsIgnoreCase("!test")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }
		
        if (message.equalsIgnoreCase("!n")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ", I am updated.");
        }

		if (message.startsWith("+1 ")) {
			String namey = message.substring(3);
			sendMessage(channel, namey + " gained 1 internet. Bravo.");
        }
		
		if (message.startsWith("-1 ")) {
			String namey = message.substring(3);
			sendMessage(channel, namey + " lost an internet. Sucks to be you.");
        }
		
		if (message.startsWith("!summon ")) {
			String summony = message.substring(8);
			sendAction(channel, "draws a pentagram on the floor and attempts to summon " + summony);
        }
		
		if (message.startsWith("!flip")) {
			sendAction(channel, "flips a coin");
			double flippy = Math.random();
			if (flippy < 0.5) {
				sendMessage(channel, "Heads");
			}
			else {			
				sendMessage(channel, "Tails");
			}
		}
		
		if (message.contains("r/")) {
			int reddichecky1;
			if (message.contains("/r/")) {
				reddichecky1 = message.indexOf("r/") - 2; // grab # of point before /r/
			}
			else {
				reddichecky1 = message.indexOf("r/") - 1; // grab # of point before r/
			}
			/*sendMessage(channel, "reddichecky1: " + reddichecky1); */
			if (reddichecky1 < 0) {
				reddichecky1 = 0; // if that point is neg/null, be 0
			}
			String reddichecky = message.substring(reddichecky1); // set reddichecky to everything from the point before r/
			/*sendMessage(channel, "reddichecky: " + reddichecky); */
			if (reddichecky.startsWith(" ") || message.startsWith("/") || reddichecky1 == 0) { 
				int reddity1 = message.indexOf("r/") + 2;
				String reddy = message.substring(reddity1);
				/* sendMessage(channel, "reddy: " + reddy); // */
				if (reddy.startsWith(" ")) {
				}
				else {
					reddy = reddy.replace(" ",",");
					int reddity2 = reddy.indexOf(",");
					String reddity;
					if (reddity2 < 0) {
						reddity = reddy.substring(0);
					}
					else {
						reddity = reddy.substring(0, reddity2);
					}
					/* sendMessage(channel, "reddy: " + reddy); // */
					if (reddity.contains(".")) {
						int reddity3 = reddity.indexOf(".");
						/* sendMessage(channel, "reddity3: " + reddity3); // */
						reddity = reddity.substring(0, reddity3);
						/* sendMessage(channel, "reddity: " + reddity); // */
					}
					sendMessage(channel, "Did you mean http://www.reddit.com/r/" + reddity + " ?");
				}
			} // empty if
			else {
				
			}
		} // /r/ if
		
		// if (message.contains("r/")) {
			// int reddity1 = message.indexOf("r/") + 2; // grab # of the point after r/
			// String reddy = message.substring(reddity1); // grab all text after r/. Any text before r/ is no longer valid.
			// reddy = reddy.replace(" ",","); // replace all spaces with commas. indexOf can have issues with spaces
			// int reddity2 = reddy.indexOf(","); // grab the # of the first comma
			// if (reddity2 < 0) { // if the first comma is non existant...
				// String reddity = reddy.substring(0); // ...don't bother with it
				// sendMessage(channel, "http://reddit.com/r/" + reddity); // send text to irc
			// }
			// else {
				// String reddity = reddy.substring(0, reddity2); // if the 1st comma place exists, end the string before it
				// sendMessage(channel, "Did you mean http://www.reddit.com/r/" + reddity + " ?"); // send text to irc
			// }
		// }
		
		if (messageIC.startsWith("!math ")) {
			message = message.substring(6); // grab message
			/*sendMessage(channel, "message: " + message); // */
			//waity = Integer.parseInt(message.substring(6));
			
			message = message.replace(" ",",");
			int calcsy1 = message.indexOf(","); // find first # stopping point
			/*sendMessage(channel, "calcsy1: " + calcsy1); // */
			
			String calcmy1 = message.substring(0, calcsy1); // number 1 start and stop
			/*sendMessage(channel, "calcmy1: " + calcmy1); // */
			int calcnoy1 = Integer.parseInt(calcmy1); // grab number 1
			/*sendMessage(channel, "calcnoy1: " + calcnoy1); // */
			
			message = message.substring(calcsy1 + 1);
			/*sendMessage(channel, "message: " + message); // */
			int calcsy2 = message.indexOf(","); // find stopping point
			/*sendMessage(channel, "calcsy2: " + calcsy2); // */
			
			String calcry = message.substring(0, calcsy2); // symbol
			/*sendMessage(channel, "calcry: " + calcry); // */
			String calcmy2 = message.substring(message.indexOf(",") + 1); // number 2 place
			/*sendMessage(channel, "calcmy2: " + calcmy2); // */
			int calcnoy2 = Integer.parseInt(calcmy2); // grab number 2
			/*sendMessage(channel, "calcnoy2: " + calcnoy2); // */
			
			int calcy;
			if (calcry.equals("+")) {
			calcy = calcnoy1 + calcnoy2;
						sendMessage(channel, "= " + calcy);
			}
			else if (calcry.equals("-")) {
			calcy = calcnoy1 - calcnoy2;
						sendMessage(channel, "= " + calcy);
			}
			else if (calcry.equals("*")) {
			calcy = calcnoy1 * calcnoy2;
						sendMessage(channel, "= " + calcy);
			}
			else if (calcry.equals("/")) {
			calcy = calcnoy1 / calcnoy2;
						sendMessage(channel, "= " + calcy);
			}
			else {
						sendMessage(channel, "Error. Format must be '# symbol #'.");
			}
		} // math
		
		if (messageIC.startsWith("!google ")) {
			String googley = message.substring(8);
			googley = googley.replace(" ","+");	
			sendMessage(channel, "https://www.google.com/search?q=" + googley);
		}
		
		if (messageIC.startsWith("!g ")) {
			String gy = message.substring(3);
			gy = gy.replace(" ","+");
			sendMessage(channel, "https://www.google.com/search?q=" + gy);
		}
		
		if (messageIC.startsWith("!lmgtfy ")) {
			String lmgtfyy = message.substring(8);
			lmgtfyy = lmgtfyy.replace(" ","+");
			sendMessage(channel, "http://lmgtfy.com/?q=" + lmgtfyy);
		}
		
		if (messageIC.startsWith("!w ")) {
			String wy = message.substring(3);
			wy = wy.replace(" ","_");
			sendMessage(channel, "http://en.wikipedia.org/wiki/" + wy);
		}
		
		if (messageIC.startsWith("!wiki ")) {
			String wikiy = message.substring(6);
			wikiy = wikiy.replace(" ","_");
			sendMessage(channel, "http://en.wikipedia.org/wiki/" + wikiy);
		}
		
		if (messageIC.startsWith("!t ")) {
			String ty = message.substring(3);
			ty = ty.replace(" ","+");
			sendMessage(channel, "http://thesaurus.com/browse/" + ty);
		}
		
		if (messageIC.startsWith("!thesaurus ")) {
			String thesy = message.substring(11);
			thesy = thesy.replace(" ","+");
			sendMessage(channel, "http://thesaurus.com/browse/" + thesy);
		}
		
		if (messageIC.startsWith("!d ")) {
			String dy = message.substring(3);
			dy = dy.replace(" ","+");
			sendMessage(channel, "http://dictionary.reference.com/browse/" + dy);
		}
		
		if (messageIC.startsWith("!dictionary ")) {
			String dicty = message.substring(12);
			dicty = dicty.replace(" ","+");
			sendMessage(channel, "http://dictionary.reference.com/browse/" + dicty);
		}
		
		if (messageIC.startsWith("!define ")) {
			String dicty = message.substring(8);
			dicty = dicty.replace(" ","+");
			sendMessage(channel, "http://dictionary.reference.com/browse/" + dicty);
		}
		
		if (messageIC.startsWith("!r ")) {
			String ry = message.substring(3);
			ry = ry.replace(" ","+");
			sendMessage(channel, "http://www.reference.com/browse/" + ry);
		}
		
		if (messageIC.startsWith("!reference ")) {
			String refery = message.substring(12);
			refery = refery.replace(" ","+");
			sendMessage(channel, "http://www.reference.com/browse/" + refery);
		}

		
		if (messageIC.startsWith("poad, take over") || messageIC.startsWith("Poad take over") || messageIC.startsWith("take over, poad") || messageIC.startsWith("take over poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
			if (sender.equals("kalez") || login.equals("failsoft")) {
				double randomosity = Math.random();
				if (randomosity <= 0.3) {
					sendAction(channel, "salutes");
				}
				else if (randomosity > 0.7) {
					sendAction(channel, "nods");
				}
				else {			
					sendMessage(channel, "No problem");
				}
			}
			else {
				sendAction(channel, "ignores you.");
			}
		} // take over if
		
		if (message.equalsIgnoreCase("poad is a bot") || message.equalsIgnoreCase("poad is my bot") || message.equalsIgnoreCase("poad is our bot")) {
			sendAction(channel,"is a cyborg.");
		}
		
		if (message.equalsIgnoreCase("!poke")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "Get that finger away from me.");
        }
		
		if (messageIC.startsWith("!poke ")) {
			String pokey = message.substring(6);
			try {
				Thread.sleep(1300);
			}  
			catch (InterruptedException e) {			
			}
			if (pokey.equals(sender)) {
				sendMessage(channel, "Whoa, no one wants to see that. Can't you do that in private?");
			}
			else if (message.equalsIgnoreCase("!poke poad")) {
				try {
					Thread.sleep(1000);
				}  
				catch (InterruptedException e) {			
				}
				sendMessage(channel, "No! I don't want it!");
			}
			else {
			sendMessage(channel, sender + " poked " + pokey + ". Kinky.");
			}
        } // poke if
		
		if (message.equalsIgnoreCase("!slay")) {
			double randomosity = Math.random();
			if (randomosity <= 0.3) {
				sendAction(channel, "beats a motherfucker with another motherfucker.");
			}
			else if (randomosity > 0.7) {
				sendAction(channel, "disintegrates the room with a kamehameha.");
			}
			else {			
				sendAction(channel, "disembowels everyone in the room with samurai-like skills.");
			}
		}
		
		if (messageIC.startsWith("!slay ")) {
			double betrayy = Math.random();
			if (betrayy > 0.9) {
				double randomosityy = Math.random();
				if (randomosityy <= 0.3) {
					sendAction(channel, "turns on " + sender + " instead.");
					sendMessage(channel, "I don't take orders from you.");
				}
				else if (randomosityy > 0.5) {
					sendMessage(channel, "Nah.");
				}
				else {			
					sendAction(channel, "goes into a rage and kills everyone in the room.");
				}
			} // betrayy > 0.9 if
			else {
				String slayer = message.substring(6);
				try {
					Thread.sleep(1000);
				}  
				catch (InterruptedException e) {			
				}
				if (slayer.equals(sender) || message.equalsIgnoreCase("!slay me")) {
					sendMessage(channel, "If you really want me to...");
					sendAction(channel, "rips out your heart and slams it onto the floor.");
				}
				else if (messageIC.startsWith("!slay poad") || message.startsWith("!slay his") || messageIC.contains(" bot's")) {
					try {
						Thread.sleep(1000);
					}  
					catch (InterruptedException e) {			
					}
					sendMessage(channel, "Yeah, not gunna happen.");
				}
				else if (message.equalsIgnoreCase("!slay everyone") || message.equalsIgnoreCase("!slay everyone in the room") || message.equalsIgnoreCase("!slay room") || message.equalsIgnoreCase("!slay the room") || message.equalsIgnoreCase("!slay channel") || message.equalsIgnoreCase("!slay the channel")) {
				double randomosity = Math.random();
					if (randomosity <= 0.3) {
						sendAction(channel, "beats a motherfucker with another motherfucker.");
					}
					else if (randomosity > 0.7) {
						sendAction(channel, "disintegrates the room with a kamehameha.");
					}
					else {			
						sendAction(channel, "disembowels everyone in the room with samurai-like skills.");
					}
				}
				else {
					// double randomosity = Math.random();
					// if (randomosity < 0.5) {
						// sendMessage(channel, "Nothing personal, " + slayer);
					// }
					// else {
						// sendMessage(channel, "Gladly.");
						// sendMessage(channel, "I hope you didn't have any plans for later, " + slayer);
					// }
					double randomosity2 = Math.random();
					if (randomosity2 <= 0.3) {
						sendAction(channel, "snaps " + slayer + "'s neck.");
					}
					else if (randomosity2 > 0.7) {
						sendAction(channel, "tosses a sticky bomb on " + slayer + "'s back, then slides on a pair of shades and smoothly walks away as explosions light up the background.");
					}
					else {			
						sendAction(channel, "performs the five-point-palm-exploding-heart-technique on " + slayer + ".");
					}
				} // slay everyone else
			} // betray > 0.9 else
        } // slay if

		if (messageIC.startsWith("hey poad") || messageIC.startsWith("hey, poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
			if (messageIC.startsWith("hey poad ") || messageIC.startsWith("hey poad,") || messageIC.startsWith("hey, poad ") || messageIC.startsWith("hey, poad,")) {
				sendMessage(channel, "Just shut up.");
			}
			else {
				sendMessage(channel, "Hello");
			}
        }
		
		if (messageIC.startsWith("hi poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "You again...");
        }
		
		if (messageIC.startsWith("hi, poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "Hey, " + sender);
        }
		
		if (messageIC.startsWith("sup, poad") || messageIC.startsWith("sup poad")) {
			try {
				Thread.sleep(500);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "Not much.");
        }
		
		if (messageIC.startsWith("hello, poad")) {
			try {
				Thread.sleep(500);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "Sup");
        }
		
		if (messageIC.startsWith("hello poad")) {
			try {
				Thread.sleep(500);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "Sup, " + sender + "?");
			try {
				Thread.sleep(5000);
			}  
			catch (InterruptedException e) {			
			}
			sendMessage(channel, "Sorry. I wasn't actually interested.");
        }
		
		if (messageIC.startsWith("poad, say hello") || messageIC.startsWith("poad say hello") || messageIC.startsWith("say hello, poad") || messageIC.startsWith("say hello poad") || messageIC.startsWith("say hi, poad") || messageIC.startsWith("say hi poad")) {
			if (joiny.equals("no")) {
				sendMessage(channel, "Hush. There is no one new to say hi to.");
			}
			else if (joiny.equals("PircBot")) {
				sendMessage(channel, "Hi, Poad. You are one badass bot.");
			}
			else {
				sendMessage(channel, "Hello, " + joiny);
				joiny = "no";
			}
		}
			
        if (messageIC.startsWith("who is poad") || messageIC.startsWith("who is poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "I am a bot, " + sender);
        }
		
        if (messageIC.startsWith("is poad a bot")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "Yes, " + sender + ", I am.");
        }
		
        if (messageIC.startsWith("!about")) {
            sendMessage(channel, "Hi, " + sender + ". I am a bot, designed in rememberance of an IRC bot belonging to an old community kalez used to be a part of. Type '!!help' for more information.");
        }
		
		if (message.equalsIgnoreCase("Poad, fuck you")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "No, fuck YOU.");
        }
		
		if (message.equalsIgnoreCase("Poad fuck you")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "I bet you wish you could.");
        }
		
		if (message.equalsIgnoreCase("fuck you, Poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "That is what your mom said.");
        }
		
		if (message.equalsIgnoreCase("fuck you Poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "If only you could.");
        }
		
		if (message.equalsIgnoreCase("poad, i love you") || message.equalsIgnoreCase("ily, poad") || message.equalsIgnoreCase("i love you, poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
			double randomosity = Math.random();
			if (randomosity <= 0.3)
			{
				sendMessage(channel,"No.");
			}
			else if (randomosity > 0.6)
			{
				sendMessage(channel, "Baby, don't hurt me.");
			}
			else
			{
            sendMessage(channel, "I know.");
			}
        } // ily, poad if
		
		if (message.equalsIgnoreCase("poad i love you") || message.equalsIgnoreCase("ily poad") || message.equalsIgnoreCase("i love you poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
			double randomosity = Math.random();
			if (randomosity <= 0.3)
			{
				sendMessage(channel, "Go away, you fube.");
			}
			else if (randomosity > 0.6)
			{
				sendMessage(channel, "Ew.");
			}
			else
			{
            sendMessage(channel, "Please go away.");
			}
        } // ily poad if
		
		if (messageIC.startsWith("good poad")) {
			try {
				Thread.sleep(1000);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "I'm not your dog.");
        }
		
		if (messageIC.startsWith("bad poad")) {
			try {
				Thread.sleep(1200);
			}  
			catch (InterruptedException e) {			
			}
            sendMessage(channel, "Admit it. You like it when I'm naughty.");
        }
		
		if (messageIC.startsWith("thanks, poad") || messageIC.startsWith("thanks poad") || messageIC.startsWith("thank you, poad") || messageIC.startsWith("thank you poad") || messageIC.startsWith("ty, poad") || messageIC.startsWith("ty poad")) {
			double randomosity = Math.random();
			if (randomosity <= 0.5) {
				sendMessage(channel,"No problem");
			}
			else {
				sendMessage(channel,"I know. I'm awesome.");
			}
		}

		if (messageIC.startsWith("bye, poad") || messageIC.startsWith("bye poad") || messageIC.startsWith("later, poad") || messageIC.startsWith("later poad") || messageIC.startsWith("see ya, poad") || messageIC.startsWith("see ya poad") || messageIC.startsWith("goodbye, poad") || messageIC.startsWith("goodbye poad") || messageIC.startsWith("good bye, poad") || messageIC.startsWith("good bye poad") || messageIC.startsWith("good night, poad") || messageIC.startsWith("good night poad") || messageIC.startsWith("gnight, poad") || messageIC.startsWith("gnight poad") || messageIC.startsWith("goodnight, poad") || messageIC.startsWith("goodnight poad")) {
			double randomosity = Math.random();
			if (randomosity <= 0.3) {
				sendMessage(channel,"bye, " + sender);
			}
			else if (randomosity > 0.8) {
				sendMessage(channel,"Just leave already.");
			}
			else if (randomosity == 0.5) {
				sendMessage(channel,"finally...");
			}
			else {
				sendAction(channel,"waves");
			}
		} // bye poad if
			
		if (messageIC.equals("!highfive")) {
			double randomosity = Math.random();
			if (randomosity <= 0.7) {
				sendAction(channel,"high-fives " + sender);
			}
			else {
				sendAction(channel,"ignores " + sender);
			}
		}
		
		if (messageIC.startsWith("poad is") && messageIC.endsWith("?") || messageIC.startsWith("poad, is") && message.endsWith("?") || messageIC.startsWith("poad do") && message.endsWith("?") || messageIC.startsWith("poad, do") && message.endsWith("?") || messageIC.startsWith("poad, should") && message.endsWith("?") || messageIC.startsWith("poad should") && message.endsWith("?") || messageIC.startsWith("poad, can") && message.endsWith("?") || messageIC.startsWith("poad can") && message.endsWith("?") || messageIC.startsWith("poad, are you") && message.endsWith("?") || messageIC.startsWith("poad are you") && message.endsWith("?") || message.endsWith("right, poad?") || message.endsWith("right poad?")) {
			if (messageIC.startsWith("poad, should") && messageIC.endsWith("write?") || messageIC.startsWith("poad, should") && messageIC.endsWith("write?")) {
				try {
					Thread.sleep(1000);
				}  
				catch (InterruptedException e) {			
				}
				sendMessage(channel,"Of course. Why are you not writing already?");
			}
			else {
				try {
					Thread.sleep(1000);
				}  
				catch (InterruptedException e) {			
				}
				double randomosity = Math.random();
				if (randomosity <= 0.4) {
					sendMessage(channel,"No");
				}
				else if (randomosity >= 0.7) {
					sendMessage(channel,"Yes");
				}
				else if (randomosity == 0.5) {
					sendMessage(channel,"I'm not answering that.");
				}
				else {
					sendMessage(channel,"If you can't figure that out for yourself, then you need more help than I thought.");
				}
			} // else
        } // right poad if
		
        if (messageIC.startsWith("what is a sprint") || messageIC.startsWith("what is a rush") || messageIC.startsWith("whats a sprint") || messageIC.startsWith("whats a rush") || messageIC.startsWith("what's a sprint") || messageIC.startsWith("what's a rush")) {
            sendMessage(channel, "A sprint/rush is a timed writing period or session during which anyone can compete with eachother or against the clock to write as many words as possible and then compare word counts. It is more about inspiration than competition. For more information, type '!!help timer' or '!!help rush'.");
        }
		
		if (message.equalsIgnoreCase("!new")) {
            sendMessage(channel, "The newest features: '!flip' and '!math' ");
        }
		
		if (message.equalsIgnoreCase("!!help")) {
            sendMessage(channel, "For help with commands, type '!!help *command*' (without !s). My available commands are: <Normal: !!help, !new, /r/, !math > [writing: !add, !join, !leave, !remove, !removeall, !list, !rush, !timer, !wpm, !check ] {Look-up: !google, !g, !lmgtfy, !d, !define, !dictionary, !t, !thesaurus, !r, !reference } <Fun: !poke, +1, -1, !summon, !flip, !slay, !highfive > (Sorry, !stop does not work yet.)");
        }
		
		if (message.equalsIgnoreCase("!!help new")) {
            sendMessage(channel, "Type '!new'. Checks the newest added commnds.");
        }
		
		if (message.equalsIgnoreCase("!!help rush")) {
            sendMessage(channel, "Type '!rush' or !rush #'. Starts a 30 minute timed writing period. The # is if you wish to add a wait duration before the timer starts. You do not need to include the wait #.");
        }
		
		if (message.equalsIgnoreCase("!!help timer")) {
            sendMessage(channel, "Type '!timer #' or '!timer # #'. Starts a timed writing period. The first # is the durations, the 2nd # is the wait duration before the timer starts. You do not need to include the wait #. (Currently the wait is forced to 1 minute or none due to the bot timing out if the wait is 2 minutes. This will be fixed soon)");
        }
		
		if (message.equalsIgnoreCase("!!help add")) {
            sendMessage(channel, "Type '!add *names*'. Adds extra names to the pings at the start and end of a timed writing period. Can be used during a writing period as well. (Type '!remove *names*' or '!removeall' to remove names from the list)");
        }
		
		if (message.equalsIgnoreCase("!!help join")) {
            sendMessage(channel, "Type '!join'. Adds your name to the pings at the start and end of a timed writing period. Can be used during a writing period as well. You do not need to !join if you are going to type '!timer #'. (Type '!remove *names*' or '!removeall' to remove names from the list)");
        }
		
		if (message.equalsIgnoreCase("!!help leave")) {
            sendMessage(channel, "Type '!leave'. Removes your name from the ping list.");
        }
		
		if (message.equalsIgnoreCase("!!help remove")) {
            sendMessage(channel, "Type '!remove *names*'. Removes a name or a list of names from the ping list.");
        }
		
		if (message.equalsIgnoreCase("!!help removeall")) {
            sendMessage(channel, "Type '!removeall'. Removes all names from the ping list.");
        }
		
		if (message.equalsIgnoreCase("!!help list")) {
            sendMessage(channel, "Type '!list'. Checks the names on the ping list.");
        }
		
		if (message.equalsIgnoreCase("!!help check")) {
            sendMessage(channel, "Type '!check'. Checks the remaining minutes of a timed writing period.");
        }
		
		if (message.equalsIgnoreCase("!!help wpm")) {
            sendMessage(channel, "Type '!wpm #'. Use at the end of a timed writing period. Calculates your words per minute.");
        }
		
		// if (message.equalsIgnoreCase("!!help wait")) {
            // sendMessage(channel, "Type '!wait #'. Adjusts the wait time before a session starts.");
        // }
		
		// if (message.equalsIgnoreCase("!!help waitcheck")) {
            // sendMessage(channel, "Type '!waitcheck'. Checks the current pre-session waiting time.");
        // }
		
		if (message.equalsIgnoreCase("!!help google") || message.equalsIgnoreCase("!!help g")) {
            sendMessage(channel, "Type '!google *word*' or '!g *word*'. Creates a google link.");
        }
		
		if (message.equalsIgnoreCase("!!help lmgtfy")) {
            sendMessage(channel, "Type '!lmgtfy *word*'. Googles that word for you.");
        }
		
		if (message.equalsIgnoreCase("!!help wiki") || message.equalsIgnoreCase("!!help w")) {
            sendMessage(channel, "Type '!wiki *word*' or '!w *word*'. Creates a wikipedia link.");
        }
		
		if (message.equalsIgnoreCase("!!help r/") || message.equalsIgnoreCase("!!help r/")) {
            sendMessage(channel, "Type 'r/*subreddit*' or '/r/*subreddit*'. Creates a link to said subreddit.");
        }
		
		if (message.equalsIgnoreCase("!!help math")) {
            sendMessage(channel, "Type '!math # symbol #'. Calculates numbers. Symbol can be '+', '-', '*', or '/'.");
        }
		
		if (message.equalsIgnoreCase("!!help poke")) {
            sendMessage(channel, "Type '!poke' or '!poke *name*'. Poke someone ;P");
        }
		
		if (message.equalsIgnoreCase("!!help +1")) {
            sendMessage(channel, "Type '+1 *name*'. Give someone a deserving internet point.");
        }
		
		if (message.equalsIgnoreCase("!!help -1")) {
            sendMessage(channel, "Type '-1 *name*'. Remove an internet point from someone.");
        }
		
		if (message.equalsIgnoreCase("!!help summon")) {
            sendMessage(channel, "Type '!summon *name*'. Attempt to summon someone to the channel.");
        }
		
		if (message.equalsIgnoreCase("!!help flip")) {
            sendMessage(channel, "Type '!flip'. Flips a coin.");
        }
		
		if (message.equalsIgnoreCase("!!help highfive")) {
            sendMessage(channel, "Type '!highfive'. The bot will high-five you back... sometimes.");
        }
		
		if (message.equalsIgnoreCase("!!help slay")) {
            sendMessage(channel, "Type '!slay *name*', '!slay', or '!slay me'. Kills someone or everyone in the room.");
        }
		
		if (message.equalsIgnoreCase("!!help help")) {
            sendMessage(channel, "Srsly...");
			long hclocky = System.currentTimeMillis();
			long hticky = (hclocky / 1000) + 3;
			long hwaity = hticky;
			while (hwaity > 0) {
				long hclocky2 = System.currentTimeMillis();
				long htocky = hclocky2 / 1000;
				hwaity = hticky - htocky;
			}				
			// int hwaity = 3000;
			// while (hwaity > 0) {
				// hwaity --; 
			// }
			sendMessage(channel, "Are you daft? Just checking.");
        } // help help if
		
		if (messageIC.equals("!go")) {
			Thread.currentThread().interrupt();
		}

		if (messageIC.startsWith("!add ")) {
			if (!groupy.equals("")) {
				String addy = message.substring(5);
				if (addy.endsWith(", ")) {
				addy = addy.substring(0, addy.length() - 2);
				}
				if (addy.endsWith(",")) {
				addy = addy.substring(0, addy.length() - 1);
				}
				//if (!groupy.contains(",")) {
					addy = addy.replace(" ",", ");
				//}
				//if (groupy.contains("  ")) {
					addy = addy.replace("  ",", ");
				//}
				//if (groupy.contains(" , ")) {
					addy = addy.replace(", ,",",");
				//}
				//if (groupy.contains(",,")) {
					addy = addy.replace(",,",",");
					addy = addy.replace(",,",",");
				//}
				groupy += ", " + addy;
				sendMessage(channel, addy + " has also been added to the join list.");
			} // group not = empty if
			else {
				groupy = message.substring(5);
				if (groupy.endsWith(", ")) {
				groupy = groupy.substring(0, groupy.length() - 2);
				}
				if (groupy.endsWith(",")) {
				groupy = groupy.substring(0, groupy.length() - 1);
				}
				//if (!groupy.contains(",")) {
					groupy = groupy.replace(" ",", ");
				//}
				//if (groupy.contains("  ")) {
					groupy = groupy.replace("  ",", ");
				//}
				//if (groupy.contains(" , ")) {
					groupy = groupy.replace(", ,",",");
				//}
				//if (groupy.contains(",,")) {
					groupy = groupy.replace(",,",",");
					groupy = groupy.replace(",,",",");
				//}
				sendMessage(channel, groupy + " has been added to the join list.");
			} // else
        } // add if
		
		if (messageIC.startsWith("!join")) {
			if (groupy.contains(sender)) {
				sendMessage(channel, "You are already on the join list.");
			}
			else {
				if (!groupy.equals("")) {
				groupy += ", " + sender;
				sendMessage(channel, sender + " has been added to the join list.");
				}
				else {
					groupy = sender;
					sendMessage(channel, sender + " has been added to the join list.");
				}
			}
        } // join if
		
		if (messageIC.equals("!list")) {
			if (groupy.equals("")) {
				sendMessage(channel, "The join list is empty.");
			}
			else {
			sendMessage(channel, "The join list currently consists of: " + groupy);
			}
        }

		if (message.equalsIgnoreCase("!removeall")) {
			groupy = "";
			sendMessage(channel, "The join list is now empty.");
        }
		
		if (messageIC.startsWith("!leave")) {
			if (!groupy.contains(sender)) {
				sendMessage(channel, "You are not on the join list.");
			}
			else {
				groupy = groupy.replace(sender,"");
				groupy = groupy.replace(" , "," ");
				if (groupy.startsWith(", ")) {
					groupy = groupy.substring(2);
				}
				if (groupy.equals("")) {
					sendMessage(channel, "The join list is now empty.");
				}
				else {
					sendMessage(channel, "You have left the join list.");
					if (!groupy.contains(",")) {
						groupy = groupy.replace(" ",", ");
					}
					if (groupy.endsWith(", ")) {
						groupy = groupy.substring(0, groupy.length() - 2);
					}
				}
				// if (groupy.contains(", ,")) {
					// groupy = groupy.replace(", ,",",");
				// }
				// if (groupy.contains(",,")) {
					// groupy = groupy.replace(",,",",");
				// }
			}
        } // leave if
		
		if (messageIC.startsWith("!remove ")) {
			String removey = message.substring(8);
			if (!groupy.contains(removey)) {
				sendMessage(channel, removey + " is not on the join list.");
			}
			else {
				groupy = groupy.replace(removey,"");
				groupy = groupy.replace(" , "," ");
				if (groupy.startsWith(", ")) {
					groupy = groupy.substring(2);
				}
				if (groupy.equals("")) {
					sendMessage(channel, "The join list is now empty.");
				}
				else {
					sendMessage(channel, removey + " has been removed from the join list.");
					if (!groupy.contains(",")) {
						groupy = groupy.replace(" ",", ");
					}
					if (groupy.endsWith(", ")) {
						groupy = groupy.substring(0, groupy.length() - 2);
					}
				}
				// if (groupy.contains("  ")) {
					// groupy = groupy.replace("  ",", ");
				// }
				// if (groupy.contains(",,")) {
					// groupy = groupy.replace(",,",",");
				// }
			}
        } // remove if
		
		// if (messageIC.startsWith("!wait ")) {
			// waity = Integer.parseInt(message.substring(6));
			// sendMessage(channel, "The wait time has been set to " + waity + " minute(s).");
        // }
		
		// if (message.equalsIgnoreCase("!waitcheck")) {
			// sendMessage(channel, "The wait time is set to " + waity + " minute(s).");
        // }
		
		message = message.trim( ).toLowerCase( );		
		if (message.startsWith("!rush")) {
			if (ticking == true) {
				sendMessage(channel, "Sorry, a session is already in progress.");
			}
			else {
				setTicking(true);
				
				if (message.startsWith("!rush ")) {
					waity = Integer.parseInt(message.substring(6));
				}
				else {
					waity = 1;
				}
				
				// long rclocky = System.currentTimeMillis();
				// long rticky = (rclocky / 60000) + waity;
				// long rwaity = rticky;
				// sendMessage(channel, "Alright, " + groupy + " " + sender + ", I have put 30 minutes on the clock and I will give you " + waity + " minute(s) to prepare. (Bot is now on standby)");
				// while (rwaity > 0) {
					// if (message.equalsIgnoreCase("!go")) {
						// rwaity = 0;
						// sendMessage(channel, "Someone is impatient.");
					// }
					// else {
						// long rclocky2 = System.currentTimeMillis();
						// long rtocky = rclocky2 / 60000;
						// rwaity = rticky - rtocky;
					// }
				// }
				
				if (waity == 0) {
				}
				else if (waity > 0) {
					if (!groupy.equals("")) {
						sendMessage(channel, "Alright, " + groupy + " and " + sender + ", I have put 30 minute(s) on the clock and I will give you " + waity + " minute(s) to prepare. (Bot is standby during wait)");
					}
					else {
						if (waity > 1) {
							waity = 1;
							sendMessage(channel, "(Sorry. At this time, the bot has been forced to a 1 minute or less wait due to the current wait system causing a ping timeout after 2 minutes, thus your wait time has been set to 1 minute. This will be changed soon.)");
							try {
								Thread.sleep(2000);
								}  
							catch (InterruptedException e) {			
							}
						}
						sendMessage(channel, "Alright, " + sender + ", I have put 30 minute(s) on the clock and I will give you " + waity + " minute(s) to prepare. (Bot is standby during wait)");
					}
					
					try {
						Thread.sleep(waity * 60000);
						}  
					catch (InterruptedException e) {			
					}
				} // waity > 0 else if
				
				// long rclocky = System.currentTimeMillis();
				// long rwaity = (rclocky / 60000) + waity;
				// sendMessage(channel, "Alright, " + groupy + " " + sender + ", I have put " + duration + " minute(s) on the clock and I will give you " + waity + " minute(s) to prepare. (Bot is now on standby)");
				// while (rwaity > 0) {
					// rwaity --;
					// if (message.equals("!go")) {
						// sendMessage(channel, sender + "Someone is impatient.");
						// break;
					// }
				// }
				if (!groupy.equals("")) {
					if (groupy.contains(sender)) {
						sendMessage(channel, "Alright, " + groupy + ", I have put 30 minutes on the clock and I will give you " + waity + " minute(s) to prepare. (Bot is standby during wait)");
					}
					else {
						sendMessage(channel, "Alright, " + groupy + " and " + sender + ", I have put 30 minutes on the clock and I will give you " + waity + " minute(s) to prepare. (Bot is standby during wait)");
					}
				}
				else {
					sendMessage(channel, sender + ", your 30 minutes start now!");
				}
				
				NiteTimerTask timerTask = new NiteTimerTask(this, sender, channel);
				timer.schedule(timerTask, 30 * 60 * 1000);
				timey = 30; //do not add waity to 30 because this calc occurs after timers starts. This is for wpm calc
				clocky = System.currentTimeMillis();
				clocky2 = (clocky / 1000) + 1800;
				waity = 1;
			} // ticking false else
		} // rush if
		
		else if (message.startsWith("!timer ")) {
			timeSender = sender;
			if (ticking == true) {
				sendMessage(channel, "Sorry, a session is already in progress.");
			}
			else {
				ticking = true;
				try {
					
					/* This is the new !timer # # system to be implemented once wait is fixed. Currently, if wait is over 2 minutes, it can time the bot out of irc because it is frozen and cant ping regularly.*/
					
					message = message.substring(7); // grab the whole message starting at 7 spot
					/*sendMessage(channel,"Message: " + message);*/
					message = message.replace(" ",","); //replace spaces with commas, indexOf can have issues with spaces
					/*sendMessage(channel,"Message: " + message);*/
					int wdy = message.indexOf(",") + 1; // this will be the spot for the waitDuration and the stopping point for duration
					/*sendMessage(channel,"wdy: " + wdy);*/
					int tdy = message.indexOf(",");
					/*sendMessage(channel,"tdy: " + tdy);*/
					//int duration;
					if (tdy < 0) {
						duration = Integer.parseInt(message.substring(0)); // grab the number from message starting at 0 spot
					/*	sendMessage(channel,"Duration: " + duration);*/
						waity = 1;
					/*	sendMessage(channel,"WaitDuration is: " + waity);*/
					}
					else {
						String wessage = message.substring(wdy); // start the number after the comma
					/*	sendMessage(channel,"Wessage: " + wessage);*/
						message = message.substring(0, tdy); // start the number at 0 and stop it before the comma
					/*	sendMessage(channel,"Message: " + message);*/
						duration = Integer.parseInt(message.substring(0)); // grab the number from message starting at 0 spot
					/*	sendMessage(channel,"Duration is: " + duration);*/
						waity = Integer.parseInt(wessage.substring(0)); // grab the number from wessage starting at 0 spot
					/*	sendMessage(channel,"WaitDuration is: " + waity);*/
					}					
					
					// previous duration grabber
					//int duration = Integer.parseInt(message.substring(7));

					// //Grabber for my old wait method
					// long tclocky = System.currentTimeMillis();
					// long tticky = (tclocky / 60000) + waity;
					// long twaity = tticky;
					
					if (waity == 0) {
					}
					else if (waity > 0) {
						if (!groupy.equals("")) {
							sendMessage(channel, "Alright, " + groupy + " and " + sender + ", I have put " + duration + " minute(s) on the clock and I will give you " + waity + " minute(s) to prepare. (Bot is standby during wait)");
						}
						else {
							if (waity > 1) {
								waity = 1;
								sendMessage(channel, "(Sorry. At this time, the bot has been forced to a 1 minute or less wait due to the current wait system causing a ping timeout after 2 minutes, thus your wait time has been set to 1 minute. This will be changed soon.)");
							}
							sendMessage(channel, "Alright, " + timeSender + ", I have put " + duration + " minute(s) on the clock and I will give you " + waity + " minute(s) to prepare. (Bot is standby during wait)");
						}
						
						// regular wait freeze for wait duration * 1 minute
						//waiter(waity, sender, channel);
						try {
							Thread.sleep(waity * 60000);
							}  
						catch (InterruptedException e) {			
						}
						
						// //My old wait method
						//while (twaity > 0) {			
							// if (message.equalsIgnoreCase("!go")) {
								// //twaity = 0;
								// sendMessage(channel, "Someone is impatient.");
								// break;
							// }
							// else {
								// long tclocky2 = System.currentTimeMillis();
								// long ttocky = tclocky2 / 60000;
								// twaity = tticky - ttocky;
							// }
						//}
						
					} // wait > 0 else if
					
					timey = duration; // timey = duration for wpm calc
					clocky = System.currentTimeMillis(); // grab current time for checking
					/*sendMessage(channel, "Clocky test says " + clocky);*/
					clocky2 = (clocky / 1000) + (duration * 60); // reduce that time to seconds and add duration minutes * 60 for seconds
					/*sendMessage(channel, "Clocky2 test says " + clocky2);*/
					
					if (duration > 0) {
											
						NiteTimerTask timerTask = new NiteTimerTask(this, sender, channel);
						timer.schedule(timerTask, duration * 60 * 1000); // Multiply the milliseconds by 1000 to get seconds.
						
						if (!groupy.equals("")) { // If the ping list isn't empty, say them
							sendMessage(channel, groupy + " and " + sender + ", your " + duration + " minute(s) start now!");
						}
						else { // If the ping list is empty, dont say them
							sendMessage(channel, sender + ", your " + duration + " minute(s) start now!");
						}
						
						// ctmo = System.currentTimeMillis(); // grab current time for checking
						// rtTick = (ctmo / 1000) + (duration * 60); // reduce ctm to seconds and add duration minutes * 60 for seconds
						// sendMessage(channel, "rtTick: " + rtTick);
						// rushTimer(rtTick, channel, message);
						
					} // duration > 0
					waity = 1;
				} // try
				catch (NumberFormatException e) {
					// Do nothing.
				}
			} // tick false else
		} // timer else
		
		if (message.equals("!ssstop")) {
			sendMessage(channel, sender + ": Fine. Timer stopped. (The timer is now fucked...)");
			timer.cancel();
			//NiteTimerTask.cancel();
			//dispose();
			
			// all fucking failed stop attempts...
			
			//NiteTimerTask taskTimer = null;
			//cancel.cancel();
			//NiteTimerTask run = cancel();
			//timer.purge();
			//NiteTimerTask run = null;
			//timer.schedule(timerTask, 1);
		} // stop...
		
		if (message.startsWith("!wpm ")) {
			//sendMessage(channel, "Calculating WPM...");
			try {
				int wrotey = Integer.parseInt(message.substring(5)); //grab wordcount
				wpmy = wrotey/timey; // wordcount divided by timer duration
				sendMessage(channel, sender + ", you wrote " + wpmy + " words per minute.");
			}
			catch (NumberFormatException e) {
			}
		} // wpm
		
		if (message.equalsIgnoreCase("!check")) {
			timeCheck (clocky2, channel, message);
		}
		
	} // onMessage
		
	public void timeCheck (Long clocky2, String channel, String message) {
		
		//if (message.equalsIgnoreCase("!check")) {
			clocky3 = System.currentTimeMillis(); // current milseconds, timey is original seconds
			/*sendMessage(channel, "Clocky3 test says " + clocky3);*/
			ticky = clocky3 / 1000; // ticky is current total day seconds?
			/*sendMessage(channel, "Ticky test says " + ticky);*/
			/*sendMessage(channel, "Clocky2 test says " + clocky2);*/
			tocky = (clocky2 - ticky); // tocky is timer left seconds
			/*sendMessage(channel, "Tocky test says " + tocky);*/
			checky = tocky / 60; // checky is minutes
			long secy = tocky - (checky * 60);
			if (checky <= 0) { // If there are no minutes, set it to 0
				checky = 0;
				if (secy < 0) { // If there are no seconds, there is no duration
					sendMessage(channel, "There is no time on the clock.");
				}
				else if( secy >= 0 && secy < 10) {
					sendMessage(channel, "Time remaining: 0:0" + secy);
				}
				else {
					sendMessage(channel, "Time remaining: 0:" + secy);
				}
			}
			else { // Show duration normally
				if( secy >= 0 && secy < 10) {
					sendMessage(channel, "Time remaining: " + checky + ":0" + secy);
				}
				else {
					sendMessage(channel, "Time remaining: " + checky + ":" + secy);
				}
			}
		//} // check if
		
	} // timerCheck
		
    //} // onMessage
	
	public void setTicking(boolean ticking){
		this.ticking = ticking;
	}
	
	public boolean isTicking(){ return this.ticking;}

	public void setGroupy(String groupy){
		this.groupy = groupy;
	}
	
	public String getGroupy(){ return groupy; }
	
	public void waiter (int waity, String sender, String channel) {
		waiting = true;
		NiteTimerTask timerTask = new NiteTimerTask(this, sender, channel);
		timer.schedule(timerTask, waity * 60 * 1000); // Multiply the milliseconds by 1000 to get seconds.
		waity = 1;
	}
	
	public void setWaiting (boolean waiting) {
		this.waiting = waiting;
	}
	
	public boolean isWaiting(){ return this.waiting;}
	
	// public void setDuration(int duration){
		// this.duration = duration;
	// }
	
	public int getDuration(){ return duration; }
	
	// public void rushTimer (Long rtTick, String channel, String message) {
		/*ctmo=currentTimeMillis, ctmn=ctmnew, rtTick/rtTock=second calcs, rtTime=remainder time*/
		
		// /*If !timer or !rush, timey = duration, grab starter time and add to it. This should be done after waity. Then, count down the seconds until */
		
		// ctmo = System.currentTimeMillis(); // grab current time for checking
		// rtTick = (ctmo / 1000) + (timey * 60); // reduce ctm to seconds and add duration minutes * 60 for seconds
		//sendMessage(channel, "I'm there!");
		
		// ctmn = System.currentTimeMillis(); // new ctm, current total day milliseconds
		// rtTock = ctmn / 1000; // current total day seconds
		// rtTime = rtTick - rtTock; // timer left seconds
		// sendMessage(channel, "ctmn: " + ctmn);
		// rtTick = rtTick * 1000;
		
		// do {
			// ctmn = System.currentTimeMillis();
			// if (message.equals("!stop")) {
				// break;
			// }
			// else {
				// ctmn += 1;
			// }
		// } while (ctmn < rtTick);
		
		// if (rtTime <= 0 ) {
			// ctmn = System.currentTimeMillis(); // new ctm, current total day milliseconds
			// rtTock = ctmn / 1000; // current total day seconds
			// rtTime = rtTick - rtTock; // timer left seconds
			// sendMessage(channel, "tick");
		// }
		// else {
			////rtTime = 0;
			// if (!groupy.equals("")) {
				// sendMessage(channel, groupy + " and " + timeSender + ", pencils down!");
			// }
			// else {
				// sendMessage(channel, timeSender + ", pencils down!");
			// }
			// sendMessage(channel, "ctmn: " + ctmn);
			// ticking = false;
			// timeSender = "";
		// }		
	//} // rushTimer
	
} // Nitebot class
