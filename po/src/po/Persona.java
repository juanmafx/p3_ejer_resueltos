package po;
import java.util.*;
public class Persona {

private int nacimiento;
private String nombre;
private String apellido;

public void Persona(){
nombre = "";
apellido = "";
nacimiento = 0;
}


public void Persona( String n, String a,int p){
nombre = n;
apellido = n;
nacimiento = p;
}

public void setNombre(String l) { nombre = l; }
public void setApellido(String n) { apellido = n; }
public void setNacimiento(int p) { nacimiento = p; }

public String getNombre() { return nombre; }
public String getApellido() { return apellido; }
public int getNacimiento() { return nacimiento; }

public String nomCompleto(){
return  getNombre()+" " + " "+getApellido() +" "+(getNacimiento());
}



public int edad(){
Calendar cal= Calendar.getInstance();
int year= cal.get(Calendar.YEAR);
return year - nacimiento ;
}




}
    