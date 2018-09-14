heroku

login               password    role

super@gmail.com     password    ROLE_ADMIN ROLE_MANAGER ROLE_USER
admin@gmail.com     admin       ROLE_ADMIN
manager@gmail.com   manager     ROLE_MANAGER
user@gmail.com      1           ROLE_USER

------------------------------------------

Email verification

https://localhost:8080/registration/confirm?token=email_verification_token

------------------------------------------

How to run ap

gradle bootRun

https://localhost:8080/test/hello
https://localhost:8080/test/hello?name=Shop

redirect from http
server.port.http=8081
to https
server.port=8080


------------------------------------------
How to build jar

chmod +x gradlew
./gradlew bootJar

gradlew bootJar


------------------------------------------
git push origin master

OK I discovered that you need to either AVOID checking the "Git Credential Manager" checkbox during the Git for Windows installer, or (after installation) run bash shell as Administrator and use git config --edit --system to remove the helper = manager line so that it is no longer registered as a credential helper.

For bonus points, use git config --edit --global and insert:

[core]
    askpass =
To disable the OpenSSH credentials popup too.

# This is Git's per-user configuration file.
[user]
# Please adapt and uncomment the following lines:
name = Java
email = java.quasar@gmail.com
[http]
        sslVerify = false
[core]
    askpass =

keytool -genkey -alias selfsigned_localhost_sslserver -keyalg RSA -keysize 2048 -validity 3650 -keypass SHOP_KEY -storepass SHOP_STORE -keystore ssl-server.jks

curl -k -X POST https://localhost:8080/actuator/shutdown

https://habr.com/post/350862/