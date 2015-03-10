package allInOne;

import java.io.File;

public class DeleteDirectory {

	/**
	 * µÝ¹éÉ¾³ýÄ¿Â¼ÏÂµÄËùÓÐÎÄ¼þ¼°×ÓÄ¿Â¼ÏÂËùÓÐÎÄ¼þ
	 * 
	 * @param dir
	 *            :½«ÒªÉ¾³ýµÄÎÄ¼þÄ¿Â¼
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// µÝ¹éÉ¾³ýÄ¿Â¼ÖÐµÄ×ÓÄ¿Â¼ÏÂ
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// Ä¿Â¼´ËÊ±Îª¿Õ£¬¿ÉÒÔÉ¾³ý
		return dir.delete();
	}
	public static boolean deleteDirs(String[] deleteDirs) {
		for(int i=0;i<deleteDirs.length;i++) {
			File dir = new File(deleteDirs[i]);
			boolean isSuccessful = deleteDir(dir);
			if(isSuccessful == true) {
				System.out.println("³É¹¦É¾³ýÄ¿Â¼£º" + dir);
			}
		}
		return false;
		
	}

	/**
	 * ²âÊÔ
	 */
	public static void main(String[] args) {
		String newDir2 = "new_dir2";
		boolean success = deleteDir(new File(newDir2));
		if (success) {
			System.out.println("Successfully deleted populated directory: "
					+ newDir2);
		} else {
			System.out.println("Failed to delete populated directory: "
					+ newDir2);
		}
	}
}
