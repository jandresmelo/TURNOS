🏫 Sistema de Gestión de Turnos - Universidad Compensar
Este programa implementa un Sistema de Gestión de Turnos diseñado para la Universidad Compensar, utilizando estructuras de datos dinámicas como listas enlazadas. El sistema gestiona tanto turnos de usuarios externos como turnos internos para colaboradores (administrativos, docentes, operarios de asesoría). La información se almacena en un archivo JSON con formato vertical, asegurando persistencia y claridad.

Características Principales
1️⃣ Asignar Turnos
Externos: Para usuarios generales.
Colaboradores: Para administrativos, docentes y operarios de asesoría.
Turnos asignados con prioridad para colaboradores.
2️⃣ Eliminar Turno
Permite eliminar un turno ingresando el número de cédula o el código del turno.
3️⃣ Marcar Turno como Atendido
Cambia el estado de un turno a "atendido" y registra la fecha y hora de atención.
4️⃣ Calificar Turno
Asigna una calificación al turno una vez atendido:
5: Excelente
3: Bueno
1: Malo
5️⃣ Reporte de Turnos Pendientes
Muestra únicamente los turnos que no han sido atendidos.
Formato visual claro y detallado, presentado en estilo vertical.
6️⃣ Restricción por Cédula
Un usuario no puede solicitar un nuevo turno si ya tiene uno pendiente.
Formato de Almacenamiento (JSON)
Los turnos se guardan en un archivo llamado ControlTurnos.json con el siguiente formato:

json
Copiar código
[
  {
    "id": "94A23DDD1C",
    "cedula": "20938811",
    "tipoTurno": "Mayores de 60",
    "numeroTurno": "MAY-7",
    "fechaHoraCreacion": "2024-11-22 17:47:01",
    "fechaHoraAtencion": "2024-11-22 17:47:11",
    "calificacion": 3
  }
]
Tecnologías y Herramientas
Lenguaje: Java
Almacenamiento: JSON
IDE sugerido: Visual Studio Code, IntelliJ IDEA
Librerías utilizadas:
Gson para la manipulación de JSON.
Requisitos Previos
Instalar Java:
Asegúrate de tener Java 8 o superior instalado en tu sistema. Puedes descargarlo desde Java Downloads.

Instalar Gson:
Descarga la biblioteca Gson desde Gson Releases y colócala en una carpeta llamada lib dentro del proyecto.

Estructura del Proyecto
bash
Copiar código
📂 SistemaGestionTurnos
├── 📂 lib
│   └── gson-2.8.9.jar       # Librería Gson para manejo de JSON
├── 📂 src
│   └── ControlTurnos.java   # Código principal del programa
├── ControlTurnos.json       # Archivo de almacenamiento de datos
└── README.md                # Documentación del proyecto
Cómo Ejecutar el Programa
Clonar el Repositorio:

bash
Copiar código
git clone https://github.com/tuusuario/SistemaGestionTurnos.git
cd SistemaGestionTurnos
Compilar el Programa: Asegúrate de incluir la biblioteca Gson en el classpath:

bash
Copiar código
javac -cp "lib/gson-2.8.9.jar" src/ControlTurnos.java -d bin
Ejecutar el Programa: Ejecuta el archivo compilado especificando el classpath:

bash
Copiar código
java -cp "lib/gson-2.8.9.jar;bin" ControlTurnos
Uso del Menú
El programa incluye un menú interactivo que permite las siguientes opciones:

markdown
Copiar código
=== Bienvenido a la Universidad Compensar ===
Sistema de Gestión de Turnos

Seleccione una opción:
1. Asignar turno
2. Asignar turno colaboradores
3. Eliminar turno
4. Marcar turno como atendido
5. Calificar turno
6. Mostrar reporte de turnos pendientes
7. Salir
Ejemplo de Flujo
Asignar un Turno Externo:

Selecciona la opción 1.
Ingresa la cédula.
Elige el tipo de turno entre las opciones (Discapacidad, General, etc.).
Se genera un turno con un número único y una estimación del tiempo de atención.
Asignar un Turno para Colaboradores:

Selecciona la opción 2.
Ingresa la cédula.
Elige el tipo de turno colaborador (Administrativo, Docente, etc.).
El turno tiene prioridad en la lista.
Eliminar un Turno:

Selecciona la opción 3.
Ingresa el código del turno o la cédula asociada al turno.
Marcar un Turno como Atendido:

Selecciona la opción 4.
Ingresa el código del turno o la cédula del usuario.
Calificar un Turno:

Selecciona la opción 5.
Ingresa el código del turno o la cédula del usuario.
Asigna una calificación al servicio.
Reporte de Turnos Pendientes:

Selecciona la opción 6.
Visualiza una lista detallada de los turnos que aún no han sido atendidos.
Contribuciones
¡Las contribuciones son bienvenidas!
Si deseas mejorar este proyecto:

Haz un fork del repositorio.
Crea una nueva rama: git checkout -b feature-mejora.
Realiza tus cambios y haz commit: git commit -m "Agrego mejora X".
Envía un pull request.
Licencia
Este proyecto está bajo la licencia MIT. Puedes consultarla en el archivo LICENSE.
