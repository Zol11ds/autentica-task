# Datortehnikas pieprasīšanas sistēma

**_Izstrāde ir saistībā ar uzdoto Autentica uzdevumu_**

----

Pamatā noteiktā web sistēma izmanto šādus satvarus:
* Maven (3.6.3 un 3.8.6 ir pārbaudīti, ka strādā)
* Spring Boot (2.7.1)
* Vaadin (23.1.6)

Dati iekšēji tiek ģenerēti (lai vieglāk izskatītu iespējas) un apstrādāti ar Spring Data JPA palīdzību.
Laika limitācijas ietekmē netika izstrādāta relāciju datu bāze.

## Iespējas

1. Autentifikācija
2. Esošo pieprasījumu izskatīšana tabulas formā
3. Datu filtrēšanas iespējas
4. Jaunu pieprasījumu izveidošana
5. Esošo pieprasījumu rediģēšana
   1. Rediģētā pieteikuma saglabāšana
   2. Rediģētā pieteikuma dzēšana
   3. Statusa maiņa

## Programmas izmantošana

Sākotnēji jāizsauc `mvn clean package -Pproduction` (Windows) vai `./mvnw clean package -Pproduction` (Mac un Linux).
Tas izveidos JAR failu, kurā būs visas atkarības un front-end resursi un kas ir gatavs izmantošanai.
Failu var atrast `target` mapē pēc faila izveides.

Pēc JAR faila izveides to var palaist, izmantojot `java -jar target/uzddps-1.0-SNAPSHOT.jar`

## Nozīmīgi!

Lai šajā limitētajā laikā varētu izzināt iepriekš neizzinātus satvarus un pilnvērtīgi izstrādāt uzdoto uzdevumu, tika izskatīti papildu resursi.
Galvenais resurss ir ["Building Modern Web Applications With Spring Boot and Vaadin" pamācība](https://vaadin.com/docs/latest/tutorial/overview)
, kurš tika izmantots kā galvenais pamats šim projektam. Protams, tas tika pārkonfigurēts paša vajadzībām.
Tā laikā tika izzinātas papildus lietas par satvariem, kas bija ārpus pamācības
