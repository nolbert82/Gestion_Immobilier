package modele.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import modele.Assureur;
import modele.Immeuble;
import modele.Locataire;
import modele.Louable;
import modele.dao.requetes.create.RequeteCreateLouable;
import modele.dao.requetes.delete.RequeteDeleteLouable;
import modele.dao.requetes.update.RequeteUpdateLouable;
import modele.dao.requetes.select.RequeteSelectLouableByID;
import modele.dao.requetes.select.RequeteSelectLouableByImmeuble;
import modele.dao.requetes.select.RequeteSelectLocataireByID;
import modele.dao.requetes.select.RequeteSelectLouable;

public class DaoLouable implements Dao<Louable> {

	private Connection connection;

	public DaoLouable(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Louable donnees) throws SQLException {
		RequeteCreateLouable requeteCreate = new RequeteCreateLouable();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteCreate.requete())) {
			requeteCreate.parametres(prSt, donnees);
			prSt.executeUpdate();
			}
	}

	@Override
	public void update(Louable donnees) throws SQLException {
		RequeteUpdateLouable requeteUpdate = new RequeteUpdateLouable();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteUpdate.requete())) {
			requeteUpdate.parametres(prSt, donnees);
			prSt.executeUpdate();
		}
	}

	@Override
	public void delete(String... id) throws SQLException {
		RequeteDeleteLouable requeteDeleteLouable = new RequeteDeleteLouable();
		try (PreparedStatement prSt = this.connection.prepareStatement(requeteDeleteLouable.requete())) {
			requeteDeleteLouable.parametres(prSt, id);
			prSt.executeUpdate();
		}
	}

	@Override
	public Louable findById(String... id) throws SQLException {
	    RequeteSelectLouableByID requeteSelectById = new RequeteSelectLouableByID();
	    DaoAssureur daoAssureur = new DaoAssureur(this.connection);
	    DaoImmeuble daoImmeuble = new DaoImmeuble(this.connection);

	    try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectById.requete())) {
	        requeteSelectById.parametres(prSt, id);
	        try (ResultSet rs = prSt.executeQuery()) {
	            if (rs.next()) {
	            	
	                int idAssureur = rs.getInt("Id_Assureur");
	                int idImmeuble = rs.getInt("Id_Immeuble");
	                Assureur assureur = daoAssureur.findById(String.valueOf(idAssureur));
	                Immeuble immeuble = daoImmeuble.findById(String.valueOf(idImmeuble));
	                
	                return new Louable(
	                    rs.getInt("Id_Louable"),
	                    rs.getString("TypeLouable"),
	                    rs.getString("Adresse"),
	                    rs.getDouble("Superficie"),
	                    rs.getString("NumeroFiscal"),
	                    rs.getString("Statut"),
	                    rs.getDate("DateAnniversaire"),
	                    rs.getDate("DateAcquisition"),
	                    rs.getInt("NbPieces"),
	                    immeuble,
	                    assureur
	                );
	            }
	        }
	    }
	    return null;
	}
	    


	@Override
	public List<Louable> findAll() throws SQLException {
		RequeteSelectLouable requeteSelectAll = new RequeteSelectLouable();
		List<Louable> logements = new ArrayList<>();
	    DaoImmeuble daoImmeuble = new DaoImmeuble(this.connection);
	    DaoAssureur daoAssureur = new DaoAssureur(this.connection);

		try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectAll.requete());
				ResultSet rs = prSt.executeQuery()) {
			while (rs.next()) {
				
				int idImmeuble = rs.getInt("Id_Immeuble");
                int idAssureur = rs.getInt("Id_Assureur");
                Immeuble immeuble = daoImmeuble.findById(String.valueOf(idImmeuble));
                Assureur assureur = daoAssureur.findById(String.valueOf(idAssureur));

				logements.add(new Louable(
						rs.getInt("Id_Louable"), 
						rs.getString("TypeLouable"), 
						rs.getString("Adresse"), 
						rs.getDouble("Superficie"), 
						rs.getString("NumeroFiscal"), 
						rs.getString("Statut"), 
						rs.getDate("DateAnniversaire"),
						rs.getDate("DateAcquisition"), 
						rs.getInt("NbPieces"),
						immeuble, 
						assureur));
			}
        }
        return logements;
    }
	
	public List<Louable> findByImmeuble(String... id) throws SQLException {
	    RequeteSelectLouableByImmeuble requeteSelectByImmeuble = new RequeteSelectLouableByImmeuble();
	    DaoAssureur daoAssureur = new DaoAssureur(this.connection);
	    DaoImmeuble daoImmeuble = new DaoImmeuble(this.connection);
	    List<Louable> louables = new ArrayList<>(); 

	    try (PreparedStatement prSt = this.connection.prepareStatement(requeteSelectByImmeuble.requete())) {
	        requeteSelectByImmeuble.parametres(prSt, id);

	        try (ResultSet rs = prSt.executeQuery()) {
	            while (rs.next()) {
	                int idAssureur = rs.getInt("Id_Assureur");
	                int idImmeuble = rs.getInt("Id_Immeuble");

	                Assureur assureur = daoAssureur.findById(String.valueOf(idAssureur));
	                Immeuble immeuble = daoImmeuble.findById(String.valueOf(idImmeuble));

	                Louable louable = new Louable(
	                    rs.getInt("Id_Louable"),
	                    rs.getString("TypeLouable"),
	                    rs.getString("Adresse"),
	                    rs.getDouble("Superficie"),
	                    rs.getString("NumeroFiscal"),
	                    rs.getString("Statut"),
	                    rs.getDate("DateAnniversaire"),
	                    rs.getDate("DateAcquisition"),
	                    rs.getInt("NbPieces"),
	                    immeuble,
	                    assureur
	                );
	                louables.add(louable);
	            }
	        }
	    }
	    return louables;
	}

	
	public double prixConsoLogement(int idLouable) throws SQLException {
	    String sql = "{ ? = call PrixConsoLogement(?) }";
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, Types.DOUBLE);
	        stmt.setInt(2, idLouable);
	        stmt.execute();
	        return stmt.getDouble(1);
	    }
	}

	public double totalOrduresMenageres(int idLouable) throws SQLException {
	    String sql = "{ ? = call TotalOrduresMenageres(?) }";
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, Types.DOUBLE);
	        stmt.setInt(2, idLouable);
	        stmt.execute();
	        return stmt.getDouble(1);
	    }
	}

	public double calculerEntretienMenager(int idLouable) throws SQLException {
	    String sql = "{ ? = call CalculerEntretienMenager(?) }";
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, Types.DOUBLE);
	        stmt.setInt(2, idLouable);
	        stmt.execute();
	        return stmt.getDouble(1);
	    }
	}

	public double totalDesTravauxBien(int idLouable) throws SQLException {
	    String sql = "{ ? = call TotalDesTravauxBien(?) }";
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, Types.DOUBLE);
	        stmt.setInt(2, idLouable);
	        stmt.execute();
	        return stmt.getDouble(1);
	    }
	}

	public double calculerSommeLoyersImpayes(int idLouable) throws SQLException {
	    String sql = "{ ? = call CalculerSommeLoyersImpayes(?) }";
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, Types.DOUBLE);
	        stmt.setInt(2, idLouable);
	        stmt.execute();
	        return stmt.getDouble(1);
	    }
	}

	public double calculerChargesRecuperables(int idLouable) throws SQLException {
	    String sql = "{ ? = call CalculerChargesRecuperables(?) }";
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, Types.DOUBLE);
	        stmt.setInt(2, idLouable);
	        stmt.execute();
	        return stmt.getDouble(1);
	    }
	}

	public double calculerRegularisationCharges(int idLouable) throws SQLException {
	    String sql = "{ ? = call CalculerRegularisationCharges(?) }";
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, Types.DOUBLE);
	        stmt.setInt(2, idLouable);
	        stmt.execute();
	        return stmt.getDouble(1);
	    }
	}

	public double calculerSoldeDeToutCompte(int idLouable) throws SQLException {
	    String sql = "{ ? = call CalculerSoldeDeToutCompte(?) }";
	    try (CallableStatement stmt = connection.prepareCall(sql)) {
	        stmt.registerOutParameter(1, Types.DOUBLE);
	        stmt.setInt(2, idLouable);
	        stmt.execute();
	        return stmt.getDouble(1);
	    }
	}
	
	public List<Object[]> detecterLoyersImpayes() throws SQLException {
	    List<Object[]> loyersImpayes = new ArrayList<>();

	    String sql = "SELECT * FROM TABLE(DetecterLoyersImpayes)";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                loyersImpayes.add(new Object[]{
	                    rs.getInt("ID_LOUABLE"),
	                    rs.getString("ADRESSE"),
	                    rs.getDate("DATE_LOYER_IMPAYE"),
	                    rs.getString("STATUT_PAIEMENT")
	                });
	            }
	        }
	    }
	    return loyersImpayes;
	}
}