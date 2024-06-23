package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_area")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AreaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer area_id;
	
	@Column(name = "nombre_area",nullable = false , length = 45)
	private String nombre_area;
	
}
