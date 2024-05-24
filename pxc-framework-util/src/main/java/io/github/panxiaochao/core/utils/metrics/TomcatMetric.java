/*
 * Copyright © 2022-2024 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// package io.github.panxiaochao.core.utils.metrics;
//
// import io.github.panxiaochao.core.utils.SpringContextUtil;
// import org.apache.catalina.Container;
// import org.apache.catalina.Context;
// import org.apache.catalina.Manager;
// import org.springframework.boot.web.context.WebServerApplicationContext;
// import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
// import org.springframework.boot.web.server.WebServer;
// import org.springframework.context.ApplicationContext;
//
// import javax.management.MBeanServer;
// import javax.management.MalformedObjectNameException;
// import javax.management.ObjectName;
// import java.lang.management.ManagementFactory;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Set;
// import java.util.concurrent.Callable;
//
// /**
//  * <p>
//  * Tomcat监控
//  * </p>
//  *
//  * @author Lypxc
//  * @since 2024-05-16
//  * @version 1.0
//  */
// public class TomcatMetric {
//
// 	private static final String JMX_DOMAIN_EMBEDDED = "Tomcat";
//
// 	private static final String JMX_DOMAIN_STANDALONE = "Catalina";
//
// 	private static final String OBJECT_NAME_SERVER_SUFFIX = ":type=Server";
//
// 	private static final String OBJECT_NAME_SERVER_EMBEDDED = JMX_DOMAIN_EMBEDDED + OBJECT_NAME_SERVER_SUFFIX;
//
// 	private static final String OBJECT_NAME_SERVER_STANDALONE = JMX_DOMAIN_STANDALONE + OBJECT_NAME_SERVER_SUFFIX;
//
// 	private final MBeanServer mBeanServer;
//
// 	private volatile String jmxDomain;
//
// 	private final Manager manager;
//
// 	private final Map<String, Object> meterMap = new HashMap<>();
//
// 	public TomcatMetric() {
// 		this.mBeanServer = ManagementFactory.getPlatformMBeanServer();
// 		this.manager = findManager(SpringContextUtil.getApplicationContext());
// 		if (manager != null) {
// 			this.jmxDomain = manager.getContext().getDomain();
// 		}
// 	}
//
// 	private Manager findManager(ApplicationContext applicationContext) {
// 		if (applicationContext instanceof WebServerApplicationContext) {
// 			WebServer webServer = ((WebServerApplicationContext) applicationContext).getWebServer();
// 			if (webServer instanceof TomcatWebServer) {
// 				Context context = findContext((TomcatWebServer) webServer);
// 				if (context != null) {
// 					return context.getManager();
// 				}
// 			}
// 		}
// 		return null;
// 	}
//
// 	private Context findContext(TomcatWebServer tomcatWebServer) {
// 		for (Container container : tomcatWebServer.getTomcat().getHost().findChildren()) {
// 			if (container instanceof Context) {
// 				return (Context) container;
// 			}
// 		}
// 		return null;
// 	}
//
// 	public Map<String, Object> getMetrics(String name) {
// 		Set<ObjectName> objectNames = this.mBeanServer.queryNames(getNamePattern(name), null);
// 		if (!objectNames.isEmpty()) {
// 			// MBeans are present, so we can register metrics now.
// 			objectNames.forEach(objectName -> {
// 				System.out
// 					.println(objectName + "=" + safeDouble(() -> mBeanServer.getAttribute(objectName, "maxThreads")));
// 			}
// 			// safeDouble(() -> mBeanServer.getAttribute(objectName, "maxThreads"))
// 			);
// 		}
// 		meterMap.put("tomcat.sessions.active.max", manager.getMaxActive());
// 		meterMap.put("tomcat.sessions.active.current", manager.getActiveSessions());
// 		return meterMap;
// 	}
//
// 	private double safeDouble(Callable<Object> callable) {
// 		try {
// 			return Double.parseDouble(callable.call().toString());
// 		}
// 		catch (Exception e) {
// 			return Double.NaN;
// 		}
// 	}
//
// 	private ObjectName getNamePattern(String namePatternSuffix) {
// 		try {
// 			return new ObjectName(namePatternSuffix);
// 		}
// 		catch (MalformedObjectNameException e) {
// 			// should never happen
// 			throw new RuntimeException("Error registering Tomcat JMX based metrics", e);
// 		}
// 	}
//
// 	private String getJmxDomain() {
// 		if (this.jmxDomain == null) {
// 			if (hasObjectName(OBJECT_NAME_SERVER_EMBEDDED)) {
// 				this.jmxDomain = JMX_DOMAIN_EMBEDDED;
// 			}
// 			else if (hasObjectName(OBJECT_NAME_SERVER_STANDALONE)) {
// 				this.jmxDomain = JMX_DOMAIN_STANDALONE;
// 			}
// 		}
// 		return this.jmxDomain;
// 	}
//
// 	private boolean hasObjectName(String name) {
// 		try {
// 			return this.mBeanServer.queryNames(new ObjectName(name), null).size() == 1;
// 		}
// 		catch (MalformedObjectNameException ex) {
// 			throw new RuntimeException(ex);
// 		}
// 	}
//
// }
