package prog.jun.prog_jun_2021.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contiene utilidades para la BBDD
 * @author Adrián Gutiérrez
 */
public class Database {

    /**
     * Obtiene el NIF de un cliente por su nombre
     * @param connection Conexión de la BBDD
     * @param name Nombre del cliente
     * @return NIF del cliente
     */
    public static String getNifFromClient(Connection connection, String name){
        String nif = "";
        try{
            String sql = "SELECT nif FROM clientes WHERE nombre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) nif = resultSet.getString(1);
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return nif;
    }

    /**
     * Obtiene el código de espectáculo por su nombre
     * @param connection Conexión de la BBDD
     * @param name Nombre del espectáculo
     * @return Código del espectáculo
     */
    public static int getCodeFromPlay(Connection connection, String name){
        int code = 0;
        try{
            String sql = "SELECT codigo FROM espectaculos WHERE obra = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) code = resultSet.getInt(1);
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return code;
    }

}
