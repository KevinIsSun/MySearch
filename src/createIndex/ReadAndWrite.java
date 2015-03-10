package createIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadAndWrite {
	public static String readFileByChars(String fileName, String encoding) {
		try {
			String s = null;
			StringBuilder result = new StringBuilder();
			BufferedReader rin = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileName)), encoding));
			while ((s = rin.readLine()) != null) {
				result.append(s);
			}
			rin.close();
			return result.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void writeFileByChars(String fileName, String value) {

		String path = fileName;
		ByteBuffer bb = ByteBuffer.wrap(value.getBytes());
		value = null;
		FileChannel out2;
		try {
			out2 = new FileOutputStream(path).getChannel();
			out2.write(bb);
			bb.clear();
			bb = null;
			out2.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

