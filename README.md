# Web Request Logger

*liferay-web-request-logger*

The Web Request Logger filters all HTTP web requests and responses to and from Liferay Portal and logs the data via the Liferay Portal logging framework.

## Overview

The Web Request Logger filters all HTTP web requests and responses to and from Liferay Portal and logs the data via the Liferay Portal logging framework.

The HTTP web requests and responses are processed using the Web Request Logger Filter which is based on the [Apache Tomcat 7.x Request Dumper Filter](http://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#Request_Dumper_Filter "The Request Dumper Filter is based on the [Apache Tomcat 7.x Request Logger Filter") and implemented using the Liferay Portal 6.1.x Servlet Filter framework.

The Web Request Logger Filter is compatible with Servlet Containers supporting Servlet API 2.5 or 3.0 (e.g. Apache Tomcat 6.x or 7.x).

### Background

The Request Dumper Valve in Apache Tomcat 6.x (TC 6.x) was replaced by the Request Dumper Filter in Apache Tomcat 7.x (TC 7.x). The Request Logger Valve (TC 6.x) worked seamlessly with Liferay Portal 6.0.x, but the Request Dumper Filter (TC 7.x) does not work with Liferay Portal 6.1.x.

The Web Request Logger Filter was developed to work around class/resource loading and logging configuration issues when using the default Apache Tomcat 7.x Request Dumper Filter with Liferay Portal 6.1.x.

## Supported Products

### GitHub Project Branch 7.0.x

* Liferay Portal 7.0 CE : 7.0 CE A1 (7.0.0+)

### GitHub Project Master and Branch 6.2.x

* Liferay Portal 6.2 CE : 6.2 CE GA1 (6.2.0+)
* Liferay Portal 6.2 EE : 6.2 EE GA1 (6.2.10+)

### GitHub Project Branch 6.1.x

* Liferay Portal 6.1 CE : 6.1 CE GA2, GA3 (6.1.1+)
* Liferay Portal 6.1 CE : 6.1 EE GA2, GA3 (6.1.20+)

## Downloads

* [Liferay Marketplace - Web Request Logger](https://www.liferay.com/marketplace/-/mp/application/40746340/ "Web Request Logger")
* [SourceForge - Liferay Web Request Logger](https://sourceforge.net/projects/permeance-apps/files/liferay-web-request-logger/ "Liferay Web Request Logger")

## Usage

### Step 1. Configure Liferay Portal logging.

Add or update logging category for WebRequestLoggerFilter using the Liferay Portal Control Panel or portal settings file.

#### Step 1.1. Configure Logging using Liferay Portal Control Panel

* Navigate to Control Panel > Server Administration > Log Levels.

* Add or update log category for WebRequestLoggerFilter

     Category Name: au.com.permeance.liferay.portal.servlet.filters.request.WebRequestLoggerFilter

     Log Level: ALL

* NOTE: This approach is temporary and is only active while the Liferay Portal instance is running.

#### Step 1.2. Configure Logging using portal settings file.

* Create or update file “LIFERAY_HOME/tomcat/lib/ext/META-INF/portal-log4j-ext.xml”.

* Refer to sample [portal-log4j-ext.xml](conf/liferay-portal/tomcat/lib/ext/META-INF/portal-log4j-ext.xml "portal-log4j-ext.xml") and associated [log4j.dtd](conf/liferay-portal/tomcat/lib/ext/META-INF/log4j.dtd "log4j.dtd"), which should be copied to folder “LIFERAY_HOME/tomcat/lib/ext/META-INF”.

* Sample portal-log-ext.xml file snippet

    &lt;category name="au.com.permeance.liferay.portal.servlet.filters.request.WebRequestLoggerFilter"&gt;
        
    &lt;priority value="ALL" /&gt;
        
    &lt;/category&gt;


* NOTE: This approach is persistent and will remain between stopping and starting the Liferay Portal instance.

### Step 2. Enable or disable Web Request Logger Filter in portal properties.

* Create or update file “LIFERAY_HOME/portal-ext.properties”.

* Enable WebRequestLoggerFilter

     au.com.permeance.liferay.portal.servlet.filters.request.WebRequestLoggerFilter=true

* Disable WebRequestLoggerFilter

     au.com.permeance.liferay.portal.servlet.filters.request.WebRequestLoggerFilter=false

### Step 3. Deploy Liferay Request Logger hook plugin to Liferay Portal

* Deploy to Liferay Portal + Apache Tomcat Bundle

     % cp liferay-web-request-logger-hook-XXX.war LIFERAY_HOME/deploy

### Step 4. Review Liferay Portal and/or Apache Tomcat log files.

#### Sample Output (Apache Tomcat)

    2014-04-30 16:37:20,660 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 FILTER START TIME=30-Apr-2014 08:37:20
    2014-04-30 16:37:20,661 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:101] Begin filter for request logger at URL http://localhost:9080/web/guest
    2014-04-30 16:37:20,661 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 requestURI=/web/guest
    2014-04-30 16:37:20,662 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 authType=null
    2014-04-30 16:37:20,662 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 characterEncoding=UTF-8
    2014-04-30 16:37:20,663 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 contentLength=-1
    2014-04-30 16:37:20,663 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 contentType=null
    2014-04-30 16:37:20,663 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 contextPath=
    2014-04-30 16:37:20,664 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 header=cache-control=no-cache
    2014-04-30 16:37:20,665 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 header=pragma=no-cache
    2014-04-30 16:37:20,665 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 header=user-agent=Java/1.6.0_35
    2014-04-30 16:37:20,666 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 header=host=localhost:9080
    2014-04-30 16:37:20,666 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 header=accept=text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
    2014-04-30 16:37:20,666 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 header=connection=keep-alive
    2014-04-30 16:37:20,667 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 locale=en_US
    2014-04-30 16:37:20,667 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 method=GET
    2014-04-30 16:37:20,668 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 pathInfo=/guest
    2014-04-30 16:37:20,668 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 protocol=HTTP/1.1
    2014-04-30 16:37:20,668 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 queryString=null
    2014-04-30 16:37:20,669 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 remoteAddr=127.0.0.1
    2014-04-30 16:37:20,669 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 remoteHost=127.0.0.1
    2014-04-30 16:37:20,669 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 remoteUser=null
    2014-04-30 16:37:20,670 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 requestedSessionId=null
    2014-04-30 16:37:20,670 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 scheme=http
    2014-04-30 16:37:20,671 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 serverName=localhost
    2014-04-30 16:37:20,671 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 serverPort=9080
    2014-04-30 16:37:20,671 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 servletPath=/web
    2014-04-30 16:37:20,672 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 isSecure=false
    2014-04-30 16:37:20,672 INFO  [http-bio-9080-exec-1][WebRequestLoggerFilter:258] http-bio-9080-exec-1 ------------------=--------------------------------------------
    2014-04-30 16:37:20,673 DEBUG [http-bio-9080-exec-1][WebRequestLoggerFilter:159] [http-bio-9080-exec-1]=> au.com.permeance.liferay.portal.servlet.filters.request.WebRequestLoggerFilter /web/guest

## Building

### Step 1. Checkout source from GitHub project

#### Step 1.1. Checkout master from GitHub project

    NOTE: GitHub master and branch 6.2.x should always be the same.

    $ md work
    $ cd work
    % md -p liferay-web-request-logger/master
    % cd liferay-web-request-logger/master
    $ git clone https://github.com/permeance/liferay-web-request-logger
    Cloning into 'liferay-web-request-logger'...
    remote: Counting objects: 518, done.
    remote: Compressing objects: 100% (223/223), done.
    remote: Total 518 (delta 173), reused 502 (delta 157)
    Receiving objects: 100% (518/518), 622.65 KiB | 273.00 KiB/s, done.
    Resolving deltas: 100% (173/173), done.
    Checking connectivity... done

#### Step 1.2. Checkout branch 6.1.x or 6.2.x from GitHub project

    NOTE: This sample shows checkout for branch 6.1.x. The same process applies for branch 6.2.x.

    % md work
    % cd work
    % md -p liferay-web-request-logger/6.1.x
    % cd liferay-web-request-logger/6.1.x
    % git clone https://github.com/permeance/liferay-web-request-logger
    Cloning into 'liferay-web-request-logger'...
    remote: Counting objects: 475, done.
    remote: Compressing objects: 100% (199/199), done.
    remote: Total 475 (delta 151), reused 470 (delta 146)
    Receiving objects: 100% (475/475), 618.21 KiB | 161.00 KiB/s, done.
    Resolving deltas: 100% (151/151), done.
    Checking connectivity... done
    % cd liferay-web-request-logger
    % git branch 6.1.x
    % git checkout 6.1.x
    Switched to branch '6.1.x'
    % git status
    # On branch 6.1.x
    nothing to commit, working directory clean

### Step 2. Build and package

    % mvn -U clean package

This will build "liferay-web-request-logger-hook-XXX.war" in the “target” folder.


## Project Team

* Tim Telcik - tim.telcik@permeance.com.au

## References

 * http://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#Request_Logger_Filter
 * https://tomcat.apache.org/tomcat-7.0-doc/api/org/apache/catalina/filters/WebRequestLoggerFilter.html
 * http://grepcode.com/file/repo1.maven.org/maven2/org.apache.tomcat.embed/tomcat-embed-core/7.0.0/org/apache/catalina/filters/WebRequestLoggerFilter.java
 * http://www.liferay.com/documentation/liferay-portal/6.1/development/-/ai/other-hooks
 * https://github.com/liferay/liferay-plugins/tree/master/hooks/sample-servlet-filter-hook
