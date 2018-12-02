package com.xyzj.crawler.utils.parsehtmlstring;

/**
 * 系统断言类,方便引用,不使用junit之类的assert
 * 
 * @author zel
 *  
 */
public class SystemAssert {
	public static void assertNotNull(Object obj) {
		if (obj == null) {
			try {
				throw new Exception("object should not be null,please check");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public static void assertTrue(boolean bool, String message) {
		if (bool) {
			try {
				throw new Exception(message);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

}
