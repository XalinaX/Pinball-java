package ICS3U;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * the background sound
 *load all the piano notes
 */
public class Songs {
	protected int[] played = new int[8];
	protected String[] songs = {"./songs/JuHuaTai","./songs/BanDaoTieHe","./songs/DaoXiang",
			"./songs/QiLiXiang","./songs/QingHuaCi","./songs/GaoBaiQiQiu","./songs/DengNiXiaKe","./songs/HongChenKeZhan"};
	public static boolean songStart;
	private boolean haveSong;
	private String song;
	public static int linenum = 1;
	static Clip clip;
	static String line;
	static HashMap<String, String> notesmap;
	static AudioInputStream audioInputStream;
	public static int num;
	
	//map the audio file with the number
	public Songs(){
		notesmap = new HashMap<String, String>();
				
		notesmap.put("0 0", "./songs/notes/Piano.mf.C3-[AudioTrimmer.com].aiff");
		notesmap.put("0 1", "./songs/notes/Piano.mf.D3-[AudioTrimmer.com].aiff");
		notesmap.put("0 2", "./songs/notes/Piano.mf.E3-[AudioTrimmer.com].aiff");
		notesmap.put("0 3", "./songs/notes/Piano.mf.F3-[AudioTrimmer.com].aiff");
		notesmap.put("0 4", "./songs/notes/Piano.mf.G3-[AudioTrimmer.com].aiff");
		notesmap.put("0 5", "./songs/notes/Piano.mf.A3-[AudioTrimmer.com].aiff");
		notesmap.put("0 6", "./songs/notes/Piano.mf.B3-[AudioTrimmer.com].aiff");

		notesmap.put("1 0", "./songs/notes/Piano.mf.C4-[AudioTrimmer.com].aiff");
		notesmap.put("1 1", "./songs/notes/Piano.mf.D4-[AudioTrimmer.com].aiff");
		notesmap.put("1 2", "./songs/notes/Piano.mf.E4-[AudioTrimmer.com].aiff");
		notesmap.put("1 3", "./songs/notes/Piano.mf.F4-[AudioTrimmer.com].aiff");
		notesmap.put("1 4", "./songs/notes/Piano.mf.G4-[AudioTrimmer.com].aiff");
		notesmap.put("1 5", "./songs/notes/Piano.mf.A4-[AudioTrimmer.com].aiff");
		notesmap.put("1 6", "./songs/notes/Piano.mf.B4-[AudioTrimmer.com].aiff");

		notesmap.put("2 0", "./songs/notes/Piano.mf.C5-[AudioTrimmer.com].aiff");
		notesmap.put("2 1", "./songs/notes/Piano.mf.D5-[AudioTrimmer.com].aiff");
		notesmap.put("2 2", "./songs/notes/Piano.mf.E5-[AudioTrimmer.com].aiff");
		notesmap.put("2 3", "./songs/notes/Piano.mf.F5-[AudioTrimmer.com].aiff");
		notesmap.put("2 4", "./songs/notes/Piano.mf.G5-[AudioTrimmer.com].aiff");
		notesmap.put("2 5", "./songs/notes/Piano.mf.A5-[AudioTrimmer.com].aiff");
		notesmap.put("2 6", "./songs/notes/Piano.mf.B5-[AudioTrimmer.com].aiff");

		notesmap.put("3 0", "./songs/notes/Piano.mf.C6-[AudioTrimmer.com].aiff");
		notesmap.put("3 1", "./songs/notes/Piano.mf.D6-[AudioTrimmer.com].aiff");
		notesmap.put("3 2", "./songs/notes/Piano.mf.E6-[AudioTrimmer.com].aiff");
		notesmap.put("3 3", "./songs/notes/Piano.mf.F6-[AudioTrimmer.com].aiff");
		notesmap.put("3 4", "./songs/notes/Piano.mf.G6-[AudioTrimmer.com].aiff");
		
		songStart=false;
	}
	
	/**
	* playing notes one by one by reading code from a file
	*/
	public void play(){
		if (!Game.soundOn) {
			linenum=1;
			return;
		}
		//if all the songs played once, set the array to zero
		haveSong=false;
		for (int i=0;i<8;i++) {
			if (played[i]==0) {
				haveSong=true;
			}
		}
		if (!haveSong) {
			for (int i=0;i<8;i++) {
				played[i]=0;
			}
		}
		
		//if no song is playing, randomly choose a song
		if (!songStart) {
			num = (int)(Math.random()*8);
			while(played[num]!=0) {
				num = (int)(Math.random()*8);
			}
			played[num]=1;
			song = songs[num];
			songStart = true;
			linenum=1;
			Game.guessed=false;
		}
		//play the song
		try (Stream<String> lines = Files.lines(Paths.get(song))){
		    line = lines.skip(linenum-1).findFirst().get();
			audioInputStream = AudioSystem.getAudioInputStream(new File(notesmap.get(line)).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (NoSuchElementException e) {
			Game.songend = true;
		} catch (LineUnavailableException e) {

		} catch (UnsupportedAudioFileException e) {

		} catch (IOException e) {

		} 
		linenum++;
	}

	public static void main(String[] arg) {
	}
}
