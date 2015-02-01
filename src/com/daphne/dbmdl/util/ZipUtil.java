package com.daphne.dbmdl.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
public class ZipUtil {
	private final static int BUFFERED_SIZE=512;
	/**
	 * 此方法描述的是： 获得压缩归档中第一个文件的内容
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:33:12
	 */
	public static String getFirstZipEentityContent(InputStream in)
			throws IOException {
		ZipInputStream zi = new ZipInputStream(in);
		StringBuilder sb = new StringBuilder();
		// Deal only with the first
		if ((zi.getNextEntry()) != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(zi));
			String str;
			
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			zi.closeEntry();
		}
		return sb.toString();
	}

	/**
	 * 此方法描述的是： 写一个string到一个zip输出流的第一个文件里
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:34:02
	 */

	public static void writeXmlToOutStream(String content, OutputStream out) {

		ZipOutputStream zout = new ZipOutputStream(out);
//		if(!(level==null||"".equals(level))){
//			zout.setLevel(Integer.valueOf(level));
//		}

		ZipEntry ze = new ZipEntry("result.xml");
		try {
			zout.putNextEntry(ze);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OutputStreamWriter wout = new OutputStreamWriter(zout);
		BufferedWriter bw=new BufferedWriter(wout);
		try {
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			char[] chars=new char[BUFFERED_SIZE];
			int i=0,len=content.length();
			for(;i<len&&i+BUFFERED_SIZE<len;i=i+BUFFERED_SIZE){
			
				content.getChars(i, i+BUFFERED_SIZE, chars, 0);
				bw.write(chars);
				bw.flush();
				
			}
			chars=new char[len-i];
			content.getChars(i, len, chars, 0);
			bw.write(chars);
			bw.flush();
			//bw.write(content, 0, content.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				wout.close();
				zout.close();
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
