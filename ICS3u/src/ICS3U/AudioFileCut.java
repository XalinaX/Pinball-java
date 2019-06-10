package ICS3U;


import java.io.*;
import javax.sound.sampled.*;

/**
 * format the music
 */
class AudioFileCut {

  public static void main(String[] args) {
    copyAudio("/tmp/uke.wav", "/tmp/uke-shortened.wav", 2, 1);
  }

  public static void copyAudio(String original, String result, int startSecond, int secondsToCopy) {
    AudioInputStream inputStream = null;
    AudioInputStream resultStream = null;
    try {
      File file = new File(original);
      AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
      AudioFormat format = fileFormat.getFormat();
      inputStream = AudioSystem.getAudioInputStream(file);
      int bytesPerSecond = format.getFrameSize() * (int)format.getFrameRate();
      inputStream.skip(startSecond * bytesPerSecond);
      long framesOfAudioToCopy = secondsToCopy * (int)format.getFrameRate();
      resultStream = new AudioInputStream(inputStream, format, framesOfAudioToCopy);
      File resultFile = new File(result);
      AudioSystem.write(resultStream, fileFormat.getType(), resultFile);
    } catch (Exception e) {
      println(e);
    } finally {
      if (inputStream != null) try { inputStream.close(); } catch (Exception e) { println(e); }
      if (resultStream != null) try {resultStream.close(); } catch (Exception e) { println(e); }
    }
  }

  public static void println(Object o) {
    System.out.println(o);
  }

  public static void print(Object o) {
    System.out.print(o);
  }

}