package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Associer;
import modele.dao.requetes.create.RequeteCreateAssocier;
import modele.dao.requetes.delete.RequeteDeleteAssocier;
import modele.dao.requetes.delete.RequeteDeleteAssocierByIndexCompteur;
import modele.dao.requetes.delete.RequeteDeleteAssocierByLouable;
import modele.dao.requetes.select.RequeteSelectAssocier;
import modele.dao.requetes.select.RequeteSelectAssocierByIndex;
import modele.dao.requetes.select.RequeteSelectAssocierByLouable;
import modele.dao.requetes.update.RequeteUpdateAssocier;

public class DaoAssocier implements Dao<Associer> {

    private Connection connection;

    public DaoAssocier(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Associer donnees) throws SQLException {
        RequeteCreateAssocier requeteCreate = new RequeteCreateAssocier();
        try (PreparedStatement prSt = connection.prepareStatement(requeteCreate.requete())) {
        	requeteCreate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void update(Associer donnees) throws SQLException {
        RequeteUpdateAssocier requeteUpdate = new RequeteUpdateAssocier();
        try (PreparedStatement prSt = connection.prepareStatement(requeteUpdate.requete())) {
            requeteUpdate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void delete(String... id) throws SQLException {
        RequeteDeleteAssocier requeteDelete = new RequeteDeleteAssocier();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }
    
    public void deleteById(String... id) throws SQLException {
        RequeteDeleteAssocier requeteDelete = new RequeteDeleteAssocier();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }
    
    public void deleteByLouable(String... id) throws SQLException {
        RequeteDeleteAssocierByLouable requeteDelete = new RequeteDeleteAssocierByLouable();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }

    public void deleteByIndexCompteur(String... id) throws SQLException {
        RequeteDeleteAssocierByIndexCompteur requeteDelete = new RequeteDeleteAssocierByIndexCompteur();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }


    // Inutile
    @Override
    public Associer findById(String... id) throws SQLException {
        return null;
    }


    @Override
    public List<Associer> findAll() throws SQLException {
        RequeteSelectAssocier requeteSelectAll = new RequeteSelectAssocier();
        List<Associer> associer = new ArrayList<>();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectAll.requete());
            ResultSet rs = prSt.executeQuery()) {
            while (rs.next()) {
                associer.add(new Associer(
                		rs.getInt("Id_Louable"),
                        rs.getInt("Id_Index_Compteur"),
                        rs.getDate("DateReleve"),
                        rs.getDouble("PrixAbonnement"),
                        rs.getDate("DateRegularisation")
                ));
            }
        }
        return associer;
    }
    
    public List<Associer> findByIndexCompteur(String... id) throws SQLException {
    	RequeteSelectAssocierByIndex requeteSelectIndex = new RequeteSelectAssocierByIndex();
    	List<Associer> associerList = new ArrayList<>();
        try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectIndex.requete())) {
        	requeteSelectIndex.parametres(prSt, id);
        	try (ResultSet rs = prSt.executeQuery()) {
                while (rs.next()) {
                    associerList.add(new Associer(
                            rs.getInt("Id_Louable"),
                            rs.getInt("Id_Index_Compteur"),
                            rs.getDate("DateReleve"),
                            rs.getDouble("PrixAbonnement"),
                            rs.getDate("DateRegularisation")));
                }
            }
        }
        return associerList;
    }
    
    public List<Associer> findByLouable(String... id) throws SQLException {
    	RequeteSelectAssocierByLouable requeteSelectLouable = new RequeteSelectAssocierByLouable();
        List<Associer> associerList = new ArrayList<>();
        try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectLouable.requete())) {
        	requeteSelectLouable.parametres(prSt, id);
        	try (ResultSet rs = prSt.executeQuery()) {
                while (rs.next()) {
                    associerList.add(new Associer(
                            rs.getInt("Id_Louable"),
                            rs.getInt("Id_Index_Compteur"),
                            rs.getDate("DateReleve"),
                            rs.getDouble("PrixAbonnement"),
                            rs.getDate("DateRegularisation")));
                }
            }
        }
        return associerList;
    }


}
