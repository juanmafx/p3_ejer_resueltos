/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;
import java.time.Year;
import java.util.*;
/**
 *
 * @author Admin
 */
public class Auto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Aut a = new Aut();
        System.out.println("Ingrese Marca");
        a.marca=sc.next();
        System.out.println("Ingrese Modelo");
        a.modelo=sc.nextInt();
        System.out.println("Ingrese Km");
        a.km=sc.nextInt();
        
        
        System.out.println(a.toString());
        System.out.println("Tiene "+ a.antiguedad() + "años de antiguedad");
        

    }
    
}
