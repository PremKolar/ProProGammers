package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.LinkedList;
import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

/**
 * TODO kommentieren!!
 * @author SE2-Team
 * @version SoSe 2017
 */
public class Vormerkkarte
{

    // Eigenschaften einer Vormerkkarte

    private List<Kunde> _dreierListe = new LinkedList<Kunde>(); // this was a Queue initially. List works much better..
    private final Medium _medium;

    /** TODO anpassen (text kopiert von verleihkarte)
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

        _dreierListe.add(merker);
        _medium = medium;
    }

    // TODO kommentieren
    public int gibQueueLaenge()
    {
        return _dreierListe.size();
    }

    // TODO kommentieren
    public void fuegeKundeHinzu(Kunde kunde)
    {
        assert _dreierListe.size() < 3;
        _dreierListe.add(kunde);
    }

    // TODO kommentieren

    public boolean kundeSchonInQueue(Kunde kunde)
    {
        return _dreierListe.contains(kunde);
    }

    // TODO kommentieren
    public Kunde getVormerker()
    {
        return _dreierListe.get(0);
    }

    // TODO kommentieren
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

    // TODO kommentieren
    public void loescheErstenKunden()
    {
        _dreierListe.remove(0);
    }

    // TODO kommentieren
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
