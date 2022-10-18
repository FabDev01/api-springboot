package br.com.projeto.api.service;

import br.com.projeto.api.model.Mensagem;
import br.com.projeto.api.model.PessoaModel;
import br.com.projeto.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoPessoa {
    @Autowired
    private Mensagem mensagem;

    @Autowired
    private PessoaRepository pessoaRepository;

    public ResponseEntity<?> getAll(){
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(0, 2, sort);
        return new ResponseEntity<>(pessoaRepository.findAll(pageable).getContent(), HttpStatus.OK);
    }

    public List<PessoaModel> getAllParams(Integer id, String name){
        return pessoaRepository.filterQueryParams(id, name);
    }

    public ResponseEntity<?> cadastrar(PessoaModel obj){
        if(obj.getNome().equals("")){
            mensagem.setMensagem("O campo nome está vazio");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else if (obj.getIdade() < 0 ) {
            mensagem.setMensagem("O campo idade está vazio");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(pessoaRepository.save(obj), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> selectById(int id){
        if (pessoaRepository.countById(id) == 0 ) {
            mensagem.setMensagem("Não foi encontrado o usuário");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(pessoaRepository.findById(id), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> remove(int id){
        if (pessoaRepository.countById(id) == 0 ) {
            mensagem.setMensagem("Não foi encontrado o usuário");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else{
            pessoaRepository.deleteById(id);
            mensagem.setMensagem("Usuário deletado");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }


    public ResponseEntity<?> editar(PessoaModel obj){

        if (pessoaRepository.countById( obj.getId() ) == 0 ) {
            mensagem.setMensagem("Não foi encontrado o usuário");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);

        }else if(obj.getNome().equals("")){
            mensagem.setMensagem("O campo nome está vazio");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);

        }else if(obj.getIdade() <= 0 ){
            mensagem.setMensagem("O campo idade está invalído");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);

        }else {
            return new ResponseEntity<>(pessoaRepository.save(obj), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> idadeMaiorIgual(Integer idade){
        if(idade <= 0){
            mensagem.setMensagem("O campo idade está invalído");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);

        }else{
            return new ResponseEntity<>( pessoaRepository.idadeMaiorIgual(idade), HttpStatus.OK);
        }
    }

    public List<PessoaModel> ordenar(){
        return pessoaRepository.findByOrderByNome();
    }
    public int somarIdades(){
        return pessoaRepository.somarIdades();
    }
}
