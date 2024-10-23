package mampos;

import java.sql.*;


public class SQLITE {
    
    private java.sql.Connection conexion = null;
    
    public SQLITE(){
        try{
            Class.forName("org.sqlite.JDBC");
    
        }catch(ClassNotFoundException ex){
            throw new ClassCastException(ex.getMessage());
        }
    }
    
    public Connection abrir(){
        try{
            if(conexion == null || conexion.isClosed()){
                String url = "jdbc:sqlite:mampos.db";            
                conexion = DriverManager.getConnection(url);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return conexion;
    }
    
    public void cerrar() {
        if(conexion != null){
            try{
                conexion.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    
    //DELETE UPDATE INSERT
    public boolean ejecutarUpdate(String querySQL){
        try{
            abrir();
            Statement r = conexion.createStatement();
            if(r.executeUpdate(querySQL) != 0){
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            cerrar();
        }
        return false;
    }
    
    public ResultSet ejecutarQuery(String querySQL){
        try{
            abrir();
            Statement r = conexion.createStatement();
            return r.executeQuery(querySQL);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
}
