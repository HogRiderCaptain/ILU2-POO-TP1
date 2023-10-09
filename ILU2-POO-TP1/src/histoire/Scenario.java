package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.Village.VillageSansChefException;

public class Scenario {

	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		Druide druide = new Druide("Panoramix", 2, 5, 10);
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois asterix = new Gaulois("Astérix", 8);
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		
		village.ajouterHabitant(bonemine);
		village.ajouterHabitant(assurancetourix);
		village.ajouterHabitant(asterix);
		village.ajouterHabitant(obelix);
		village.ajouterHabitant(druide);
		village.ajouterHabitant(abraracourcix);
		System.out.println(village.afficherVillageois());
  	    
		System.out.println(village.installerVendeur(bonemine, "fleurs", 20));
		System.out.println(village.installerVendeur(assurancetourix, "lyres", 5));
		System.out.println(village.installerVendeur(obelix, "menhirs", 2));
		System.out.println(village.installerVendeur(druide, "fleurs", 10));

		System.out.println(village.rechercherVendeursProduit("fleurs"));
		Etal etalFleur = village.rechercherEtal(bonemine);
		System.out.println(etalFleur.acheterProduit(10, abraracourcix));
		System.out.println(etalFleur.acheterProduit(15, obelix));
		System.out.println(etalFleur.acheterProduit(15, assurancetourix));
     	System.out.println(village.partirVendeur(bonemine));
		System.out.println(village.afficherMarche());
		
		
		Etal etal = new Etal();
		etal.libererEtal();
		System.out.println("Fin du test 1");
		
		try {
			etal.acheterProduit(1,null);
		}catch (Exception e) {
			System.out.println(e.toString());
			try {
				etalFleur.acheterProduit(-2,assurancetourix);
			} catch (Exception e2) {
				System.out.println(e2.toString());
				try {
					etal.acheterProduit(2,assurancetourix);
				} catch (Exception e3) {
					System.out.println(e3.toString());
					etalFleur.acheterProduit(2,assurancetourix);
				}
			}
		}
		System.out.println("Fin du test 2");
		village.setChef(null);
		try{
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			System.out.println(e.toString());
		}
		System.out.println("Fin du test 3");
	}

}
