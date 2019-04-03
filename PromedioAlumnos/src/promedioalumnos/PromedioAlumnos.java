package promedioalumnos;
import java.util.Scanner;
/**
 *
 * @author Admin
 */
public class PromedioAlumnos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Scanner sc= new Scanner(System.in);
       
        System.out.println("Ingresa cantidad de alumnos");
        int ca=sc.nextInt();
        double acuProm=0;
        double genProm=0;
       Alumno b= new Alumno();
       double mejorProm=b.promedio();
       int regulares=0;
       int libres=0;
       String mejorAlumno="";
       
        
        for (int i = 0; i < ca; i++) {
            Alumno a = new Alumno();
            System.out.println("Nombre del Alumno");
            a.setNombre(sc.next());
            System.out.println("Ingrese  nota 1");
            a.setN1(sc.nextInt());
            System.out.println("Ingrese  nota 2");
            a.setN2(sc.nextInt());
            System.out.println("Ingrese  nota 3");
            a.setN3(sc.nextInt());
            
            acuProm=acuProm + a.promedio();
            
            if(a.promedio() > mejorProm){
             mejorProm=a.promedio();
             mejorAlumno=a.toString();
             
            }
            
            if (a.regular()) {
                regulares++;
            }
            else{
                libres++;}}
        
        genProm=acuProm/ca;
        
        System.out.println("Promedio general: "+ genProm);
        System.out.println("Mejor promedio es de "+ mejorAlumno);
        System.out.println("Catindad de Regulares ="+ regulares);
        System.out.println("Catindad de LIBRES ="+ libres);
    }
    
}
