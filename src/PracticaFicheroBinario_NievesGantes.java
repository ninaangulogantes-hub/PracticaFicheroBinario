import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class PracticaFicheroBinario_NievesGantes {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String ruta = ("src/ficheroBinario/productos.dat");
        int op = 0;
    do {
        System.out.println("--- GESTIÓN DE INVENTARIO --- \n" +
                "1. Alta de producto \n" +
                "2. Ver todos los productos \n" +
                "3. Buscar producto por nombre \n" +
                "4. Listar productos por categoría \n" +
                "5. Mostrar productos económicos (precio < 30€) \n" +
                "6. Mostrar productos premium (precio >= 50€) \n" +
                "7. Salir \n");

        op = validarValor("Elige una opción:",7,1,sc);

        switch (op) {
            case 1 -> altaProducto(ruta, sc);
            case 2 -> listarProducto(ruta, sc);
            case 3 -> buscarProducto(ruta, sc);
            case 4 -> listarCategoria(ruta, sc);
            case 5 -> listarEconomico(ruta, sc);
            case 6 -> listarPremium(ruta, sc);
            case 7 -> System.out.println("Saliendo...");
        }
    }while (op != 6);
    }

//  Fucion que lista los productos agrupados por categoria.
    private static void listarCategoria(String ruta, Scanner sc) throws IOException {

//      Variables
        int tipo = 0;
        String categoria = "";
        boolean esCategoria = false;

//      Le pedimos la categoria al usuario
        System.out.println("¿De que categoria desea listar los productos: (1.Alimentacion 2.Bebidas 3.Drogueria 4.Electronica)");
        tipo = sc.nextInt();

        switch (tipo){
            case 1 -> categoria = "alimentacion";
            case 2 -> categoria = "bebidas";
            case 3 -> categoria = "drogueria";
            case 4 -> categoria = "electronica";
        }

//      Abrimos el fichero.
        FileInputStream fis = new FileInputStream(ruta);
//      Leemos e fichero.
        DataInputStream dis = new DataInputStream(fis);
        while(true){
            String p = "";
            String c = "";
            int s = 0;
            double pr = 0.0;

            try {
                if(c.equalsIgnoreCase(categoria)){
                    System.out.print(p + "--" + c + "--" + s + "--" + pr);
                    esCategoria = true;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        dis.close();
        fis.close();


    }

    private static void listarPremium(String ruta, Scanner sc) {
    }

    private static void listarEconomico(String ruta, Scanner sc) {
    }
//Funcion que busca un producto.
    private static void buscarProducto(String ruta, Scanner sc) throws IOException {
    String producto = "";
    boolean esProducto = false;

//      Abrimos el fichero.
        FileInputStream fis = new FileInputStream(ruta);
//      Leemos e fichero.
        DataInputStream dis = new DataInputStream(fis);

//  Pedimos el nombre del producto a buscar.
    System.out.println("Introduce nombre");
    producto = sc.next();

//    Mientras exista el producto mostramos su informacion.
    while(true){
        String p = "";
        String c = "";
        int s = 0;
        double pr = 0.0;

        try {
            p = dis.readUTF();
            c = dis.readUTF();
            s = dis.readInt();
            pr = dis.readDouble();

            if(p.equalsIgnoreCase(producto)){
                System.out.print(p + "--" + c + "--" + s + "--" + pr);
                esProducto = true;
            }else{
                System.out.println("Su producto no existe en nuestro inventario.");
            }
        } catch (IOException e) {
            break;
        }
        fis.close();
        dis.close();

    }
    }
//  Funcion que lista el contenido del fichero
    private static void listarProducto(String ruta, Scanner sc) throws IOException {
//      Abrimos el fichero.
        FileInputStream fis = new FileInputStream(ruta);
//      Leemos e fichero.
        DataInputStream dis = new DataInputStream(fis);

        while(true){
            try {

                System.out.print(dis.readUTF() + "--");
                System.out.print(dis.readUTF()+ "--");
                System.out.print(dis.readInt()+ "--");
                System.out.println(dis.readDouble());

            } catch (IOException e) {
                System.out.println("\nFin fichero\n");
                break;
            }
        }

        dis.close();
        fis.close();
    }
//  Funcion que introduce datos en un fichero.
    private static void altaProducto(String ruta, Scanner sc) throws IOException {
//        Variables
        String nombre = "", alimentacion = "", bebidas = "", drogueria = "", electronica = "", categoria = "";
        int stock = 0, tipo = 0;
        double precio = 0.0;
        boolean esDoble = true;

//      Abrimos el fichero
        FileOutputStream fos = new FileOutputStream(ruta, true);
        DataOutputStream dos = new DataOutputStream(fos);

//       Limpiamos el buffer
        sc.nextLine();

//      Le pedimos al usuario los datos
        System.out.println("Introduce el nombre");
        nombre = sc.nextLine();

        tipo = validarValor("Introduce el tipo de producto: (1.Alimentacion 2.Bebidas 3.Drogueria 4.Electronica)", 4,1,sc);

        switch (tipo){
            case 1 -> categoria = "alimentacion";
            case 2 -> categoria = "bebidas";
            case 3 -> categoria = "drogueria";
            case 4 -> categoria = "electronica";
        }

        System.out.println("Introduzca el stock de "+nombre);
        stock = sc.nextInt();

    do {
        esDoble = true;
        try {

            System.out.println("Introduzca el precio de " + nombre);
            precio = sc.nextDouble();

        } catch (InputMismatchException ime) {
            System.out.println("Tipo incorrecto.");
            sc.nextLine();
            esDoble = false;
        }
    }while(!esDoble);

//      Guardamos los datos
        dos.writeUTF(nombre);
        dos.writeUTF(categoria);
        dos.writeInt(stock);
        dos.writeDouble(precio);

        dos.close();
        fos.close();

        System.out.println("\n¡Producto dado de alta con exito!\n");
    }
//  Funcion que valida lo que mete el usuario por teclado
    private static int validarValor(String mensaje, int max, int min, Scanner sc) {
//      Variables
        int res = 0;
        boolean correcto = false;
//      Mientras no se ajuste a lo pedido sigue preguntando.
        do {
//          Validamos que solo ingrese numeros
            try {
                System.out.println(mensaje);
                res = sc.nextInt();

//              Informamos del rango
                if (res < min || res > max) {
                    System.out.println("El numero debe estar entre " + min + " y " + max + ".Por favor vuelva a introducirlo.");
                } else {
                    correcto = true;
                }

            } catch (InputMismatchException IME) {
                System.out.println("No has introducido un numero" + ".Por favor vuelva a introducirlo.");
                sc.nextLine();
            }

        } while (!correcto);

        return res;
    }
    }

