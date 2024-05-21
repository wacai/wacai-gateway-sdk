package com.wacai.gateway.sdk.extension;

import java.util.Comparator;

/**
 * 
 * @author bairen
 *
 */
public class ExtensionPointComparator implements Comparator<Object> {

	public static final Comparator<Object> COMPARATOR = new ExtensionPointComparator();

	@Override
	public int compare(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return 0;
		}
		if (o1 == null) {
			return -1;
		}
		if (o2 == null) {
			return 1;
		}
		if (o1.equals(o2)) {
			return 0;
		}

		ExtensionPoint a1 = o1.getClass().getAnnotation(ExtensionPoint.class);
		ExtensionPoint a2 = o2.getClass().getAnnotation(ExtensionPoint.class);

		int n1 = (a1 == null) ? 0 : a1.order();
		int n2 = (a2 == null) ? 0 : a2.order();
		return n1 > n2 ? 1 : -1;
	}
}
