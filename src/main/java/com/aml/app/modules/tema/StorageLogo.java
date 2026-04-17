package com.aml.app.modules.tema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StorageLogo {
    @Value("${app.storage.location}")
    private String storagePath;

    public String guardar(byte[] contenido, String nombreArchivo) {
        try {
            Path directorioPath = Paths.get(storagePath);

            if (!Files.exists(directorioPath)) {
                Files.createDirectories(directorioPath);
            }

            Path rutaCompleta = directorioPath.resolve(nombreArchivo);

            Files.write(rutaCompleta, contenido,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            log.info("Archivo guardado: ", nombreArchivo);
            return nombreArchivo;

        } catch (IOException e) {
            log.error("No se pudo borrar el archivo físico: {}", nombreArchivo);
            throw new RuntimeException("Error al escribir el archivo en el servidor: " + e.getMessage());
        }
    }


    public void eliminar(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.isEmpty())
            return;
        try {
            Path ruta = Paths.get(storagePath).resolve(nombreArchivo);
            Files.deleteIfExists(ruta);
            log.info("Archivo borrado: ", nombreArchivo);
        } catch (IOException e) {
            log.error("No se pudo borrar el archivo físico: {}", nombreArchivo);
            throw new RuntimeException("Error crítico al intentar eliminar el archivo: " + nombreArchivo, e);
        }
    }

}
