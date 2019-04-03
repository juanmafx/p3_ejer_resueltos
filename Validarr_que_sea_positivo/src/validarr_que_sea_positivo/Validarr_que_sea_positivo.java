/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validarr_que_sea_positivo;

/**
 *
 * @author Admin
 */
import java.util.Scanner;
public class Validarr_que_sea_positivo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc= new Scanner(System.in);
        double num;
        do {
            System.out.println("Ingrese un numero:");
            num = sc.nextDouble();
        } while (num < 0);
        
        System.out.println(num+ " Es positivo");
    }
    
}
