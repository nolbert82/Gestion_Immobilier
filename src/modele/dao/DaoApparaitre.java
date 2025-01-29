package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Apparaitre;
import modele.dao.requetes.create.RequeteCreateApparaitre;
import modele.dao.requetes.delete.RequeteDeleteApparaitre;
import modele.dao.requetes.select.RequeteSelectApparaitre;
import modele.dao.requetes.select.RequeteSelectApparaitreByCharge;
import modele.dao.requetes.select.RequeteSelectApparaitreByIndex;
import modele.dao.requetes.update.RequeteUpdateApparaitre;
import modele.dao.requetes.delete.RequeteDeleteApparaitreByCharge;
import modele.dao.requetes.delete.RequeteDeleteApparaitreByIndexCompteur;

public class DaoApparaitre implements Dao<Apparaitre> {

    private Connection connection;

    public DaoApparaitre(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Apparaitre donnees) throws SQLException {
        RequeteCreateApparaitre requeteCreate = new RequeteCreateApparaitre();
        try (PreparedStatement prSt = connection.prepareStatement(requeteCreate.requete())) {
        	requeteCreate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void update(Apparaitre donnees) throws SQLException {
        RequeteUpdateApparaitre requeteUpdate = new RequeteUpdateApparaitre();
        try (PreparedStatement prSt = connection.prepareStatement(requeteUpdate.requete())) {
            requeteUpdate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void delete(String... id) throws SQLException {
        RequeteDeleteApparaitre requeteDelete = new RequeteDeleteApparaitre();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }
    
    public void deleteByCharge(String... id) throws SQLException {
    	RequeteDeleteApparaitreByCharge requeteDelete = new RequeteDeleteApparaitreByCharge();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }
    
    public void deleteByIndexCompteur(String... id) throws SQLException {
        RequeteDeleteApparaitreByIndexCompteur requeteDelete = new RequeteDeleteApparaitreByIndexCompteur();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }


	// Cette methode est inutile, car elle renvoie exactement les parametres de la requete
    @Override
    public Apparaitre findById(String... id) throws SQLException {
        return null;
    }

    @Override
    public List<Apparaitre> findAll() throws SQLException {
        RequeteSelectApparaitre requeteSelectAll = new RequeteSelectApparaitre();
        List<Apparaitre> apparaitre = new ArrayList<>();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectAll.requete());
             ResultSet rs = prSt.executeQuery()) {
            while (rs.next()) {
                apparaitre.add(new Apparaitre(
                        rs.getInt("Id_Charge"),
                        rs.getInt("Id_Index_Compteur")
                ));
            }
        }
        return apparaitre;
    }
    
    public List<Apparaitre> findByCharge(String... id) throws SQLException {
    	RequeteSelectApparaitreByCharge requeteSelectByCharge = new RequeteSelectApparaitreByCharge();
        List<Apparaitre> apparaitreList = new ArrayList<>();
        try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectByCharge.requete())) {
        	requeteSelectByCharge.parametres(prSt, id);
        	try (ResultSet rs = prSt.executeQuery()) {
                while (rs.next()) {
                    apparaitreList.add(new Apparaitre(
                            rs.getInt("Id_Charge"),
                            rs.getInt("Id_Index_Compteur")));
                }
            }
        }
        return apparaitreList;
    }
    
    public List<Apparaitre> findByIndex(String... id) throws SQLException {
    	RequeteSelectApparaitreByIndex requeteSelectByIndex = new RequeteSelectApparaitreByIndex();
        List<Apparaitre> apparaitreList = new ArrayList<>();
        try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectByIndex.requete())) {
            prSt.setInt(1, Integer.parseInt(id[0]));
            try (ResultSet rs = prSt.executeQuery()) {
                while (rs.next()) {
                    apparaitreList.add(new Apparaitre(
                            rs.getInt("Id_Charge"),
                            rs.getInt("Id_Index_Compteur")));
                }
            }
        }
        return apparaitreList;
    }

    
}
