package com.bshf.util;

/**
 * 静态变量设置
 * 
 * @author zel
 * 
 */
public class StaticValue {
	public static String default_encoding = "UTF-8";
	public static String encoding_gbk = "gbk";
	
	public static String separator_tab = "\t";
	public static String separator_vertical_line = "\\|";
	public static String separator_space = " ";
	public static String separator_next_line = "\n";
	
	public static String NULL = null;
	
	/**
	 * 要去除的html tags,regex string
	 */
	public static String htmlTagRegex="";
//	public static String htmlTagRegex="";
	
	//定义分隔块变量，与spider中保持一致，减少项目上依赖
	public static String split_block_index = "#block_index#";
	
}
