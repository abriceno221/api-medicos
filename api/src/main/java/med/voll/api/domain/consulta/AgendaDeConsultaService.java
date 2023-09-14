package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;

@Service
public class AgendaDeConsultaService {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private MedicoRepository medicopository;
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	public void agendar(DatosAgendarConsulta datos) {
		
		if(pacienteRepository.findById(datos.idPaciente()).isPresent()) {
			throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
		}
		
		if(datos.idMedico() != null && medicopository.existsById(datos.idMedico())) {
			throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
		}
		
		var paciente = pacienteRepository.findById(datos.idPaciente()).get();
		var medico = seleccionaMedico(datos);
		
		var consulta =  new Consulta(null, medico, paciente, datos.fecha());
		consultaRepository.save(consulta);
	}

	private Medico seleccionaMedico(DatosAgendarConsulta datos) {
		if(datos.idMedico() != null) {
			return medicopository.getReferenceById(datos.idMedico());
		}
		
		if(datos.especialiadd() == null) {
			throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el medico");
		}
		
		return medicopository.seleccionarMedicoConEspecilidadEnFecha(datos.especialiadd(), datos.fecha());
	}
}
