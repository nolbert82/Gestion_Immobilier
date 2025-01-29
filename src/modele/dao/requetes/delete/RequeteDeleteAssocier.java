package modele.dao.requetes.delete;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Associer;
import modele.dao.requetes.Requete;

public class RequeteDeleteAssocier implements Requete<Associer> {

	@Override
	public String requete() {
		return "DELETE From Associer WHERE Id_Louable = ? AND Id_Index_Compteur = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... ids) throws SQLException {
		prSt.setString(1, ids[0]);
		prSt.setString(2, ids[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Associer donnee) throws SQLException {
		return;
	}
}
