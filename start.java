package game;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.io.*;


public class start extends StateBasedGame {
	
	public static final String gameName = "Snake";
	public static final int snke = 0;
	public static AppGameContainer appgc;
	
	
	
	public start (String gameName){
		super(gameName);
		this.addState(new Snke(snke));
	}
	
	@Override 
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(snke).init(gc, this);
		this.enterState(snke);
	}

	public static void main(String[] args) {		
		//appgc.setShowFPS(false);
		try{
			appgc = new AppGameContainer(new start(gameName));
			appgc.setDisplayMode(600, 600, false);
			appgc.setTargetFrameRate(12);
			appgc.start();
			appgc.setShowFPS(false);
		}catch(SlickException e){
			e.printStackTrace();
		}
		
		
	}

}
