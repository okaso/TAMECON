/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author HP
 */
public class CopiaSeguridad {

    FileInputStream entrada;
    FileOutputStream salida;
    File archivos;

    public CopiaSeguridad() {

    }

    public String guardarTexto(File archivo, String contenido) {
        String respuesta = null;
        try {
            salida = new FileOutputStream(archivo);
            byte[] byteText = contenido.getBytes();
            salida.write(byteText);
            respuesta = "Se guardo con exito el archivo";
        } catch (Exception e) {
            System.out.println("Error al Guardar");
        }
        return respuesta;
    }


}
