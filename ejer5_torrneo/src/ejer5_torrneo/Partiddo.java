/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejer5_torrneo;

/**
 *
 * @author Admin
 */
public class Partiddo  {

    Equipo a;
    Equipo b;
    String ganador="";
    
    public String ganador (Equipo a, Equipo b){
        
        if (a.golesHechos > b.golesHechos) {
            ganador=a.equipo;
        }
        
        if (a.golesHechos==b.golesHechos) {
            ganador="empate";
        }
        else ganador=a.getEquipo();
        
    return ganador;
    }


}
