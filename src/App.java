import pgCRUD.*;

public class App {
    public static void main(String[] args) throws Exception {
        SQLConnection conn = new SQLConnection("Militar", "postgres", "123456789");
        
        QueryEngine queryEngine = new QueryEngine(conn);

        queryEngine.GetColumns("soldado");

        queryEngine.Select("soldado","*", "id_soldado_cuerpo=10");

        queryEngine.PrintResult();

    }
}
