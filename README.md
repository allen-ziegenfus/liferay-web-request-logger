# Liferay Request Dumper

*liferay-request-dumper*

This project provides a Liferay Portal servlet filter that logs all web requests and responses messages via the Liferay Portal logging framework.

NOTE: The servlet filter is only compatible with Servlet API 3.0+


## Supported Products

### GitHub Project Master

* Equivalent to branch 6.1.x

### GitHub Project Branch 6.1.x

* Liferay Portal 6.1 CE : 6.1 CE GA2, GA3 (6.1.1+)
* Liferay Portal 6.1 CE : 6.1 EE GA2, GA3 (6.1.20+)


## Downloads

The latest release is available from [SourceForge - Liferay Request Dumper](https://sourceforge.net/projects/permeance-apps/files/liferay-request-dumper/ "Liferay Request Dumper").


## Usage

Step 1. Configure Liferay Portal logging for Liferay Request Dumper.

Step 1.1. Create or update log4j settings file. 

NOTE: For a Liferay Portal + Tomcat bundle the log4j settings file is located at 
"LIFERAY_HOME/tomcat-xxx/lib/ext/META-INF/portal-log4j-ext.xml".

Sample “portal-log4j-ext.xml”

    <?xml version="1.0"?>
    <!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">

    <log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/" debug="true">

            <appender name="CONSOLE" class="org.apache.log4j.ConsoleAppender">
                    <layout class="org.apache.log4j.EnhancedPatternLayout">
                            <param name="ConversionPattern" value="%d{ISO8601}{Australia/Perth} %-5p [%t][%c{1}:%L] %m%n" />
                    </layout>
            </appender>

             <appender name="FILE" class="org.apache.log4j.rolling.RollingFileAppender">
                    <rollingPolicy class="org.apache.log4j.rolling.TimeBasedRollingPolicy">
                            <param name="FileNamePattern" value="@liferay.home@/logs/liferay@spi.id@.%d{yyyy-MM-dd}.log" />
                    </rollingPolicy>

                    <layout class="org.apache.log4j.EnhancedPatternLayout">
                            <param name="ConversionPattern" value="%d{ISO8601}{Australia/Perth} %-5p [%t][%c{1}:%L] %m%n" />
                    </layout>
            </appender>

            <category name="au.com.permeance.liferay.portal.servlet.filters.request.RequestDumperFilter">
                    <priority value="ALL" />
            </category>

            <root>
                    <priority value="INFO" />
                    <appender-ref ref="CONSOLE" />
                    <appender-ref ref="FILE" />
            </root>

    </log4j:configuration>

Step 1.2. Copy log4j.dtd to same folder as portal-log4j-ext.xml

NOTE: For Liferay Portal + Tomcat bundle, the log4j.dtd file can be found in file “LIFERAY_HOME/tomcat-xxx/webapps/ROOT/WEB-INF/lib/portal-impl.jar”.

Step 2. Enable or disable Liferay Request Dumper in portal properties.

Edit file “LIFERAY_HOME/portal-ext.properties”.

    # Enable RequestDumperFilter
       au.com.permeance.liferay.portal.servlet.filters.request.RequestDumperFilter=true

    # Disable RequestDumperFilter
    au.com.permeance.liferay.portal.servlet.filters.request.RequestDumperFilter=false

## Building

Step 1. Checkout source from GitHub project

Step 1.1. Checkout master from GitHub project

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

Step 1.2. Checkout branch 6.1.x from GitHub project

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

Step 2. Build and package

    % mvn -U clean package

This will build "liferay-request-dumper-A.B.C.war" in the targets tolder.


## Installation

### Liferay Portal + Apache Tomcat Bundle

eg.

Deploy "liferay-request-dumper-A.B.C.war" to "LIFERAY_HOME/deploy" folder.


## Project Team

* Tim Telcik - tim.telcik@permeance.com.au
