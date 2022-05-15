package prog.jun.prog_jun_2021.bbdd;

import java.sql.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import prog.jun.prog_jun_2021.ModelTable;
import prog.jun.prog_jun_2021.utils.Database;

/**
 * Almacena los métodos relacionados con la BBDD
 * @author Adrián Gutiérrez
 */
public class ConnectionDB {


    private static Connection connection = null;
    /**
     * Genera la conexión a la base de datos
     * @return Connection
     */
    public static Connection openConnection(){
        try{
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/entradas",
                    "root",
                    "");
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        return connection;
    }


    /**
     * Cierra la conexión a la base de datos
     */
    public static void closeConnection(){
        try{
            if(connection != null) connection.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    /**
     * Contiene las operaciones con la BBDD
     */
    public static class Operations{

        /**
         * Obtiene los clientes de la BBDD
         * @return Clientes
         */
        public static ArrayList<String> getClientes() {
            ArrayList<String> result = new ArrayList<>();
            try {

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT nombre FROM clientes");

                // Guardamos los resultados en el ArrayList
                while (resultSet.next()) {
                    String info = resultSet.getString(1);
                    result.add(info);
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

            return result;
        }

        /**
         * Obtiene los espectáculos de la BBDD
         * @return Espectáculos
         */
        public static ArrayList<String> getPlays(){
            ArrayList<String> result = new ArrayList<>();
            try {

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT obra FROM espectaculos");

                // Guardamos los resultados en el ArrayList
                while (resultSet.next()) {
                    String info = resultSet.getString(1);
                    result.add(info);
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

            return result;
        }

        /**
         * Inserta una nueva entrada en la BBDD
         * @param play Nombre del espectáculo
         * @param client Nombre del cliente
         * @param date Fecha de venta
         * @param price Precio de venta
         */
        public static void insertTicket(String play, String client, Date date, Double price){

            try{
                String sql = "INSERT INTO entrada (obra, nif, fecha, precio) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, Database.getCodeFromPlay(connection, play));
                preparedStatement.setString(2, Database.getNifFromClient(connection, client));
                preparedStatement.setDate(3, date);
                preparedStatement.setDouble(4, price);
                preparedStatement.executeUpdate();

            }catch(SQLException sqle){
                sqle.printStackTrace();
            }

        }


        /**
         * Genera una lista con los objetos para la tabla de entradas vendidas
         * @param play Nombre del espectáculo
         * @param startDate Fecha de inicio
         * @param endDate Fecha de finalización
         * @return Objeto con la colección de objetos para el TableView
         */
        public static ObservableList<ModelTable> getTickets(String play, Date startDate, Date endDate){
            ObservableList<ModelTable> objList = FXCollections.observableArrayList();
            try {
                String sql = "SELECT c.nif, SUBSTRING_INDEX(c.nombre, ' ', 1) AS name, SUBSTRING_INDEX(c.nombre, ' ', -2) as surname, e.fecha as date, e.precio as price FROM clientes c INNER JOIN entrada e ON c.nif = e.nif AND (e.fecha BETWEEN ? AND ?) AND e.obra = ? ORDER BY date ASC";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDate(1, startDate);
                preparedStatement.setDate(2, endDate);
                preparedStatement.setInt(3, Database.getCodeFromPlay(connection, play));
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    objList.add(
                            new ModelTable(
                                    resultSet.getString("nif"),
                                    resultSet.getString("name"),
                                    resultSet.getString("surname"),
                                    resultSet.getString("date"),
                                    resultSet.getString("price")
                            ));
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

            return objList;
        }


    }


}