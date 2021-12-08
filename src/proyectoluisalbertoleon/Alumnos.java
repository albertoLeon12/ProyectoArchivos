package proyectoluisalbertoleon;

import javax.swing.JOptionPane;

public class Alumnos {  
    String nControl ="";
    String nombre = "";
    byte semestre;
    
    public void altaAlumnos() {
        nControl = JOptionPane.showInputDialog(null, "Nuevo numero de control:");
        nombre = JOptionPane.showInputDialog(null, "Ingrese Nombre del nuevo Alumno:");
        semestre = Byte.parseByte(JOptionPane.showInputDialog(null,"Ingrese Semestre:"));
        
    }

    public void setnControl(String nControl) {
        this.nControl = nControl;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSemestre(byte semestre) {
        this.semestre = semestre;
    }

    public String getnControl() {
        return nControl;
    }

    public String getNombre() {
        return nombre;
    }

    public byte getSemestre() {
        return semestre;
    }
    
    
    
}
