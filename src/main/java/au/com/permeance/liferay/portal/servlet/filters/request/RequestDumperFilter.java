/**
 * Copyright (C) 2014 Permeance Technologies
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package au.com.permeance.liferay.portal.servlet.filters.request;

import au.com.permeance.liferay.portal.kernel.servlet.BasePortalFilter;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Request Dumper Filter.
 * 
 * This Request Dumper Filter is based on the Apache Tomcat 7.x Request Dumper Filter 
 * and implemented using the Liferay Portal 6.1.x Servlet Filter framework.
 * 
 * This Request Dumper Filter was developed to work around classloading and logging configuration
 * issues with using the default Apache Tomcat 7.x Request Dumper Filter with Liferay Portal 6.1.x. 
 * 
 * This Request Dumper Filter logs messages via the Liferay Portal 6.1.x logging framework.
 * 
 * @author Craig R. McClanahan
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 * 
 * @see http://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#Request_Dumper_Filter
 * @see https://tomcat.apache.org/tomcat-7.0-doc/api/org/apache/catalina/filters/RequestDumperFilter.html
 * 
 * @see http://grepcode.com/file/repo1.maven.org/maven2/org.apache.tomcat.embed/tomcat-embed-core/7.0.0/org/apache/catalina/filters/RequestDumperFilter.java
 * @see http://www.liferay.com/documentation/liferay-portal/6.1/development/-/ai/other-hooks
 * @see https://github.com/liferay/liferay-plugins/tree/master/hooks/sample-servlet-filter-hook
 * 
 * @see https://tomcat.apache.org/tomcat-7.0-doc/api/org/apache/catalina/filters/RequestDumperFilter.html
 * @see BasePortalFilter
 */
public class RequestDumperFilter extends au.com.permeance.liferay.portal.kernel.servlet.BasePortalFilter {

	public static final String SKIP_FILTER = RequestDumperFilter.class.getName() + "SKIP_FILTER";
	
    private static final String NON_HTTP_REQ_MSG = "Not available. Non-HTTP request.";
    
	private static final String NON_HTTP_RES_MSG = "Not available. Non-HTTP response.";
	
	private static final int SEFVLET_API_3_0_MAJOR_VERSION = 3;
	
	private static final int SEFVLET_API_3_0_MINOR_VERSION = 0;
	

	private static final ThreadLocal<Timestamp> timestamp = new ThreadLocal<Timestamp>() {
		@Override
		protected Timestamp initialValue() {
			return new Timestamp();
		}
	};
	
	
	public RequestDumperFilter() {
		super();
	}

	
	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		boolean filterEnabled = false;
		
		if (isAlreadyFiltered(request)) {
			getLog().info("already filtered; skip");
			filterEnabled = false;
		}
		else {
			getLog().info("not yet filtered; continue");
			filterEnabled = true;
		}
		
		getLog().info("filterEnabled: " + filterEnabled);
		return filterEnabled;
	}
	
	
	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 * This processFilter method is essentially a clone 
	 * of Tomcat's RequestDumperFilter#doFilter(ServletRequest,ServletResponse,FilterChain)
	 * merged with Liferay Portal's hook filter mechanism.
	 * 
	 * @see http://grepcode.com/file/repo1.maven.org/maven2/org.apache.tomcat.embed/tomcat-embed-core/7.0.0/org/apache/catalina/filters/RequestDumperFilter.java
	 */
	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws Exception {

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);
		
        HttpServletRequest hRequest = null;
        HttpServletResponse hResponse = null;
        
        if (request instanceof HttpServletRequest) {
            hRequest = (HttpServletRequest) request;
        }
        if (response instanceof HttpServletResponse) {
            hResponse = (HttpServletResponse) response;
        }		
        
        ServletContext sc = hRequest.getServletContext();
        
		String completeURL = HttpUtil.getCompleteURL(request);
		
		if (getLog().isInfoEnabled()) {
			getLog().info("Begin filter for request dumper at URL " + completeURL);
		}
		
        // Log pre-service information
        doLog("START TIME        ", getTimestamp());
        
        if (sc != null) {
            doLog("  servletContext serverInfo", ""+sc.getServerInfo());
            doLog("  servletContext majorVersion", ""+sc.getMajorVersion());
            doLog("  servletContext minorVersion", ""+sc.getMinorVersion());
        }

        if (hRequest == null) {
            doLog("        requestURI", NON_HTTP_REQ_MSG);
            doLog("          authType", NON_HTTP_REQ_MSG);
        } else {
            doLog("        requestURI", hRequest.getRequestURI());
            doLog("          authType", hRequest.getAuthType());
        }
        
        doLog(" characterEncoding", request.getCharacterEncoding());
        doLog("     contentLength",
                Integer.valueOf(request.getContentLength()).toString());
        doLog("       contentType", request.getContentType());
        
        if (hRequest == null) {
            doLog("       contextPath", NON_HTTP_REQ_MSG);
            doLog("            cookie", NON_HTTP_REQ_MSG);
            doLog("            header", NON_HTTP_REQ_MSG);
        } else {
            doLog("       contextPath", hRequest.getContextPath());
            Cookie cookies[] = hRequest.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++)
                    doLog("            cookie", cookies[i].getName() +
                            "=" + cookies[i].getValue());
            }
            if (doesServletContainerSupportServletAPI_3_0(sc)) {
                Enumeration<String> hnames = hRequest.getHeaderNames();
                while (hnames.hasMoreElements()) {
                    String hname = hnames.nextElement();
                    Enumeration<String> hvalues = hRequest.getHeaders(hname);
                    while (hvalues.hasMoreElements()) {
                        String hvalue = hvalues.nextElement();
                        doLog("            header", hname + "=" + hvalue);
                    }
                }
            } else {
                Enumeration hnames = hRequest.getHeaderNames();
                while (hnames.hasMoreElements()) {
                    String hname = (String) hnames.nextElement();
                    Enumeration<String> hvalues = hRequest.getHeaders(hname);
                    while (hvalues.hasMoreElements()) {
                        String hvalue = hvalues.nextElement();
                        doLog("            header", hname + "=" + hvalue);
                    }
                }
            }
        }
        
        doLog("            locale", request.getLocale().toString());
        
        if (hRequest == null) {
            doLog("            method", NON_HTTP_REQ_MSG);
        } else {
            doLog("            method", hRequest.getMethod());
        }
        
        Enumeration<String> pnames = request.getParameterNames();
        while (pnames.hasMoreElements()) {
            String pname = pnames.nextElement();
            String pvalues[] = request.getParameterValues(pname);
            StringBuilder result = new StringBuilder(pname);
            result.append('=');
            for (int i = 0; i < pvalues.length; i++) {
                if (i > 0)
                    result.append(", ");
                result.append(pvalues[i]);
            }
            doLog("         parameter", result.toString());
        }
        
        if (hRequest == null) {
            doLog("          pathInfo", NON_HTTP_REQ_MSG);
        } else {
            doLog("          pathInfo", hRequest.getPathInfo());
        }
        
        doLog("          protocol", request.getProtocol());
        
        if (hRequest == null) {
            doLog("       queryString", NON_HTTP_REQ_MSG);
        } else {
            doLog("       queryString", hRequest.getQueryString());
        }
        
        doLog("        remoteAddr", request.getRemoteAddr());
        doLog("        remoteHost", request.getRemoteHost());
        
        if (hRequest == null) {
            doLog("        remoteUser", NON_HTTP_REQ_MSG);
            doLog("requestedSessionId", NON_HTTP_REQ_MSG);
        } else {
            doLog("        remoteUser", hRequest.getRemoteUser());
            doLog("requestedSessionId", hRequest.getRequestedSessionId());
        }
        
        doLog("            scheme", request.getScheme());
        doLog("        serverName", request.getServerName());
        doLog("        serverPort",
                Integer.valueOf(request.getServerPort()).toString());
        
        if (hRequest == null) {
            doLog("       servletPath", NON_HTTP_REQ_MSG);
        } else {
            doLog("       servletPath", hRequest.getServletPath());
        }
        
        doLog("          isSecure",
                Boolean.valueOf(request.isSecure()).toString());
        doLog("------------------",
                "--------------------------------------------");

        // Perform the request
		processFilter(RequestDumperFilter.class, hRequest, hResponse, filterChain);

        // Log post-service information
        doLog("------------------",
                "--------------------------------------------");
        if (hRequest == null) {
            doLog("          authType", NON_HTTP_REQ_MSG);
        } else {
            doLog("          authType", hRequest.getAuthType());
        }
        
        doLog("       contentType", response.getContentType());
        
        if (hResponse == null) {
            doLog("            header", NON_HTTP_RES_MSG);
        } else {
        	if (doesServletContainerSupportServletAPI_3_0(sc)) {
                Iterable<String> rhnames = hResponse.getHeaderNames();
                for (String rhname : rhnames) {
                    Iterable<String> rhvalues = hResponse.getHeaders(rhname);
                    for (String rhvalue : rhvalues)
                        doLog("            header", rhname + "=" + rhvalue);
                }
        	} else {
        		doLog("Unable to log response headers; Servlet Container does not support Servlet API 3.0.", StringPool.BLANK);
        	}
        }

        if (hRequest == null) {
            doLog("        remoteUser", NON_HTTP_REQ_MSG);
        } else {
            doLog("        remoteUser", hRequest.getRemoteUser());
        }
        
        if (hResponse == null) {
            doLog("        remoteUser", NON_HTTP_RES_MSG);
        } else {
            doLog("            status",
                    Integer.valueOf(hResponse.getStatus()).toString());
        }

        doLog("END TIME          ", getTimestamp());
        doLog("==================",
                "============================================");
        
		if (getLog().isInfoEnabled()) {
			getLog().info("End filter for request dumper at URL " + completeURL);
		} 
	}
	
	
    private void doLog(String attribute, String value) {
        StringBuilder sb = new StringBuilder(80);
        sb.append(Thread.currentThread().getName());
        sb.append(' ');
        sb.append(attribute);
        sb.append('=');
        sb.append(value);
        getLog().info(sb.toString());
    }

    
    private String getTimestamp() {
        Timestamp ts = timestamp.get();
        long currentTime = System.currentTimeMillis();
        
        if ((ts.date.getTime() + 999) < currentTime) {
            ts.date.setTime(currentTime - (currentTime % 1000));
            ts.update();
        }
        return ts.dateString;
    }
	
	
    private static final class Timestamp {
        private Date date = new Date(0);
        private SimpleDateFormat format =
            new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        private String dateString = format.format(date);
        private void update() {
            dateString = format.format(date);
        }
    }	

    
    private static boolean doesServletContainerSupportServletAPI_3_0( ServletContext sc ) {
    	
    	boolean result = false;
    	
    	if (sc != null) {
        	int majorVersion = sc.getMajorVersion();
        	int minorVersion = sc.getMinorVersion();

        	if ((majorVersion >= SEFVLET_API_3_0_MAJOR_VERSION) && (minorVersion >= SEFVLET_API_3_0_MINOR_VERSION)) {
        		result = true;
        	}
    	}
    	
    	return result;
    }    

}
