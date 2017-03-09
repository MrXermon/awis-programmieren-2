/**
 * Jan Gilla
 * 09.03.2017
 * V1.0
 * 
 * Abstrakte Klasse eines Vermittlers.
 */

package mypack;

public abstract class Vermittler {

	public abstract void zuteilen(Zuord z);
	public abstract void zuteilen(Zuord z, Kollege k);
	
	public abstract void aenderungAufgetreten(Kollege k, Zuord z);
	
	public abstract void registriereKollege(Kollege k);
	public abstract Boolean find(Kollege k);
}
