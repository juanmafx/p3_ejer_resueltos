/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package promedioalumnos;

/**
 *
 * @author Admin
 */
public class Alumno {
    
   private String nombre;

    public Alumno(String nombre, int n1, int n2, int n3) {
        this.nombre = nombre;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }
    
        public Alumno() {
        this.nombre ="";
        this.n1 = 0;
        this.n2 = 0;
        this.n3 = 0;
    }
   private int n1;
   private int n2;
   private int n3;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getN3() {
        return n3;
    }

    public void setN3(int n3) {
        this.n3 = n3;
    }

    public String toString() {
        return "Alumno{" + "nombre=" + nombre + ", n1=" + n1 + ", n2=" + n2 + ", n3=" + n3 + '}';
    }
    
    
    public double promedio(){
        double promedio = (this.n1+this.n2+this.n3)/3;
        return promedio;
}
    
    public boolean regular(){
    if(this.promedio()>=4)
           return true;
    else 
           return false;
    }

   
    
}
