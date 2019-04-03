/*
. Ingresar un año e indicar si ese año es bisiesto. Un año es bisiesto si es
múltiplo de 4 y no es múltiplo de 100 o si es múltiplo de 400.
 */
package bisiesto_o_no;
import java.util.Scanner;
public class Bisiesto_o_no {

    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       int year;
       System.out.println("Ingrese el año para saber si es o no Bisisesto: ");
       
        do {
                   year= sc.nextInt();
       
        if (((year%4==0) && !(100%year==0))||(400%year==0)) {
            System.out.println("El año "+ year + " es bisiesto ");
        }
        else{
            System.out.println("El año "+ year + " no es bisiesto ");
        }
        } while( ! (year==1));
        

        
    }
    
}
