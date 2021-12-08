package proyectoluisalbertoleon;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Archivos {
    
    Alumnos al = new Alumnos(); //obAl
    Materias mat = new Materias();//obMa
    RandomAccessFile archivo;
    File reportAlu = new File("ReporteAlumnos.dat"); //FileRAl
    File reportMat = new File("ReporteMaterias.dat"); //FileRM
    File reportIns = new File("ReporteInscripciones.dat"); //FileRI
    FileWriter escribir;
    PrintWriter linea;
    int registro;
    int nReg;
    int tmRegA = 53;
    int tmRegM = 44;
    int tmRegI = 16;
    String resp = "respuesta";
    String buscar;
    String rutaAlu = "Alumnos.dat";
    String rutaMat = "Materias.dat";
    String rutaIns = "Inscripciones.dat";
    boolean f;
    
    //Abrir archivo binario
    public void abrirAlu () {
        try {
            archivo = new RandomAccessFile( rutaAlu, "rw" );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    public void abriAlu () {
        try {
            archivo = new RandomAccessFile( rutaAlu, "r" );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //registrar datos en archivo binario
    public void escrirAlta(){
        try {
            abrirAlu();
            do{
                try {
                    archivo.seek(archivo.length());
                    al.altaAlumnos();
                    archivo.writeUTF(al.nControl);
                    archivo.writeUTF(al.nombre);
                    archivo.writeByte(al.semestre);
                } catch (IOException ex) {
                    Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
                }
                resp = JOptionPane.showInputDialog(null,"¿Desea ingresar otro Alumno? \t (s/n)\n");
            }while(resp.equals("s"));
            JOptionPane.showMessageDialog(null,"Se han agrgado los datos correctamente al archivo "+rutaAlu);
            archivo.close();
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ordenarAlu(){
        try {
            abrirAlu();
            Alumnos alum = new Alumnos(); //obA
            Alumnos alumAux = new Alumnos();//obAaux
            int nRegi = (int) (archivo.length()/tmRegA);
            for(int i = 1;i< nRegi;i++){
                for(int com = 1;com <= (nRegi-i);com++){
                    alum = LeerA(archivo,com-1);
                    alumAux = LeerA(archivo,com);
                    if(alum.nControl.compareToIgnoreCase(alumAux.nControl) > 0){
                        EscribirA2(archivo,alumAux,com-1);
                        EscribirA2(archivo,alum,com);
                    }
                    archivo.seek(com*tmRegA);
                }
            }////
            archivo.close();
            JOptionPane.showMessageDialog(null,
                    "Se han ordenado los datos del archivo "
                            +rutaAlu
                            +" correctamente.",
                    "Ordenamiento de Alumnos.",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error: "+ex,
                    "Ordenamiento de Alumnos.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //escribe los datos en el archivo bin 
    public void EscribirA2(RandomAccessFile archivo,Alumnos ObA,int reg){
        try {
            archivo.seek(reg*tmRegA);
            archivo.writeUTF(ObA.nControl);
            archivo.writeUTF(ObA.nombre);
            archivo.writeByte(ObA.semestre);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error: "+ex,
                    "EscribirA2.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //leer datos del archivo
    public Alumnos LeerA(RandomAccessFile archivo, int reg){
        Alumnos ObA = new Alumnos();
        try {
            archivo.seek(reg*tmRegA);
            ObA.nControl = archivo.readUTF();
            ObA.nombre = archivo.readUTF();
            ObA.semestre = archivo.readByte();
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ObA;
    }
    
    //buscar un dato en el archivo bin
    public int buscarA()  { 
        int pm;
        String buscar;
        Alumnos ObA = new Alumnos();
        try {
            abriAlu();
            buscar = JOptionPane.showInputDialog(null,
                    "Ingresa el numero de control del Alumno.\n",
                    "Buscando Alumno",
                    JOptionPane.QUESTION_MESSAGE);
            buscar = String.format("%-8s",buscar);
            int Li = 0, Ls = (int) (archivo.length()/tmRegA-1);
            do{
                pm = (Li+Ls)/2;
                archivo.seek(pm*tmRegA);
                ObA = LeerA(archivo,pm);
                if(ObA.nControl.compareTo(buscar)>0)
                    Ls=pm-1;
                else
                    Li= pm+1;
            }while(!ObA.nControl.equals(buscar) && Li<=Ls);
            if(ObA.nControl.equals(buscar)){
                //archivo.seek(pm*TmRegA);
                JOptionPane.showMessageDialog(null,
                        "Se ha encontrado al Alumno."
                                +"\nN.Control: "+ObA.nControl
                                +"\nNombre: "+ObA.nombre
                                +"\nSemestre: "+ObA.semestre,
                        "Buscando Alumno.",
                        JOptionPane.INFORMATION_MESSAGE);
                registro = pm*tmRegA;
                al.setnControl(ObA.nControl);
                al.setNombre(ObA.nombre);
                al.setSemestre(ObA.semestre);
                f=true;
            }else{
                JOptionPane.showMessageDialog(null,
                        "No se ha encontrado el Alumno."
                                +"\nDatos no existentes.",
                        "Buscando Alumno.",
                        JOptionPane.ERROR_MESSAGE);
                f=false;
            }   
            archivo.close();
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registro;
    }
    public void modificarAm(){
        buscarA();
        if(f==true){
            int modificar = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Se ha encontrado al Alumno.\n"
                        +"¿Que deseas modificar?"
                        +"\n1) N.Control: "+al.getnControl()
                        +"\n2) Nombre: "+al.getNombre()
                        +"\n3) Semestre: "+al.getSemestre()
                        +"\n4) salir (No deseo modificar).",
                "Modificando datos del Alumno.",
                JOptionPane.INFORMATION_MESSAGE));
            modificarAo(modificar);
        }
    }

    public void modificarAo(int modificar){
         Alumnos ObA = new Alumnos();
         abrirAlu();
         switch(modificar){
             case 1:
                 ObA.nControl = JOptionPane.showInputDialog(null,
                         "Ingresa el nuevo numero de control del Alumno.\n",
                         "Modificando datos del Alumno.",
                         JOptionPane.INFORMATION_MESSAGE);
                 ObA.nControl = String.format("%-8s",ObA.nControl);
                 al.setnControl(ObA.nControl);
                 EscribirA2(archivo, ObA, nReg);
                 break;
             case 2:
                 ObA.nombre = JOptionPane.showInputDialog(null,
                         "Ingresa el nuevo nombre del Alumno.\n",
                         "Modificando datos del Alumno.",
                         JOptionPane.INFORMATION_MESSAGE);
                 ObA.nombre = String.format("%-40s",ObA.nombre);
                 break;
             case 3:
                 ObA.semestre = Byte.parseByte(JOptionPane.showInputDialog(null,
                         "Ingresa el nuevo semestre del Alumno.\n",
                         "Modificando datos del Alumno.",
                         JOptionPane.INFORMATION_MESSAGE));
                 break;
             case 4:
                 modificar = 4;
                JOptionPane.showMessageDialog(null,
                        "No se a modificado ningun registro.",
                        "Modificando datos del Alumno.",
                        JOptionPane.INFORMATION_MESSAGE);
                 break;
             default:
                 JOptionPane.showMessageDialog(null,
                        "No selecciono ninguna opcion.\n" +
                                "Porfavor selecione su opcion con un numero del 1-4",
                        "ERROR!",
                        JOptionPane.ERROR_MESSAGE);
                 break;
         }
     }
     
    public void EscribirRA(PrintWriter linea,Alumnos ObA) {
        String s1;s1 = String.format("%-8s \t %-40s \t ",ObA.nControl,ObA.nombre,ObA.semestre);
        linea.println(s1);
    }
    
    //Se realiza el reporte
    public void ReporteAl(){
        Alumnos ObA = new Alumnos();
        abrirAlu();
        if (!reportAlu.exists()){
            try {
                reportAlu.createNewFile();
                nReg = (int) (archivo.length()/tmRegA);
                escribir = new FileWriter(reportAlu);
                linea = new PrintWriter(escribir);
                linea.println("\t\t\t\tREPORTE ALUMNOS.\n");
                linea.println("\t\tInstituto Tecnologico Nacional de Mexico en Celaya.\n");
                linea.println("-----------------------------------------------------------------------------------\n");
                linea.println("N.Control. \t Nombre.                                  \t Semestr.\n");
                linea.println("-----------------------------------------------------------------------------------\n");
                for(int i=0;i<nReg;i++){
                    ObA = LeerA(archivo,i);
                    EscribirRA(linea, ObA);
                }
                linea.println("-----------------------------------------------------------------------------------\n");
                linea.println("\n");
                linea.close();
                escribir.close();
                archivo.close();
                JOptionPane.showMessageDialog(null,
                        "Se ha realizado el reporte de los alumnos correctamente.",
                        "Reporte de Alumnos.",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                nReg = (int) (archivo.length()/tmRegA);
                escribir = new FileWriter(reportAlu);
                linea = new PrintWriter(escribir);
                linea.println("\t\t\t\tREPORTE ALUMNOS.\n");
                linea.println("\t\tInstituto Tecnologico Nacional de Mexico en Celaya.\n");
                linea.println("-----------------------------------------------------------------------------------\n");
                linea.println("N.Control. \t Nombre.                                  \t Semestr.\n");
                linea.println("-----------------------------------------------------------------------------------\n");
                for(int i=0;i<nReg;i++){
                    ObA = LeerA(archivo,i);
                    EscribirRA(linea, ObA);
                }
                linea.println("-----------------------------------------------------------------------------------\n");
                linea.println("\n");
                linea.close();
                escribir.close();
                archivo.close();
                JOptionPane.showMessageDialog(null,
                        "Se ha realizado el reporte de los alumnos correctamente.",
                        "Reporte de Alumnos.",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
//////////////////////////////////////////////////////////////////////////////////////////////////////
    
//Abrir archivo binario
    public void abrirl_eM () {
        try {
            archivo = new RandomAccessFile( rutaMat, "rw" );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
//Abrir archivo binario
    public void abrirLM () {
        try {
            archivo = new RandomAccessFile( rutaMat, "r" );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//registrar datos en archivo binario
    public void EscribirMlT(){
        try {
            abrirl_eM();
            do{
                try {
                    archivo.seek(archivo.length());
                    mat.altaMateria();
                    archivo.writeUTF(mat.clave);
                    archivo.writeUTF(mat.nombre);
                    archivo.writeByte(mat.creditos);
                } catch (IOException ex) {
                    Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
                }
                resp = JOptionPane.showInputDialog(null,
                        "¿Desea ingresar otro Alumno? \t (si/no)\n",
                        "Ingresando Alumno.",
                        JOptionPane.INFORMATION_MESSAGE);
            }while(resp.equals("si"));
            JOptionPane.showMessageDialog(null,
                    "Se han agrgado los datos correctamente al archivo "
                            +rutaMat,
                    "Ingresando Materias.",
                    JOptionPane.INFORMATION_MESSAGE);
            archivo.close();
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//Ordenar archivo binario    
    public void OrdenarM(){
        try {
            abrirl_eM();
            Materias ObM = new Materias();
            Materias ObMaux = new Materias();
            nReg = (int) (archivo.length()/tmRegM);
            for(int pass = 1;pass < nReg;pass++){
                for(int com = 1;com <= (nReg - pass);com++){
                    ObM = LeerM(archivo,com-1);
                    ObMaux = LeerM(archivo,com);
                    if(ObM.clave.compareToIgnoreCase(ObMaux.clave) > 0){
                        EscribirM2(archivo,ObMaux,com-1);
                        EscribirM2(archivo,ObM,com);
                    }
                    archivo.seek(com*tmRegM);
                }
            }
            archivo.close();
            JOptionPane.showMessageDialog(null,
                    "Se han ordenado los datos del archivo "
                            +rutaMat
                            +" correctamente.",
                    "Ordenamiento de Materias.",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error: "+ex,
                    "Ordenamiento de Materias.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
//escribe los datos en el archivo bin 
    public void EscribirM2(RandomAccessFile archivo,Materias ObM,int reg){
        try {
            archivo.seek(reg*tmRegM);
            archivo.writeUTF(ObM.clave);
            archivo.writeUTF(ObM.nombre);
            archivo.writeByte(ObM.creditos);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error: "+ex,
                    "EscribirM2.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
//leer datos del archivo
    public Materias LeerM(RandomAccessFile archivo, int reg){
        Materias ObM = new Materias();
        try {
            archivo.seek(reg*tmRegM);
            ObM.clave = archivo.readUTF();
            ObM.nombre = archivo.readUTF();
            ObM.creditos = archivo.readByte();
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ObM;
    }
    
//buscar un dato en el archivo bin
    public boolean buscarM()  { 
        int pm;
        String buscar;
        Materias ObM = new Materias();
        try {
            abrirl_eM();
            buscar = JOptionPane.showInputDialog(null,
                    "Ingresa la clave de la Materia.\n",
                    "Buscando Materia",
                    JOptionPane.INFORMATION_MESSAGE);
            buscar = String.format("%-4s",buscar);
            int Li = 0, Ls = (int) (archivo.length()/tmRegM-1);
            do{
                pm = (Li+Ls)/2;
                archivo.seek(pm*tmRegM);
                ObM = LeerM(archivo,pm);
                if(ObM.clave.compareTo(buscar)>0)
                    Ls=pm-1;
                else
                    Li= pm+1;
            }while(!ObM.clave.equals(buscar) && Li<=Ls);
            if(ObM.clave.equals(buscar)){
                archivo.seek(pm*tmRegM);
                JOptionPane.showMessageDialog(null,
                        "Se ha encontrado la Materia."
                                +"\nClave: "+ObM.clave
                                +"\nNombre: "+ObM.nombre
                                +"\nCreditos: "+ObM.creditos,
                        "Buscando Materia.",
                        JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,
                        "No se ha encontrado la Materia."
                                +"\nDatos no existentes.",
                        "Buscando Materia.",
                        JOptionPane.INFORMATION_MESSAGE);
            }   
            archivo.close();
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }
    
//escribe los datos en el archivo reporte
    public void EscribirRM(PrintWriter linea,Materias ObM) {
        String s1;s1 = String.format("%-4s \t %-35s \t ",ObM.clave,ObM.nombre,ObM.creditos);
        linea.println(s1);
    }
    
//Se realiza el reporte
    public void ReporteM(){
        Materias ObM = new Materias();
        abrirLM();
        if (!reportMat.exists()){
            try {
                reportMat.createNewFile();
                nReg = (int) (archivo.length()/tmRegM);
                escribir = new FileWriter(reportMat);
                linea = new PrintWriter(escribir);
                linea.println("\t\t\t\tREPORTE MATERIAS.\n");
                linea.println("\t\tInstituto Tecnologico Nacional de Mexico en Celaya.\n");
                linea.println("-----------------------------------------------------------------------------------\n");
                linea.println("Clave. \t Nombre.                             \t Semestr.\n");
                linea.println("-----------------------------------------------------------------------------------\n");
                for(int i=0;i<nReg;i++){
                    ObM = LeerM(archivo,i);
                    EscribirRM(linea, ObM);
                }
                linea.println("-----------------------------------------------------------------------------------\n");
                linea.println("\n");
                linea.close();
                escribir.close();
                archivo.close();
                JOptionPane.showMessageDialog(null,
                        "Se ha realizado el reporte de las materias correctamente.",
                        "Reporte de Materias.",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                nReg = (int) (archivo.length()/tmRegM);
                escribir = new FileWriter(reportMat);
                linea = new PrintWriter(escribir);
                linea.println("\t\t\t\tREPORTE MATERIAS.\n");
                linea.println("\t\tInstituto Tecnologico Nacional de Mexico en Celaya.\n");
                linea.println("-----------------------------------------------------------------------------------\n");
                linea.println("Clave. \t Nombre.                             \t Semestr.\n");
                linea.println("-----------------------------------------------------------------------------------\n");
                for(int i=0;i<nReg;i++){
                    ObM = LeerM(archivo,i);
                    EscribirRM(linea, ObM);
                }
                linea.println("-----------------------------------------------------------------------------------\n");
                linea.println("\n");
                linea.close();
                escribir.close();
                archivo.close();
                JOptionPane.showMessageDialog(null,
                        "Se ha realizado el reporte de las materias correctamente.",
                        "Reporte de Materias.",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
////////////////////////////////////////////////////////////////////////////////////////
    
//Abrir archivo binario
    public void abrirl_eI () {
        try {
            archivo = new RandomAccessFile( rutaIns, "rw" );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
//Abrir archivo binario
    public void abrirLI () {
        try {
            archivo = new RandomAccessFile( rutaIns, "r" );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//escribe los datos en el archivo bin 
    public void EscribirI(RandomAccessFile archivo,Materias ObM,int reg){
        try {
            archivo.seek(reg*tmRegM);
            archivo.writeUTF(ObM.clave);
            archivo.writeUTF(ObM.nombre);
            archivo.writeByte(ObM.creditos);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error: "+ex,
                    "EscribirM2.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
//escribe los datos en el archivo bin 
    public void EscribirI2(RandomAccessFile archivo,Alumnos ObA,Materias ObM,int reg){
        try {
            archivo.seek(reg*tmRegI);
            archivo.writeUTF(ObA.nControl);
            archivo.writeUTF(ObM.clave);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error: "+ex,
                    "EscribirM2.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

//incripcciones
    public void incripciones(){
        int reg=0;
        abrirl_eI();
        resp = JOptionPane.showInputDialog(null,
                "¿Desea inscribir a un Alumno? \t (si/no)\n",
                "Inscripciones.",
                JOptionPane.QUESTION_MESSAGE);
        if(resp.equals("si"))
            do{
                buscarA();
                if(f=true){
                    do{
                        try {
                            buscarM();
                            reg++;
                            archivo.seek(reg*tmRegM);
                            archivo.writeUTF(al.nombre);
                            archivo.writeUTF(mat.clave);
                            resp = JOptionPane.showInputDialog(null,
                                    "¿Desea inscribir otra materia? \t (si/no)\n",
                                    "Inscripciones.",
                                    JOptionPane.QUESTION_MESSAGE);
                        } catch (IOException ex) {
                            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }while(resp.equals("si"));
                    incripciones();
                }else{
                    resp = JOptionPane.showInputDialog(null,
                            "¿Desea buscar otro numero de control? \t (si/no)\n",
                            "Inscripciones.",
                            JOptionPane.QUESTION_MESSAGE);
                }
            }while(resp.equals("si"));
    }
}