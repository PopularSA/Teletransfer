20220113: V1.6 Se retira las referencias a la libreria LOG4J por provocar vulnerabilidad critica
20210818: v1.5 Actualización teletransfer para mype dólares
20210422: V1.4 Se implementa nuevos fondos: FONDO CAPITAL EMPRENDEDOR DOLARES y FONDO POPULAR 1 - RM DOLARES
20201209: v1.3 Se cambia la cadena de conexion a la base de datos, ahora es la DB que esta en AWS

12:45 21/01/2019
Salio un warning en el log respecto a la configuracion de las conexiones, se ha actualizado a 2,1,0 para ver como va con eso.

12:19 21/01/2019
Se ha detectado que esta dejando conexiones abiertas y tambien se ha realizado una reingenieria a EVA, SACIV,
para que ahora la instancia unica es dbemprende, dentro de la cual existe el esquema TELETRANSFER,
el usuario es del mismo nombre y la claves ha sido definido y cambiada.

12:19 16/05/2018
Traslado hacia servidor de base de datos de produccion 192.168.70.13