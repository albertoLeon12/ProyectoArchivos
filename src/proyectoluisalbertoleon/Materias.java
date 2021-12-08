package proyectoluisalbertoleon;

import javax.swing.JOptionPane;

public class Materias {
    byte creditos;
    String clave = "";
    String nombre = "";
    
    public void altaMateria() {
        clave = JOptionPane.showInputDialog(null, "Ingrese Clave de Materia:");
        nombre = JOptionPane.showInputDialog(null, "Ingresar Nombre de Materia:");
        creditos = Byte.parseByte(JOptionPane.showInputDialog(null,"Ingrese Creditos de la materia"));
        
    }
}
