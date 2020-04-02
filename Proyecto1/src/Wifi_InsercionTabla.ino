
/********************************************************************************
    Autor:
      Sergio Bemposta
 
    Mas informacion Comados AT:
      https://www.itead.cc/wiki/ESP8266_Serial_WIFI_Module
 
    Cableado:
    Arduino Mega       ||  Adaptador ESP8266
      TX1-18           ||  RX
      RX-19            ||  TX
      5V               ||  VCC
      GND              ||  GND
********************************************************************************/
 
const char* ssid = "Wifi";
const char* password = "contraseña";
 
const char* dbUser = "pr_healthtech";
const char* dbPass = "Jamboneitor12";
const char* dbName = "pr_healthtech";
//http://2.139.176.212:81/bd/insert.php?db=test&user=alumno&pass=alumno&insert=ccaa(id_ccaa,%20NombreId,%20Nombre)%20values%20(21,%20%27nombre%27,%20%27Nombre%20Largo%27);
 
const unsigned int TAM_BUFF = 250;
char cmd[TAM_BUFF];
 
/********************************************************************************/
/********************************************************************************/
bool WaitOK( long, bool = false, bool = true );
int  WaitAnswer( long, bool = false, bool = true );
void sendComand( String, bool = true );
 
/********************************************************************************/
/********************************************************************************/
void setup() {
    Serial.begin( 115200 ); // Connection to PC
    Serial1.begin( 115200 ); // Connection to ESP8266
}
 
/********************************************************************************/
/********************************************************************************/
void loop() {
    int respuesta;
    String cad;
 
    sendComand( "AT" );
    if( !WaitOK( 1000 ) ) {
        Serial.println( "Error: Modulo Wifi no responde" );
        return;
    }
 
    // Esto es para dar tiempo a que se conecte a la wireless..
    delay( 5000 );
 
    cad = "AT+CIPSTART=\"TCP\",\"2.139.176.212\",81";
    sendComand( cad );
    WaitOK( 2000 );
    sendComand( "AT+CIPSTATUS" );
    WaitOK( 1000 );
 
    /* Esto es lo que queremos:
        insert into ccaa(id_ccaa, NombreId, Nombre) values (30, 'nombre', 'Nombre Largo')
        Comando GEET "GET /bd/insert.php?db=test&user=alumno&pass=alumno&insert=ccaa(id_ccaa,%20NombreId,%20Nombre)%20values%20(20,%20%27nombre2%27,%20%27Nombre%20Largo%27) HTTP/1.0";
    */
    char *format = "GET /bd/insert.php?db=%s&user=%s&pass=%s&insert=%s HTTP/1.0";
    char *inserCmd = "ccaa(id_ccaa,%20NombreId,%20Nombre)%20values%20(50,%20%27nombre50%27,%20%27Nombre%20Largo%27)";
    snprintf(cmd, TAM_BUFF, format, dbName, dbUser, dbPass, inserCmd);
 
    sendGet( cmd );
    respuesta = WaitAnswer( 10000 );
    switch( respuesta ) {
        case 0:
            Serial.println( "** Insercion Correcta **" );
            break;
        case 1:
            Serial.println( "Error 1" );
            break;
        case 2:
            Serial.println( "Error 2" );
            break;
        default:
            Serial.println( "Not Fount" );
            sendComand( "AT+CIPCLOSE" );
            break;
    }
 
    Serial.print( "Terminado" );
    for( ;; );
}
 
/********************************************************************************/
/********************************************************************************/
void sendComand( String c, bool echo ) {
    Serial1.flush();
    Serial1.println( c );
    if( echo )Serial.println( "*****************************************" );
    if( echo )Serial.println( c );
    Serial1.flush();
}
 
/********************************************************************************/
/********************************************************************************/
void sendGet( String cmd ) {
    String cad = "AT+CIPSEND=" + String( cmd.length() + 4 );
    sendComand( cad );
    WaitOK( 500 );
    sendComand( cmd, true );
    sendComand( "" );
}
 
/********************************************************************************/
/********************************************************************************/
void PrintResponse( long timeoutamount ) {
    unsigned long timeout = millis() + timeoutamount;
    unsigned long timeStart = millis();
    char c;
    Serial.println( "=========================================" );
    while( millis() <= timeout ) {
        if( Serial1.available() > 0 ) {
            c = Serial1.read();
            Serial.write( c );
        }
    }
}
 
/********************************************************************************/
/********************************************************************************/
bool WaitOK( long timeoutamount, bool echo, bool echoError ) {
    static int codigo = 0;
    unsigned long timeout = millis() + timeoutamount;
    unsigned long timeStart = millis();
    char c0 = ' ', c1 = ' ';
    codigo += 1;
    if( echo )Serial.println( "-----------------------------------------" );
    while( millis() <= timeout ) {
        while( Serial1.available() > 0 ) {
            c1 = Serial1.read();
            if( echo )Serial.write( c1 );
            if( c0 == 'O' && c1 == 'K' ) {
                if( echo )Serial.println( "\nWaitOK TRUE [" + String( millis() - timeStart ) + "ms]" );
                delay( 100 );
                while( Serial1.available() > 0 ) Serial1.read();
                return true;
            }
            c0 = c1;
        }
    }
    if( echoError )Serial.println( "WaitOK False {Code:" + String( codigo ) + "} [" + String( millis() - timeStart ) + "ms]" );
    return false;
}
 
/********************************************************************************/
/********************************************************************************/
int WaitAnswer( long timeoutamount, bool echo, bool echoError ) {
    unsigned long timeout = millis() + timeoutamount;
    unsigned long timeStart = millis();
    char c[7] = "       ";
    if( echo )Serial.println( "-----------------------------------------" );
    while( millis() <= timeout ) {
        while( Serial1.available() > 0 ) {
            c[6] = Serial1.read();
            if( echo )Serial.write( c[6] );
            if( c[6] == '#' ) {
                if( c[0] == 'E' && c[1] == 'R' && c[2] == 'R' && c[3] == 'O' && c[4] == 'R' ) {
                    Serial.println( "\nAnswer ERROR:" + String( c[5] ) + " [" + String( millis() - timeStart ) + "ms]" );
                    delay( 100 );
                    while( Serial1.available() > 0 ) Serial1.read();
                    return ( c[5] - '0' );
                }
                if( c[4] == 'O' && c[5] == 'K' ) {
                    Serial1.read();
                    char code = Serial1.read();
                    if( echo )Serial.println( "\nAnswer OK [" + String( millis() - timeStart ) + "ms]" );
                    delay( 100 );
                    while( Serial1.available() > 0 ) Serial1.read();
                    return 0;
                }
            }
            c[0] = c[1]; c[1] = c[2]; c[2] = c[3]; c[3] = c[4]; c[4] = c[5]; c[5] = c[6];
        }
    }
    if( echoError )Serial.println( "WaitAnswer False [" + String( millis() - timeStart ) + "ms]" );
    return -1;
}
