package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.LinkedList;
import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

/**
 * TODO text anpassen
 * Mit Hilfe von Verleihkarten werden beim Verleih eines Mediums alle relevanten
 * Daten notiert.
 * 
 * Sie beantwortet die folgenden Fragen: Welches Medium wurde ausgeliehen? Wer
 * hat das Medium ausgeliehen? Wann wurde das Medium ausgeliehen?
 * 
 * Wenn Medien zurück gegeben werden, kann die zugehörige Verleihkarte entsorgt
 * werden. Um die Verwaltung der Karten kümmert sich der VerleihService
 * 
 * @author SE2-Team
 * @version SoSe 2017
 */
public class Vormerkkarte
{

    // Eigenschaften einer Vormerkkarte

    private List<Kunde> _queue = new LinkedList<Kunde>(); // this was a Queue initially. List works much better..
    private final Medium _medium;

    /** TODO anpassen
     * Initialisert eine neue Verleihkarte mit den gegebenen Daten.
     * 
     * @param entleiher Ein Kunde, der das Medium ausgeliehen hat.
     * @param medium Ein verliehene Medium.
     * @param ausleihdatum Ein Datum, an dem der Kunde das Medium ausgeliehen
     *            hat.
     * 
     * @require entleiher != null
     * @require medium != null
     * @require ausleihdatum != null
     * 
     * @ensure #getEntleiher() == entleiher
     * @ensure #getMedium() == medium
     * @ensure #getAusleihdatum() == ausleihdatum
     */
    public Vormerkkarte(Medium medium, Kunde merker)
    {
        assert merker != null : "Vorbedingung verletzt: entleiher != null";
        assert medium != null : "Vorbedingung verletzt: medium != null";

        _queue.add(merker);
        _medium = medium;
    }

    public int gibQueueLaenge()
    {
        return _queue.size();
    }

    public void fuegeKundeHinzu(Kunde kunde)
    {
        assert _queue.size() < 3;
        _queue.add(kunde);
    }

    public boolean kundeSchonInQueue(Kunde merker)
    {
        return _queue.contains(merker);
    }

    public Kunde getVormerker()
    {
        return _queue.get(0);
    }

    public Kunde getVormerker(int i)
    {
        return _queue.get(i);
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

}
