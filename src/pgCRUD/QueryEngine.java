package pgCRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class QueryEngine
{
    public ArrayList<HashMap<String,?>> result = new ArrayList<>();
    private SQLConnection conn;
    public QueryEngine(SQLConnection connection){
        this.conn = connection;
    }

    public ArrayList<HashMap<String,?>> QueryFromString(String query) {
        try {
            Statement statement = conn.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();

            int columns = md.getColumnCount();
            
            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<String, Object>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                result.add(row); 
            }
            statement.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return this.result;
    }

    // Select method, all overflowing posibilities
    public ArrayList<HashMap<String,?>> Select(String table){
        return this.Select(table, "*");
        
    }
    public ArrayList<HashMap<String,?>> Select(String table, String column) {
        try {
            String query = "SELECT " + column + " FROM \"" + table + "\";";
            this.result = this.QueryFromString(query);
        } catch (Exception e) {
            System.out.println(e);
        }
        return this.result;
    }
    public ArrayList<HashMap<String,?>> Select(String table, String column, String filter) {
        try {
            String query = "SELECT " + column + " FROM \"" + table + "\" WHERE " + filter + ";";
            this.result = this.QueryFromString(query);
        } catch (Exception e) {
            System.out.println(e);
        }
        return this.result;
    }
    public ArrayList<HashMap<String,?>> SelectTop(String table, int top) {
        return this.SelectTop(table, "*", top);
        
    }
    public ArrayList<HashMap<String,?>> SelectTop(String table, String column, int top) {
        try {
            String query = "SELECT " + column + " FROM \"" + table + "\" LIMIT " + top + ";";
            this.result = this.QueryFromString(query);
        } catch (Exception e) {
            System.out.println(e);
        }
        return this.result;
    }
    public void Insert(String table, String[] columns, String[] values) {
        if (columns.length != values.length) {
            throw new IllegalArgumentException("Columns and values must be the same length");
        }
        try {
            String query = "INSERT INTO \"" + table + "\" (";
            for (String column : columns) {
                query += "\"" + column + "\", ";
            }
            query = query.substring(0, query.length() - 2);
            query += ") VALUES (";
            for (String value : values) {
                query += "'" + value + "', ";
            }
            query = query.substring(0, query.length() - 2);
            query += ");";
            this.QueryFromString(query);
        } catch (Exception e) {
            System.out.println(e);
            
        }
    }
    public void Update(String table, String[] columns, String[] values, String filter) {
        if (columns.length != values.length) {
            throw new IllegalArgumentException("Columns and values must be the same length");
        }
        try {
            String query = "UPDATE \"" + table + "\" SET ";
            for (int i = 0; i < columns.length; i++) {
                query += "\"" + columns[i] + "\" = '" + values[i] + "', ";
            }
            query = query.substring(0, query.length() - 2);
            query += " WHERE " + filter + ";";
            this.QueryFromString(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void Delete(String table, String filter) {
        try {
            String query = "DELETE FROM \"" + table + "\" WHERE " + filter + ";";
            this.QueryFromString(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void GetColumns(String table) {
        String query = "SELECT * FROM \"" + table + "\" LIMIT 1;";
        try {
            Statement statement = conn.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();

            int columns = md.getColumnCount();



            String insertInstructions = table + ":\n";
            for (int i = 1; i <= columns; ++i) {
                insertInstructions += "" + md.getColumnName(i) + " <" + md.getColumnTypeName(i) + ">";
                if (md.getColumnTypeName(i) == "serial") {
                    insertInstructions += " HAS DEFAULT";
                }
                insertInstructions += "\n";

            }
            System.out.println(insertInstructions);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void PrintResult() {
        for (HashMap<String, ?> row : this.result) {
            System.out.println(row);
        }
    }
}
