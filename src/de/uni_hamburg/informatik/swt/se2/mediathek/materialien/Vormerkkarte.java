package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.LinkedList;
import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

/**
 * 
 * Mit Hilfe der Vormerkkarte wird das Vormerken eines Mediums organisiert. 
 * 
 * 
 * 
 * @author SE2-Team
 * @version SoSe 2017
 */
public class Vormerkkarte
{

    // Eigenschaften einer Vormerkkarte

    private List<Kunde> _dreierListe = new LinkedList<Kunde>(); // this was a Liste initially. List works much better..
    private final Medium _medium;

    /** 
     * Initialisert eine neue Vormerkkarte mit den gegebenen Daten.
     * 
     * @param entleiher Ein Kunde, der das Medium ausgeliehen hat.
     * @param medium Ein verliehene Medium.
     * 
     * 
     * @require entleiher != null
     * @require medium != null
     *      * 
     * @ensure #getEntleiher() == entleiher
     * @ensure #getMedium() == medium
     *
     */
    public Vormerkkarte(Medium medium, Kunde merker)
    {
        assert merker != null : "Vorbedingung verletzt: entleiher != null";
        assert medium != null : "Vorbedingung verletzt: medium != null";

        _dreierListe.add(merker);
        _medium = medium;
    }

    /**
     * Gibt die aktuelle Länge der Liste zurück
     */
    public int gibListeLaenge()
    {
        return _dreierListe.size();
    }

   /**
    * 
    * Fügt der Liste einen neuen Kunden zu
    * 
    * @require _dreierListe.size() < 3
    * 
    * @param kunde
    */
    public void fuegeKundeHinzu(Kunde kunde)
    {
        assert _dreierListe.size() < 3;
        _dreierListe.add(kunde);
    }

    /**
     * 
     * Prüft ob der Kunde schon in der Liste vorhanden ist
     * 
     * 
     * @param kunde
     * @return true/false
     */
    public boolean kundeSchonInListe(Kunde kunde)
    {
        return _dreierListe.contains(kunde);
    }

    /**
     * 
     * Gibt den ersten Vormerker zurück
     * 
     * @return ersten Vormerker
     */
    public Kunde getVormerker()
    {
        return _dreierListe.get(0);
    }

    /**
     *
     * Gibt den Vormerker an der Stelle i in der Liste zurück
     * 
     * @param i
     * @return 
     */
    public Kunde getVormerker(int i)
    {
        if (_dreierListe.size() - 1 < i)
        {
            return null;
        }
        return _dreierListe.get(i);
    }

    /**
     * Gibt das Medium, dessen Ausleihe auf der Karte vermerkt ist, zurück.
     * 
     * @return Das Medium, dessen Ausleihe auf dieser Karte vermerkt ist.
     * 
     * @ensure result != null
     */
    public Medium getMedium()
    {
        return _medium;
    }

    /**
     * 
     * Löscht den ersten Vormerker in der Liste
     */
    public void loescheErstenKunden()
    {
        _dreierListe.remove(0);
    }

    /**
     * 
     * Entfernt den Kunden aus der Vormerkung
     * 
     * @param kunde
     */
    public void loescheXtenKunden(Kunde kunde)
    {
        if (_dreierListe.contains(kunde))
        {
            int x = _dreierListe.indexOf(kunde);
            _dreierListe.remove(x);
        }
        else
        {
            System.out.println(kunde.getVorname() + " hat das Medium "
                    + _medium.getTitel() + " nicht vorgemerkt...");
        }

    }

}
