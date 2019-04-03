/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package po;
import java.util.Scanner;
public class Po {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Persona p =  new Persona();
       
       p.setNombre( "juan");
       p.setApellido( "Ramall0");
       p.setNacimiento(1989);
    System.out.println(p.nomCompleto());
    
    System.out.println("Edad :"+p.edad());
       
    }
    
}
