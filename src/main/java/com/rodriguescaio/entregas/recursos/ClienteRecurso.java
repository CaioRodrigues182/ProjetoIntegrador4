package com.rodriguescaio.entregas.recursos;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rodriguescaio.entregas.dominio.Cliente;
import com.rodriguescaio.entregas.servico.ClienteServico;

@RestController
@RequestMapping("/clientes")
public class ClienteRecurso {

	@Autowired
	private ClienteServico serv;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Cliente>> todos() {
		return ResponseEntity.status(HttpStatus.OK).body(serv.buscarTodos());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Integer id) {
		Cliente art = serv.buscar(id);
		if (art == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(art);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> criar(@RequestBody Cliente Cliente) {
		Cliente = serv.inserir(Cliente);
		URI uri = getUri("/{id}", Cliente.getCodCliente());
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Integer id) {
		serv.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Cliente Cliente, @PathVariable("id") Integer id) {
		Cliente.setCodCliente(id);
		serv.atualizar(Cliente);
		return ResponseEntity.noContent().build();
	}

	private URI getUri(String nome, Integer valor) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path(nome).buildAndExpand(valor).toUri();
	}

}
