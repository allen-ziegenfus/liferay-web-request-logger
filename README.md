# Liferay Request Dumper

*liferay-request-dumper*

This project provides a Liferay Portal Request Dumper Filter hook plugin.

## Overview

The Request Dumper Filter is based on the [Apache Tomcat 7.x Request Dumper Filter ](http://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#Request_Dumper_Filter "The Request Dumper Filter is based on the [Apache Tomcat 7.x Request Dumper Filter") and implemented using the Liferay Portal 6.1.x Servlet Filter framework.

The Request Dumper Filter was developed to work around class/resource loading and logging configuration issues when using the default Apache Tomcat 7.x Request Dumper Filter with Liferay Portal 6.1.x.

NOTE: The Request Dumper Filter is only compatible with Servlet API 3.0+ and requires a Servlet Container that supports Servlet API 3.0+ such as Apache Tomcat 7.x that is often used with Liferay Portal 6.1 CE GA2+ or Liferay Portal 6.1 EE GA2+.

### Background

The Request Dumper Valve in Apache Tomcat 6.x (TC 6.x) was replaced by the Request Dumper Filter in Apache Tomcat 7.x (TC 7.x). The Request Dumper Valve (TC 6.x) worked seamlessly with Liferay Portal 6.0.x, but the Request Dumper Filter (TC 7.x) does not work with Liferay Portal 6.1.x, hence the need for the Liferay Portal Request Dumper Filter.

The Request Dumper Filter logs messages via the Liferay Portal 6.1.x logging framework, which normally delegates to Apache Log4j.


## Supported Products

### GitHub Project Master

* Equivalent to branch 6.1.x

### GitHub Project Branch 6.1.x

* Liferay Portal 6.1 CE : 6.1 CE GA2, GA3 (6.1.1+)
* Liferay Portal 6.1 CE : 6.1 EE GA2, GA3 (6.1.20+)


## Downloads

The latest release is available from [SourceForge - Liferay Request Dumper](https://sourceforge.net/projects/permeance-apps/files/liferay-request-dumper/ "Liferay Request Dumper").


## Usage

### Step 1. Configure Liferay Portal logging for Liferay Request Dumper.

Update logging category “au.com.permeance.liferay.portal.servlet.filters.request.RequestDumperFilter” in “portal-log4j-ext.xml” file and/or via Liferay Portal Control Panel.

Refer to sample [portal-log4j-ext.xml](conf/liferay-portal/tomcat/lib/ext/META-INF/portal-log4j-ext.xml "portal-log4j-ext.xml") and associated [log4j.dtd](conf/liferay-portal/tomcat/lib/ext/META-INF/log4j.dtd "log4j.dtd"), which should be copied to folder “LIFERAY_HOME/tomcat/lib/ext/META-INF”.

### Step 2. Enable or disable Liferay Request Dumper in portal properties.

Edit file “LIFERAY_HOME/portal-ext.properties”.

    # Enable RequestDumperFilter
    au.com.permeance.liferay.portal.servlet.filters.request.RequestDumperFilter=true

    # Disable RequestDumperFilter
    au.com.permeance.liferay.portal.servlet.filters.request.RequestDumperFilter=false

### Step 3. Review Liferay Portal and/or Apache Tomcat log files.

#### Sample Output (Apache Tomcat)

    2014-04-30 16:37:20,660 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1 START TIME        =30-Apr-2014 08:37:20
    2014-04-30 16:37:20,661 INFO  [http-bio-9080-exec-1][RequestDumperFilter:101] Begin filter for request dumper at URL http://localhost:9080/web/guest
    2014-04-30 16:37:20,661 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1         requestURI=/web/guest
    2014-04-30 16:37:20,662 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1           authType=null
    2014-04-30 16:37:20,662 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1  characterEncoding=UTF-8
    2014-04-30 16:37:20,663 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1      contentLength=-1
    2014-04-30 16:37:20,663 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1        contentType=null
    2014-04-30 16:37:20,663 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1        contextPath=
    2014-04-30 16:37:20,664 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1             header=cache-control=no-cache
    2014-04-30 16:37:20,665 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1             header=pragma=no-cache
    2014-04-30 16:37:20,665 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1             header=user-agent=Java/1.6.0_35
    2014-04-30 16:37:20,666 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1             header=host=localhost:9080
    2014-04-30 16:37:20,666 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1             header=accept=text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
    2014-04-30 16:37:20,666 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1             header=connection=keep-alive
    2014-04-30 16:37:20,667 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1             locale=en_US
    2014-04-30 16:37:20,667 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1             method=GET
    2014-04-30 16:37:20,668 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1           pathInfo=/guest
    2014-04-30 16:37:20,668 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1           protocol=HTTP/1.1
    2014-04-30 16:37:20,668 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1        queryString=null
    2014-04-30 16:37:20,669 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1         remoteAddr=127.0.0.1
    2014-04-30 16:37:20,669 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1         remoteHost=127.0.0.1
    2014-04-30 16:37:20,669 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1         remoteUser=null
    2014-04-30 16:37:20,670 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1 requestedSessionId=null
    2014-04-30 16:37:20,670 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1             scheme=http
    2014-04-30 16:37:20,671 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1         serverName=localhost
    2014-04-30 16:37:20,671 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1         serverPort=9080
    2014-04-30 16:37:20,671 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1        servletPath=/web
    2014-04-30 16:37:20,672 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1           isSecure=false
    2014-04-30 16:37:20,672 INFO  [http-bio-9080-exec-1][RequestDumperFilter:258] http-bio-9080-exec-1 ------------------=--------------------------------------------
    2014-04-30 16:37:20,673 DEBUG [http-bio-9080-exec-1][RequestDumperFilter:159] [http-bio-9080-exec-1]=> au.com.permeance.liferay.portal.servlet.filters.request.RequestDumperFilter /web/guest

## Building

### Step 1. Checkout source from GitHub project

#### Step 1.1. Checkout master from GitHub project

    NOTE: GitHub master and branch 6.1.x should always be the same.

    $ md work
    $ cd work
    $ md master
    $ git clone https://github.com/permeance/liferay-request-dumper
    Cloning into 'liferay-request-dumper'...
    remote: Counting objects: 518, done.
    remote: Compressing objects: 100% (223/223), done.
    remote: Total 518 (delta 173), reused 502 (delta 157)
    Receiving objects: 100% (518/518), 622.65 KiB | 273.00 KiB/s, done.
    Resolving deltas: 100% (173/173), done.
    Checking connectivity... done

#### Step 1.2. Checkout branch 6.1.x from GitHub project

    NOTE: This sample shows checkout for branch 6.1.x. 

    % md work
    % cd work
    % md -p liferay-request-dumper/branches/6.1.x
    % cd liferay-request-dumper/branches/6.1.x
    % git clone https://github.com/permeance/liferay-request-dumper
    Cloning into 'liferay-request-dumper'...
    remote: Counting objects: 475, done.
    remote: Compressing objects: 100% (199/199), done.
    remote: Total 475 (delta 151), reused 470 (delta 146)
    Receiving objects: 100% (475/475), 618.21 KiB | 161.00 KiB/s, done.
    Resolving deltas: 100% (151/151), done.
    Checking connectivity... done
    % cd liferay-request-dumper
    % git branch 6.1.x
    % git checkout 6.1.x
    Switched to branch '6.1.x'
    % git status
    # On branch 6.1.x
    nothing to commit, working directory clean

### Step 2. Build and package

    % mvn -U clean package

This will build "liferay-request-dumper-A.B.C.war" in the “target” folder.


## Installation

### Liferay Portal + Apache Tomcat Bundle

eg.

Deploy "liferay-request-dumper-A.B.C.war" to "LIFERAY_HOME/deploy" folder.


## Project Team

* Tim Telcik - tim.telcik@permeance.com.au

## References

 * http://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#Request_Dumper_Filter
 * https://tomcat.apache.org/tomcat-7.0-doc/api/org/apache/catalina/filters/RequestDumperFilter.html
 * http://grepcode.com/file/repo1.maven.org/maven2/org.apache.tomcat.embed/tomcat-embed-core/7.0.0/org/apache/catalina/filters/RequestDumperFilter.java
 * http://www.liferay.com/documentation/liferay-portal/6.1/development/-/ai/other-hooks
 * https://github.com/liferay/liferay-plugins/tree/master/hooks/sample-servlet-filter-hook
