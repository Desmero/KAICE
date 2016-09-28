package fr.kaice.tools.local;

/**
 * Created by merkling on 28/09/16.
 */
public abstract class French {
    // {@value fr.kaice.tools.local.French#}
    // ===== ===== ===== ..... ..... ..... ===== ===== =====
    // -- -- -- -- -- -- ..... ..... ..... -- -- -- -- -- --
    
    public static final String PROGRAMME_NAME = "KAICE v2.0";
    
    
    // ===== ===== =====       Column      ===== ===== =====
    
    public static final String COL_NAME = "Nom";
    public static final String COL_FIRST_NAME = "Prénom";
    public static final String COL_CLIENT = "Client";
    public static final String COL_CASHIER = "Caissier";
    
    public static final String COL_ARTICLE = "Article";
    public static final String COL_TRANSACTION = "Transaction";
    public static final String COL_PRICE = "Prix";
    public static final String COL_UNIT_PRICE = "Prix unitaire";
    public static final String COL_CASH = "Espèces";
    public static final String COL_QUANTITY = "Quantité";
    
    public static final String COL_DATE = "Date";
    public static final String COL_NUM = "Num";
    public static final String COL_ORDER_ARRANGE = "Ordre";
    
    
    // ===== ===== =====       Title       ===== ===== =====
    
    public static final String TITLE_TRANSACTION = "Transaction";
    public static final String TITLE_ORDER = "Commandes";
    public static final String TITLE_LISTS = "Listes";
    public static final String TITLE_MEMBERS = "Membres";
    public static final String TITLE_DETAILS = "Détail membre";
    public static final String TITLE_NEW_MEMBER = "Nouveau membre";
    public static final String TITLE_NO_ONE = "Personne";
    public static final String TITLE_NEW_CASHIER = "Nouveau caissier";
    public static final String TITLE_NEW_HISTORIC_LINE = "Nouvelle entrée";
    public static final String TITLE_E_MAIL_LIST = "Liste des addresses e-mails";
    
    public static final String ADD_TITLE_EDIT = " (édition)";
    public static final String SUB_TITLE_SEARCH = "Recherche";
    
    
    // ===== ===== =====        Tabs       ===== ===== =====
    
    public static final String TAB_SELLING = "Ventes";
    public static final String TAB_BUYING = "Achats";
    public static final String TAB_RAW_MATERIAL = "Produits bruts";
    public static final String TAB_SOLD_PRODUCT = "Articles en vente";
    public static final String TAB_PURCHASED_PRODUCT = "Articles achetés";
    public static final String TAB_HISTORIC = "Historique";
    public static final String TAB_MEMBER = "Membre";
    
    
    // ===== ===== =====        Menu       ===== ===== =====
    
    public static final String M_FILE = "Fichier";
    public static final String MF_EXPORT = "Exporter";
    public static final String MF_EXIT = "Quitter";
    public static final String M_EDIT = "Édition";
    public static final String ME_ADD_CASHIER = "Ajouter un caissier";
    public static final String ME_OPTION = "Préférences";
    public static final String M_VIEW = "Affichage";
    public static final String MV_HIDDEN = "Produits masqués";
    public static final String M_HELP = "Aide";
    
    
    // ===== ===== =====       Button      ===== ===== =====
    
    public static final String B_ADD = "Ajouter";
    public static final String B_REM = "Retirer";
    public static final String B_VALID = "Valider";
    public static final String B_CANCEL = "Annuler";
    public static final String B_VIEW = "Visualiser";
    public static final String B_E_MAIL = "E-mails";
    public static final String B_EDIT = "Éditer";
    public static final String B_ADD_CASHIER = "Ajouter caissier";
    public static final String B_REM_CASHIER = "Supprimer caissier";
    
    
    // ===== ===== =====       Field       ===== ===== =====
    
    public static final String TF_MEMBERSHIP_NUM = "Numero d'adhérent : ";
    public static final String TF_NAME = "Nom : ";
    public static final String TF_FIRST_NAME = "Prénom : ";
    public static final String TF_BIRTH_DATE = "Date de naissance : ";
    public static final String TF_GENDER = "Sexe : ";
    public static final String GENDER_M = "Homme";
    public static final String GENDER_F = "Femme";
    public static final String TF_STUDIES = "Filière : ";
    public static final String TF_MAIL_STREET = "Adresse : ";
    public static final String TF_MAIL_PC = "    (Code postal)";
    public static final String TF_MAIL_TOWN = "    (Commune)";
    public static final String TF_E_MAIL = "Adresse e-Mail : ";
    public static final String TF_PHONE_NUM = "Numéro de Tél : ";
    
    public static final String TF_PRICE = "Prix : ";
    public static final String TF_CASH = "Espèce : ";
    public static final String TF_CASH_BACK = "Rendu : ";
    
    public static final String TF_TOTAL = "Total : ";
    public static final String TF_TEXT = "Texte : ";
    public static final String TF_YEAR = "Année :";
    
    public static final String RB_MEMBER = "Membre";
    public static final String RB_CENS = "CENS";
    public static final String RB_NULL = "Aucun";
    
    public static final String CB_NEWS_LETTER = "newsLetter";
    public static final String CB_SUBSCRIBERS_ONLY = "Afficher seulement les adresses des abonnés";
    
    
    // ===== ===== =====       Others      ===== ===== =====
    
    // -- -- -- -- -- --    Empty field    -- -- -- -- -- --
    
    public static final String EMP_NAME = "[Nom]";
    public static final String EMP_FIRST_NAME = "[Prénom]";
    public static final String EMP_BIRTH_DATE = "[Date de naissance]";
    public static final String EMP_GENDER = " [Genre]";
    public static final String EMP_STUDIES = "[Études]";
    public static final String EMP_MAIL_STREET = "[Rue]";
    public static final String EMP_MAIL_PC = "[Code postal]";
    public static final String EMP_MAIL_TOWN = "[Commune]";
    public static final String EMP_E_MAIL = "[Adresse e-mail]";
    public static final String EMP_PHONE_NUM = "[Numéro de télephone]";
    
    // -- -- -- -- -- --       Dialog      -- -- -- -- -- --
    
    public static final String DIALOG_NAME_INSUFFICIENT_CASH = "Espèces insuffisants";
    public static final String DIALOG_TEXT_INSUFFICIENT_CASH = "Le paiment en espèce est insuffisant, vous devez " +
            "débiter le compte. Continuer ?";
    public static final String DIALOG_NAME_ACCOUNT = "Pensez à débiter le compte dans le carnet de comptes, merci";
    public static final String DIALOG_TEXT_ACCOUNT = "Comptes";
    public static final String DIALOG_NAME_ENR_PAYMENT = "Le paiment a-t-il été effectué en espèces ?";
    public static final String DIALOG_TEXT_ENR_PAYMENT = "Paiement";
    
    // -- -- -- -- -- --       Names       -- -- -- -- -- --
    
    public static final String TR_ADD = "Ajout stock : ";
    public static final String TR_SUB = "Retrait stock : ";
    public static final String TR_CANCEL = "Vente annulée : ";
    public static final String TR_SELL = "Vente : ";
    public static final String TR_BUY = "Courses : ";
    public static final String TR_ENR = "Inscription : ";
    public static final String TR_MISC = "Entrée manuelle : ";
    
    // -- -- -- -- -- --       Others      -- -- -- -- -- --
    
    public static final String TOTAL_LINE = "Total : ";
    public static final String _TRANSACTION = " transaction(s)";
    
}
