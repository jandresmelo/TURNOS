# Sistema de Gestión de Turnos - Universidad Compensar

Este programa implementa un **Sistema de Gestión de Turnos** diseñado para la **Universidad Compensar**, utilizando estructuras de datos dinámicas como **listas enlazadas**. El sistema gestiona tanto turnos de usuarios externos como turnos internos para colaboradores (administrativos, docentes, operarios de asesoría). La información se almacena en un archivo JSON con formato vertical, asegurando persistencia y claridad.

---

## **Características Principales**

### 1️⃣ **Asignar Turnos**
- **Externos:** Para usuarios generales.
- **Colaboradores:** Para administrativos, docentes y operarios de asesoría.
- Turnos asignados con prioridad para colaboradores.

### 2️⃣ **Eliminar Turno**
- Permite eliminar un turno ingresando el **número de cédula** o el **código del turno**.

### 3️⃣ **Marcar Turno como Atendido**
- Cambia el estado de un turno a "atendido" y registra la fecha y hora de atención.

### 4️⃣ **Calificar Turno**
- Asigna una calificación al turno una vez atendido:  
  - `5`: Excelente  
  - `3`: Bueno  
  - `1`: Malo  

### 5️⃣ **Reporte de Turnos Pendientes**
- Muestra únicamente los turnos que no han sido atendidos.
- Formato visual claro y detallado, presentado en estilo vertical.

### 6️⃣ **Restricción por Cédula**
- Un usuario no puede solicitar un nuevo turno si ya tiene uno pendiente.

---

## **Formato de Almacenamiento (JSON)**

Los turnos se guardan en un archivo llamado `ControlTurnos.json` con el siguiente formato:

```json
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
```

## 🛠️ Tecnologías y Herramientas

### 💻 Lenguaje
- **Java**

### 🗃️ Almacenamiento
- **JSON**

### 🖥️ IDE Sugerido
- **Visual Studio Code**
- **IntelliJ IDEA**

### 📚 Librerías Utilizadas
- **[Gson](https://github.com/google/gson):** Para la manipulación de archivos JSON.

## 🛠️ Tecnologías y Herramientas

### 💻 Lenguaje
- **Java**

### 🗃️ Almacenamiento
- **JSON**

### 🖥️ IDE Sugerido
- **Visual Studio Code**
- **IntelliJ IDEA**

### 📚 Librerías Utilizadas
- **[Gson](https://github.com/google/gson):** Para la manipulación de archivos JSON.

## 📂 Estructura del Proyecto

```bash
📂 SistemaGestionTurnos
├── 📂 lib
│   └── gson-2.8.9.jar       # Librería Gson para manejo de JSON
├── 📂 src
│   └── ControlTurnos.java   # Código principal del programa
├── ControlTurnos.json       # Archivo de almacenamiento de datos
└── README.md                # Documentación del proyecto
```
## 📂 Cómo Ejecutar el Programa

### Clonar el Repositorio:

```bash
git clone https://github.com/tuusuario/SistemaGestionTurnos.git
cd SistemaGestionTurnos
```
### Compilar el Programa: Asegúrate de incluir la biblioteca Gson en el classpath:

```bash
javac -cp "lib/gson-2.8.9.jar" src/ControlTurnos.java -d bin
```

## 📝 Ejemplo de Flujo

### 1️⃣ Asignar un Turno Externo
1. Selecciona la opción **1** en el menú.
2. Ingresa la **cédula**.
3. Elige el tipo de turno entre las opciones (Discapacidad, General, etc.).
4. El sistema genera un turno con un número único y una estimación del tiempo de atención.

### 2️⃣ Asignar un Turno para Colaboradores
1. Selecciona la opción **2** en el menú.
2. Ingresa la **cédula**.
3. Elige el tipo de turno colaborador (Administrativo, Docente, etc.).
4. El turno tiene prioridad en la lista.

### 3️⃣ Eliminar un Turno
1. Selecciona la opción **3** en el menú.
2. Ingresa el **código del turno** o la **cédula** asociada al turno.
3. El sistema eliminará el turno correspondiente.

### 4️⃣ Marcar un Turno como Atendido
1. Selecciona la opción **4** en el menú.
2. Ingresa el **código del turno** o la **cédula** del usuario.
3. El turno se marcará como atendido, incluyendo la fecha y hora de atención.

### 5️⃣ Calificar un Turno
1. Selecciona la opción **5** en el menú.
2. Ingresa el **código del turno** o la **cédula** del usuario.
3. Asigna una calificación al servicio:
   - `5`: Excelente
   - `3`: Bueno
   - `1`: Malo

### 6️⃣ Reporte de Turnos Pendientes
1. Selecciona la opción **6** en el menú.
2. Visualiza una lista detallada de los turnos que aún no han sido atendidos, con formato vertical.

---

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Si deseas mejorar este proyecto.

