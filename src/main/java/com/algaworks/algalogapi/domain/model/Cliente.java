package com.algaworks.algalogapi.domain.model;

import com.algaworks.algalogapi.domain.ValidationGroups;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "cliente")
public class Cliente {

    @NotNull(groups = ValidationGroups.ClienteId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(name = "nome", columnDefinition = "VARCHAR(60)", nullable = false)
    private String nome;

    @NotBlank
    @Size(max = 255)
    @Column(name = "email", columnDefinition = "VARCHAR(255)", nullable = false)
    private String email;

    @Size(max = 20)
    @Column(name = "telefone", columnDefinition = "VARCHAR(20)", nullable = false)
    private String telefone;

}
