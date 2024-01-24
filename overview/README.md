# Overview (Übersicht)

In diesem Ordner findet ihr die React App des Overview Teams.
Im folgenden wird noch erklärt wie ihr die app bei euch lokal starten könnt und wie die App innerhalb eines Dockercontainers auf der VM gestartet werden kann.


## App Lokal testen
### Vorraussetzungen

Damit du die App überhaupt starten kannst benötigst du node. 
Node: https://nodejs.org/en/download/

Nachdem du node installiert hast kann du die App auch schon mit wenigen commands starten.

### App compilieren und starten

Öffne ein Terminal im Projekt und navigiere in den Unterordner ```overview```
Führe dann folgende commands nacheinander aus:
1. ```npm install```
2.  ```npm start```

Diese beiden Commands laden alle dependecies der app herunter, compilieren die app und starten sie dann auf einem lokalen webserver welcher unter [http://localhost:3000](http://localhost:3000) erreichbar ist.

## App auf der VM starten
Damit die VM samt Webserver auf der Linux VM laufen kann muss ein Docker image gebaut werden.
Logge dich hierfür auf der VM ein und navigieren in folgenden Unterordner: ```~/wi-inf-projekt-2-greenbpm/overview```

### Neues Dockerimage bauen
Führe folgenden command aus: ```sudo docker build . -t overview:latest```
Nun wird ein neues Dockerimage auf Basis der Dockerfile im overview Ordner erstellt. Dieses image enthält die fertig compilierte react app. (Achtung: Dieser Prozess kann einige Zeit in anspruch nehmen)

### Dockercontainer starten
Nachdem das Image erstellt wurde kannst du mit folgendem command einen container auf basis des eben erstellen images starten: ```sudo docker run -p 3000:3000 overview```

Die react app startet und wird unter der IP adresse der VM auf port 3000 bereitgestellt. Du kannst die laufende Anwendung dann unter folgender URL sehen: ```http://193.196.143.145:3000/```
