# RES-2021-Labo-SMTP

### RES-2021-Labo-SMTP By Paul Reeve & Maxime Scharwath

This repo is a project for the course RES at HEIG-VD
## Let's introduce :
```
 ________  ________  ________  ________   ___  __    _______   ________    _______  ________  ________  ________     
|\   __  \|\   __  \|\   __  \|\   ___  \|\  \|\  \ |\  ___ \ |\   __  \  /  ___  \|\   __  \|\   __  \|\   __  \    
\ \  \|\  \ \  \|\  \ \  \|\  \ \  \\ \  \ \  \/  /|\ \   __/|\ \  \|\  \/__/|_/  /\ \  \|\  \ \  \|\  \ \  \|\  \   
 \ \   ____\ \   _  _\ \   __  \ \  \\ \  \ \   ___  \ \  \_|/_\ \   _  _\__|//  / /\ \  \\\  \ \  \\\  \ \  \\\  \  
  \ \  \___|\ \  \\  \\ \  \ \  \ \  \\ \  \ \  \\ \  \ \  \_|\ \ \  \\  \|  /  /_/__\ \  \\\  \ \  \\\  \ \  \\\  \ 
   \ \__\    \ \__\\ _\\ \__\ \__\ \__\\ \__\ \__\\ \__\ \_______\ \__\\ _\ |\________\ \_______\ \_______\ \_______\
    \|__|     \|__|\|__|\|__|\|__|\|__| \|__|\|__| \|__|\|_______|\|__|\|__| \|_______|\|_______|\|_______|\|_______|
```

## Config

Please put these 3 config files on a folder named "config" which will be in the same folder as the .jar file.

```
├── PrankClient.jar
└── config
    ├── config.properties
    ├── messages.utf8
    └── victims.utf8
```

### config.properties

```properties
smtpServerAddress=example.smtpserver.com
smtpServerPort=25
numberOfGroups=8
groupSize=30
witnessToCC=example@example.com
```

This example config will send the messages to the server ___example.smtpserver.com___ on the Port ___25___.
___8___ groups of ___30___ victims will be created and an hidden copy will be sent to ___example@example.com___

### messages.utf8

```text
Example of subject 1
Hello,
I am a body
:firstname :lastname
====
Example of subject 1
Body
====
```

You need to separate each message by ___====___ and the first line of each message is used as email subject. You can
use __:firstname__, __:lastname__ and __:email__ in the message's body and they will be remplace by the adequate "From
contact"'s properties.

### victims.utf8

```text
firstname,lastname,email@example.com
john,doe,john.doe@unknown.com
monsieur,x,mx@chaispas.fr
```

This file is a list of victims. You need to put 3 properties in each , like so: ___firstname,lastname,email___ and use a
break line to separate each victim.

## Mock SMTP Server

In our project we use MockMock. (https://github.com/tweakers/MockMock)
We use a dockerized version of it with this dockerfile:

```dockerfile
FROM openjdk:8

ENV MOCKMOCK_SMPT_PORT=25
ENV MOCKMOCK_HTTP_PORT=8282
ENV MOCKMOCK_MAX_QUEUE_SIZE=1000

ADD ["https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true", \
     "/mockmock/"]

EXPOSE 25 8282

WORKDIR /mockmock/

ENTRYPOINT java -jar MockMock.jar -p $MOCKMOCK_SMPT_PORT -h $MOCKMOCK_HTTP_PORT -m $MOCKMOCK_MAX_QUEUE_SIZE
```

We use this dockerized version and not directly in our device because there are some problems on Windows device. See
this issue: https://github.com/tweakers/MockMock/issues/10
