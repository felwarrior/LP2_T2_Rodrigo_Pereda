package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.AreaEntity;
import com.example.demo.entity.EmpleadoEntity;

import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.EmpleadoRepository;

@Controller
public class EmpleadoController {
	
	@Autowired
	private EmpleadoRepository repository;
	
	@Autowired
    private AreaRepository areaRepository;

    @GetMapping("/")
    public String menu(Model model) {
        List<EmpleadoEntity> listaEmpleado = repository.findAll();
        model.addAttribute("listaEmpleado", listaEmpleado);
        return "home";
    }

    @GetMapping("/registrar_empleado")
    public String showRegistrarEmpleado(Model model) {
        model.addAttribute("empleado", new EmpleadoEntity());
        List<AreaEntity> areas = areaRepository.findAll();
        if (areas.isEmpty()) {
            AreaEntity areaPredeterminada = crearAreaPredeterminada();
            areas = List.of(areaPredeterminada); 
        }
        model.addAttribute("areas", areas);
        return "registrar_empleado";
    }
	
	@PostMapping("/registrar_empleado")
	public String RegistrarEmpleado(@ModelAttribute EmpleadoEntity empleado, Model model) {
		if(repository.findByCorreo(empleado.getCorreo()) != null) {
			model.addAttribute("errorMessage", "El correo electrónico ya está en uso");
			model.addAttribute("empleado", new EmpleadoEntity());
			List<AreaEntity> areas = areaRepository.findAll();
			model.addAttribute("areas", areas);
			return "registrar_empleado";
		}
		repository.save(empleado);
		return "redirect:/";
	}
	
	@GetMapping("/detalle_empleado/{id}")
	public String verEmpleado(Model model, @PathVariable("id")Integer id) {
		EmpleadoEntity empleadoEncontrado = repository.findById(id).get();
		model.addAttribute("empleado", empleadoEncontrado);
		return "detalle_empleado";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminarEmpleado(@PathVariable("id")Integer id) {
		repository.deleteById(id);
		return "redirect:/";
	}
	
	@GetMapping("/editar_empleado/{dni_empleado}")
    public String mostrarFormularioEdicion(@PathVariable Integer dni_empleado, Model model) {
        Optional<EmpleadoEntity> empleadoOptional = repository.findById(dni_empleado);
        if (empleadoOptional.isPresent()) {
            model.addAttribute("empleado", empleadoOptional.get());
            return "editar_empleado";
        } else {
            model.addAttribute("errorMessage", "Empleado no encontrado con DNI: " + dni_empleado);
            return "redirect:/";
        }
    }

    @PostMapping("/actualizar_empleado")
    public String actualizarEmpleado(@ModelAttribute EmpleadoEntity empleado, Model model) {
        try {
            Optional<AreaEntity> area = areaRepository.findById(empleado.getAreaEntity().getArea_id());
            if (area.isPresent()) {
                empleado.setAreaEntity(area.get());
                repository.save(empleado);
                return "redirect:/";
            } else {
                model.addAttribute("errorMessage", "El área especificada no existe");
                model.addAttribute("empleado", empleado);
                return "editar_empleado";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error al actualizar el empleado: " + e.getMessage());
            model.addAttribute("empleado", empleado);
            return "editar_empleado";
        }
    }
	
	
	private AreaEntity crearAreaPredeterminada() {
        AreaEntity areaPredeterminada = new AreaEntity();
        areaPredeterminada.setNombre_area("Nombre del área predeterminada");

        areaRepository.save(areaPredeterminada);

        return areaPredeterminada;
    }
}
