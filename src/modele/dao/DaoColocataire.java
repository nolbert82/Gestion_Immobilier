package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Colocataire;
import modele.Locataire;
import modele.dao.requetes.create.RequeteCreateColocataire;
import modele.dao.requetes.delete.RequeteDeleteColocataire;
import modele.dao.requetes.delete.RequeteDeleteColocataireByLocataire;
import modele.dao.requetes.delete.RequeteDeleteLocataire;
import modele.dao.requetes.select.RequeteSelectColocataire;
import modele.dao.requetes.select.RequeteSelectColocataireByLocataire;
import modele.dao.requetes.select.RequeteSelectColocataireByLocataire1;
import modele.dao.requetes.update.RequeteUpdateColocataire;

public class DaoColocataire implements Dao<Colocataire> {

	private Connection connection;

	public DaoColocataire(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Colocataire donnees) throws SQLException {
		RequeteCreateColocataire requeteCreate = new RequeteCreateColocataire();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}
	@Override
    public void update(Colocataire donnees) throws SQLException {
        RequeteUpdateColocataire requeteUpdate = new RequeteUpdateColocataire();
        try (PreparedStatement prSt = connection.prepareStatement(requeteUpdate.requete())) {
            requeteUpdate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void delete(String... id) throws SQLException {
        RequeteDeleteColocataire requeteDelete = new RequeteDeleteColocataire();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }
    
    public void deleteByLocataire(String... id) throws SQLException {
        RequeteDeleteColocataireByLocataire requeteDelete = new RequeteDeleteColocataireByLocataire();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }


    // fonction inutile
    @Override
    public Colocataire findById(String... id) throws SQLException {
        return null;
    }

    @Override
    public List<Colocataire> findAll() throws SQLException {
        RequeteSelectColocataire requeteSelectAll = new RequeteSelectColocataire();
        List<Colocataire> immeubles = new ArrayList<>();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectAll.requete());
             ResultSet rs = prSt.executeQuery()) {
            while (rs.next()) {
                immeubles.add(new Colocataire(
                    rs.getString("Id_Locataire"),
                    rs.getString("Id_Locataire")
                ));
            }
        }
        return immeubles;
    }
    
    public List<Colocataire> findByLocataire(String... id) throws SQLException {
    	RequeteSelectColocataireByLocataire requeteSelectLocataire = new RequeteSelectColocataireByLocataire();
	    List<Colocataire> colocataires = new ArrayList<>();
	    try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectLocataire.requete())) {
	    	requeteSelectLocataire.parametres(prSt, id);
	        try (ResultSet rs = prSt.executeQuery()) {
	            while (rs.next()) {
	                colocataires.add(new Colocataire(
	                        rs.getString("Id_Locataire"),
	                        rs.getString("Id_Locataire_1")));
	            }
	        }
	    }	    
	    RequeteSelectColocataireByLocataire1 requeteSelectLocataire1 = new RequeteSelectColocataireByLocataire1();
	    try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectLocataire1.requete())) {
	    	requeteSelectLocataire.parametres(prSt, id);
	        try (ResultSet rs = prSt.executeQuery()) {
	            while (rs.next()) {
	                colocataires.add(new Colocataire(
	                        rs.getString("Id_Locataire_1"),
	                        rs.getString("Id_Locataire")));
	            }
	        }
	    }
	    return colocataires;
	}
    
	public void deleteByLocataire(Locataire donnees) throws SQLException {
		RequeteDeleteLocataire requeteDelete = new RequeteDeleteLocataire();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDelete.requete())) {
			requeteDelete.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}
}
