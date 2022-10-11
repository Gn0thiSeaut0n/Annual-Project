@ECHO off
title "SPRING STOP"

SET /P VALUE_FROM_FILE= < [pid 파일 경로 및 파일 명]
REM SET /P VALUE_FROM_FILE= < C:\hncis-annual-project\build\libs\pid.pid
taskkill /pid %VALUE_FROM_FILE% /f

ECHO "SPRING STOP"

pause
exit