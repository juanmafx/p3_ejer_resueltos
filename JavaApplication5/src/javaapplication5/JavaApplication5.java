/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

/**
 *
 * @author Admin
 */
public class JavaApplication5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
             

int i=0;
for (i=1; i<5; i++) {
    System.out.println("antes "+i);
    if (i==2) continue;
    if (i==3) break;
    System.out.println("después "+i);
}

}

}
