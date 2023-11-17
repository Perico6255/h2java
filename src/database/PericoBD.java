package database;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PericoBD {

    private String url = "jdbc:h2:mem:perico";

    private String user = "sa";
    private String password = "password";
    private Connection connection;

    public PericoBD() {
        // Constructor: Abre la conexión al crear una instancia del DAO
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        // Método para obtener la conexión
        return connection;
    }

    public void createTables() {
        // Método para crear la tabla clasificacion
        try {
            String query = "CREATE TABLE competicion (id INT PRIMARY KEY, nombre VARCHAR(255))";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "CREATE TABLE evento (id INT PRIMARY KEY,fecha DATE,equipo_local VARCHAR(100),equipo_visitante VARCHAR(100),resultado_local INT,resultado_visitante INT,competicion_id INT,FOREIGN KEY (competicion_id) REFERENCES competicion(id));";
            statement.executeUpdate(query);
            query = "CREATE TABLE clasificacion (id INT PRIMARY KEY,nombre VARCHAR(100),puntos INT,partidos_jugados INT,goles_favor INT,goles_contra INT, competicion_id INT,FOREIGN KEY (competicion_id) REFERENCES competicion(id));";
            statement.executeUpdate(query);
            }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        // Método para cerrar la conexión cuando ya no se necesite
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //crear clasificacion
    public void crearCompeticion(int id, String nombre) {
        try {
            String query = "INSERT INTO competicion (id, nombre) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.setString(2, nombre);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearEvento(int id, Date fecha, String equipo_local, String equipo_visitante,String resultado_local, String resultado_visitante, int competicion_id){

        try {
            String query = "INSERT INTO evento (id, fecha, equipo_local, equipo_visitante, resultado_local, resultado_visitante, competicion_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.setDate(2,fecha);
                statement.setString(3,equipo_local );
                statement.setString(4, equipo_visitante);
                statement.setString(5, resultado_local );
                statement.setString(6, resultado_visitante );
                statement.setInt(7, competicion_id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearClasificacion(int id, String nombre, int puntos, int partidos_jugados,int goles_favor, int goles_contra, int competicion_id){

        try {
            String query = "INSERT INTO clasificacion (id, nombre, puntos, partidos_jugados, goles_favor, goles_contra, competicion_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.setString(2, nombre);
                statement.setInt(3, puntos);
                statement.setInt(4, partidos_jugados);
                statement.setInt(5, goles_favor);
                statement.setInt(6, goles_contra);
                statement.setInt(7, competicion_id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // leer competicion
     public void leerCompeticion(int id) {
        try {
            // Preparar la consulta SQL
            String query = "SELECT * FROM competicion WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Establecer el valor del parámetro en la consulta
                preparedStatement.setInt(1, id);

                // Ejecutar la consulta
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Verificar si se encontraron resultados
                    if (resultSet.next()) {
                        // Acceder a los datos de la fila
                        int retrievedId = resultSet.getInt("id");
                        String retrievedNombre = resultSet.getString("nombre");


                        //imprimir
                        System.out.println("ID: " + retrievedId);
                        System.out.println("Nombre: " + retrievedNombre);
                        // ... Imprimir otros campos según sea necesario
                    } else {
                        System.out.println("No se encontraron datos para el ID proporcionado.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // leer clasificacion
     public void leerClasificacion(int id) {
        try {
            // Preparar la consulta SQL
            String query = "SELECT * FROM clasificacion WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Establecer el valor del parámetro en la consulta
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Acceder a los datos de la fila
                        int retrievedId = resultSet.getInt("id");
                        Date retrievedFecha = resultSet.getDate("fecha");
                        String retrievedEquipo_local = resultSet.getString("equipo_local");
                        String retrievedEquipo_visitate = resultSet.getString("equipo_visitante");
                        String retrievedResultado_local = resultSet.getString("resultado_local");
                        String retrievedResultado_visitate = resultSet.getString("resultado_visitante");
                        String retrievedCompeticion_id = resultSet.getString("competicion_id");

                        System.out.println("ID: " + retrievedId);
                        System.out.println("Fecha: " + retrievedFecha);
                        System.out.println("Equipo local: " + retrievedEquipo_local);
                        System.err.println("Equipo visitante: " + retrievedEquipo_visitate);
                        System.out.println("Resultado local: " + retrievedResultado_local);
                        System.out.println("Resultado visitante: " + retrievedResultado_visitate);
                        System.out.println("Competicion id: " + retrievedCompeticion_id);

                    } else {
                        System.out.println("No se encontraron datos para el ID proporcionado.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // leer evento
     public void leerEvento(int id) {
        try {
            // Preparar la consulta SQL
            String query = "SELECT * FROM evento WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Establecer el valor del parámetro en la consulta
                preparedStatement.setInt(1, id);

                // Ejecutar la consulta
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Verificar si se encontraron resultados
                    if (resultSet.next()) {
                        // Acceder a los datos de la fila
                        int retrievedId = resultSet.getInt("id");
                        Date retrievedFecha = resultSet.getDate("fecha");
                        String retrievedEquipo_local = resultSet.getString("equipo_local");
                        String retrievedEquipo_visitate = resultSet.getString("equipo_visitante");
                        String retrievedResultado_local = resultSet.getString("resultado_local");
                        String retrievedResultado_visitate = resultSet.getString("resultado_visitante");
                        String retrievedCompeticion_id = resultSet.getString("competicion_id");
                        // ... Acceder a otros campos según sea necesario

                        // Hacer algo con los datos obtenidos (por ejemplo, imprimirlos)
                        System.out.println("ID: " + retrievedId);
                        System.out.println("Fecha: " + retrievedFecha);
                        System.out.println("Equipo local: " + retrievedEquipo_local);
                        System.err.println("Equipo visitante: " + retrievedEquipo_visitate);
                        System.out.println("Resultado local: " + retrievedResultado_local);
                        System.out.println("Resultado visitante: " + retrievedResultado_visitate);
                        System.out.println("Competicion id: " + retrievedCompeticion_id);

                    } else {
                        System.out.println("No se encontraron datos para el ID proporcionado.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
