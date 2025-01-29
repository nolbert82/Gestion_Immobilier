package modele;

import java.util.Objects;

public class ContratDeLocation {
    private int idContratDeLocation;
    private java.util.Date dateDebut;
    private java.util.Date dateFin;
    private double montantLoyer;
    private double provisionsCharges;
    private String typeContrat;
    private java.util.Date dateAnniversaire;
    private java.util.Date dateDerniereRegularisation;
    private double indiceICC;
    private double montantCaution;
    private String nomCaution;
    private String adresseCaution;

    private Louable louable; // Relation avec Louable

    public ContratDeLocation(int idContratDeLocation, java.util.Date dateDebut, java.util.Date dateFin, double montantLoyer,
                              double provisionsCharges, String typeContrat, java.util.Date dateAnniversaire, java.util.Date dateDerniereRegularisation, double indiceICC,
                              double montantCaution, String nomCaution, String adresseCaution, Louable louable) {
        this.idContratDeLocation = idContratDeLocation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montantLoyer = montantLoyer;
        this.provisionsCharges = provisionsCharges;
        this.typeContrat = typeContrat;
        this.dateAnniversaire = dateAnniversaire;
        this.dateDerniereRegularisation = dateDerniereRegularisation;
        this.indiceICC = indiceICC;
        this.montantCaution = montantCaution;
        this.louable = louable;
        this.nomCaution = nomCaution;
        this.adresseCaution = adresseCaution;
    }

	public int getIdContratDeLocation() {
		return idContratDeLocation;
	}

	public void setIdContratDeLocation(int idContratDeLocation) {
		this.idContratDeLocation = idContratDeLocation;
	}

	public java.util.Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(java.util.Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public java.util.Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(java.util.Date dateFin) {
		this.dateFin = dateFin;
	}

	public double getMontantLoyer() {
		return montantLoyer;
	}

	public void setMontantLoyer(double montantLoyer) {
		this.montantLoyer = montantLoyer;
	}

	public double getProvisionsCharges() {
		return provisionsCharges;
	}

	public void setProvisionsCharges(double provisionsCharges) {
		this.provisionsCharges = provisionsCharges;
	}

	public String getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public java.util.Date getDateAnniversaire() {
		return dateAnniversaire;
	}

	public void setDateAnniversaire(java.util.Date dateAnniversaire) {
		this.dateAnniversaire = dateAnniversaire;
	}
	
	public java.util.Date getDateDerniereRegularisation() {
		return dateDerniereRegularisation;
	}

	public void setDateDerniereRegularisation(java.util.Date dateDerniereRegularisation) {
		this.dateDerniereRegularisation = dateDerniereRegularisation;
	}

	public double getIndiceICC() {
		return indiceICC;
	}

	public void setIndiceICC(double indiceICC) {
		this.indiceICC = indiceICC;
	}

	public double getMontantCaution() {
		return montantCaution;
	}

	public void setMontantCaution(double montantCaution) {
		this.montantCaution = montantCaution;
	}

	public Louable getLouable() {
		return louable;
	}

	public void setLouable(Louable louable) {
		this.louable = louable;
	}

	public String getNomCaution() {
		return nomCaution;
	}

	public void setNomCaution(String nomCaution) {
		this.nomCaution = nomCaution;
	}

	public String getAdresseCaution() {
		return adresseCaution;
	}

	public void setAdresseCaution(String adresseCaution) {
		this.adresseCaution = adresseCaution;
	}

	@Override
	public String toString() {
		return "" + idContratDeLocation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(adresseCaution, dateDerniereRegularisation, nomCaution, dateAnniversaire, dateDebut,
				dateFin, idContratDeLocation, indiceICC, louable, montantCaution, montantLoyer, provisionsCharges,
				typeContrat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ContratDeLocation)) {
			return false;
		}
		ContratDeLocation other = (ContratDeLocation) obj;
		return Objects.equals(adresseCaution, other.adresseCaution)
				&& Objects.equals(dateDerniereRegularisation, other.dateDerniereRegularisation)
				&& Objects.equals(nomCaution, other.nomCaution)
				&& Objects.equals(dateAnniversaire, other.dateAnniversaire)
				&& Objects.equals(dateDebut, other.dateDebut) && Objects.equals(dateFin, other.dateFin)
				&& idContratDeLocation == other.idContratDeLocation
				&& Double.doubleToLongBits(indiceICC) == Double.doubleToLongBits(other.indiceICC)
				&& Objects.equals(louable, other.louable)
				&& Double.doubleToLongBits(montantCaution) == Double.doubleToLongBits(other.montantCaution)
				&& Double.doubleToLongBits(montantLoyer) == Double.doubleToLongBits(other.montantLoyer)
				&& Double.doubleToLongBits(provisionsCharges) == Double.doubleToLongBits(other.provisionsCharges)
				&& Objects.equals(typeContrat, other.typeContrat);
	}



}
