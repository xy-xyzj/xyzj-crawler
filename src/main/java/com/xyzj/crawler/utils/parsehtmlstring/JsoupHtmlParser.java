package com.xyzj.crawler.utils.parsehtmlstring;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 对Jsoup的再次封装,更加简明扼要
 * 
 * @author zel
 * 
 */
public class JsoupHtmlParser {
	/**
	 * 得到指定文档的纯文档
	 * 
	 * @param htmlSource
	 * @return
	 */
	public static String getCleanTxt(String htmlSource) {
		if (htmlSource == null || htmlSource.isEmpty()) {
			return StaticValue.NULL;
		}
		return Jsoup.clean(htmlSource, Whitelist.none());
	}

	/**
	 * 沒有DataFormatStatus參數的情況
	 * 
	 * @param htmlSource
	 * @param tagName
	 * @return
	 */
	public static String getTagContent(String htmlSource, String tagName) {
		return getTagContent(htmlSource, tagName, DataFormatStatus.CleanTxt);
	}

	/**
	 * 得到指定tag标签的的内容，包括纯文本和标签全部内容两种格式
	 * 
	 * @param htmlSource
	 * @param tagName
	 * @param dataFormatStatus
	 * @return
	 */
	public static String getTagContent(String htmlSource, String tagName,
			DataFormatStatus dataFormatStatus) {
		if (htmlSource == null || htmlSource.isEmpty()) {
			return StaticValue.NULL;
		}
		SystemAssert.assertNotNull(dataFormatStatus);

		StringBuilder sb = new StringBuilder();
		Document doc = Jsoup.parse(htmlSource);
		Elements elements = doc.getElementsByTag(tagName);

		Iterator<Element> iterator = elements.iterator();
		Element element = null;

		if (dataFormatStatus == DataFormatStatus.CleanTxt) {
			while (iterator.hasNext()) {
				element = iterator.next();
				sb.append(getCleanTxt(element.toString()));
				sb.append(StaticValue.separator_next_line);
			}
		} else {
			while (iterator.hasNext()) {
				element = iterator.next();
				sb.append(element.toString());
				sb.append(StaticValue.separator_next_line);
			}
		}

		return sb.toString();
	}

	/**
	 * getNestTagContent无参数的情况
	 * 
	 * @param htmlSource
	 * @param tagList
	 * @return
	 */
	public static List<String> getNestTagContent(String htmlSource,
			List<String> tagList, boolean isFilter) {
		return getNestTagContent(htmlSource, tagList,
				DataFormatStatus.CleanTxt, isFilter);
	}

	/**
	 * 得到嵌套的标签的数据,包括纯文本、标签全部内容两种格式
	 * 
	 * @param htmlSource
	 * @param tagList
	 * @param dataFormatStatus
	 * @return
	 */
	public static List<String> getNestTagContent(String htmlSource,
			List<String> tagList, DataFormatStatus dataFormatStatus,
			boolean isFilter) {
		if (htmlSource == null || htmlSource.isEmpty() || tagList == null
				|| tagList.isEmpty()) {
			return null;
		}

		SystemAssert.assertNotNull(dataFormatStatus);

		Document doc = Jsoup.parse(htmlSource);// 先预解析
		Iterator<String> tagIteraotr = tagList.iterator();
		String temp_tag = null;

		List<Element> temp_list_element = new LinkedList<Element>();
		Elements elements = null;

		Elements temp_elements = null;
		// 暂存循环迭代时候的结果
		List<String> temp_list_line = new LinkedList<String>();
		Document temp_doc = null;// 暂存二次解析出来的doc
		boolean isFirst = true;// 标志是否是第一次进行选择器处理
		while (tagIteraotr.hasNext()) {
			temp_tag = tagIteraotr.next();
			if (isFirst) {
				elements = doc.getElementsByTag(temp_tag);
				isFirst = false;
			} else {
				/**
				 * 对每个先前的结果集进行过滤
				 */
				elements.clear();
				for (String line : temp_list_line) {
					if (line != null && (!line.isEmpty())) {
						temp_doc = Jsoup.parse(line);// 先预解析
						temp_elements = temp_doc.getElementsByTag(temp_tag);
						if (temp_elements != null && (!temp_elements.isEmpty())) {
							elements.addAll(temp_elements);
						}
					}
				}
			}

			temp_list_element.clear();
			temp_list_element.addAll(elements);

			Iterator<Element> elementIteraotr = temp_list_element.iterator();

			temp_list_line.clear();
			while (elementIteraotr.hasNext()) {
				Element element = elementIteraotr.next();
				temp_list_line.add(getTagContent(element.toString(), temp_tag,
						DataFormatStatus.TagAllContent));
			}
		}

		temp_list_line = doListFilter(temp_list_line, dataFormatStatus,
				isFilter);

		return temp_list_line;
	}

	/**
	 * 对选择器的默认调用
	 * 
	 * @param htmlSource
	 * @param selectorList
	 * @param isFilter
	 * @return
	 */
	public static List<String> getNodeContentBySelector(String htmlSource,
			List<String> selectorList, boolean isFilter) {
		return getNodeContentBySelector(htmlSource, selectorList,
				DataFormatStatus.CleanTxt, isFilter);
	}

	/**
	 * 通过选择器来检索和获取节点数据,直接以集合形式传参
	 * 
	 * @param htmlSource
	 * @param selectorList
	 * @return
	 */
	public static List<String> getNodeContentBySelector(String htmlSource,
			List<String> selectorList, DataFormatStatus dataFormatStatus,
			boolean isFilter) {
		if (htmlSource == null || htmlSource.isEmpty() || selectorList == null
				|| selectorList.isEmpty()) {
			return null;
		}
		SystemAssert.assertNotNull(dataFormatStatus);

		Document doc = Jsoup.parse(htmlSource);// 先预解析

		Iterator<String> selectorIteraotr = selectorList.iterator();
		String temp_selector = null;
		List<Element> temp_list_element = new LinkedList<Element>();
		Elements elements = null;
		Elements temp_elements = null;
		// 暂存循环迭代时候的结果
		List<String> temp_list_line = new LinkedList<String>();
		Document temp_doc = null;// 暂存二次解析出来的doc
		boolean isFirst = true;// 标志是否是第一次进行选择器处理
		while (selectorIteraotr.hasNext()) {

			temp_selector = selectorIteraotr.next();
			if (isFirst) {
				elements = doc.select(temp_selector);

				//System.out.println("elements: "+elements.toString());
				isFirst = false;
			} else {
				/**
				 * 对每个先前的结果集进行过滤
				 */
				elements.clear();
				for (String line : temp_list_line) {
					if (line != null && (!line.isEmpty())) {
						temp_doc = Jsoup.parse(line);// 先预解析
						temp_elements = temp_doc.select(temp_selector);

						if (temp_elements != null && (!temp_elements.isEmpty())) {
							elements.addAll(temp_elements);
						}
					}
				}
			}
			temp_list_element.clear();
			temp_list_element.addAll(elements);

			Iterator<Element> elementIteraotr = temp_list_element.iterator();

			temp_list_line.clear();
			while (elementIteraotr.hasNext()) {
				Element element = elementIteraotr.next();
                //System.out.println("element: "+element);

				temp_list_line.add(element.toString());
			}
		}
		// 做下过滤
		temp_list_line = doListFilter(temp_list_line, dataFormatStatus,
				isFilter);


		return temp_list_line;
	}

	/**
	 * 第二版，主要是为有多个完全相同的内容块时，选取其中指定的索引位置的内容块,内容块位置从基数一开始
	 * 
	 * @param htmlSource
	 * @param selectorList
	 * @param dataFormatStatus
	 * @param isFilter
	 * @return
	 */
	public static List<String> getNodeContentBySelector4MultiSameBlock(
			String htmlSource, List<String> selectorList,
			DataFormatStatus dataFormatStatus, boolean isFilter) {
		if (htmlSource == null || htmlSource.isEmpty() || selectorList == null
				|| selectorList.isEmpty()) {
			return null;
		}
		SystemAssert.assertNotNull(dataFormatStatus);

		Document doc = Jsoup.parse(htmlSource);// 先预解析

		Iterator<String> selectorIteraotr = selectorList.iterator();
        String temp_selector = null;
		List<Element> temp_list_element = new LinkedList<Element>();
		Elements elements = null;
		Elements temp_elements = null;
		// 暂存循环迭代时候的结果
		List<String> temp_list_line = new LinkedList<String>();
		Document temp_doc = null;// 暂存二次解析出来的doc
		boolean isFirst = true;// 标志是否是第一次进行选择器处理
		String[] split_array = null;
		boolean find_block_index = false;// 标志是否有block_index存在
		int block_index = 0;// 如果block_index存在，则其具体的值在这
		while (selectorIteraotr.hasNext()) {
			temp_selector = selectorIteraotr.next();
			// 判断是否有#index#来做分隔
			split_array = temp_selector.split(StaticValue.split_block_index);
			temp_selector = split_array[0];

			//System.out.println("==="+temp_selector);
			if (split_array.length == 1) {
				find_block_index = false;
			} else if (split_array.length == 2) {
				try {
					block_index = Integer.parseInt(split_array[1]);
					if (block_index <= 0) {
						throw new Exception(
								"block index value <= 0 is wrong, plase the base value is 1");
					}
					find_block_index = true;
				} catch (Exception e) {
					find_block_index = false;
					e.printStackTrace();
				}
			} else {
				try {
					throw new Exception("jsoup规则写法有错误，目前只支持一个规则行中最多有一个 "
							+ StaticValue.split_block_index
							+ ",且在行末尾\n如果想写多个请用#split_big#来区分");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (isFirst) {
				elements = doc.select(temp_selector);
				isFirst = false;
			} else {
				/**
				 * 对每个先前的结果集进行过滤
				 */
				elements.clear();
				for (String line : temp_list_line) {
					if (line != null && (!line.isEmpty())) {
						temp_doc = Jsoup.parse(line);// 先预解析
						temp_elements = temp_doc.select(temp_selector);

						if (temp_elements != null && (!temp_elements.isEmpty())) {
							elements.addAll(temp_elements);
						}
					}
				}
			}

			// 对前边的elements做block_index截取
			if (find_block_index
					&& StringOperatorUtil.isNotBlankCollection(elements)) {
				int size = elements.size();
				if (size >= block_index) {
					List<Element> eleList = new LinkedList<Element>();
					eleList.add(elements.get(block_index - 1));
					elements = new Elements(eleList);
				}
			}

			temp_list_element.clear();
			temp_list_element.addAll(elements);

			Iterator<Element> elementIteraotr = temp_list_element.iterator();

			temp_list_line.clear();
			while (elementIteraotr.hasNext()) {
				Element element = elementIteraotr.next();
				temp_list_line.add(element.toString());
			}
		}
		// 做下过滤
		temp_list_line = doListFilter(temp_list_line, dataFormatStatus,
				isFilter);

		return temp_list_line;
	}

	/**
	 * 取得字符串source的对应的属性attr的值
	 * 
	 * @param source
	 * @param attr
	 * @return
	 */
	public static String getAttributeValue(String source, String attr) {
		if (source == null || attr == null) {
			return null;
		}
		Document doc = Jsoup.parse(source);
		Elements elements = doc.select("[" + attr + "]");
		String temp = null;
		if (elements != null) {
			for (Element ele : elements) {
				temp = ele.attr(attr);
			}
		}
		return temp;
	}

	/**
	 * 集合方式处理，得取集合中的每个元素串的attr属性对应的值
	 * 
	 * @param sourceList
	 * @param attr
	 * @return
	 */
	public static List<String> getAttributeValueList(List<String> sourceList,
			String attr) {
		if (sourceList == null || attr == null) {
			return null;
		}
		List<String> resultList = new LinkedList<String>();
		String selString = "[" + attr + "]";
		for (String tempLine : sourceList) {
			Document doc = Jsoup.parse(tempLine);
			Elements elements = doc.select(selString);
			if (elements != null) {
				for (Element ele : elements) {
					resultList.add(ele.attr(attr));
				}
			}
		}

		return resultList;
	}

	/**
	 * 做最后的字符串过滤，该方法对用户透明
	 * 
	 * @param temp_list_line
	 * @param dataFormatStatus
	 * @return
	 */
	private static List<String> doListFilter(List<String> temp_list_line,
			DataFormatStatus dataFormatStatus, boolean isFilter) {
		if (temp_list_line == null || temp_list_line.isEmpty()) {
			return null;
		}

		SystemAssert.assertNotNull(dataFormatStatus);

		// 最终的结合集进行所要的数据格式的过滤
		if (dataFormatStatus == DataFormatStatus.CleanTxt) {
			List<String> cleanResultList = new LinkedList<String>();
			String temp_clean = null;
			for (String item : temp_list_line) {
				if (isFilter) {
					item = item.replaceAll(StaticValue.htmlTagRegex, "");
				}
				if ((temp_clean = getCleanTxt(item)) != null
						&& (!temp_clean.isEmpty())) {
					cleanResultList.add(temp_clean);
				}
				/*	for (int i = 0; i < temp_list_line.size(); i ++) {
						if(null != cleanResultList && cleanResultList.get(i).indexOf(temp_clean) != -1) {

						} else {
							cleanResultList.add(temp_clean);
						}
					}
				}*/
			}
			return cleanResultList;
		}
		return temp_list_line;
	}

	/**
	 * url抽取相关,不带过滤条件
	 * @param htmlSource
	 * @return
	 */
	public static List<String> getAllHref(String htmlSource) {
		try {
			Document doc = Jsoup.parse(htmlSource);// 先预解析
			Elements links = doc.getElementsByTag("a");
			String linkHref = null;
			List<String> urlList = new LinkedList<String>();
			for (Element link : links) {
				linkHref = link.attr("href");
				// String linkText = link.text();
				if (UrlOperatorUtil.isValidUrl(linkHref)
						&& linkHref.startsWith("http:")) {
					urlList.add(linkHref.trim());
				}
			}
			return urlList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * url抽取相关,附带添加相关的host前缀
	 * 
	 * @param fromUrl
	 * @param host
	 * @param htmlSource
	 * @return
	 */
	public static List<String> getAllHref4AddHost(String fromUrl, String host,
			String htmlSource) {
		try {
			Document doc = Jsoup.parse(htmlSource);// 先预解析
			Elements links = doc.getElementsByTag("a");
			String linkHref = null;
			List<String> urlList = new LinkedList<String>();
			// String host = UrlOperatorUtil.getHost(fromUrl);

			for (Element link : links) {
				linkHref = link.attr("href");
				if (linkHref.startsWith("http://")) {
					// 不作处理
				} else if (linkHref.startsWith("/")) {
					// 绝对地址
					linkHref = "http://" + host + linkHref;
				} else {
					// 相对地址
					// 说明是相对路径
					int last_pos = fromUrl.lastIndexOf("/");
					String relative_path = fromUrl.substring(0, last_pos + 1);
					linkHref = relative_path + linkHref;
				}
				if (UrlOperatorUtil.isValidUrl(linkHref)
						&& linkHref.startsWith("http:")) {
					urlList.add(linkHref.trim());
				}
			}
			return urlList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 去掉标签中某一部分内容,暂定位第一版
	 * 
	 * @param htmlSource
	 * @param selector
	 * @param removeSelector
	 * @return
	 */
	public static String removeInnerContent(String htmlSource, String selector,
			List<String> removeSelector) {
		if (selector == null
				|| StringOperatorUtil.isBlankCollection(removeSelector)) {
			return htmlSource;
		}
		try {
			Document doc = Jsoup.parse(htmlSource);// 先预解析
			Elements elements = doc.select(selector);
			String result = null;
			if (elements != null) {
				for (Element ele : elements) {
					for (String sel : removeSelector) {
						ele.select(sel).remove();
					}
					 result = ele.toString();
					//result = JsoupHtmlParser.getCleanTxt(ele.toString());
					break;
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    /**
     *
     *百度词条重新爬取数据
     * @param htmlSource
     * @param selectorList
     * @return
     */
    public static List<String> getContentBySelector(String htmlSource,
                                                        List<String> selectorList, DataFormatStatus dataFormatStatus,
                                                        boolean isFilter) {
        if (htmlSource == null || htmlSource.isEmpty() || selectorList == null
                || selectorList.isEmpty()) {
            return null;
        }
        SystemAssert.assertNotNull(dataFormatStatus);

        Document doc = Jsoup.parse(htmlSource);// 先预解析

        Iterator<String> selectorIteraotr = selectorList.iterator();
        String temp_selector = null;
        List<Element> temp_list_element = new LinkedList<Element>();
        Elements elements = null;
        Elements temp_elements = null;
        // 暂存循环迭代时候的结果
        List<String> temp_list_line = new LinkedList<String>();
        Document temp_doc = null;// 暂存二次解析出来的doc
        boolean isFirst = true;// 标志是否是第一次进行选择器处理
        while (selectorIteraotr.hasNext()) {

            temp_selector = selectorIteraotr.next();
            if (isFirst) {
                elements = doc.select(temp_selector);

                //System.out.println("elements: "+elements.toString());
                isFirst = false;
            } else {
                /**
                 * 对每个先前的结果集进行过滤
                 */
                elements.clear();
                for (String line : temp_list_line) {
                    if (line != null && (!line.isEmpty())) {
                        temp_doc = Jsoup.parse(line);// 先预解析
                        temp_elements = temp_doc.select(temp_selector);

                        if (temp_elements != null && (!temp_elements.isEmpty())) {
                            elements.addAll(temp_elements);
                        }
                    }
                }
            }
            temp_list_element.clear();
            temp_list_element.addAll(elements);

            Iterator<Element> elementIteraotr = temp_list_element.iterator();
            temp_list_line.clear();
            while (elementIteraotr.hasNext()) {
                Element element = elementIteraotr.next();
				String aClass = element.attr("class");
				String paraText = null;
				//String paraText = element.toString();
                if("para".equals(aClass)){
					paraText = element.text();
					Element element2 = element.nextElementSibling();
					String aClass2 = element2.attr("class");
					while ("para".equals(aClass2)){
						paraText +=element2.text();
						element2 = element2.nextElementSibling();
						aClass2 = element2.attr("class");
						if(!"para".equals(aClass2)){
							aClass=aClass2;
						}
					}

				}else{
					paraText = element.toString();

				}


                /*while ("para".equals(aClass)) {
                    element = element.nextElementSibling();
                    aClass = element.attr("class");

                    if ("para".equals(aClass)){
						String text = element.text();
						paraText+=text;
					}
                }*/
                temp_list_line.add(paraText);
            }
        }
        // 做下过滤
        temp_list_line = doListFilter(temp_list_line, dataFormatStatus,
                isFilter);


        return temp_list_line;
    }
	/**
	 *
	 *中国军医网数据爬取
	 * @param htmlSource
	 * @param selectorList
	 * @return
	 */
	public static List<String> getContentByChinese(String htmlSource,
													List<String> selectorList, DataFormatStatus dataFormatStatus,
													boolean isFilter) {
		if (htmlSource == null || htmlSource.isEmpty() || selectorList == null
				|| selectorList.isEmpty()) {
			return null;
		}
		SystemAssert.assertNotNull(dataFormatStatus);

		Document doc = Jsoup.parse(htmlSource);// 先预解析

		Iterator<String> selectorIteraotr = selectorList.iterator();
		String temp_selector = null;
		List<Element> temp_list_element = new LinkedList<Element>();
		Elements elements = null;
		Elements temp_elements = null;
		// 暂存循环迭代时候的结果
		List<String> temp_list_line = new LinkedList<String>();
		Document temp_doc = null;// 暂存二次解析出来的doc
		boolean isFirst = true;// 标志是否是第一次进行选择器处理
		while (selectorIteraotr.hasNext()) {

			temp_selector = selectorIteraotr.next();
			if (isFirst) {
				elements = doc.select(temp_selector);

				//System.out.println("elements: "+elements.toString());
				isFirst = false;
			} else {
				/**
				 * 对每个先前的结果集进行过滤
				 */
				elements.clear();
				for (String line : temp_list_line) {
					if (line != null && (!line.isEmpty())) {
						temp_doc = Jsoup.parse(line);// 先预解析
						temp_elements = temp_doc.select(temp_selector);

						if (temp_elements != null && (!temp_elements.isEmpty())) {
							elements.addAll(temp_elements);
						}
					}
				}
			}
			temp_list_element.clear();
			temp_list_element.addAll(elements);

			Iterator<Element> elementIteraotr = temp_list_element.iterator();

			temp_list_line.clear();
			while (elementIteraotr.hasNext()) {
				Element element = elementIteraotr.next();
				//System.out.println("element: "+element.toString());
				String paraText = element.toString();
				String aClass = element.attr("class");
				while ("PreCaption".equals(aClass)) {
					element = elementIteraotr.next();
					aClass = element.attr("class");
					//System.out.println("elements: "+element.text());
					if ("ColumnValue".equals(aClass)|"".equals(aClass)){
						String text1 = element.text();
                        paraText +=text1;
                    }
				}
				temp_list_line.add(paraText);

			}
		}
		// 做下过滤
		temp_list_line = doListFilter(temp_list_line, dataFormatStatus,
				isFilter);


		return temp_list_line;
	}
}