package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Apparaitre;
import modele.dao.requetes.Requete;


public class RequeteSelectIndexer implements Requete<Apparaitre> {

		@Override
		public String requete() {
			return "SELECT * FROM Indexer";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Apparaitre data) throws SQLException {
		}
	}