package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.*;

public class MyEngine {
	String indexFile; // the index file
	Vector<String> vecKey = new Vector<String>();
	HashMap<String, String> hashWord = null;
	final int isOr = 0;
	final int isAnd = 1;
	long time = 0;
	int symbol = 0;
	
	public MyEngine() {
		
	}
	
	public MyEngine(String indexFile) {
		this.indexFile = indexFile;
		long begin = System.currentTimeMillis();
		hashWord = new HashMap<String, String>();
		try {
			String line = null;
			BufferedReader rin = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(indexFile)), "UTF-8"));
			while ((line = rin.readLine()) != null) {
				String[] array = line.split("  ");
				hashWord.put(array[0], array[1]); // array[0]keyword,array[1]others
			}
			rin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		this.time = end - begin;
	}

	public ArrayList<ResultModel> getResultSet(String key) {
		int pos = key.indexOf("&");
		if (pos > 0) {
			symbol = 1;
			String keyBefore = key.substring(0, pos);
			String keyAfter = key.substring(pos + 1, key.length());
			vecKey.add(keyBefore);
			vecKey.add(keyAfter);
			System.out.println("keyBefore is:" + keyBefore + "keyAfter is:"
					+ keyAfter);
			ArrayList<ResultModel> modList = new ArrayList<ResultModel>();
			ArrayList<ResultModel> modListBefore = new ArrayList<ResultModel>();
			ArrayList<ResultModel> modListAfter = new ArrayList<ResultModel>();
			if (this.hashWord.size() > 0) {
				long begin = System.currentTimeMillis();
				ResultModel[] modArray = null;
				String resultBefore = this.hashWord.get(keyBefore);
				String resultAfter = this.hashWord.get(keyAfter);
				String[] array = resultBefore.split("#next#"); // µÃµ½´æÔÚ¸Ã¹Ø¼ü×ÖµÄËùÓÐÎÄ±¾ÎÄ¼þÐÅÏ¢
				modArray = new ResultModel[array.length]; // Ã¿¸öÎÄ±¾ÎÄ¼þÐÅÏ¢¶¼¿ÉÒÔ»ñµÃÒ»¸öResultModel
				for (int i = 0; i < array.length; i++)
					modArray[i] = new ResultModel(keyBefore, array[i]);

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modListBefore.add(modArray[i]);
					}
					// ½«½á¹û°´ÕÕ´ÊÆµÅÅÐò
					Collections.sort(modList, new sortByWordNum());
				}
				array = resultAfter.split("#next#"); // µÃµ½´æÔÚ¸Ã¹Ø¼ü×ÖµÄËùÓÐÎÄ±¾ÎÄ¼þÐÅÏ¢
				modArray = new ResultModel[array.length]; // Ã¿¸öÎÄ±¾ÎÄ¼þÐÅÏ¢¶¼¿ÉÒÔ»ñµÃÒ»¸öResultModel
				for (int i = 0; i < array.length; i++)
					modArray[i] = new ResultModel(keyAfter, array[i]);

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modListAfter.add(modArray[i]);
					}
					// ½«½á¹û°´ÕÕ´ÊÆµÅÅÐò
					Collections.sort(modList, new sortByWordNum());
				}
				for(int i=0;i<modListAfter.size();i++) {
					for(int j=0;j<modListBefore.size();j++) {
						if(modListBefore.get(j).getUrl().equals(modListAfter.get(i).getUrl())) {
							modList.add(modListBefore.get(j));
						}
					}
				}
				long end = System.currentTimeMillis();
				this.time += (end - begin);
			}
			return modList;
		}
		int posDiff = key.indexOf("-");
		if (posDiff > 0) {
			symbol = 1;
			String keyBefore = key.substring(0, posDiff);
			String keyAfter = key.substring(posDiff + 1, key.length());
			vecKey.add(keyBefore);
			vecKey.add(keyAfter);
			System.out.println("keyBefore is:" + keyBefore + "keyAfter is:"
					+ keyAfter);
			ArrayList<ResultModel> modList = new ArrayList<ResultModel>();
			ArrayList<ResultModel> modListBefore = new ArrayList<ResultModel>();
			ArrayList<ResultModel> modListAfter = new ArrayList<ResultModel>();
			if (this.hashWord.size() > 0) {
				long begin = System.currentTimeMillis();
				ResultModel[] modArray = null;
				String resultBefore = this.hashWord.get(keyBefore);
				String resultAfter = this.hashWord.get(keyAfter);
				String[] array = resultBefore.split("#next#"); // µÃµ½´æÔÚ¸Ã¹Ø¼ü×ÖµÄËùÓÐÎÄ±¾ÎÄ¼þÐÅÏ¢
				modArray = new ResultModel[array.length]; // Ã¿¸öÎÄ±¾ÎÄ¼þÐÅÏ¢¶¼¿ÉÒÔ»ñµÃÒ»¸öResultModel
				for (int i = 0; i < array.length; i++)
					modArray[i] = new ResultModel(keyBefore, array[i]);

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modListBefore.add(modArray[i]);
					}
					// ½«½á¹û°´ÕÕ´ÊÆµÅÅÐò
					Collections.sort(modList, new sortByWordNum());
				}
				array = resultAfter.split("#next#"); // µÃµ½´æÔÚ¸Ã¹Ø¼ü×ÖµÄËùÓÐÎÄ±¾ÎÄ¼þÐÅÏ¢
				modArray = new ResultModel[array.length]; // Ã¿¸öÎÄ±¾ÎÄ¼þÐÅÏ¢¶¼¿ÉÒÔ»ñµÃÒ»¸öResultModel
				for (int i = 0; i < array.length; i++)
					modArray[i] = new ResultModel(keyAfter, array[i]);

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modListAfter.add(modArray[i]);
					}
					// ½«½á¹û°´ÕÕ´ÊÆµÅÅÐò
					Collections.sort(modList, new sortByWordNum());
				}
				for(int i=0;i<modListAfter.size();i++) {
					for(int j=0;j<modListBefore.size();j++) {
						if(modListBefore.get(j).getUrl().equals(modListAfter.get(i).getUrl())) {
							modListBefore.remove(j);
						}
					}
				}
				for(int i=0;i<modListBefore.size();i++) {
					modList.add(modListBefore.get(i));
				}
				long end = System.currentTimeMillis();
				this.time += (end - begin);
			}
			return modList;
		}

		ArrayList<ResultModel> modList = new ArrayList<ResultModel>();
		if (this.hashWord.size() > 0) {
			long begin = System.currentTimeMillis();
			ResultModel[] modArray = null;
			// ¶Ô¹Ø¼ü×Ö·Ö´Ê
			IKSegmenter iksegmentation = new IKSegmenter(new StringReader(key),
					true);
			Lexeme lexeme = null;
			try {
				while ((lexeme = iksegmentation.next()) != null) {
					System.out.println(lexeme.getLexemeText());
					vecKey.add(lexeme.getLexemeText());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ·Ö±ð²éÕÒ¸÷¸ö´ÊÔÚË÷ÒýÖÐµÄÆ¥Åä
			for (String strKey : vecKey) {
				String result = this.hashWord.get(strKey);
				if (result != null) {
					String[] array = result.split("#next#"); // µÃµ½´æÔÚ¸Ã¹Ø¼ü×ÖµÄËùÓÐÎÄ±¾ÎÄ¼þÐÅÏ¢
					modArray = new ResultModel[array.length]; // Ã¿¸öÎÄ±¾ÎÄ¼þÐÅÏ¢¶¼¿ÉÒÔ»ñµÃÒ»¸öResultModel
					for (int i = 0; i < array.length; i++)
						modArray[i] = new ResultModel(key, array[i]);
				}
				// }

				if (modArray != null) {
					for (int i = 0; i < modArray.length; i++) {
						modList.add(modArray[i]);
					}

					// ºÏ²¢ÏàÍ¬³ö´¦ÄÚÈÝµÄ´ÊÆµ
					this.ResultMerger(modList);
					// ½«½á¹û°´ÕÕ´ÊÆµÅÅÐò
					Collections.sort(modList, new sortByWordNum());
				}
			}
			long end = System.currentTimeMillis();
			this.time += (end - begin);
		}
		return modList;
	}
	
	// »ñµÃ´¦ÀíÊ±¼ä
	public long getTime() {
		return this.time;
	}

	// ºÏ²¢ÏàÍ¬³ö´¦ÄÚÈÝµÄ´ÊÆµ
	private void ResultMerger(ArrayList<ResultModel> modList) {
		for (int i = 0; i < modList.size(); i++)
			for (int j = i + 1; j < modList.size(); j++) {
				if (modList.get(i) != null && modList.get(j) != null) {
					if (modList.get(i).getUrl().trim()
							.equals(modList.get(j).getUrl().trim())) {
						modList.get(i).addWordV(modList.get(j).getWordV());// Ïà¼ÓÆµÂÊ
						modList.remove(j);
					}
				}
			}
	}
		
	
	public String HighLightKey(String content) {
		content = content.replaceAll(" ", "");
		for (String word : this.vecKey) {
			content = content.replaceAll(word,
					"<font style='color:#ff0000;font-weight:bold;'>" + word
							+ "</font>");
		}

		return content.replaceAll(
				"</font>[\\W]*<font style='color:#ff0000;font-weight:bold;'>",
				"");
	}

	public static void main(String[] argv) {
		MyEngine index = new MyEngine("WebContent/index.txt");
		ArrayList<ResultModel> testList = index.getResultSet("ÖÐ¹ú&ÃÀ¹ú");
		for(int i=0;i<testList.size();i++) {
			testList.get(i).printInfo();
			System.out.println(i);
		}
		
	}
}
