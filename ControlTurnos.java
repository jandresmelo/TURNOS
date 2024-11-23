import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControlTurnos {

    // Lista dinámica para gestionar los turnos
    private List<Turno> colaTurnos = new LinkedList<>();
    private static final String FILE_NAME = "ControlTurnos.json"; // Archivo de persistencia

    // Tipos de turno con prioridades
    private static final Map<Integer, String> TIPOS_TURNO = Map.of(
            1, "Discapacidad",
            2, "Embarazadas",
            3, "Niños en brazos",
            4, "Mayores de 60",
            5, "General",
            6, "Externo"
    );

    private static final Map<Integer, String> TIPOS_TURNO_INTERNO = Map.of(
            1, "Administrativo",
            2, "Docente",
            3, "Operario de Asesoría"
    );

    // Clase interna para representar un turno
    static class Turno {
        private final String id;
        private final String cedula;
        private final String tipoTurno;
        private final String numeroTurno;
        private final String fechaHoraCreacion;
        private String fechaHoraAtencion;
        private int calificacion;

        public Turno(String id, String cedula, String tipoTurno, String numeroTurno, String fechaHoraCreacion) {
            this.id = id;
            this.cedula = cedula;
            this.tipoTurno = tipoTurno;
            this.numeroTurno = numeroTurno;
            this.fechaHoraCreacion = fechaHoraCreacion;
            this.fechaHoraAtencion = null;
            this.calificacion = 0;
        }

        public String getCedula() {
            return cedula;
        }

        public String getNumeroTurno() {
            return numeroTurno;
        }

        public boolean isAtendido() {
            return fechaHoraAtencion != null;
        }

        public void marcarAtendido() {
            this.fechaHoraAtencion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }

        public void asignarCalificacion(int calificacion) {
            this.calificacion = calificacion;
        }

        @Override
        public String toString() {
            return "{\n" +
                    "\"id\":\"" + id + "\",\n" +
                    "\"cedula\":\"" + cedula + "\",\n" +
                    "\"tipoTurno\":\"" + tipoTurno + "\",\n" +
                    "\"numeroTurno\":\"" + numeroTurno + "\",\n" +
                    "\"fechaHoraCreacion\":\"" + fechaHoraCreacion + "\",\n" +
                    (fechaHoraAtencion != null ? "\"fechaHoraAtencion\":\"" + fechaHoraAtencion + "\",\n" : "") +
                    "\"calificacion\":" + calificacion + "\n}";
        }
    }

    // Método para cargar turnos desde un archivo JSON
    private void cargarTurnos() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            Gson gson = new Gson();
            colaTurnos = gson.fromJson(reader, new TypeToken<LinkedList<Turno>>() {
            }.getType());
            if (colaTurnos == null) {
                colaTurnos = new LinkedList<>();
            }
        } catch (IOException e) {
            System.out.println(colorTexto("No se encontraron turnos guardados. Iniciando nueva lista.", "YELLOW"));
        }
    }

    // Método para guardar turnos en un archivo JSON (en formato vertical)
    private void guardarTurnos() {
        try (Writer writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(colaTurnos, writer);
        } catch (IOException e) {
            System.out.println(colorTexto("Error al guardar los turnos: " + e.getMessage(), "RED"));
        }
    }

    // Método para verificar si la cédula ya tiene un turno sin finalizar
    private boolean cedulaEnProceso(String cedula) {
        for (Turno turno : colaTurnos) {
            if (turno.getCedula().equalsIgnoreCase(cedula) && !turno.isAtendido()) {
                return true; // Turno pendiente encontrado
            }
        }
        return false;
    }

    // Método para registrar un turno con todos los detalles
    private void registrarTurno(String cedula, String tipoTurno, int numeroTurno, String categoria) {
        if (cedulaEnProceso(cedula)) {
            System.out.println(colorTexto("Error: Ya existe un turno pendiente asociado a esta cédula.", "RED"));
            return;
        }

        String id = generarIdCorto();
        String numeroTurnoStr = tipoTurno.substring(0, 3).toUpperCase() + "-" + numeroTurno;
        String fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Turno nuevoTurno = new Turno(id, cedula, tipoTurno, numeroTurnoStr, fechaHora);

        if (categoria.equals("INTERNO")) {
            colaTurnos.add(0, nuevoTurno); // Alta prioridad para internos
        } else {
            colaTurnos.add(nuevoTurno); // Se agrega al final
        }

        System.out.println(colorTexto("Turno asignado: " + nuevoTurno, "GREEN"));
        System.out.println(colorTexto("Tiempo estimado de atención: " + (colaTurnos.size() * 15) + " minutos.", "BLUE"));
        guardarTurnos();
    }

    // Método para eliminar un turno por cédula o número de turno
    private void eliminarTurno(String identificador) {
        boolean encontrado = colaTurnos.removeIf(turno -> turno.getCedula().equalsIgnoreCase(identificador) ||
                turno.getNumeroTurno().equalsIgnoreCase(identificador));
        if (encontrado) {
            System.out.println(colorTexto("Turno eliminado: " + identificador, "GREEN"));
        } else {
            System.out.println(colorTexto("No se encontró un turno con el identificador proporcionado.", "RED"));
        }
        guardarTurnos();
    }

    // Método para marcar un turno como atendido
    private void marcarTurnoAtendido(String identificador) {
        for (Turno turno : colaTurnos) {
            if (turno.getCedula().equalsIgnoreCase(identificador) ||
                    turno.getNumeroTurno().equalsIgnoreCase(identificador)) {
                turno.marcarAtendido();
                System.out.println(colorTexto("Turno marcado como atendido: " + turno, "CYAN"));
                guardarTurnos();
                return;
            }
        }
        System.out.println(colorTexto("No se encontró un turno con el identificador proporcionado.", "RED"));
    }

    // Método para calificar un turno
    private void calificarTurno(String identificador, int calificacion) {
        for (Turno turno : colaTurnos) {
            if (turno.getCedula().equalsIgnoreCase(identificador) ||
                    turno.getNumeroTurno().equalsIgnoreCase(identificador)) {
                turno.asignarCalificacion(calificacion);
                System.out.println(colorTexto("Turno calificado: " + turno, "GREEN"));
                guardarTurnos();
                return;
            }
        }
        System.out.println(colorTexto("No se encontró un turno con el identificador proporcionado.", "RED"));
    }

    // Método para mostrar los turnos pendientes (no atendidos)
    private void mostrarTurnosPendientes() {
        boolean hayTurnosPendientes = false;
        System.out.println(colorTexto("\n=== Reporte de Turnos Pendientes ===", "BLUE"));
        for (Turno turno : colaTurnos) {
            if (!turno.isAtendido()) {
                hayTurnosPendientes = true;
                System.out.println(turno + "\n");
            }
        }
        if (!hayTurnosPendientes) {
            System.out.println(colorTexto("No hay turnos pendientes.", "YELLOW"));
        }
    }

    // Generar un ID corto de 10 caracteres
    private String generarIdCorto() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    // Método para cambiar el color del texto
    private String colorTexto(String texto, String color) {
        switch (color.toUpperCase()) {
            case "RED":
                return "\u001B[31m" + texto + "\u001B[0m";
            case "GREEN":
                return "\u001B[32m" + texto + "\u001B[0m";
            case "YELLOW":
                return "\u001B[33m" + texto + "\u001B[0m";
            case "BLUE":
                return "\u001B[34m" + texto + "\u001B[0m";
            case "CYAN":
                return "\u001B[36m" + texto + "\u001B[0m";
            default:
                return texto;
        }
    }

    // Método principal para ejecutar el menú interactivo
    public void ejecutar() {
        cargarTurnos(); // Cargar turnos previos desde el archivo JSON
        Scanner scanner = new Scanner(System.in);
        int opcion;

        System.out.println(colorTexto("\n=== Bienvenido a la Universidad Compensar ===", "CYAN"));
        System.out.println(colorTexto("Sistema de Gestión de Turnos", "BLUE"));

        do {
            System.out.println(colorTexto("\nSeleccione una opción:", "YELLOW"));
            System.out.println("1. Asignar turno");
            System.out.println("2. Asignar turno colaboradores");
            System.out.println("3. Eliminar turno");
            System.out.println("4. Marcar turno como atendido");
            System.out.println("5. Calificar turno");
            System.out.println("6. Mostrar reporte de turnos pendientes");
            System.out.println("7. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese su cédula: ");
                    String cedula = scanner.nextLine();

                    System.out.println("Seleccione el tipo de turno:");
                    TIPOS_TURNO.forEach((codigo, descripcion) ->
                            System.out.println(codigo + ". " + descripcion));
                    System.out.print("Opción: ");
                    int tipoTurnoSeleccionado = scanner.nextInt();
                    scanner.nextLine();

                    registrarTurno(cedula, TIPOS_TURNO.get(tipoTurnoSeleccionado), colaTurnos.size() + 1, "EXTERNO");
                }
                case 2 -> {
                    System.out.print("Ingrese su cédula: ");
                    String cedula = scanner.nextLine();

                    System.out.println("Seleccione el tipo de turno colaboradores:");
                    TIPOS_TURNO_INTERNO.forEach((codigo, descripcion) ->
                            System.out.println(codigo + ". " + descripcion));
                    System.out.print("Opción: ");
                    int tipoTurnoSeleccionado = scanner.nextInt();
                    scanner.nextLine();

                    registrarTurno(cedula, TIPOS_TURNO_INTERNO.get(tipoTurnoSeleccionado), colaTurnos.size() + 1, "INTERNO");
                }
                case 3 -> {
                    System.out.print("Ingrese el número de cédula o el código del turno a eliminar: ");
                    String identificador = scanner.nextLine();
                    eliminarTurno(identificador);
                }
                case 4 -> {
                    System.out.print("Ingrese el número de cédula o el código del turno a marcar como atendido: ");
                    String identificador = scanner.nextLine();
                    marcarTurnoAtendido(identificador);
                }
                case 5 -> {
                    System.out.print("Ingrese el número de cédula o el código del turno a calificar: ");
                    String identificador = scanner.nextLine();
                    System.out.print("Ingrese la calificación (5 = Excelente, 3 = Bueno, 1 = Malo): ");
                    int calificacion = scanner.nextInt();
                    scanner.nextLine();
                    calificarTurno(identificador, calificacion);
                }
                case 6 -> mostrarTurnosPendientes();
                case 7 -> System.out.println(colorTexto("¡Gracias por usar el sistema! Hasta luego.", "GREEN"));
                default -> System.out.println(colorTexto("Opción inválida. Intente nuevamente.", "RED"));
            }
        } while (opcion != 7);
    }

    // Método main
    public static void main(String[] args) {
        ControlTurnos controlTurnos = new ControlTurnos();
        controlTurnos.ejecutar();
    }
}
// Compilar: javac -cp "lib/gson-2.8.9.jar" ControlTurnos.java
// Ejecutar: java -cp ".;lib/gson-2.8.9.jar" ControlTurnos
