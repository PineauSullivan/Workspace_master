package dependances;

public interface IPersonne {

	/* (non-Javadoc)
	 * @see dependances.Yo#setNom(java.lang.String)
	 */
	public abstract void setNom(String nom);

	public abstract String toString();
	
	public 	abstract String setAll(String s1, String s2, int age);
	
	public abstract void active();
}