package fr.kaice.tools.local;

/**
 * Created by merkling on 28/09/16.
 */
public abstract class French {
    // {@value fr.kaice.tools.local.French#COL_}
    // ===== ===== ===== ..... ..... ..... ===== ===== =====
    // -- -- -- -- -- -- ..... ..... ..... -- -- -- -- -- --
    
    public static final String PROGRAMME_NAME = "KAICE v2.0";
    
    
    // ===== ===== =====       Title       ===== ===== =====
    
    public static final String TITLE_MEMBERS = "Membres";
    public static final String TITLE_NEW_MEMBER = "Nouveau membre";
    public static final String TITLE_E_MAIL_LIST = "Liste des addresses e-mails";
    public static final String TITLE_NEW_CASHIER = "Nouveau caissier";
    public static final String TITLE_NO_ONE = "Personne";
    public static final String TITLE_DETAILS_MEMBER = "Détail membre";
    
    public static final String TITLE_LISTS = "Listes";
    public static final String TITLE_TRANSACTION = "Transaction";
    public static final String TITLE_BUY_NEW_ARTICLE = "Nouvel article à l'achat";
    public static final String TITLE_SELL_NEW_ARTICLE = "Nouvel article en vente";
    public static final String TITLE_DETAILS_SOLD_PRODUCT = "Vente : ";
    public static final String TITLE_DETAILS_PURCHASE_PRODUCT = "Achat : ";
    public static final String TITLE_NEW_HISTORIC_LINE = "Nouvelle entrée";
    public static final String TITLE_ORDER = "Commandes";
    public static final String TITLE_SHOPPING_LIST = "Liste des courses";
    
    public static final String TITLE_DETAILS = "Détail";
    public static final String TITLE_OPTION = "Options";
    
    public static final String ADD_TITLE_EDIT = " (édition)";
    public static final String SUB_TITLE_SEARCH = "Recherche";
    
    public static final String WIN_TITLE_AUTHENTICATION = "Authentification";
    
    
    // ===== ===== =====        Tabs       ===== ===== =====
    
    public static final String TAB_SELLING = "Ventes";
    public static final String TAB_BUYING = "Achats";
    public static final String TAB_RAW_MATERIAL = "Produits bruts";
    public static final String TAB_SOLD_PRODUCT = "Articles en vente";
    public static final String TAB_PURCHASED_PRODUCT = "Articles achetés";
    public static final String TAB_HISTORIC = "Historique";
    public static final String TAB_MEMBER = "Membre";
    public static final String TAB_MISC = "Divers";
    
    
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
    
    
    // ===== ===== =====       Column      ===== ===== =====
    
    public static final String COL_NAME = "Nom";
    public static final String COL_FIRST_NAME = "Prénom";
    public static final String COL_CLIENT = "Client";
    public static final String COL_CASHIER = "Caissier";
    
    public static final String COL_ITEM_NAME = "Nomenclature";
    public static final String COL_ARTICLE = "Article";
    public static final String COL_RAW_MATERIAL = "Produit brute";
    public static final String COL_TRANSACTION = "Transaction";
    public static final String COL_QUANTITY = "Quantité";
    public static final String COL_USED_QUANTITY = "Quantité utilisée";
    public static final String COL_DISP = "Disp.";
    public static final String COL_STOCK = "Stock";
    public static final String COL_ALERT = "Alerte";
    
    public static final String COL_PRICE = "Prix";
    public static final String COL_UNIT_PRICE = "Prix unitaire";
    public static final String COL_SELL_PRICE = "Prix de vente";
    public static final String COL_BUY_PRICE = "Prix d'achat";
    public static final String COL_PROFIT = "Bénéfices";
    public static final String COL_TOTAL_PRICE = "Prix total";
    public static final String COL_CASH = "Espèces";
    
    public static final String COL_ID = "Id";
    public static final String COL_DATE = "Date";
    public static final String COL_NUM = "Num";
    public static final String COL_ORDER_ARRANGE = "Ordre";
    
    
    // ===== ===== =====       Button      ===== ===== =====
    
    public static final String B_ADD = "Ajouter";
    public static final String B_REM = "Retirer";
    public static final String B_VALID = "Valider";
    public static final String B_CANCEL = "Annuler";
    public static final String B_VIEW = "Visualiser";
    public static final String B_HIDE = "Cacher";
    public static final String B_DELETE = "Supprimer";
    public static final String B_E_MAIL = "E-mails";
    public static final String B_EDIT = "Éditer";
    public static final String B_ADD_CASHIER = "Ajouter caissier";
    public static final String B_REM_CASHIER = "Supprimer caissier";
    public static final String B_CHANGE_CASHIER_CODE = "Changer le code";
    public static final String B_SHOPPING_LIST = "Liste de courses";
    
    
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
    public static final String TF_CLIENT = "Client : ";
    public static final String TF_CASHIER = "Caissier : ";
    public static final String TF_PASSWORD = "Mot de passe : ";
    public static final String TF_PASSWORD_REG = "Nombre secret : ";
    
    public static final String TF_ARTICLE_NAME = "Nom de l'article : ";
    public static final String TF_PRODUCT = "Produit : ";
    public static final String NO_PROD = "Aucun";
    public static final String TF_STOCK = "Stock : ";
    public static final String TF_QUANTITY = "Quantité : ";
    public static final String TF_ALERT = "Alerte : ";
    public static final String TF_TYPE = "Type : ";
    
    public static final String TF_PRICE = "Prix : ";
    public static final String TF_CALCULATED_PRICE = "Prix calculé : ";
    public static final String TF_PAID_PRICE = "Prix paiée : ";
    public static final String TF_CASH = "Espèce : ";
    public static final String TF_CASH_BACK = "Rendu : ";
    public static final String TF_COST = "Coût : ";
    public static final String TF_PROFIT = "Bénéfice : ";
    
    public static final String TF_TOTAL = "Total : ";
    public static final String TF_TEXT = "Texte : ";
    public static final String TF_DATE = "Date :";
    public static final String TF_YEAR = "Année :";
    
    public static final String RB_MEMBER = "Membre";
    public static final String RB_CENS = "CENS";
    public static final String RB_NULL = "Aucun";
    
    public static final String CB_NEWS_LETTER = "NewsLetter";
    public static final String CB_SUBSCRIBERS_ONLY = "Afficher seulement les adresses des abonnés";
    public static final String CB_CASH_PAYMENT = "Paiement en espèce";
    public static final String CB_ADD_TYPE = "Ajouter les types";
    public static final String CB_DISPLAY_CASHIER = "Afficher les caissiers";
    public static final String CB_DISPLAY_HIDDEN_ITMES = "Afficher les produits cachés";
    
    public static final String DC_START = "Déb : ";
    public static final String DC_END = "Fin : ";
    public static final String CB_PRESELECT = "Préselecction : ";
    
    
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
    public static final String DIALOG_NAME_ACCOUNT = "Comptes";
    public static final String DIALOG_TEXT_ACCOUNT = "Pensez à débiter le compte dans le carnet de comptes, merci";
    public static final String DIALOG_NAME_ENR_PAYMENT = "Paiement";
    public static final String DIALOG_TEXT_ENR_PAYMENT = "Le paiment a-t-il été effectué en espèces ?";
    public static final String DIALOG_NAME_SHOPPING_PRICE = "Prix";
    public static final String DIALOG_TEXT_SHOPPING_PRICE = "La somme pay? ne correspond pas au prix calcul?";
    public static final String DIALOG_NAME_NEW_RAW_MATERIAL = "Nouveau produit";
    public static final String DIALOG_TEXT_NEW_RAW_MATERIAL = "Nom du produit : ";
    
    // -- -- -- -- -- --       Names       -- -- -- -- -- --
    
    public static final String TR_FULL_NAME_SELL = "Vente : ";
    public static final String TR_FULL_NAME_CANCEL = "Vente annulée : ";
    public static final String TR_FULL_NAME_ADD = "Ajout stock : ";
    public static final String TR_FULL_NAME_SUB = "Retrait stock : ";
    public static final String TR_FULL_NAME_BUY = "Courses : ";
    public static final String TR_FULL_NAME_SELL_CHANGE = "Mise à jours prix : ";
    public static final String TR_FULL_NAME_ENR = "Inscription : ";
    public static final String TR_FULL_NAME_MISC = "Entrée manuelle : ";
    public static final String TR_FULL_ADMIN_CHANGE = "Modification de caissier : ";
    public static final String TR_FULL_DEPOSIT = "Dépôt : ";
    
    public static final String TR_SELL = "Vente";
    public static final String TR_CANCEL = "Annulation de vente";
    public static final String TR_ADD = "Augmentation des stocks";
    public static final String TR_SUB = "Réduction des stocks";
    public static final String TR_BUY = "Courses";
    public static final String TR_SELL_CHANGE = "Changement de prix";
    public static final String TR_ENR = "Inscription";
    public static final String TR_MISC = "Opération diver";
    public static final String TR_ADMIN_CHANGE = "Modification de caissier";
    public static final String TR_ADMIN_CHANGE_CODE = "Mise à jours caissier";
    public static final String TR_ADMIN_CHANGE_REM = "Suppression caissier";
    public static final String TR_DEPOSIT = "Dépôt d'argent";
    
    public static final String TYPE_FOOD = "Nourriture";
    public static final String TYPE_CANDY = "Friandise";
    public static final String TYPE_DRINK = "Boisson";
    public static final String TYPE_MISC = "Autre";
    
    // -- -- -- -- -- --        Time       -- -- -- -- -- --
    
    public static final String TIME_DAY = "Aujourd'hui";
    public static final String TIME_WEEK = "Cette semaine";
    public static final String TIME_MONTH = "Ce mois";
    public static final String TIME_YEAR = "Cette année";
    public static final String TIME_ALL = "Tout";
    
    // -- -- -- -- -- --       Others      -- -- -- -- -- --
    
    public static final String TOTAL_LINE = "Total : ";
    public static final String _TRANSACTION = " transaction(s)";
    
}
