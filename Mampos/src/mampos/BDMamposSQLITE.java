package mampos;

public class BDMamposSQLITE {
    
    public void crearTabla(){
        String sql = "CREATE TABLE IF NOT EXISTS viga ("
                + " codigo varchar(13) PRIMARY KEY,"
                + " existencias int "
                + ");";
        SQLITE mysql = new SQLITE();
        mysql.ejecutarUpdate(sql);
    }
    
}
