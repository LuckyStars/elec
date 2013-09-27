package com.nbcedu.function.booksite.website.uitl;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * 
 * <p>文件上传</p>
 * @author 黎青春
 * Create at:2012-4-6 下午03:51:37
 */
public class FileUtil {

	public static final int BUFFERSIZE = 4096;


	  public static String getExtension(String fileName) {
		String extension = "";
	    if (fileName != null && !"".equals(fileName)) {
	    	extension = fileName.substring(fileName.lastIndexOf("."));
	    }
	    return extension;
	  }


	  public static void saveFile(String fileName, File file)
	    throws IOException {
	    createPath(fileName);
	    BufferedInputStream inputStream = null;
	    FileOutputStream outputStream = null;
	    try {
	      inputStream = new BufferedInputStream(new FileInputStream(file));
	      outputStream = new FileOutputStream(fileName);

	      byte[] b = new byte[4096];
	      int c;
	      while ((c = inputStream.read(b)) != -1) {
	        
	        outputStream.write(b, 0, c);
	      }
	    } finally {
	      if (outputStream != null) {
	        outputStream.flush();
	        outputStream.close();
	      }

	      if (inputStream != null)
	        inputStream.close();
	    }
	  }

	  public static void createPath(String fileName) {
	    int pathpos = fileName.lastIndexOf(File.separatorChar);
	    String path = "";
	    if (pathpos > 0) {
	      path = fileName.substring(0, pathpos);
	      File file = new File(path);
	      if ((!(file.exists())) && 
	        (!(file.mkdirs()))) {
	        return;
	      }

	      file = new File(fileName);
	      if (file.exists())
	        file.delete();
	    }
	  }

}
