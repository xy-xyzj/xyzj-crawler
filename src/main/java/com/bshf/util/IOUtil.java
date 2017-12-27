package com.bshf.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * 读取字典时的I/O工具类
 * 
 * @author zel
 * 
 */
public class IOUtil {
	public static String readDirOrFile(String filePath, String fileEncoding) {
		File f = new File(filePath);
		StringBuilder sb = new StringBuilder();
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File temp_file : files) {
				sb.append(readDirOrFile(temp_file.getAbsolutePath(),
						fileEncoding));
			}
			return sb.toString();
		}
		return readFile(filePath, fileEncoding);
	}

	public static ArrayList<String> readDirOrFileToList(String filePath,
			String fileEncoding, ArrayList<String> linkList) {
		File f = new File(filePath);
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File temp_file : files) {
				linkList.add(readFileWithRegexFilter(temp_file
						.getAbsolutePath(), fileEncoding));
			}
			return linkList;
		} else {
			linkList.add(readFileWithRegexFilter(filePath, fileEncoding));
		}
		return linkList;
	}

	/**
	 * fileEncoding若为null,则采用系统默认编码
	 * 
	 * @param filePath
	 * @param fileEncoding
	 * @return
	 */
	public static String readFile(String filePath, String fileEncoding) {
		if (fileEncoding == null) {
			fileEncoding = System.getProperty("file.encoding");
		}
		File file = new File(filePath);
		BufferedReader br = null;

		String line = null;

		StringBuilder sb = new StringBuilder();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), fileEncoding));
			while ((line = br.readLine()) != null) {
				// if(line.startsWith("中")) {
				sb.append(line + "\n");
				// }
			}
			// System.out.println("line---"+line);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String readFileWithRegexFilter(String filePath,
			String fileEncoding) {
		if (fileEncoding == null) {
			fileEncoding = System.getProperty("file.encoding");
		}
		File file = new File(filePath);
		BufferedReader br = null;

		String line = null;

		StringBuilder sb = new StringBuilder();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), fileEncoding));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 将一个字符串写入到一个文件
	 * 
	 * @param path
	 *            储存的文件路径
	 * @param value
	 *            储存的文件内容
	 * @throws IOException
	 */
	public static synchronized void writeFile(String path, String value) {
		File f = new File(path);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			fos.write(value.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws Exception {
		// String source=readFile("resource/library.dic",null);
		// String source = readFile(ReadConfigUtil.getValue("dic.path"), null);
		// String source = readDirOrFile("d://temp", "gbk");
		// System.out.println(source);

		String source_string = IOUtil.readFile("d:\\test\\new_words2.txt",
				"utf-8");
		StringReader sr = new StringReader(source_string);
		BufferedReader br = new BufferedReader(sr);
		String temp = null;
		StringBuilder sb = new StringBuilder();

		HashSet<String> hashSet = new HashSet<String>();

		while ((temp = br.readLine()) != null) {
			if (temp.trim().length() > 1 && temp.trim().length() <= 4) {
				if (!hashSet.contains(temp)) {
					sb.append(temp + "\n");
				} else {
					hashSet.add(temp);
				}
			}
		}
		IOUtil.writeFile("d:\\test\\new_words3.txt", sb.toString());
	}
}
