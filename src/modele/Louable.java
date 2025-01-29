package modele;

import java.util.Objects;

public class Louable {
    protected int idLouable;
    private String typeLouable;
    private String adresse;
    private double superficie;
    private String numeroFiscal;
    private String statut;
    private java.util.Date dateAnniversaire;
    private java.util.Date dateAcquisition;
    private int nbPieces;
    private Immeuble immeuble; // Relation avec Immeuble
    private Assureur assureur; // Relation avec Assureur

    public Louable(int idLouable, String typeLouable, String adresse, double superficie, String numeroFiscal, String statut,
                   java.util.Date dateAnniversaire, java.util.Date dateAcquisition, int nbPieces, Immeuble immeuble, Assureur assureur) {
        this.idLouable = idLouable;
        this.typeLouable = typeLouable;
        this.adresse = adresse;
        this.superficie = superficie;
        this.numeroFiscal = numeroFiscal;
        this.statut = statut;
        this.dateAnniversaire = dateAnniversaire;
        this.dateAcquisition = dateAcquisition;
        this.immeuble = immeuble;
        this.assureur = assureur;
        this.nbPieces = nbPieces;
    }

	public int getIdLouable() {
		return idLouable;
	}

	public void setIdLouable(int idLouable) {
		this.idLouable = idLouable;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public String getTypeLouable() {
		return typeLouable;
	}

	public void settypeLouable(String typeLouable) {
		this.typeLouable = typeLouable;
	}

	public double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(double superficie) {
	    if (superficie > 99999.999) {
	        throw new IllegalArgumentException("La superficie ne peut pas dépasser 99999.999");
	    }
	    this.superficie = Math.round(superficie * 1000.0) / 1000.0; // Arrondir à 3 décimales
	}

	public String getNumeroFiscal() {
		return numeroFiscal;
	}

	public void setNumeroFiscal(String numeroFiscal) {
	    if (String.valueOf(numeroFiscal).length() > 12) {
	        throw new IllegalArgumentException("NumeroFiscal ne peut pas dépasser 12 chiffres");
	    }
	    this.numeroFiscal = numeroFiscal;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public java.util.Date getDateAnniversaire() {
		return dateAnniversaire;
	}

	public void setDateAnniversaire(java.util.Date dateAnniversaire) {
		this.dateAnniversaire = dateAnniversaire;
	}

	public Assureur getAssureur() {
		return assureur;
	}

	public void setAssureur(Assureur assureur) {
		this.assureur = assureur;
	}

	public Immeuble getImmeuble() {
		return immeuble;
	}

	public void setImmeuble(Immeuble immeuble) {
		this.immeuble = immeuble;
	}

	public java.util.Date getDateAcquisition() {
		return dateAcquisition;
	}

	public void setDateAcquisition(java.util.Date dateAcquisition) {
		this.dateAcquisition = dateAcquisition;
	}
	
	public int getNbPieces() {
		return nbPieces;
	}

	public void setNbPieces(int nbPieces) {
		this.nbPieces = nbPieces;
	}

	@Override
	public String toString() {
		return "" + idLouable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(adresse, assureur, dateAcquisition, dateAnniversaire, idLouable, immeuble, nbPieces,
				numeroFiscal, statut, superficie, typeLouable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Louable)) {
			return false;
		}
		Louable other = (Louable) obj;
		return Objects.equals(adresse, other.adresse) && Objects.equals(assureur, other.assureur)
				&& Objects.equals(dateAcquisition, other.dateAcquisition)
				&& Objects.equals(dateAnniversaire, other.dateAnniversaire) && idLouable == other.idLouable
				&& Objects.equals(immeuble, other.immeuble) && nbPieces == other.nbPieces
				&& Objects.equals(numeroFiscal, other.numeroFiscal) && Objects.equals(statut, other.statut)
				&& Double.doubleToLongBits(superficie) == Double.doubleToLongBits(other.superficie)
				&& Objects.equals(typeLouable, other.typeLouable);
	}



}
