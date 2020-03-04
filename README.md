# OOMTEST - for springboot application

The OutOfMemory test application is a web application with rest api endpoint.
The application has one thread that continuously allocates memory, sooner or later the spring's scheduler would crash.
On the other hand there is a rest api to be used with a browser.

The behaviour that could occur is:
- the scheduled thread goes out of memory (and crash) while the springboot application won't be shutdown as expected and the rest api keep working.
the rest api can be tested with:
 
```
http://127.0.0.1:38080/oomtest/health
```

## build jar artifact 

```
   mvn clean package
```

## Build docker image
Put the Dockerfile in a folder along with application.jar, application.yaml, log4j2.yaml and run:

```
  docker build . -t oomtest-springboot
```

## Run oomtest
### Case1: OOM without jvm exit 
Docker will enforce memory limitation at 256MB but won't kill jvm since it not exceed memory limit
In this case, Xmx is not explicitly set and is dinamically calculated by jvm at 128MB.
A crash occurs in the scheduled thread but rest api will continue to work and springboot application won't stop. 

```
   docker run -it --rm -p38080:8080 -e JAVA_OPTS='-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 ' -m=256m oomtest-springboot:latest
```

### Case2: OOM with jvm exit and explicit -Xmx
In this case, Xmx is not explicitly set and is dinamically calculated by jvm.
Same as Case1.

```
   docker run -it --rm -p38080:8080 -e JAVA_OPTS='-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Xmx128m ' -m=256m oomtest-springboot:latest
```

### Case3: OOM with jvm exit triggered by Docker
Docker will enforce memory limitation at 256MB and will kill jvm.

```
   docker run -it --rm -p38080:8080 -e JAVA_OPTS='-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Xmx256m ' -m=256m oomtest-springboot:latest
```

### Case4: OOM with jvm exit
Docker will enforce memory limitation at 256MB but won't kill jvm since it not exceed memory limit.
Jvm is startedwith a directive that kills jvm on OutOfMemory. **-XX:+ExitOnOutOfMemoryError**

```
   docker run -it --rm -p38080:8080 -e JAVA_OPTS='-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -XX:+ExitOnOutOfMemoryError ' -m=256m oomtest-springboot:latest
```
