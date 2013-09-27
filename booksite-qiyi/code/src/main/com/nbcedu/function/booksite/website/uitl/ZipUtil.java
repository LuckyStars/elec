package com.nbcedu.function.booksite.website.uitl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * 
 * <p>文件压缩，解压</p>
 * @author 黎青春
 * Create at:2012-4-6 下午03:50:46
 */
public class ZipUtil {

	public static boolean compressZip(List<String> fileNameList,String saveZipPathName)throws IOException {
		
		if (fileNameList!=null && fileNameList.size()>0) {
			
			List<File> fileList = new LinkedList<File>();
			//生成的ZIP文件名为Demo.zip   
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(saveZipPathName));

			//需要同时下载的两个文件result.txt ，source.txt   
			for (int i = 0; i < fileNameList.size(); i++) {
				fileList.add(new File((String) fileNameList.get(i)));
			}
			for (int i = 0; i < fileList.size(); i++) {
				FileInputStream fis = new FileInputStream((File) fileList.get(i));
				out.putNextEntry(new ZipEntry(((File) fileList.get(i)).getName()));
				byte[] buffer = new byte[1024];
				int len;
				//读入需要下载的文件的内容，打包到zip文件   
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
			return true;
		}
		return false;

	}

	public static void decompressionZip(String zipPath,String savePath) throws IOException {
		
		File file = new File(zipPath);// 压缩文件
		ZipFile zipFile = new ZipFile(file);// 实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
		// 实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
		ZipEntry zipEntry = null;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String fileName = zipEntry.getName();
			File temp = new File(savePath + fileName);
			if (!temp.getParentFile().exists())
				temp.getParentFile().mkdirs();
			OutputStream os = new FileOutputStream(temp);
			// 通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
			InputStream is = zipFile.getInputStream(zipEntry);
			int len = 0;
			while ((len = is.read()) != -1){
				os.write(len);
			}
			os.close();
			is.close();
		}
		zipInputStream.close();

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		testDecompressionZip();
	}
	
	static void testCompressZip() throws IOException{
		List<String> fileNameList = new LinkedList<String>();
		for(int i=0;i<23;i++){
			fileNameList.add("D:/jpg/"+i+".jpg");
		}
		ZipUtil.compressZip(fileNameList,"D:/jpg/图片.zip");
	}
	static void testDecompressionZip(){
		
		File file =new File("D:/jpg/图片.zip");
		if(file.exists()){
			try {
				ZipUtil.decompressionZip("D:/jpg/图片.zip", "D:/jpg/图片/");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("1111");
			file.delete();
		}
	}
}
