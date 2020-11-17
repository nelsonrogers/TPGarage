package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

	private final String immatriculation;
	private final List<Stationnement> myStationnements = new LinkedList<>();
        private final Set<Garage> myGarages = new HashSet<>();

	public Voiture(String i) {
		if (null == i) {
			throw new IllegalArgumentException("Une voiture doit avoir une immatriculation");
		}

		immatriculation = i;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	/**
	 * Fait rentrer la voiture au garage 
         * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws java.lang.Exception Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws Exception {
		// Et si la voiture est déjà dans un garage ?
                if (estDansUnGarage()) { // Si la voiture est déjà dans un garage : Exception
                    throw new Exception("La voiture est déjà dans un garage");
                }
                else {
                    Stationnement s = new Stationnement(this, g);
                    myStationnements.add(s);
                    myGarages.add(g);
                }
	}

	/**
	 * Fait sortir la voiture du garage 
         * Précondition : la voiture doit être dans un garage
	 *
	 * @throws java.lang.Exception si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws Exception {
		// Trouver le dernier stationnement de la voiture
		// Terminer ce stationnement
                if (!estDansUnGarage()) { // Si la voiture n'est pas dans un garage : Exception
                    throw new Exception("La voiture n'est pas dans un garage");
                }
                else {
                    Stationnement dernierStationnement = myStationnements.get(myStationnements.size() - 1);
                    dernierStationnement.terminer();
                }
	}

	/**
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
                return myGarages;
	}

	/**
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
                if (myStationnements.size() > 0) {
                    Stationnement dernierStationnement = myStationnements.get(myStationnements.size() - 1);
                    return (dernierStationnement.estEnCours());
                }
                return false;
	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des dates d'entrée / sortie dans ce
	 * garage
	 * <br>Exemple :
	 * <pre>
	 * Garage Castres:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 *		Stationnement{ entree=28/01/2019, en cours }
	 *  Garage Albi:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out)
	 */
	public void imprimeStationnements(PrintStream out) {
                // On créé une HashMap de listes de stationnements ayant comme clé le garage correspondant
                HashMap<Garage, List<Stationnement>> hashMap = new HashMap<>();
                // On parcourt la liste des stationnements
                myStationnements.stream().forEach((stationnement) -> {
                    // On ajoute les garages en tant que clé avec le stationnement correspondant
                    if (!hashMap.containsKey(stationnement.getGarage())) {
                        List<Stationnement> list = new ArrayList<>();
                        list.add(stationnement);

                        hashMap.put(stationnement.getGarage(), list);
                
                    } 
                    else { // Si la clé existe déjà, on ajoute le stationnement correspondant à la liste qui a cette clé
                        hashMap.get(stationnement.getGarage()).add(stationnement);
                    }
                });
                
                
                garagesVisites().stream().forEach((garage) -> {
                    
                    out.println(garage);
                    out.println(hashMap.get(garage));
                });
        }

}
