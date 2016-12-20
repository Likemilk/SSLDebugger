#SSL Debugging Program on client side 0.0.1v

##Intro
This program's idea is so simple. The program as much as catch occured error while SSL Connecting on client side. If it didn't catch several exception, It will show of **'StackOverFlow'** search result that sorted by votes count.


##Source Installation on Eclipse Editor
It need to set up a JVM argument and build path. Following example is setting up this **SSL Debugger**

>1. Set up just one value that SSL_HOME's directory path in JVM argument.   
> ``` sh
> #Run -> Run Configuration -> (Double click Java Application,
> #then show new configuration about java runtime setting) -> write SSL_HOME's directory path in program argument on argument tab
>
> #Path name isn't effected delimiters in path (like ['\','/'] )
> #case in Windows system
> C:\Workspace\ApacheStudy\SSL_HOME
> #case in UNIX system
> /usr/local/develop/workspace/ApacheStudy
> ```
>2. Set up build path for use **jsoup** lib.
> ``` bash
> (select Project)->(Right Click)->Build Path-> Configuration Build Path ->
> Libraries Tab -> Add Jars -> Select #{SSL_HOME}/lib/jsoup-1.10.1.jar -> apply and close.
> ```

###Version 0.0.1v  (Base on Revision 1)
1. As passible as catch any Exception while SSL Connecting with service server. But, didn't any action just catch.

2. If you in occured Exception while SSL Connecting, This program search solution that available resolve itself that high pointed about 'votes' in **'StackOverFlow'**

##Writer's Plans (Based on 2016-12-20)
1. If it catch any Exception, I will give any message for resolve **SSL Connection errors**(Promise to give you advanced solution).

2. It will be converted to CLI program and release on 'yum', 'apt-get' packages.   


##Writer's Info.
**E-Mail** : dydwls121200@gmail.com

**SlideShare** : http://www.slideshare.net/ssuser67b08e
