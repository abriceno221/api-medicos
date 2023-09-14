package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;


public record DatoActualizarMedico(@NotNull Long id, String nombre, String documento, DatosDireccion direccion) {

}
