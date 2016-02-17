import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

class Alarm extends Thread {
	
	/** 실행중 여부 */
	private static boolean isPlaying = false;
	private int EXTERNAL_BUFFER_SIZE = 128000;

	public void playAudioFile() throws Exception {
		String audioFile = "./wav/beep.wav"; // 연결할 wav파일 위치s
		File soundFile = new File(audioFile);
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(soundFile);
			AudioFormat audioFormat = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					audioFormat);
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
			line.start();
			int nBytesRead = 0;
			byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0) {
					line.write(abData, 0, nBytesRead);
				}
			}
			line.drain();
			line.close();
			audioInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		
		if(!isPlaying){
			try {
				isPlaying = true;
				playAudioFile();
				isPlaying = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}