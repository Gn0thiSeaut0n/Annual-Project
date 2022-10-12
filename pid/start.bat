@ECHO  off
title "SPRING START"

SET JAVA_PATH=[자바 경로]
REM SET JAVA_PATH=C:\"Program Files"\Java\jdk-11.0.16.1\bin
SET START_PATH=[시작(jar) 경로]
REM ex) SET START_PATH=C:\Annual-Project\build\libs

cd %START_PATH%
start %JAVA_PATH%\javaw -jar [jar 파일 명]
REM ex) start %JAVA_PATH%\javaw -jar Annual-Project-0.0.1-SNAPSHOT.jar

@ECHO on
ECHO "SPRING START"

pause
exit