package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
			model.addAttribute("user", new EmpleadoEntity());
			return "registrar_empleado";
		}
		repository.save(empleado);
		return "redirect:/";
	}
	
	private AreaEntity crearAreaPredeterminada() {
        AreaEntity areaPredeterminada = new AreaEntity();
        areaPredeterminada.setNombre_area("Nombre del área predeterminada");

        areaRepository.save(areaPredeterminada);

        return areaPredeterminada;
    }
}
