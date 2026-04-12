package main;

import dao.PostulanteDAO;
import model.Postulante;
import util.Validador;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PostulanteDAO dao = new PostulanteDAO();
        int opcion;

        do {
        	System.out.println("\n--- MENU POSTULANTES ---");
        	System.out.println("1. Registrar postulante");
        	System.out.println("2. Listar postulantes");
        	System.out.println("3. Actualizar correo");
        	System.out.println("4. Eliminar postulante");
        	System.out.println("5. Buscar distrito (Ubigeo)");
        	System.out.println("6. Buscar por codigo de ubigeo");
        	System.out.println("0. Salir");
            opcion = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcion) {
                case 1:
                    Postulante p = new Postulante();

                    System.out.print("Nombre: ");
                    p.setNombre(sc.nextLine());
                    Validador.textoObligatorio(p.getNombre(), "Nombre");

                    System.out.print("Apellido paterno: ");
                    p.setApellidoPaterno(sc.nextLine());
                    Validador.textoObligatorio(p.getApellidoPaterno(), "Apellido paterno");

                    System.out.print("Apellido materno: ");
                    p.setApellidoMaterno(sc.nextLine());

                    System.out.print("DNI: ");
                    p.setDni(sc.nextLine());
                    Validador.dni(p.getDni());

                    System.out.print("Correo: ");
                    p.setCorreo(sc.nextLine());
                    Validador.correo(p.getCorreo());

                    System.out.print("Celular: ");
                    p.setTelefonoCelular(sc.nextLine());

                    System.out.print("Sexo (M/F): ");
                    p.setSexo(sc.nextLine());
                    Validador.sexo(p.getSexo());

                    System.out.print("Ubigeo (6 digitos): ");
                    p.setUbigeo(sc.nextLine());

                    dao.insertar(p);
                    System.out.println("✅ Postulante registrado");
                    break;

                case 2:
                    dao.listar();
                    break;

                case 3:
                    System.out.print("ID postulante: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nuevo correo: ");
                    String correo = sc.nextLine();
                    Validador.correo(correo);

                    dao.actualizarCorreo(id, correo);
                    System.out.println("✅ Correo actualizado");
                    break;

                case 4:
                    System.out.print("ID a eliminar: ");
                    dao.eliminar(sc.nextInt());
                    System.out.println("✅ Eliminado");
                    break;
                
                case 5:
                    System.out.print("Ingrese nombre del distrito: ");
                    String distrito = sc.nextLine();
                    dao.buscarDistrito(distrito);
                    break;
                    
                case 6:
                    System.out.print("Ingrese codigo de ubigeo (4 o 6 digitos): ");
                    String cod = sc.nextLine();
                    dao.buscarPorUbigeo(cod);
                    break;
                    
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }

        } while (opcion != 0);

        sc.close();
    }
}