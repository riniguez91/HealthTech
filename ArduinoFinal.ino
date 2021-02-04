// Librerias
#include <ESP8266WiFi.h>        // Conectar NodeMCU a WiFi
#include <MySQL_Connection.h>   // Conectar con la base de datos
#include <MySQL_Cursor.h>       // Para la sentecnias SQL
#include <OneWire.h>            // Con él conseguimos enviar y recibir datos por un único cable              
#include <DallasTemperature.h>  // Para la temperatura (DS1820B)
#include <TinyGPS++.h>          // Para el GPS
#include <SoftwareSerial.h>     // Para crear una instancia de un objeto SoftwareSerial (GPS)
#include "MQ135.h"              // Para el MQ-135

// ------------------------------VARIABLES---------------------------------//
// TEMPERATURA: Variables del sensor
  OneWire ourWire(5);                             // Se establece el pin D1 como bus OneWire
  DallasTemperature sensorTemperatura(&ourWire);  // Se declara una variable u objeto para nuestro sensor

// GAS: Varibales del sensor
  const int ANALOGPIN = 0;              // Se establece el pin A0 como lectura del sensor de Gas
  MQ135 gasSensor = MQ135(ANALOGPIN);   // Se asigna en pin a la libreria MQ135

// Sensor Presión de la cama (Simulado con un botón)
  int boton = 4;        // Definimos el puerto de descarga D2
  int val;              // Definimos la variable digital val
  int valorAnterior;    // Valor anterior de la variable
    // Interrupciones
    int buttonState;                      // La lectura actual del pin de entrada
    unsigned long lastDebounceTime = 0;   // La última vez que se activó el pin de salida
    unsigned long debounceDelay = 500;    // El tiempo de rebote que creemos conveniente; 

// Sensor Magnético de la puerta (Simulado con un botón)
  int boton1 = 14;      // Definimos el puerto de descarga D5
  int val1;             // Definimos la variable digital val
  int valorAnterior1;   // Valor anterior de la variable
  // Inerrupciones
    int buttonState1;                     // La lectura actual del pin de entrada
    unsigned long lastDebounceTime1 = 0;  // La última vez que se activó el pin de salida
    unsigned long debounceDelay1 = 500;   // El tiempo de rebote que creemos conveniente; 


// GPS: Variables del sensor
  // Pines del software serial
  int RXPin = 13;     // Rx del GPS conectado al D8
  int TXPin = 15;     // Tx del GPS conectado al D7
  int GPSBaud = 9600; // Baud a los que funciona el GPS
  TinyGPSPlus gps;    // Creamos un objeto TinyGPS++
  SoftwareSerial gpsSerial(RXPin, TXPin); // Creamos un puerto software serial llamado "gpsSerial"

// Credenciales del WiFi.
  char ssid[] = "INFORMATICA_FUENTEALBILLAD35A";  // Nombre de la red WiFi
  char pass[] = "93348591";                       // Contraseña de la red WiFi

// Credenciales de la Base de Datos
  IPAddress server_addr(2,139,176,212);     // IP del servidor MySQL 
  char user[] = "pr_healthtech";            // MySQL user login username
  char password[] = "Jamboneitor123";       // MySQL user login password

// Creamos un cliente WiFi y lo asociamos a la conexión con la base de datos
  WiFiClient client;                          // Creamos nuestro cliente WiFi
  MySQL_Connection conn((Client *)&client);   // Y lo conectamos a la base de datos

// ------------------------------SETUP---------------------------------//
void setup() {
  Serial.begin(9600); // Conexion  al PC
  
  // Comprobamos que se conecta al WiFi
  Serial.println("Test WiFi!");             // Comprobamos que se conecta al WiFi
  WiFi.begin(ssid, pass);                   // Le pasamos al WiFi los parametros
  while (WiFi.status() != WL_CONNECTED) {   // Comrpbamos que se conecta
    Serial.print(".");                      // Mandamos un . cada 500ms hasta que este conectado
    delay(500);                             // Esperamos a que se conecte
  }

  Serial.println("");
  Serial.println("WiFi conectado"); // Cuando se conecte lo imprime
  Serial.println(WiFi.localIP());   // Mostramos la Ip de la conexión
  // Conectamos el sensor GPS
  gpsSerial.begin(GPSBaud);         // Se incia el sensor GPS (Velocidad de lectura de SoftwareSerial)
  // Conectamos el sensor de temperatura
  sensorTemperatura.begin();        // Se inicia el sensor de Temperatura (DallasTemperatura)
  // Simulamos el sensor de presion
  pinMode(boton, INPUT_PULLUP);     // Define shock sensor as a output port
  // Simulamos el sensor magnético
  pinMode(boton1, INPUT_PULLUP);    // Define shock sensor as a output port
}

// ------------------------------LOOP---------------------------------//
unsigned long currentTime;
void loop() {
  // Inicializamos el GPS para que este disponible
  if (millis() > 5000 && gps.charsProcessed() < 10)
    Serial.println(F("No se recibieron datos de GPS: verifique el cableado"));

  // Leemos sensores discretos en cada iteracción
  obtenerDatosHorasDurmiendo();   // Llamamos a la funcion para obtener los datos de las Horas Durmiendo
  obtenerDatoPuertaAbierta();     // Llamamos a la funcion para obtener los datos del Sensor Magnético

  // Interrupciones de tiempo para mandar a la base de datos
  // los valores en función el tiempo que seleccionemos
  interrupcionTiempoDeEspera();
  
  Serial.println();
  smartDelay(1000);
}

// OTROS MÉTODOS
// ------------------------------SENSORES---------------------------------//
// TEMPERATURA
void obtenerDatosTemp() {
  sensorTemperatura.requestTemperatures();              // Se envía el comando para leer la temperatura
  double temp = sensorTemperatura.getTempCByIndex(0);   // Se obtiene la temperatura en ºC
  Serial.print("Temperatura= ");
  Serial.print(temp);                                   // Imprimimos el valor recibido en la consola
  Serial.println(" ºC"); 
  grabarDatoTempBD(temp, 56); // corregir falta el ID
}

// CALIDAD DEL AIRE
void obtenerDatosCalidadDelAire() {
  float ppm = gasSensor.getPPM();
  Serial.print("Calidad del aire: ");
  Serial.println(ppm);
  grabarDatoGasBD (ppm,59);
}

// HORAS DURMIENDO (Simulado con boton)
void obtenerDatosHorasDurmiendo() {
  val = digitalRead(boton); // Leemos el valor digital del botón
  Serial.print("Sensor Presión: ");
  Serial.println(val);
  interrupcionPresion();    // Interrupcion para evitar el Debouncing
}

// MAGNETICO (Simulado con boton)
void obtenerDatoPuertaAbierta() {
  val1 = digitalRead(boton1);
  Serial.print("Sensor Magnético: ");
  Serial.println(val1);
  interrupcionMagnetico();
}

// ------------------------------BASE DE DATOS---------------------------------//
// Grabar datos en la base de datos del Sensor de Temperatura
void grabarDatoTempBD (double temperatura, int sensores_ID1) {
  Serial.println("Mandando datos de temperatura a sensores_continuos");
  if (conn.connect(server_addr, 3306, user, password)) {
    char INSERT_SQL1[] = "INSERT INTO pr_healthtech.sensores_continuos (Reading, Date_Time_Activation, Sensores_ID1) VALUES (%f, NOW(), %d)";
    char query[128];
    sprintf(query, INSERT_SQL1, temperatura, sensores_ID1);
    MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
    cur_mem->execute(query);
    delete cur_mem;
    Serial.println("Registro grabado ");
    conn.close();
  }
  else
    Serial.println("Conexión a la base de datos MySQL fallida.");
}

// Grabar datos en la base de datos del Sensor de Calidad del Aire
void grabarDatoGasBD (double ppmCalidad, int sensores_ID1) {
  Serial.println("Mandando datos del calidad del aire a sensores_continuos");
  if (conn.connect(server_addr, 3306, user, password)) {
    char INSERT_SQL2[] = "INSERT INTO pr_healthtech.sensores_continuos (Reading, Date_Time_Activation, Sensores_ID1) VALUES (%f, NOW(), %d)";
    char query[128];
    sprintf(query, INSERT_SQL2, ppmCalidad, sensores_ID1);
    MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
    cur_mem->execute(query);
    delete cur_mem;
    Serial.println("Registro grabado ");
    conn.close();
  }
  else
    Serial.println("Conexión a la base de datos MySQL fallida.");
}

// Grabar datos en la base de datos del Sensor de Presión
void grabarDatoPresionBD (int presionActivado, int sensores_ID2) {
  Serial.println("Mandando datos de Sensor de Presion a sensores_discretos");
  if (conn.connect(server_addr, 3306, user, password)) {
    char INSERT_SQL3[] = "INSERT INTO pr_healthtech.sensores_discretos (Reading, Date_Time_Activation, Sensores_ID2) VALUES (%d, NOW(), %d)";
    char query[128];
    sprintf(query, INSERT_SQL3, presionActivado, sensores_ID2);
    MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
    cur_mem->execute(query);
    delete cur_mem;
    Serial.println("Registro grabado ");
    conn.close();
  }
  else
    Serial.println("Conexión a la base de datos MySQL fallida.");
}

// Grabar datos en la base de datos del Sensor Magnético
void grabarDatoMagneticoBD (int magneticoActivado, int sensores_ID3) {
  Serial.println("Mandando datos de Sensor Magnético a sensores_discretos");
  if (conn.connect(server_addr, 3306, user, password)) {
    char INSERT_SQL4[] = "INSERT INTO pr_healthtech.sensores_discretos (Reading, Date_Time_Activation, Sensores_ID2) VALUES (%d, NOW(), %d)";
    char query[128];
    sprintf(query, INSERT_SQL4, magneticoActivado, sensores_ID3);
    MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
    cur_mem->execute(query);
    delete cur_mem;
    Serial.println("Registro grabado ");
    conn.close();
  }
  else
    Serial.println("Conexión a la base de datos MySQL fallida.");
}

// Grabar datos en la base de datos del GPS
void grabarDatoGPSBD (double latitud, double longitud, int idsensor) {
  Serial.println("Mandando datos a sensor_gps");
  if (conn.connect(server_addr, 3306, user, password)) {
    char INSERT_SQL4[] = "UPDATE pr_healthtech.sensor_gps SET Latitude = %f, Longitude = %f, Date_Time_Activation = NOW() WHERE Sensores_ID3 = %d" ;
    char query[128];
    sprintf(query, INSERT_SQL4, latitud, longitud, idsensor);
    MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
    cur_mem->execute(query);
    delete cur_mem;
    Serial.println("Registro grabado ");
    conn.close();
  }
  else
    Serial.println("Conexión a la base de datos MySQL fallida.");
}

// ------------------------------INTERRUPCIONES---------------------------------//
// Tiempo de espera para mandar los datos a la Base de Datos
void interrupcionTiempoDeEspera() {
  // start time of wait period
  static unsigned long startTime = 0;
  static unsigned long startTime2 = 1;

  currentTime = millis();

  if (startTime == 0) {
    obtenerDatosTemp();   // Llamamos a la funcion para obtener los datos de la Temperatura
      
    startTime = currentTime;
  }
  
  if (startTime2 == 1) {
    // GPS
    Serial.println();
    Serial.print("Latitude: ");
    Serial.println(gps.location.lat(), 6);
    Serial.print("Longitude: ");
    Serial.println(gps.location.lng(), 6);
    grabarDatoGPSBD (gps.location.lat(), gps.location.lng(), 60);
    // GAS
    obtenerDatosCalidadDelAire();   // Llamamos a la funcion para obtener los datos de la Calidad de Aire
 
    startTime2 = currentTime;
  }
  // Condiciones de tiempo para llamar a los sensores
  if (currentTime - startTime >= 600000UL) { // Cada 10 minutos (Temperatura)
    // Hora de inicio de reposo; la próxima iteración del ciclo se realizará una lectura de temperatura porque startTime es igual a 0
    startTime = 0;
  }
  
  if (currentTime - startTime2 >= 60000UL) { // Cada minuto (Gas)
    // Hora de inicio de reposo; la próxima iteración del ciclo se realizará una lectura de GPS y GAS porque startTime es igual a 1
    startTime2 = 1;
  }  
}
// Tiempo de espera para elimiar el Debouncing
void interrupcionPresion() {
  // Si el interruptor cambia, debido al ruido o al presionar:
  if (val != valorAnterior) {
  // Restablece el temporizador de eliminación de rebotes
    lastDebounceTime = millis();
  }
  if ((millis() - lastDebounceTime) > debounceDelay) {
    // Cualquiera que sea la lectura, ha estado allí por más tiempo que el rebote
    // Retraso, así que tómalo como el estado actual real:
 
    // si el estado del botón ha cambiado:
    if (val != buttonState) {
      buttonState = val;
 
      // Solo manda los datos si cambia de estado
      if (buttonState == 1 || buttonState == 0) {
        grabarDatoPresionBD (val, 57);
      }
    }
  }
  valorAnterior = val;
}

void interrupcionMagnetico() {
  // Si el interruptor cambia, debido al ruido o al presionar:
  if (val1 != valorAnterior1) {
  // Restablece el temporizador de eliminación de rebotes
    lastDebounceTime1 = millis();
  }
  if ((millis() - lastDebounceTime1) > debounceDelay) {
    // Cualquiera que sea la lectura, ha estado allí por más tiempo que el rebote
    // Retraso, así que tómalo como el estado actual real:
 
    // Si el estado del botón ha cambiado:
    if (val1 != buttonState1) {
      buttonState1 = val1;

      // Solo manda los datos si cambia de estado
      if (buttonState1 == 1 || buttonState1 == 0) {
        grabarDatoMagneticoBD (val1, 58);
      }
    }
  }
  valorAnterior1 = val1;
}

// ------------------------------DELAY-GPS---------------------------------//
// Esta versión personalizada de delay () asegura que el objeto gps esta siendo alimentado
static void smartDelay(unsigned long ms)
{
  unsigned long start = millis();
  do 
  {
    while (gpsSerial.available())
      gps.encode(gpsSerial.read());
  } while (millis() - start < ms);
}
