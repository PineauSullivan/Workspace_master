package dependances;

public class Personne{


	private String nom;
	private String ville;
	private int age;
	

	public Personne() {
		// <3
	}
	
	
	/* (non-Javadoc)
	 * @see dependances.Yo#getNom()
	 */
	public String getNom() {
		return nom;
	}


	/* (non-Javadoc)
	 * @see dependances.Yo#setNom(java.lang.String)
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/* (non-Javadoc)
	 * @see dependances.Yo#getVille()
	 */
	public String getVille() {
		return ville;
	}


	/* (non-Javadoc)
	 * @see dependances.Yo#setVille(java.lang.String)
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}


	/* (non-Javadoc)
	 * @see dependances.Yo#getAge()
	 */
	public int getAge() {
		return age;
	}


	/* (non-Javadoc)
	 * @see dependances.Yo#setAge(int)
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	public String toString() {
		return "Personne [nom=" + nom + ", ville=" + ville + ", age=" + age
				+ "]";
	}
	
	

}
