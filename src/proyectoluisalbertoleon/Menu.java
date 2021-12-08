package proyectoluisalbertoleon;

import javax.swing.JOptionPane;

public class Menu {
    
    Archivos ar = new Archivos();
    int menu,menu2,menu3,menu4;
    String resp = null;
    
    public void menus(){ //opcm1
        switch(menu){
            case 1:
                menuAlumnos();
                break;
            case 2:
                menuMaterias();
                break;
            case 3:
                menuInscripcciones();
                break;
            case 4:
                menu = 4;
                JOptionPane.showMessageDialog(null, "SS HA FINALIZADO EL PRGAMA.");
                break;
            default:
                JOptionPane.showMessageDialog(null,"ERROr.\n" +"Por favor Selecione una opcion correcta");
                menuPrincipal();
                break;
        }
    }
   
    
    public void menuPrincipal (){ //Menu
        String[] arr = {"Alumnos","Materias","Inscripciones","Salir"}; 
        String str ="Ingrese Una Opcion\n\n";
        for(int i =0;i<arr.length; i++){
            str+=(i+1)+")"+arr[i]+".\n";
        }
        
        menu = Integer.parseInt(JOptionPane.showInputDialog(null,str));
            menus();
    }
    
    
    public void menuAlumnos (){  //Menu2
        
        String[] arr = {"Ingresar Alumno Nuevo","Consultar Datos", "Hacer Reporte", "Volver"};
        String str = "Ingrese Una Opcion\n\n";
        for(int i =0; i<arr.length;i++){
            str+=(i+1)+")"+arr[i]+".\n";
        }
        menu2 = Integer.parseInt(JOptionPane.showInputDialog(null,str));
            menuAlumnosOpc();
    }
    
    //Menu materias
    public void menuMaterias (){ //Menu3
        String[] arr = {"Ingresar nueva Materia","Consultar Datos","Hacer Reporte","Volver"};
        String str = "Ingrese Una Opcion: \n\n";
        for(int i = 0; i<arr.length; i++) {
            str += (i+1)+")"+arr[i]+".\n";
        }
        menu3 = Integer.parseInt(JOptionPane.showInputDialog(null,str));
        menuMateriaOpc();
    }
    
    //menu inscripcciones
    public void menuInscripcciones (){ //Menu4
        String[] arr = {"Inscribirse","Reportes de Inscripcion","Volver"};
        String str = "Ingrese Una Opcion:\n\n";
        for(int i = 0; i<arr.length; i++) {
            str += (i+1)+")"+arr[i]+".\n";
        }
        menu4 = Integer.parseInt(JOptionPane.showInputDialog(null,str));
        menuInscripcionOpc();
    }
    
    
    //Menu alumnos opciones
    public void menuAlumnosOpc(){ //opc_m2
        switch(menu2){
            case 1:
                ar.escrirAlta();
                menuAlumnos();
                break;
            case 2:
                ar.modificarAm();
                menuAlumnos();
                break;
            case 3:
                ar.ordenarAlu();
                ar.reporteAlu();
                menuAlumnos();
                break;
            case 4:
                menuPrincipal();
                break;
            default:
                JOptionPane.showMessageDialog(null,"ERROr.\n" +"Por favor Selecione una opcion correcta");
                menuAlumnos();
                break;
        }
    }
    
    //Menu materias opciones
    public void menuMateriaOpc(){ //opc_m3
        switch(menu3){
            case 1:
                ar.EscribirMlT();
                menuMaterias();
                break;
            case 2:
                ar.buscarM();
                menuMaterias();
                break;
            case 3:
                ar.OrdenarM();
                ar.ReporteM();
                menuMaterias();
                break;
            case 4:
                menuPrincipal();
                break;
            default:
                JOptionPane.showMessageDialog(null,"ERROr.\n" +"Por favor Selecione una opcion correcta");
                menuMaterias();
                break;
        }   
    }
    
    //Menu inscripciones opciones
    public void menuInscripcionOpc() {  //opcm4
        switch(menu4){
            case 1:
                ar.incripciones();
                menuInscripcciones();
                break;
            case 2:
                menuInscripcciones();
                break;
            case 3:
                menuPrincipal();
                break;
            default:
                JOptionPane.showMessageDialog(null,"ERROr.\n" +"Por favor Selecione una opcion correcta");
                menuInscripcciones();
                break;
        }
    }
    
}

