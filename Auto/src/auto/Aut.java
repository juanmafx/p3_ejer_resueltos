/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import java.time.Year;

public class Aut {
    String marca;
    int modelo;
    double precio;
    int km;
    
    
    public Aut(){
    marca="";
    modelo=0;
    precio=0;
    km=0;
    }
    
    public Aut(String m,int mo, double pre, int km){
    marca=m;
    modelo=mo;
    precio=pre;
    km=km;
    }
    
    public String toString(){
    String auto=marca+" " + modelo + " "+ precio+" " + km;
    return auto;
    }
    
    public int antiguedad (){
    int year = Year.now().getValue();
    int antiguedad = year - modelo;
    return antiguedad;
    }
    
    
    }
    

