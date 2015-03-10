package engine;

public class ResultModel {
	private String word;
	private String url; 
	private int wordV; 
	private String title; 
	private String partContent; 
	
	public ResultModel() {
	}

	public ResultModel(String word, String result) {
		this.word = word;
		if (result.indexOf("#split#") > 0) {
			String[] array = result.split("#split#");
			this.url = "http://" + array[0].replaceFirst("_", "/").replaceAll(".txt", "");
			this.title = array[1];
			this.partContent = array[2];
			this.wordV = Integer.parseInt(array[3].trim());
		}
	}

	public String word() {
		return word;
	}

	public String getUrl() {
		return this.url;
	}

	public String getTitle() {
		return this.title;
	}

	public int getWordV() {
		return this.wordV;
	}

	public String getPartContent() {
		return this.partContent;
	}

	public void addWordV(int v) {
		this.wordV += v;
	}

	public void printInfo() {
		System.out.println(word);
		System.out.println(url);
		
	}
}
