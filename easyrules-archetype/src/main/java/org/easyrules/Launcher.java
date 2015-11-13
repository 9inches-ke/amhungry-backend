package org.easyrules;

import org.easyrules.api.RulesEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

/**
 * Launcher class of the Hello World sample.
 */
public class Launcher {

    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you a friend of duke? [yes/no]:");
        String input = scanner.nextLine();

        /**
         * Declare the rule
         */
        HelloWorldRule helloWorldRule = new HelloWorldRule();

        /**
         * Set business data to operate on
         */
        helloWorldRule.setInput(input.trim());

        /**
         * Create a rules engine and register the business rule
         */
        RulesEngine rulesEngine = aNewRulesEngine().build();

        rulesEngine.registerRule(helloWorldRule);

        /**
         * Fire rules
         */
        rulesEngine.fireRules();
        
        Connection c = null;
        Statement stmt = null;
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
          System.out.println("path");
          c.setAutoCommit(false);
          System.out.println("Opened database successfully");

          stmt = c.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");

          while ( rs.next() ) {
             String  name = rs.getString("name");
             System.out.println( "NAME = " + name );
             System.out.println();
          }    

          stmt = c.createStatement();
          ResultSet rs1 = stmt.executeQuery("SELECT * FROM restaurant_restaurant;");
          while ( rs1.next() ) {
            int id = rs1.getInt("id");
             String  name = rs1.getString("name");
             System.out.println( "ID = " + id );
             System.out.println( "NAME = " + name );
             System.out.println();
          }    c.close();

        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("Operation done successfully");

    }
}
