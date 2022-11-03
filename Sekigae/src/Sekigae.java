
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sekigae {
	public static void main(String[] args) throws Exception {
		
		Sound drum = new Sound();
		File file = new File("E:/java環境/workspace/Sekigae/bin/drum.wav");
		Clip clip = drum.createClip(file);
		clip.start();
		Thread.sleep(7000);
		clip.close();
		
		ArrayList<String> man = new ArrayList<String>();
		man.add("     潟山さん     ");
		man.add("     金丸さん     ");
		man.add("   木下(富)さん   ");
		man.add("   木下(源)さん   ");
		man.add("    久保田さん    ");
		man.add("     蔵満さん     ");
		man.add("     近藤さん     ");
		man.add("     中村さん     ");
		man.add("     吉満さん     ");
		man.add("     岡田さん     ");
		man.add("     坂本さん     ");
		man.add("      東さん      ");
		man.add("     木塲さん     ");
		man.add("     肱岡さん     ");

		ArrayList<String> woman = new ArrayList<String>();
		woman.add("     谷口さん     ");
		woman.add("      陳さん      ");
		woman.add("     永廣さん     ");
		woman.add("     渡邉さん     ");


		Collections.shuffle(man);
		Collections.shuffle(woman);

		try {
			Thread.sleep(1000);
			System.out.println("------------------------------------------ 正面 ----------------------------------------");
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println(" | " + woman.get(0) + " | " + man.get(0) + " | | " + man.get(1) + " | "+ man.get(2) + " | ");
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println(" | " + man.get(3) + " | " + woman.get(1) + " | | " + man.get(4) + " | "+ man.get(5) + " | ");
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println(" | " + woman.get(2) + " | " + man.get(6) + " | | " + man.get(7) + " | "+ man.get(8) + " | ");
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println(" | " + man.get(9) + " | " + woman.get(3) + " | | " + man.get(10) + " | "+ man.get(11) + " | ");
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------------------");
			System.out.println(" | " + man.get(12) + " | " + man.get(13)  + " | |                    |" + "                    |");
			System.out.println("---------------------------------------------------------------------------------------");
			Thread.sleep(2000);
			System.exit(0);
		} catch (InterruptedException e) {
			System.out.println("!!! Exception occured !!!");
			System.out.println("");
			System.out.println(e);
		} finally {
			System.out.println("--席替え終了--");
		}
	}
}

class Sound {
	public Clip createClip(File path) {
		//指定されたURLのオーディオ入力ストリームを取得
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)){
			
			//ファイルの形式取得
			AudioFormat af = ais.getFormat();
			
			//単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
			
			//指定された Line.Info オブジェクトの記述に一致するラインを取得
			Clip c = (Clip)AudioSystem.getLine(dataLine);
			
			//再生準備完了
			c.open(ais);
			
			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
}


