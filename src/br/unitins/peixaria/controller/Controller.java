package br.unitins.peixaria.controller;

import java.io.Serializable;

import javax.persistence.EntityManager;

import br.unitins.peixaria.application.RepositoryException;
import br.unitins.peixaria.application.Util;
import br.unitins.peixaria.factory.JPAFactory;
import br.unitins.peixaria.model.DefaultEntity;
import br.unitins.peixaria.repository.Repository;

public abstract class Controller<T extends DefaultEntity<T>> implements Serializable {

	
	private static final long serialVersionUID = 4484472358684304677L;
	
	
	protected T entity;

	public abstract T getEntity();

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public Controller() {
		super();
	}

	public void salvar() {

		Repository<T> r = new Repository<T>();
		try {
			r.beginTransaction();
			r.salvar(getEntity());
			r.commitTransaction();
		} catch (RepositoryException e) {
			e.printStackTrace();
			r.rollBackTransaction();
			Util.addMessageError("Erro ao salvar");
			return;
		}
		limpar();
		Util.addMessageInfo("Cadastro realizado com sucesso.");

	}

	public void excluir() {

		Repository<T> r = new Repository<T>();
		try {
			r.beginTransaction();
			r.excluir(getEntity());
			r.commitTransaction();
		} catch (RepositoryException e) {
			e.printStackTrace();
			r.rollBackTransaction();
			Util.addMessageError("Erro ao excluir");
			return;
		}
		limpar();
		Util.addMessageInfo("Exclusao realizada com sucesso.");
	}

	public void editar(int id) {
		EntityManager em = JPAFactory.getEntityManager();
		setEntity((T) em.find(getEntity().getClass(), id));
	}

	public void limpar() {
		entity = null;
	}

}