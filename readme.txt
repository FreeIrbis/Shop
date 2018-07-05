How to run ap

gradle bootRun

http://localhost:8080/test/hello
http://localhost:8080/test/hello?name=Shop

------------------------------------------
How to build jar

chmod +x gradlew
./gradlew bootJar

gradlew bootJar



git push origin master

OK I discovered that you need to either AVOID checking the "Git Credential Manager" checkbox during the Git for Windows installer, or (after installation) run bash shell as Administrator and use git config --edit --system to remove the helper = manager line so that it is no longer registered as a credential helper.

For bonus points, use git config --edit --global and insert:

[core]
    askpass =
To disable the OpenSSH credentials popup too.