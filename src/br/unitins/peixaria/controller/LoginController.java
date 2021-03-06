package br.unitins.peixaria.controller;

import br.unitins.peixaria.factory.JPAFactory;
import br.unitins.peixaria.model.Usuario;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.unitins.peixaria.application.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class LoginController extends Controller<Usuario> implements Serializable {

   
	private static final long serialVersionUID = 8843973377372239240L;
	private String filtro;

	//função de entrar com hash
    public void entrar() {
        String senha = Util.hashSHA256(getEntity().getSenha());
        //teste senha hash
        System.out.println(senha);

        EntityManager em = JPAFactory.getEntityManager();
        Query query = em.createQuery("Select a " + "From Usuario a " + "Where a.cpf = :cpf AND a.senha = :senha");
        query.setParameter("senha", senha);
        query.setParameter("cpf", getEntity().getCpf());
        try {
            entity = (Usuario) query.getSingleResult();

        } catch (NoResultException e) {
            entity = null;
        }

        if (entity != null)
            Util.redirect("/faces/usuario.xhtml");
        else
            Util.addMessageError("Erro");
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    @Override
    public Usuario getEntity() {
        if (entity == null)
            entity = new Usuario();
        return entity;
    }

}