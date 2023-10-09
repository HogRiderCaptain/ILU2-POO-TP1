package villagegaulois;

import java.util.Objects;
import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.Etal;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	public Village(String nom, int nbVillageoisMaximum,int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	@SuppressWarnings("serial")
	public class VillageSansChefException extends RuntimeException {
		  public VillageSansChefException(String s) {
		    super(s);
		  }
	}
	
	public String afficherVillageois() throws VillageSansChefException {
		try {
			chef.getNom();
		} catch (Exception e) {
			throw new VillageSansChefException("Il n'y aucun chef dans ce village !");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public static class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i=0;i<nbEtals;i++)
				this.etals[i] = new Etal();
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		int trouverEtalLibre() {
			for(int i=0;i<etals.length;i++) {
				if (!(etals[i].etalOccupe)) {return i;}
			}
			return -1;
		}
		
		Etal[] trouverEtals(String produit){
			Etal[] listeEtals = etals;
			int nbEtals = 0;
			for(int i=0;i<etals.length;i++) {
				if (listeEtals[i].produit == null) {
				listeEtals[i] = null;
				} else {nbEtals++;}
			}
			Etal[] listeEtals2 = new Etal[nbEtals];
			int indice2 = 0;
			for (int i=0;i<etals.length;i++) {
				if (listeEtals[i] != null) {
					listeEtals2[indice2] = listeEtals[i];
					indice2++;
				}
			}
			return listeEtals2;
		}

		
		Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<etals.length;i++) {
				if (Objects.equals((etals[i].getVendeur()).getNom(), gaulois.getNom())) {return etals[i];}
			}
			return null;
		}
		
		String afficherMarche() {
			int nbEtalVide =0;
			StringBuilder chaine = new StringBuilder();
			for(int i=0;i<etals.length;i++) {
				if ("L'étal est libre".equals(etals[i].afficherEtal())) {nbEtalVide++;}
			}
			chaine.append( "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		int premierEtalNonOccupee = marche.trouverEtalLibre();
		StringBuilder chaine = new StringBuilder();
		String nomVendeur = vendeur.getNom();
		chaine.append(nomVendeur + " cherche un endroit pour vendre " + nbProduit + " " + produit + " " + ".\nLe vendeur " + nomVendeur);
		if (premierEtalNonOccupee!=-1){
			marche.utiliserEtal(premierEtalNonOccupee, vendeur, produit, nbProduit);
			premierEtalNonOccupee++;
			chaine.append(" des " + produit + " à l'étal n°" + premierEtalNonOccupee + ".\n");
			
		} else {
			chaine.append(" n'a pas trouver d'étal pour vendre ses " + produit + ".\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
		Etal[] listeEtals = marche.trouverEtals(produit);
		for (int i=0;i<listeEtals.length;i++) {
			Etal etalActuel = listeEtals[i];
			if (etalActuel.contientProduit(produit))
				chaine.append("- " + etalActuel.getVendeur().getNom()+"\n");
		}
		return chaine.toString(); 
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		Etal[] listeEtals = marche.etals;
		for (int i=0;i<marche.etals.length;i++) {
			if (listeEtals[i].getVendeur() == vendeur) {
				return listeEtals[i];
			}
		}
		return null;
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etalActuel = rechercherEtal(vendeur);
		chaine.append(etalActuel.libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + nom + "\" possède plusieurs étals :\n");
		Etal[] listeEtals = marche.etals;
		int nbEtal = 0;
		for (int i=0;i<marche.etals.length;i++) {
			Etal etalActuel = listeEtals[i];
			if (etalActuel != null && etalActuel.quantite != 0) {
				Gaulois vendeur = listeEtals[i].vendeur;
				chaine.append(vendeur.getNom() + " vend " + etalActuel.quantite + " " + etalActuel.produit + "\n");
				nbEtal++;
			}
		}
		chaine.append("Il reste " + (marche.etals.length - nbEtal) + " étals non utilisés dans le marché.\n");
		return chaine.toString();
	}
}