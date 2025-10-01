package com.itb.inf2cm.pizzariac.controller;


import com.itb.inf2cm.pizzariac.model.entity.Produto;
import com.itb.inf2cm.pizzariac.model.services.ProdutoSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 @ResponseEntity: Toda resposta HTTP (status, cabeçalhos e corpo ), aqui temos mais controle sobre o que é devolvido
                  para o cliente
     1. Status HTTP: (200 ok, 201 CREATED, 404 NOT FOUND etc ...)
     2. Headers:    (cabeçalhos extras, como Location, Authorization etc ...)
     3. Body:       (o objeto que será convertido em JSON/XML para o cliente )

  @ResponseBody: Corpo da Requisição ( Recebendo um objeto JSON)
*/

@RestController
@RequestMapping("/api/v1/produto")
public class ProdutoController {

    @Autowired
    private ProdutoSevice produtoSevice;


    @GetMapping
    public ResponseEntity<List<Produto>> findAll(){

        return ResponseEntity.ok(produtoSevice.findAll());
    }

    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody Produto produto){
        Produto novo = produtoSevice.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarProdutoPorId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(produtoSevice.findById(Long.parseLong(id)));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Produto não encontrado com o id: " + id
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable String id, @RequestBody Produto produto){
        try {
            return ResponseEntity.ok(produtoSevice.update(Long.parseLong(id), produto));
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Produto não encontrado com o id: " + id
                    )
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable String id){
        try {
            produtoSevice.delete(Long.parseLong(id));
            return ResponseEntity.ok().body (
                    Map.of(
                            "status", 200,
                            "message", "Produto excluído com sucesso " + id
                    )
            );
        }
        catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "O id informado não é válido: " + id
                    )
            );
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", "Produto não encontrado com o id: " + id
                    )
            );
        }
    }
}
