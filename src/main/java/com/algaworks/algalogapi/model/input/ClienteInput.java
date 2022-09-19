package com.algaworks.algalogapi.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ClienteInput {

    @Id
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String nome;

    @NotBlank
    @Size(max = 255)
    private String email;

    @Size(max = 20)
    private String telefone;

}
