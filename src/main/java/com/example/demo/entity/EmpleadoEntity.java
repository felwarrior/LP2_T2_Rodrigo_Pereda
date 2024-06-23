package com.example.demo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_empleado")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoEntity {

	@Id
	@Column(name = "dni_empleado",nullable = false, length = 8, columnDefinition = "CHAR(8)")
	private String dni_empleado;
	
	@Column(name = "nombre_empleado",nullable = false , length = 45)
	private String nombre_empleado;
	
	@Column(name = "apellido_empleado",nullable = false, length = 45)
	private String apellido_empleado;
	
	 
	    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	    @Column(name = "fecha_nacimiento", nullable = false)
	    private LocalDate fecha_nacimiento;
	 	
	@Column(name = "direccion",nullable = false, length = 45)
	private String direccion;
	
	@Column(name = "correo", nullable = false, unique = true, length = 45)
	private String correo;
	
	@ManyToOne
	@JoinColumn(name = "area_id", nullable = false)
	private AreaEntity areaEntity;
	
}
