package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modele.*;
import modele.dao.*;
import vue.AffichageDonnees;

public class GestionSelecteur implements ActionListener {

    private AffichageDonnees fenetreAffichageDonnees;
    

    public GestionSelecteur(AffichageDonnees fenetreAffichageDonnees) {
        this.fenetreAffichageDonnees = fenetreAffichageDonnees;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
            Object selectedItem = comboBox.getSelectedItem();
            ElementsSelectionnables selection = (ElementsSelectionnables) selectedItem;

            if (selection == null) {
                return;
            }
            
            afficherBoutonsSelonSelection(selection);
            
            
            // Pour la table selectionnée, appelle la fonction afficherDonnees en passant le dao correspondant
            try {
                switch (selection) {
                case LOCATAIRE:
                    DaoLocataire daoLocataire = new DaoLocataire(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoLocataire, Locataire.class);
                    fenetreAffichageDonnees.setDao(daoLocataire);
                    break;
                case INDEX_COMPTEUR:
                    DaoIndexCompteur daoIndexCompteur = new DaoIndexCompteur(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoIndexCompteur, IndexCompteur.class);
                    fenetreAffichageDonnees.setDao(daoIndexCompteur);
                    break;
                case ASSUREUR:
                    DaoAssureur daoAssureur = new DaoAssureur(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoAssureur, Assureur.class);
                    fenetreAffichageDonnees.setDao(daoAssureur);
                    break;
                case ENTREPRISE:
                    DaoEntreprise daoEntreprise = new DaoEntreprise(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoEntreprise, Entreprise.class);
                    fenetreAffichageDonnees.setDao(daoEntreprise);
                    break;
                case IMMEUBLE:
                    DaoImmeuble daoImmeuble = new DaoImmeuble(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoImmeuble, Immeuble.class);
                    fenetreAffichageDonnees.setDao(daoImmeuble);
                    break;
                case TAXE:
                    DaoTaxe daoTaxe = new DaoTaxe(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoTaxe, Taxe.class);
                    fenetreAffichageDonnees.setDao(daoTaxe);
                    break;
                case FACTURE:
                    DaoFacture daoFacture = new DaoFacture(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoFacture, Facture.class);
                    fenetreAffichageDonnees.setDao(daoFacture);
                    break;
                case DIAGNOSTIC:
                    DaoDiagnostic daoDiagnostic = new DaoDiagnostic(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoDiagnostic, Diagnostic.class);
                    fenetreAffichageDonnees.setDao(daoDiagnostic);
                    break;
                case CONTRAT_DE_LOCATION:
                    DaoContratDeLocation daoContratDeLocation = new DaoContratDeLocation(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoContratDeLocation, ContratDeLocation.class);
                    fenetreAffichageDonnees.setDao(daoContratDeLocation);
                    break;
                case CHARGE:
                    DaoCharge daoCharge = new DaoCharge(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoCharge, Charge.class);
                    fenetreAffichageDonnees.setDao(daoCharge);
                    break;
                case QUITTANCES:
                    DaoQuittances daoQuittances = new DaoQuittances(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoQuittances, Quittances.class);
                    fenetreAffichageDonnees.setDao(daoQuittances);
                    break;
                case LOUABLE:
                    DaoLouable daoLouable = new DaoLouable(CictOracleDataSource.getConnectionBD());
                    afficherDonnees(daoLouable, Louable.class);
                    fenetreAffichageDonnees.setDao(daoLouable);
                    break;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(fenetreAffichageDonnees,
                    "Erreur lors de l'accès à la base de données : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Sert a afficher les boutons pour generer les rapports en fonction de l'element selectionné
    private void afficherBoutonsSelonSelection(ElementsSelectionnables selection) {
        // Rendre visibles ou invisibles les boutons selon la sélection
        boolean isImmeuble = selection == ElementsSelectionnables.IMMEUBLE;
        boolean isLouable = selection == ElementsSelectionnables.LOUABLE;

        // Gérer les boutons pour "Immeuble"
        fenetreAffichageDonnees.getBtnADI().setVisible(isImmeuble);
        fenetreAffichageDonnees.getBtnDF().setVisible(isImmeuble);
        fenetreAffichageDonnees.getBtnDO().setVisible(isImmeuble);

        // Gérer les boutons pour "Louable"
        fenetreAffichageDonnees.getBtnSTC().setVisible(isLouable);

        // Cacher les autres boutons si la sélection ne correspond pas
        if (!isImmeuble) {
            fenetreAffichageDonnees.getBtnADI().setVisible(false);
            fenetreAffichageDonnees.getBtnDF().setVisible(false);
            fenetreAffichageDonnees.getBtnDO().setVisible(false);
        }

        if (!isLouable) {
            fenetreAffichageDonnees.getBtnSTC().setVisible(false);
        }
    }

    // Affiche les donnees de l'elements selectionné dans la liste a gauche
    private <T> void afficherDonnees(Dao<T> dao, Class<T> type) throws SQLException {
        // Récupérer toutes les données du type spécifié depuis le DAO
        List<T> donnees = dao.findAll();

        // Vérifier si la case "archivé" est cochée
        boolean afficherArchives = fenetreAffichageDonnees.isArchiveCoche();

        // Appliquer des filtres spécifiques selon les types et l'état de la case "archivé"
        donnees = donnees.stream()
            .filter(obj -> {
                if (obj instanceof Locataire) {
                    Locataire locataire = (Locataire) obj;
                    return afficherArchives || locataire.getDateDepart() == null; // Inclut ou exclut les locataires archivés
                }
                if (obj instanceof ContratDeLocation) {
                    ContratDeLocation contrat = (ContratDeLocation) obj;
                    return afficherArchives || contrat.getDateFin() == null; // Inclut ou exclut les contrats terminés
                }
                if (obj instanceof Facture) {
                    Facture facture = (Facture) obj;
                    return afficherArchives || facture.getDatePaiement() == null; // Inclut ou exclut les factures payées
                }
                if (obj instanceof Taxe) {
                    Taxe taxe = (Taxe) obj;
                    return afficherArchives || taxe.getDateTaxe() == null; // Inclut ou exclut les taxes non validées
                }
                if (obj instanceof Charge) {
                    Charge charge = (Charge) obj;
                    return afficherArchives || charge.getPeriodeFin() == null; // Inclut ou exclut les charges terminées
                }
                return true; // Pas de filtre pour les autres types
            })
            .collect(Collectors.toList());

        // Initialisation des colonnes et des données
        String[] colonnes;
        Object[][] data;

        // Déterminer l'affichage en fonction du type d'objet
        switch (type.getSimpleName().toUpperCase()) {
        case "LOCATAIRE":
            colonnes = new String[]{"ID Locataire", "Nom"};
            data = donnees.stream()
                .map(obj -> (Locataire) obj)
                .map(locataire -> new Object[]{
                    locataire.getIdLocataire(),
                    locataire.getNom(),
                    locataire.getDateDepart()
                })
                .toArray(Object[][]::new);
            break;

        case "INDEXCOMPTEUR":
            colonnes = new String[]{"ID Compteur", "Type"};
            data = donnees.stream()
                .map(obj -> (IndexCompteur) obj)
                .map(compteur -> new Object[]{
                    compteur.getIdIndexCompteur(),
                    compteur.getTypeCompteur()
                })
                .toArray(Object[][]::new);
            break;

        case "ASSUREUR":
            colonnes = new String[]{"ID Assurance", "Nom"};
            data = donnees.stream()
                .map(obj -> (Assureur) obj)
                .map(assureur -> new Object[]{
                    assureur.getIdAssureur(),
                    assureur.getNom()
                })
                .toArray(Object[][]::new);
            break;

        case "ENTREPRISE":
            colonnes = new String[]{"ID Entreprise", "Nom"};
            data = donnees.stream()
                .map(obj -> (Entreprise) obj)
                .map(entreprise -> new Object[]{
                    entreprise.getIdEntreprise(),
                    entreprise.getNom()
                })
                .toArray(Object[][]::new);
            break;

        case "IMMEUBLE":
            colonnes = new String[]{"ID Immeuble", "Adresse"};
            data = donnees.stream()
                .map(obj -> (Immeuble) obj)
                .map(immeuble -> new Object[]{
                    immeuble.getIdImmeuble(),
                    immeuble.getAdresse()
                })
                .toArray(Object[][]::new);
            break;

        case "TAXE":
            colonnes = new String[]{"ID Taxe", "Montant"};
            data = donnees.stream()
                .map(obj -> (Taxe) obj)
                .map(taxe -> new Object[]{
                    taxe.getIdTaxe(),
                    taxe.getMontantTaxeFoncieres(),
                    taxe.getDateTaxe()
                })
                .toArray(Object[][]::new);
            break;

        case "FACTURE":
            colonnes = new String[]{"ID Facture", "Date"};
            data = donnees.stream()
                .map(obj -> (Facture) obj)
                .map(facture -> new Object[]{
                    facture.getIdFacture(),
                    facture.getDateFacture(),
                    facture.getDatePaiement()
                })
                .toArray(Object[][]::new);
            break;

        case "DIAGNOSTIC":
            colonnes = new String[]{"ID Diagnostic", "Type"};
            data = donnees.stream()
                .map(obj -> (Diagnostic) obj)
                .map(diagnostic -> new Object[]{
                    diagnostic.getIdDiagnostic(),
                    diagnostic.getTypeDiagnostic()
                })
                .toArray(Object[][]::new);
            break;

        case "CONTRATDELOCATION":
            colonnes = new String[]{"ID Contrat", "Montant Loyer"};
            data = donnees.stream()
                .map(obj -> (ContratDeLocation) obj)
                .map(contrat -> new Object[]{
                    contrat.getIdContratDeLocation(),
                    contrat.getMontantLoyer(),
                    contrat.getDateFin()
                })
                .toArray(Object[][]::new);
            break;

        case "CHARGE":
            colonnes = new String[]{"ID Charge", "Type"};
            data = donnees.stream()
                .map(obj -> (Charge) obj)
                .map(charge -> new Object[]{
                    charge.getIdCharge(),
                    charge.getTypeCharge(),
                    charge.getPeriodeFin()
                })
                .toArray(Object[][]::new);
            break;

        case "QUITTANCES":
            colonnes = new String[]{"ID Quittance", "Montant Loyer"};
            data = donnees.stream()
                .map(obj -> (Quittances) obj)
                .map(quittance -> new Object[]{
                    quittance.getIdQuittances(),
                    quittance.getMontantLoyer()
                })
                .toArray(Object[][]::new);
            break;

        case "LOUABLE":
            colonnes = new String[]{"ID Louable", "Type"};
            data = donnees.stream()
                .map(obj -> (Louable) obj)
                .map(louable -> new Object[]{
                    louable.getIdLouable(),
                    louable.getTypeLouable()
                })
                .toArray(Object[][]::new);
            break;

            default:
                throw new IllegalArgumentException("Type non pris en charge : " + type.getSimpleName());
        }

        // Met à jour le tableau de la fenêtre avec les données
        JTable tableListeElements = fenetreAffichageDonnees.getTableListeElements();
        DefaultTableModel model = new DefaultTableModel(data, colonnes) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rend les cellules non éditables
            }
        };
        tableListeElements.setModel(model); // Applique le nouveau modèle
    }

}
