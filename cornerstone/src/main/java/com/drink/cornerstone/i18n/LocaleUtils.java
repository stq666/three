package com.drink.cornerstone.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.util.StringUtils;

/**
 * hold all locale properties
 * 
 * @author kimmking (kimmking.cn@gmail.com)
 * @date 2013-12-16
 */
public class LocaleUtils {

	/**
	 * 获取ResourceBundle，不能直接使用类名作为properties的名称
	 * 
	 * @param base
	 *            properties的全限定名称
	 * @param locale
	 *            Locale
	 * @return
	 */
	public static ResourceBundle getBundle(String base, Locale locale) {
		return ResourceBundle.getBundle(base, locale);
	}

	/**
	 * 使用相当于类的路径获取ResourceBundle；如果locale为空，则默认获取不带locale的properties
	 * 
	 * @param clazz
	 *            相同包下的类
	 * @param base
	 *            properties的名称
	 * @param locale
	 *            Locale
	 * @return
	 */
	public static ResourceBundle getBundle(Class clazz, String base, Locale locale) {
		String pkg = clazz.getPackage().getName();
		String fullBase = StringUtils.isEmpty(pkg) ? base : (pkg + "." + base);
		ResourceBundle bundle = getBundle(fullBase, locale == null ? Locale.ROOT : locale);
		return (bundle == null) ? new ResourceBundle() {
			public Enumeration<String> getKeys() {
				return null;
			}
			protected Object handleGetObject(String key) {
				return null;
			}
			public String toString() {
				return "NONEXISTENT_BUNDLE";
			}
		} : bundle;
	}

}
