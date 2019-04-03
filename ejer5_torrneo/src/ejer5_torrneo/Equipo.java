package ejer5_torrneo;
/*

*/
public class Equipo {
    public String equipo;
    public int puntos;
    public int pganados;
    public int pempatados;
    public int perdidos;
    public int golesHechos;
    public int golesRecibidos;
    public int posiscion;

    @Override
    public String toString() {
        return "Equipo{" + "equipo=" + equipo + ", puntos=" + puntos + ", pganados=" + pganados + ", pempatados=" + pempatados + ", perdidos=" + perdidos + ", golesHechos=" + golesHechos + ", golesRecibidos=" + golesRecibidos + ", posiscion=" + posiscion + '}';
    }

    
    
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setPganados(int pganados) {
        this.pganados = pganados;
    }

    public void setPempatados(int pempatados) {
        this.pempatados = pempatados;
    }

    public void setPerdidos(int perdidos) {
        this.perdidos = perdidos;
    }

    public void setGolesHechos(int golesHechos) {
        this.golesHechos = golesHechos;
    }

    public void setGolesRecibidos(int golesRecibidos) {
        this.golesRecibidos = golesRecibidos;
    }

    public void setPosiscion(int posiscion) {
        this.posiscion = posiscion;
    }

    
    
    public String getEquipo() {
        return equipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getPganados() {
        return pganados;
    }

    public int getPempatados() {
        return pempatados;
    }

    public int getPerdidos() {
        return perdidos;
    }

    public int getGolesHechos() {
        return golesHechos;
    }

    public int getGolesRecibidos() {
        return golesRecibidos;
    }

    public int getPosiscion() {
        return posiscion;
    }

    public Equipo(String equipo, int puntos, int pganados, int pempatados, int perdidos, int golesHechos, int golesRecibidos, int posiscion) {
        this.equipo = equipo;
        this.puntos = puntos;
        this.pganados = pganados;
        this.pempatados = pempatados;
        this.perdidos = perdidos;
        this.golesHechos = golesHechos;
        this.golesRecibidos = golesRecibidos;
        this.posiscion = posiscion;
    }
    
        public Equipo() {
        this.equipo = "";
        this.puntos = 0;
        this.pganados = 0;
        this.pempatados =0;
        this.perdidos = 0;
        this.golesHechos = 0;
        this.golesRecibidos = 0;
        this.posiscion = 0;
    }
    
}
