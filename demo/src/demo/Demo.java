/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

/**
 *
 * @author Admin
 */
public class Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int i=0;
for (i=1; i<5; i++) {
System.out.println("antes "+ i);
if (i==2) continue;
if (i==3) break;
System.out.println("después "+i);

    }
    
}
}
