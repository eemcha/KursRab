package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class conn {
	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;

	public void Conn() throws ClassNotFoundException, SQLException {
		conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/dbBilet.s3db");
		System.out.println("База Подключена!");
		statmt = conn.createStatement();
	}

	public void AddDisciplin(Integer IdDisciplin, String NameDiscipline) throws SQLException {
		statmt.execute("INSERT INTO [Disciplines] ([IdDiscipline], [NameDiscipline]) VALUES (" + IdDisciplin + ", '"
				+ NameDiscipline + "');");
		System.out.println("Таблица Disciplin заполнена");
	}

	public void AddGroup(Integer IdGroups, String NameGroup) throws SQLException {
		statmt.execute("INSERT INTO Groups (IdGroups, NameGroup) VALUES (" + IdGroups + ", '" + NameGroup + "');");
		System.out.println("Таблица Groups заполнена");
	}

	public void AddQuestion(Integer IdQuestion, Integer IdDiscipline, String TextQuestion) throws SQLException {
		statmt.execute("INSERT INTO Questions (IdQuestion, IdDiscipline, TextQuestion) " + "VALUES (" + IdQuestion
				+ ", " + IdDiscipline + ", '" + TextQuestion + "');");
		System.out.println("Таблица Questions заполнена");
	}

	public void AddTicket(Integer IdTicket, Integer IdDiscipline, Integer IdGroup, Integer IdQuestion,
			Integer TicketNumber) throws SQLException {
		statmt.execute("INSERT INTO Tickets (IdTicket, IdDiscipline, IdGroup, IdQuestion,TicketNumber) " + "VALUES ("
				+ IdTicket + ", " + IdDiscipline + ", " + IdGroup + ", " + IdQuestion + " , " + TicketNumber + ");");
		System.out.println("Таблица Tickets заполнена");
	}

	public void DelDisciplin(Integer IdDisciplin, String NameDiscipline) throws SQLException {
		statmt.execute("DELETE FROM Disciplines WHERE NameDiscipline='" + NameDiscipline + "' OR IdDiscipline="
				+ IdDisciplin + ";");
		statmt.execute("DELETE FROM Tickets WHERE IdDiscipline=" + IdDisciplin + ";");
		statmt.execute("DELETE FROM Questions WHERE IdDiscipline=" + IdDisciplin + ";");
		System.out.println("Запись Discipline удалена ");
	}

	public void DelGroup(Integer IdGroups, String NameGroup) throws SQLException {
		statmt.execute("DELETE FROM Groups WHERE IdGroups=" + IdGroups + " OR NameGroup='" + NameGroup + "';");
		statmt.execute("DELETE FROM Tickets WHERE IdGroup=" + IdGroups + ";");
		System.out.println("Запись Group удалена ");
	}

	public void DelQuestion(Integer IdQuestion, String TextQuestion) throws SQLException {
		resSet = statmt.executeQuery("SELECT * FROM Questions WHERE TextQuestion='" + TextQuestion + "';");
		String s = resSet.getString("IdQuestion");
		statmt.execute("DELETE FROM Questions WHERE" + " IdQuestion=" + IdQuestion + " OR  TextQuestion='"
				+ TextQuestion + "';");
		statmt.execute("DELETE FROM Tickets WHERE IdQuestion=" + s + ";");
		System.out.println("Запись Question удалена ");
	}

	public void DelQuestionD(Integer IdDisciplin) throws SQLException {
		resSet = statmt.executeQuery("SELECT * FROM Questions WHERE IdDiscipline=" + IdDisciplin + ";");
		String s = resSet.getString("IdQuestion");
		statmt.execute("DELETE FROM Questions WHERE" + " IdDiscipline=" + IdDisciplin + ";");
		statmt.execute("DELETE FROM Tickets WHERE IdDiscipline=" + IdDisciplin + ";");
		System.out.println("Запись Question удалена ");
	}

	public void DelTicket(Integer IdTicket) throws SQLException {
		statmt.execute("DELETE FROM Tickets WHERE " + "IdTicket=" + IdTicket + ";");
		System.out.println("Запись Tickets удалена ");
	}

	public void DelTicketQuestion(Integer IdTicket, Integer IdQuestion) throws SQLException {
		statmt.execute("DELETE FROM Tickets WHERE " + "IdTicket=" + IdTicket + " AND IdQuestion=" + IdQuestion + ";");
		System.out.println("Запись Tickets удалена ");
	}

	public void DelTicketQuestion(Integer IdQuestion) throws SQLException {
		statmt.execute("DELETE FROM Tickets WHERE IdQuestion=" + IdQuestion + ";");
		System.out.println("Запись Tickets удалена ");
	}

	public void DelTicket(int idDisciplin, int IdGroup) throws SQLException {
		statmt.execute("DELETE FROM Tickets WHERE " + "IdDiscipline=" + idDisciplin + " AND IdGroup=" + IdGroup + ";");
		System.out.println("Таблица Tickets заполнена");
	}

	public ArrayList<String[]> ReadDisciplin() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM Disciplines");
		ArrayList<String[]> list = new ArrayList<String[]>();
		while (resSet.next()) {
			String[] s = new String[] { resSet.getString("IdDiscipline"), resSet.getString("NameDiscipline") };
			list.add(s);
		}
		System.out.println("Таблица Disciplines выведена");
		return list;
	}

	public ArrayList<String[]> ReadGroup() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM Groups");
		ArrayList<String[]> list = new ArrayList<String[]>();
		while (resSet.next()) {
			String[] s = new String[] { resSet.getString("IdGroups"), resSet.getString("NameGroup") };
			list.add(s);
		}
		System.out.println("Таблица Groups выведена");
		return list;
	}

	public ArrayList<String[]> ReadQuestion() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM Questions");
		ArrayList<String[]> list = new ArrayList<String[]>();
		while (resSet.next()) {
			String[] s = new String[] { resSet.getString("IdQuestion"), resSet.getString("IdDiscipline"),
					resSet.getString("TextQuestion") };
			list.add(s);
		}
		System.out.println("Таблица Questions выведена");
		return list;
	}

	public ArrayList<String[]> ReadQuestionWHERE(int DisciplinId) throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM Questions WHERE IdDiscipline=" + DisciplinId);
		ArrayList<String[]> list = new ArrayList<String[]>();
		while (resSet.next()) {
			String[] s = new String[] { resSet.getString("IdQuestion"), resSet.getString("IdDiscipline"),
					resSet.getString("TextQuestion") };
			list.add(s);
		}
		System.out.println("Таблица Question выведена");
		return list;
	}

	public String[] ReadQuestionWHEREidQuestion(int IdQuestion) throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM Questions WHERE IdQuestion=" + IdQuestion);
		String[] list = new String[] { resSet.getString("IdQuestion"), resSet.getString("IdDiscipline"),
				resSet.getString("TextQuestion") };
		System.out.println("Таблица Questions выведена");
		return list;
	}

	public ArrayList<String[]> ReadTicket() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM Tickets");
		ArrayList<String[]> list = new ArrayList<String[]>();
		while (resSet.next()) {
			String[] s = new String[] { resSet.getString("IdTicket"), resSet.getString("IdDiscipline"),
					resSet.getString("IdGroup"), resSet.getString("IdQuestion") };
			list.add(s);
		}
		System.out.println("Таблица Tickets выведена");
		return list;
	}

	public ArrayList<String[]> ReadTicketWHERE(int DisciplinId, int GroupId)
			throws ClassNotFoundException, SQLException {
		resSet = statmt
				.executeQuery("SELECT * FROM Tickets WHERE IdDiscipline=" + DisciplinId + " AND IdGroup=" + GroupId);
		ArrayList<String[]> list = new ArrayList<String[]>();
		while (resSet.next()) {
			String[] s = new String[] { resSet.getString("IdTicket"), resSet.getString("IdDiscipline"),
					resSet.getString("IdGroup"), resSet.getString("IdQuestion"), resSet.getString("TicketNumber") };
			list.add(s);
		}
		System.out.println("Таблица выведена");
		return list;
	}

	public void CloseDB() throws ClassNotFoundException, SQLException {
		conn.close();
		resSet.close();
		System.out.println("Соединения закрыты");
	}
}
