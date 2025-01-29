package modele.dao.requetes.select;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Colocataire;
import modele.dao.requetes.Requete;


public class RequeteSelectColocataire implements Requete<Colocataire> {

		@Override
		public String requete() {
			return "SELECT * FROM Locataire";
		}

		@Override
		public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		}

		@Override
		public void parametres(PreparedStatement prSt, Colocataire data) throws SQLException {
		}
	}