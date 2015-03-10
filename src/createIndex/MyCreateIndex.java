package createIndex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class MyCreateIndex {
	long start, end;
	long temp = 0;

	public MyCreateIndex() {
		HashMap<String, String> hashResult = new HashMap<String, String>();
		File dirFile = new File("wordDoc");
		File[] fileList = dirFile.listFiles();

		System.out.println("ÕýÔÚ¶ÔÎÄ±¾ÄÚÈÝ½øÐÐ·ÖÎö£¬¿ÉÄÜ»áÐèÒª½Ï³¤Ê±¼ä£¬ÇëÄÍÐÄµÈ´ý¡­¡­");
		start = System.currentTimeMillis();
		for (int i = 0; i < fileList.length; i++) {
			String fileName = fileList[i].getName();
			System.out.println("\tÏÖÔÚÕýÔÚ¶ÔÎÄ¼þ" + fileName + "½øÐÐ·ÖÎö");
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			String content = ReadAndWrite.readFileByChars(
					"wordDoc/" + fileName, "gbk");
			String[] wordArray = content.split(" ");
			for (int j = 0; j < wordArray.length; j++) {
				if (hashMap.keySet().contains(wordArray[j])) {
					Integer integer = (Integer) hashMap.get(wordArray[j]);
					int value = integer.intValue() + 1;
					hashMap.put(wordArray[j], new Integer(value));
				} else
					hashMap.put(wordArray[j], new Integer(1));
			}
			// »ñµÃ±êÌâ
			String title_origin = ReadAndWrite.readFileByChars("titleDoc/"
					+ fileName, "gbk");
			// »ñµÃ¸ú´ÊÏà¹ØµÄ²¿·ÖÄÚÈÝ
			String fullContent_origin = ReadAndWrite.readFileByChars("srcDoc/"
					+ fileName, "gbk");
			for (String str : hashMap.keySet()) {
				String title = title_origin;
				String fullContent = fullContent_origin;
				String partContent = "";
				int wordStart = fullContent.indexOf(str);// °üº¬´ÊµÄÎ»ÖÃ
				while (wordStart > 0) {
					String strTmp;
					int s = 0, e = fullContent.length();
					if (wordStart > 10)
						s = wordStart - 10;
					else
						s = 0;
					if (e > (wordStart + 10))
						e = wordStart + 10;
					strTmp = fullContent.substring(s, e);
					// partContent.append(fullContent.substring(s,
					// e)).append("......");
					partContent += (strTmp + "......");
					fullContent = fullContent.substring(e);
					wordStart = fullContent.indexOf(str);
				}
				// ÐÎ³Éµ¹ÅÅË÷Òý
				String tmp = fileName + "#split#" + title + "#split#"
						+ partContent + "#split#" + hashMap.get(str);
				if (hashResult.keySet().contains(str)) {// °üº¬¸Ã´Ê
					String value = (String) hashResult.get(str);
					value += ("#next#" + tmp);
					hashResult.put(str, value);
				} else
					hashResult.put(str, tmp);
			}

		}
		end = System.currentTimeMillis();
		System.out.println("ÎÄ¼þÄÚÈÝ·ÖÎöÍê±Ï£¬¹²ÓÃÊ±£º" + (end - start) + "ms");

		if (hashResult.size() > 0) {
			StringBuilder value = new StringBuilder("");

			System.out.println("ÏÖÔÚÕýÔÚ½¨Á¢Ë÷ÒýÄÚÈÝ£¬¿ÉÄÜ»áÐèÒª½Ï³¤Ê±¼ä£¬ÇëÄÍÐÄµÈ´ý¡­¡­");
			start = System.currentTimeMillis();
			for (String str : hashResult.keySet()) {
				StringBuilder tmp = new StringBuilder(str).append("  ").append(
						hashResult.get(str));
				// String tmp = str + "  " + hashResult.get(str); // Á½¸ö¿Õ¸ñ
				value.append(tmp).append("#LINE#");
				// value += (tmp + "#LINE#");

			}
			end = System.currentTimeMillis();
			System.out.println("Ë÷ÒýÄÚÈÝ½¨Á¢Íê±Ï£¬¹²ÓÃÊ±£º" + (end - start) + "ms");

			System.out.println("ÏÖÔÚÕýÔÚ½«Ë÷ÒýÄÚÈÝÐ´Èë´ÅÅÌ£¬¿ÉÄÜ»áÐèÒª½Ï³¤Ê±¼ä£¬ÇëÄÍÐÄµÈ´ý¡­¡­");
			start = System.currentTimeMillis();
			this.writeFileByChars("WebContent/index.txt", value.toString());
			end = System.currentTimeMillis();
			System.out.println("Ë÷ÒýÄÚÈÝÐ´ÈëÍê±Ï£¬¹²ÓÃÊ±£º" + (end - start) + "ms");
		}

	}

	public void writeFileByChars(String fileName, String value) {
		String path = fileName;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path, false), "UTF-8"));

			String[] arryStr = value.split("#LINE#");
			for (int i = 0; i < arryStr.length; i++) {
				bw.write(arryStr[i]);
				bw.write(13);
				bw.write(10);
			}

			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new MyCreateIndex();

	}

}

