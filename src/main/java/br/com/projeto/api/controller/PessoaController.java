package br.com.projeto.api.controller;

import br.com.projeto.api.service.ServicoPessoa;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.projeto.api.repository.PessoaRepository;
import br.com.projeto.api.model.PessoaModel;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@RestController
public class PessoaController {
    @Autowired
    private ServicoPessoa service;

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping("/user")
    public ResponseEntity<?> getAll(){
        return service.getAll();
    }

    @GetMapping("/userFilter")
    public List<PessoaModel> getAllParams(
            @RequestParam(value = "name", required = false) String name,
//            @RequestParam(value = "idade", required = false) Integer idade,
            @RequestParam(value = "id", required = false) Integer id
    ){
        return service.getAllParams(id, name);
    }


    @PostMapping("/add")
    public ResponseEntity<?> cadastrar(@RequestBody PessoaModel obj){
        return service.cadastrar(obj);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return service.selectById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> editar(@RequestBody PessoaModel obj){
        return service.editar(obj);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> remover(@PathVariable Integer id){;
        return service.remove(id);
    }

    @GetMapping("/user/count")
    public long contador(){
        return pessoaRepository.count();
    }

    @GetMapping("user/somarIdade")
    public int somarIdadesQuery(){
        return service.somarIdades();
    }

    @GetMapping("user/queryidade/{idade}")
    public ResponseEntity<?> idadesQuery(@PathVariable Integer idade){
        return service.idadeMaiorIgual(idade);
    }

    @GetMapping("/user/order")
    public List<PessoaModel> ordenar(){
        return service.ordenar();
    }

    @GetMapping("/user/ordername/{nome}")
    public List<PessoaModel> ordenarNome(@Valid @PathVariable String nome){
        return pessoaRepository.findByNomeOrderByIdadeDesc(nome);
    }

    @GetMapping("/user/contain/{key}")
    public List<PessoaModel> nomeContem(@PathVariable String key){
        return pessoaRepository.findByNomeContaining(key);
    }
 
}
