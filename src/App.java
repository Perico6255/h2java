
import database.PericoBD;
public class App {
    public static void main(String[] args) {
        PericoBD pericoBD = new PericoBD();
        pericoBD.createTables();
        pericoBD.crearCompeticion(1, "Juan");
        pericoBD.crearEvento(0, null, null, null, null, null, 1);
        pericoBD.leerCompeticion(1);
        pericoBD.leerClasificacion(1);
        pericoBD.leerEvento(0);
        pericoBD.cerrarConexion();
    }
}