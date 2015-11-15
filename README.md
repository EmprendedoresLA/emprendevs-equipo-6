# emprendevs-equipo-6

Social Map

   Beneficio a usuarios
Social Map es una aplicación que tiene como objetivo visualizar y compartir la ubicación actual en el Mapa de las personas previamente registradas en la misma y vinculadas a un grupo (a futuro) con la posibilidad de enviar alertas con la locación al resto de usuarios del grupo. Al momento del desarrollo se tuvo muy en cuenta el consumo de la bateria, que es muy bajo cuando se utiliza. 

Esto se logra con una aplicación movil para los usuarios con Android y una aplicación en un servidor de nexo entre los dispositivos. La notificación intantánea de la alerta se logra utilizando la API Google GCM como también la visualización en mapa.

Se podrá obtener y compartir el Geoposicionamiento de manera muy rápida y eficaz, con un muy baja consumo de batería. 

Para quién? Personas de entre 14 y 60 años.

Los posibles situaciones donde esta app sería de mucha utilizad son:
  En Familas: Seguimiento de hijos, ver última ubicación con fecha y hora, recibir o enviar alerta adjuntando la ubicación en caso de peligro, visualizar en Mapa a todos los miembros en eventos/country. Ideal para viajes.
  Grupo de amigo: Conocer la ubicación de los mismo en todo momento. Ideal para viajes. 
  Encuentros: Permite coordinarlo sin que ninguno de los participantes espere demasiado.
  

    Buenas
-	Disponible las 24hs para reportar nuestra posición actual y ALERTAS con última ubicación.
-	Visualización Mapa. Detalle de cada usuario. (nombre, apellido, telefono,mail, fechaNac, sexo, localidad, latitud, longitud, fechaUltGeoPosicion)
-	En caso de emergencia posibilidad de reportar un alerta de nuestra ubicación al instante. Esta alerta les llegará a todos los usuarios registrados (En un futuro, a un grupo/s en particular).
-	Consume batería únicamente cuando se ejecuta la app. Por lo tanto compartirá la ubicación y visualizará las mismas en dicho mento.

Para dispositivos Android, adaptable a celulares y tablet. Compatible con Android 3.0 en adelante. 


  Arquitectura de la Aplicación

Consta de dos desarrollos, una parte en java para Móviles con sistemas operactivos Android (Para los usuarios finales). Y el segundo desarrollo fue hecho en C# para el Servidor, donde se encuentran los Web Services y Base de Datos.

Desarrollada principalmente en Eclipse ADT pero la parte de Server esta en Visual Studio y SQL Server.

Se utilizam dos API de Google para mostrar el mapa y para permitir enviar notificación push con las alertas.

La aplicación inicia con un mapa que muestra nuestra posición actual. 
Luego al pulsar el item de registro se dirige a la pantalla de Registración para completar los campos necesarios para darse de alta como usuario y al presionar el botón con la imagen de guardar. Se envía el registro al servidor de la aplicación.


  Posibilidad de Big Data.
  
  Se podrían brindar REPORTES a empresas o municipalidades con información NO confidencial. Por ejemplo un mapa caliente de mujeres entre 20 y 40 años en determinada zona de la ciudad y día.

