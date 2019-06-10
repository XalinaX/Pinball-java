package ICS3U;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import javax.swing.JButton;
import javax.swing.JFrame;

// notes source: http://theremin.music.uiowa.edu/MISpiano.html
// perform one note as tapping the button
// hashmap is used
/**
 * play the music
 *
 */
public class PinBall1 implements ActionListener {
	static int linenum = 1;
	static JFrame F;
	static JButton button;
	static Clip clip;
	static String line;
	static HashMap<String, String> notesmap = new HashMap<String, String>();
	static AudioInputStream audioInputStream;
//	static BufferedReader infile = new BufferedReader(new FileReader(new File("DaoXiang")));

	public PinBall1() {
		F = new JFrame("collision music test");
		F.setPreferredSize(new Dimension(300, 300));
	//	F.setBackground(Color.black);
		F.setVisible(true);
		button = new JButton ("Average");
		button.addActionListener(this);
		F.add(button);
		F.pack();
	}

	public void actionPerformed(ActionEvent E) {
		if(E.getSource() == button)
			collisionMusic();
	}

	public static void collisionMusic(){
		try (Stream<String> lines = Files.lines(Paths.get("./songs/GaoBaiQiQiu.txt"))){
		    line = lines.skip(linenum-1).findFirst().get();
			System.out.println("line number is " + linenum + "     line is "+ line);
			audioInputStream = AudioSystem.getAudioInputStream(new File(notesmap.get(line)).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (NoSuchElementException e) {
			System.out.println("no such element");
		} catch (LineUnavailableException e) {
			System.out.println("line unavaliable");
		} catch (UnsupportedAudioFileException e) {
			System.out.println("unsupported audio file");
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		} 
		linenum++;
	}

	public static void main(String[] arg) {
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

		new PinBall1();
	
	}
}
