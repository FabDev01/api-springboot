package br.com.projeto.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projeto.api.model.PessoaModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Integer> {
    Page<PessoaModel> findAll(Pageable pageable);

    Optional<PessoaModel> findById(Integer id);

    List<PessoaModel> findByOrderByNome();

    List<PessoaModel> findByNomeOrderByIdadeDesc(String nome);

    List<PessoaModel> findByNomeContaining(String termo);

    @Query(value = "SELECT SUM(idade) FROM usuarios", nativeQuery = true)  int somarIdades();

    @Query(value = "SELECT * FROM usuarios WHERE idade >= :idade", nativeQuery = true) List<?> idadeMaiorIgual(int idade);

    @Query(value ="SELECT id, nome, idade FROM usuarios WHERE (id = :id OR id IS NULL) OR (nome = :nome OR nome IS NULL)", nativeQuery = true) List<PessoaModel> filterQueryParams(
            @Param("id") Integer id,
            @Param("nome") String nome
            //@Param("idade") int idade
    );


    int countById(int id);

}