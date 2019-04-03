/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejer5_torrneo;
import java.util.*;
public class Ejer5_torrneo {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       Partiddo p = new Partiddo();
       Equipo a = new Equipo();
       Equipo b = new Equipo();
       
        System.out.println("Ingrese nombre del equipo LOCAL:");
        a.setEquipo(sc.next());
       
        System.out.println("Ingrese Cantidad de goles hechos por "+ a.getEquipo());
        a.setGolesHechos(sc.nextByte());
        
        System.out.println("Ingrese nombre del equipo VISITANTE:");
        b.setEquipo(sc.next());
        
        System.out.println("Ingrese Cantidad de goles hechos por "+ b.getEquipo());
        a.setGolesHechos(sc.nextByte());
        
        
        
        System.out.println("El resultado del partido fue: "+ p.ganador(a,b));
        
       
    }
    
}
