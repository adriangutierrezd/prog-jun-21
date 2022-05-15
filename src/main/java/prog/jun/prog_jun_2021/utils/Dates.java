package prog.jun.prog_jun_2021.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Contiene utilidades para el tratamiento de fechas
 * @author Adrián Gutiérrez
 */
public class Dates {
    /**
     * Genera una fecha a partir de un DatePicker
     * @param lDate Valor extraido del DatePicker
     * @return Objeto Date con la fecha
     */
    public static Date getDate(LocalDate lDate){
        LocalDateTime ldt = lDate.atStartOfDay();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Europe/Madrid"));
        long millis = zdt.toInstant().toEpochMilli();
        return new Date(millis);
    }
}
