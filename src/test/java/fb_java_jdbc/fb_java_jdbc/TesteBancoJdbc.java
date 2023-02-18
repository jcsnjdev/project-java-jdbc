package fb_java_jdbc.fb_java_jdbc;

import java.util.List;

import org.junit.Test;

import dao.UserFbDao;
import model.BeanUserFone;
import model.Telefone;
import model.UserFbJava;

public class TesteBancoJdbc {

	@Test
	public void testeSalvarUser() {

		UserFbDao userFbDao = new UserFbDao();
		UserFbJava userFbJava = new UserFbJava();

		userFbJava.setNome("testeNome");
		userFbJava.setEmail("testeEmail@gmail.com");

		userFbDao.salvarUser(userFbJava);
	}

	@Test
	public void testeSalvarTel() {

		UserFbDao userFbDao = new UserFbDao();
		Telefone telefone = new Telefone();

		try {
			telefone.setNumero("3263-9854");
			telefone.setTipo("Casa");
			telefone.setUserpessoa(1L);

			userFbDao.salvarTel(telefone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testeListarUser() {

		UserFbDao dao = new UserFbDao();

		try {
			List<UserFbJava> list = dao.listaUser();
			for (UserFbJava userFbJava : list) {
				System.out.println(userFbJava);
				System.out.println("---------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testeCarregaFonesUser() {

		UserFbDao dao = new UserFbDao();

		List<BeanUserFone> beanUserFones = dao.listaUserFone(1L);

		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
		}
	}

	@Test
	public void testeBuscarUser() {

		UserFbDao dao = new UserFbDao();

		try {
			UserFbJava userFbJava = dao.buscarUser(1L);
			System.out.println(userFbJava);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testeAtualizarUser() {

		UserFbDao dao = new UserFbDao();

		try {
			UserFbJava obBanco = dao.buscarUser(1L);
			obBanco.setNome("Nome atualizado");
			dao.atualizarUser(obBanco);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testeDeleteUser() {

		UserFbDao dao = new UserFbDao();

		try {
			dao.deletarUser(3L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testeDeleteUserFone() {

		UserFbDao dao = new UserFbDao();
		dao.deleteFonesPorUser(1L);
	}

}
