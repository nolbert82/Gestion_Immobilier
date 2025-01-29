package modele.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import modele.Immeuble;
import modele.dao.requetes.create.RequeteCreateImmeuble;
import modele.dao.requetes.delete.RequeteDeleteImmeuble;
import modele.dao.requetes.select.RequeteSelectImmeuble;
import modele.dao.requetes.select.RequeteSelectImmeubleByID;
import modele.dao.requetes.update.RequeteUpdateImmeuble;

public class DaoImmeuble implements Dao<Immeuble> {

    private Connection connection;

    public DaoImmeuble(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Immeuble donnees) throws SQLException {
        RequeteCreateImmeuble requeteCreate = new RequeteCreateImmeuble();
        try (PreparedStatement prSt = connection.prepareStatement(requeteCreate.requete())) {
        	requeteCreate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void update(Immeuble donnees) throws SQLException {
        RequeteUpdateImmeuble requeteUpdate = new RequeteUpdateImmeuble();
        try (PreparedStatement prSt = connection.prepareStatement(requeteUpdate.requete())) {
            requeteUpdate.parametres(prSt, donnees);
            prSt.executeUpdate();
        }
    }

    @Override
    public void delete(String... id) throws SQLException {
        RequeteDeleteImmeuble requeteDelete = new RequeteDeleteImmeuble();
        try (PreparedStatement prSt = connection.prepareStatement(requeteDelete.requete())) {
            requeteDelete.parametres(prSt, id);
            prSt.executeUpdate();
        }
    }

    @Override
    public Immeuble findById(String... id) throws SQLException {
        RequeteSelectImmeubleByID requeteSelectById = new RequeteSelectImmeubleByID();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectById.requete())) {
            requeteSelectById.parametres(prSt, id);
            try (ResultSet rs = prSt.executeQuery()) {
                if (rs.next()) {
                    return new Immeuble(
                        rs.getInt("Id_Immeuble"),
                        rs.getString("Adresse")
                    );
                }
            }
        }
        return null; // Retourne null si aucun immeuble trouvé
    }

    @Override
    public List<Immeuble> findAll() throws SQLException {
        RequeteSelectImmeuble requeteSelectAll = new RequeteSelectImmeuble();
        List<Immeuble> immeubles = new ArrayList<>();
        try (PreparedStatement prSt = connection.prepareStatement(requeteSelectAll.requete());
             ResultSet rs = prSt.executeQuery()) {
            while (rs.next()) {
                immeubles.add(new Immeuble(
                    rs.getInt("Id_Immeuble"),
                    rs.getString("Adresse")
                ));
            }
        }
        return immeubles;
    }
    
    public double totalChargesImmeuble(int idImmeuble) throws SQLException {
        String sql = "{ ? = call TOTALChargesImmeuble(?) }";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.DOUBLE);
            stmt.setInt(2, idImmeuble);
            stmt.execute();
            return stmt.getDouble(1);
        }
    }

    public double totalLoyersPayes(int idImmeuble) throws SQLException {
        String sql = "{ ? = call TotalLoyersPayes(?) }";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.DOUBLE);
            stmt.setInt(2, idImmeuble);
            stmt.execute();
            return stmt.getDouble(1);
        }
    }

    public double totalDesTravauxImmeuble(int idImmeuble) throws SQLException {
        String sql = "{ ? = call TotalDesTravauxImmeuble(?) }";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.DOUBLE);
            stmt.setInt(2, idImmeuble);
            stmt.execute();
            return stmt.getDouble(1);
        }
    }

    public double facturesImpayeParImmeuble(int idImmeuble) throws SQLException {
        String sql = "{ ? = call FacturesImpayeParImmeuble(?) }";
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.registerOutParameter(1, Types.DOUBLE);
            stmt.setInt(2, idImmeuble);
            stmt.execute();
            return stmt.getDouble(1);
        }
    }


}
