package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.UserFbJava;

public class UserFbDao {

	private Connection connection;

	public UserFbDao() {
		connection = SingleConnection.getConnection();
	}

	/*
	 * === Create
	 */

	public void salvarUser(UserFbJava userFbJava) {
		try {
			String sql = "insert into userfbjava (nome, email) values (?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userFbJava.getNome());
			statement.setString(2, userFbJava.getEmail());
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	public void salvarTel(Telefone telefone) {
		try {
			String sql = "insert into telefoneuser (numero, tipo, userpessoa) values (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUserpessoa());
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/*
	 * === Read
	 */

	public List<UserFbJava> listaUser() throws Exception {
		List<UserFbJava> list = new ArrayList<UserFbJava>();

		String sql = "select * from userfbjava";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			UserFbJava userFbJava = new UserFbJava();

			userFbJava.setId(resultSet.getLong("id"));
			userFbJava.setNome(resultSet.getString("nome"));
			userFbJava.setEmail(resultSet.getString("email"));

			list.add(userFbJava);
		}

		return list;
	}

	public List<BeanUserFone> listaUserFone(Long idUser) {
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();

		String sql = "select nome, numero, email from telefoneuser as fone";
		sql += " inner join userfbjava as userfb ";
		sql += " on fone.USERPESSOA = userfb.ID ";
		sql += "where userfb.ID = " + idUser;

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				BeanUserFone userFone = new BeanUserFone();

				userFone.setNome(resultSet.getString("nome"));
				userFone.setNumero(resultSet.getString("numero"));
				userFone.setEmail(resultSet.getString("email"));

				beanUserFones.add(userFone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return beanUserFones;
	}

	public UserFbJava buscarUser(Long id) throws Exception {
		UserFbJava retorno = new UserFbJava();

		String sql = "select * from userfbjava where id = " + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) { // Retorna apenas um ou nenhum campo
			retorno.setId(resultSet.getLong("id"));
			retorno.setNome(resultSet.getString("nome"));
			retorno.setEmail(resultSet.getString("email"));
		}

		return retorno;
	}

	/*
	 * === Update
	 */

	public void atualizarUser(UserFbJava userFbJava) {
		try {
			String sql = "update userfbjava set nome = ? where id = " + userFbJava.getId();

			PreparedStatement update = connection.prepareStatement(sql);
			update.setString(1, userFbJava.getNome());

			update.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	/*
	 * === Delete
	 */

	public void deletarUser(Long id) {
		try {
			String sql = "delete from userfbjava where id = " + id;
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void deleteFonesPorUser(Long idUser) {

		try {
			String sqlFone = "delete from telefoneuser where userpessoa = " + idUser;
			String sqlUser = "delete from userfbjava where id = " + idUser;

			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();

			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
