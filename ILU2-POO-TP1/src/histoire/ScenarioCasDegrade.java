package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {

	public static void main(String[] args) {		
		Etal etal = new Etal();
		etal.libererEtal();
		System.out.println("Fin du test 1");
		
		Village village = new Village("le village des irréductibles", 10, 5);
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
		village.ajouterHabitant(bonemine);
		village.ajouterHabitant(assurancetourix);
		village.installerVendeur(bonemine, "fleurs", 20);
		
		
		Etal etalFleur = village.rechercherEtal(bonemine);
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
	}
}