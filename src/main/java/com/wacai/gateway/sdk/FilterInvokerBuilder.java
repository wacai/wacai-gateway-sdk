package com.wacai.gateway.sdk;

import java.util.List;

/**
 * 
 * @author bairen
 *
 */
public class FilterInvokerBuilder {
	/**
	 * build filter chain
	 * @param invoker
	 * @param filters
	 * @return
	 */
	public static Invoker build(Invoker invoker, List<Filter> filters) {
		Invoker last = invoker;
		if (!filters.isEmpty()) {
			for (int i = filters.size() - 1; i >= 0; i--) {
				final Filter filter = filters.get(i);
				final Invoker next = last;
				last = new Invoker() {
					@Override
					public Result invoke(Invocation invocation) throws NeptuneException {
						return filter.invoke(next, invocation);
					}

					@Override
					public NeptuneConfig getConfig() {
						return next.getConfig();
					}

				};
			}
		}
		return last;
	}
}
