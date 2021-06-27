call runcrud.bat
if "%ERRORLEVEL%" == "0" goto lunchBrowser
echo.
echo runcrud.bar has errors - breaking work
goto fail

:lunchBrowser
start "" http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo cannot launch browser...
goto fail

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.