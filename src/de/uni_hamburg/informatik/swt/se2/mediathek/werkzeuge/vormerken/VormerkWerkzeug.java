package de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.vormerken;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Vormerkkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.ServiceObserver;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih.VerleihService;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.SubWerkzeugObserver;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.subwerkzeuge.kundenauflister.KundenauflisterWerkzeug;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.subwerkzeuge.kundendetailanzeiger.KundenDetailAnzeigerWerkzeug;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.subwerkzeuge.mediendetailanzeiger.MedienDetailAnzeigerWerkzeug;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.subwerkzeuge.vormerkmedienauflister.VormerkMedienauflisterWerkzeug;

/**
 * Ein VormerkWerkzeug stellt die Funktionalität des Vormerkens für die
 * Benutzungsoberfläche bereit. Die UI wird durch die VormerkUI gestaltet.
 * 
 * @author SE2-Team
 * @version SoSe 2017
 */
public class VormerkWerkzeug
{

    /**
     * Die UI-Komponente der Ausleihe.
     */
    private final VormerkUI _vormerkUI;

    /**
     * Der Service zum Ausleihen von Medien.
     */
    private final VerleihService _verleihService;

    /**
     * Das Sub-Werkzeug zum Darstellen und Selektieren der Kunden.
     */
    private KundenauflisterWerkzeug _kundenAuflisterWerkzeug;

    /**
     * Das Sub-Werkzeug zum Darstellen und Selektieren der Medien.
     */
    private VormerkMedienauflisterWerkzeug _medienAuflisterWerkzeug;

    /**
     * Das Sub-Werkzeug zum Anzeigen der Details der selektieten Medien.
     */
    private MedienDetailAnzeigerWerkzeug _medienDetailAnzeigerWerkzeug;

    /**
     * Das Sub-Werkzeug zum Anzeigen der Details des selektieten Kunden.
     */
    private KundenDetailAnzeigerWerkzeug _kundenDetailAnzeigerWerkzeug;

    /**
     * Initialisiert ein neues VormerkWerkzeug. Es wird die Benutzungsoberfläche
     * mit den Ausleihaktionen erzeugt, Beobachter an den Services registriert
     * und die anzuzeigenden Materialien gesetzt.
     * 
     * @param medienbestand Der Medienbestand.
     * @param kundenstamm Der Kundenstamm.
     * @param verleihService Der Verleih-Service.
     * 
     * @require medienbestand != null
     * @require kundenstamm != null
     * @require verleihService != null
     */
    public VormerkWerkzeug(MedienbestandService medienbestand,
            KundenstammService kundenstamm, VerleihService verleihService)
    {
        assert medienbestand != null : "Vorbedingung verletzt: medienbestand != null";
        assert kundenstamm != null : "Vorbedingung verletzt: kundenstamm != null";
        assert verleihService != null : "Vorbedingung verletzt: verleihService != null";

        _verleihService = verleihService;

        // Subwerkzeuge erstellen
        _kundenAuflisterWerkzeug = new KundenauflisterWerkzeug(kundenstamm);
        _medienAuflisterWerkzeug = new VormerkMedienauflisterWerkzeug(
                medienbestand, verleihService);
        _medienDetailAnzeigerWerkzeug = new MedienDetailAnzeigerWerkzeug();
        _kundenDetailAnzeigerWerkzeug = new KundenDetailAnzeigerWerkzeug();

        // UI wird erzeugt.
        _vormerkUI = new VormerkUI(_kundenAuflisterWerkzeug.getUIPanel(),
                _medienAuflisterWerkzeug.getUIPanel(),
                _kundenDetailAnzeigerWerkzeug.getUIPanel(),
                _medienDetailAnzeigerWerkzeug.getUIPanel());

        // Beobachter erzeugen und an den Services registrieren
        registriereServiceBeobachter();

        // Beobachter erzeugen und an den Subwerkzeugen registrieren
        registriereSubWerkzeugBeobachter();

        // Die Vormerkaktionen werden erzeugt und an der UI registriert.
        registriereUIAktionen();
    }

    /**
     * Registriert die Aktionen, die bei benachrichtigungen der Services
     * ausgeführt werden.
     */
    private void registriereServiceBeobachter()
    {
        registriereVormerkButtonAktualisierenAktion();
    }

    /**
     * Registriert die Aktionen, die bei bestimmten Änderungen in Subwerkzeugen
     * ausgeführt werden.
     */
    private void registriereSubWerkzeugBeobachter()
    {
        registriereKundenAnzeigenAktion();
        registriereMedienAnzeigenAktion();
    }

    /**
     * Registriert die Aktionen, die bei bestimmten UI-Events ausgeführt werden.
     */
    private void registriereUIAktionen()
    {
        registriereVormerkAktion();
        registriereVormerkCancelAktion();
    }

    /**
     * Registriert die Aktion zur Aktualisierung des Vormerkbuttons, wenn eine
     * Benachrichtigung vom Verleihservice auftaucht.
     */
    private void registriereVormerkButtonAktualisierenAktion()
    {
        _verleihService.registriereBeobachter(new ServiceObserver()
        {

            @Override
            public void reagiereAufAenderung()
            {
                aktualisiereVormerkButton();

            }
        });
    }

    /**
     * Registriert die Aktion, die ausgeführt wird, wenn auf den Vormerk-Button
     * gedrückt wird.
     */
    private void registriereVormerkAktion()
    {
        _vormerkUI.getVormerkenButton()
            .addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    merkeAusgewaehlteMedienVor();

                    // TODO die zeile musste ich hier einfügen, da sich sonst die gui nicht aktualisiert hatte.. dafür musste ich auch setzeAnzuzeigendeMedien auf public ändern..
                    _medienAuflisterWerkzeug.setzeAnzuzeigendeMedien();
                }
            });
    }

    /**
     * Registriert die Aktion, die ausgeführt wird, wenn auf den cancel-Button
     * gedrückt wird.
     */
    private void registriereVormerkCancelAktion()
    {
        _vormerkUI.getVormerkenCancelButton()
            .addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {

                    cancelAusgewaehltenKundenAlsVormerker();

                    _medienAuflisterWerkzeug.setzeAnzuzeigendeMedien();
                }
            });
    }

    /**
     * Registiert die Aktion, die ausgeführt wird, wenn ein Kunde ausgewählt
     * wird.
     */
    private void registriereKundenAnzeigenAktion()
    {
        _kundenAuflisterWerkzeug.registriereBeobachter(new SubWerkzeugObserver()
        {
            @Override
            public void reagiereAufAenderung()
            {
                zeigeAusgewaehltenKunden();
                aktualisiereVormerkButton();
            }
        });
    }

    /**
     * Registiert die Aktion, die ausgeführt wird, wenn ein Medium ausgewählt
     * wird.
     */
    private void registriereMedienAnzeigenAktion()
    {
        _medienAuflisterWerkzeug.registriereBeobachter(new SubWerkzeugObserver()
        {

            @Override
            public void reagiereAufAenderung()
            {
                zeigeAusgewaehlteMedien();
                aktualisiereVormerkButton();
            }
        });
    }

    /**
     * Überprüft, ob ein Kunde selektiert ist und ob selektierte Medien für
     * diesen Kunden vorgemerkt werden können.
     * 
     * @return true, wenn vormerken möglich ist, sonst false.
     */
    private boolean istVormerkenMoeglich()
    {
        List<Medium> medien = _medienAuflisterWerkzeug.getSelectedMedien();
        Kunde kunde = _kundenAuflisterWerkzeug.getSelectedKunde();

        // TODO,DONE,NK für Aufgabenblatt 6 (nicht löschen): Prüfung muss noch eingebaut
        // werden. Ist dies korrekt imlpementiert, wird der Vormerk-Button gemäß
        // der Anforderungen a), b), c) und e) aktiviert.
        boolean vormerkenMoeglich = (kunde != null) && !medien.isEmpty()
                && _verleihService.sindAlleVormerkenMoeglich(kunde, medien);

        return vormerkenMoeglich;
    }

    /**
     * 
     * Prüft ob der Kunde die Vormerkung beim ausgewählten Medium 
     * "Cancel" kann
     * 
     * 
     */
    private boolean istVormerkenCancelMoeglich()
    {
        List<Medium> medien = _medienAuflisterWerkzeug.getSelectedMedien();
        if (medien.size() != 1)
        {
            return false;
        }
        Medium medium = medien.get(0);
        if (!_verleihService.istVorgemerkt(medium))
        {
            return false;
        }
        Kunde kunde = _kundenAuflisterWerkzeug.getSelectedKunde();
        Vormerkkarte karte = _verleihService.getVormerkkarteFuer(medium);
        return karte.kundeSchonInListe(kunde);
    }

    /**
     * 
     * Löscht den Kunden in der Vormerkkarte für das ausgewählte Medium
     * über den Cancel Button 
     * 
     */
    private void cancelAusgewaehltenKundenAlsVormerker()
    {

        List<Medium> selectedMedien = _medienAuflisterWerkzeug
            .getSelectedMedien();
        if (!istVormerkenCancelMoeglich())
        {
            return;
        }
        Medium medium = selectedMedien.get(0);
        Kunde kunde = _kundenAuflisterWerkzeug.getSelectedKunde();
        Vormerkkarte karte = _verleihService.getVormerkkarteFuer(medium);
        karte.loescheXtenKunden(kunde);
    }

    /**
     * Merkt die ausgewählten Medien für einen Kunden vor. Diese Methode wird
     * über einen Listener angestoßen, der reagiert, wenn der Benutzer den
     * VormerkButton drückt.
     */
    private void merkeAusgewaehlteMedienVor()
    {

        List<Medium> selectedMedien = _medienAuflisterWerkzeug
            .getSelectedMedien();
        Kunde selectedKunde = _kundenAuflisterWerkzeug.getSelectedKunde();
        // TODO,DONE,NK für Aufgabenblatt 6 (nicht löschen): Vormerken einbauen. WORKS
        _verleihService.vormerkeAn(selectedKunde, selectedMedien);

    }

    /**
     * Zeigt die Details der ausgewählten Medien.
     */
    private void zeigeAusgewaehlteMedien()
    {
        List<Medium> selectedMedien = _medienAuflisterWerkzeug
            .getSelectedMedien();
        _medienDetailAnzeigerWerkzeug.setMedien(selectedMedien);
    }

    /**
     * Zeigt die Details des ausgewählten Kunden (rechts im Fenster)
     */
    private void zeigeAusgewaehltenKunden()
    {
        Kunde kunde = _kundenAuflisterWerkzeug.getSelectedKunde();
        _kundenDetailAnzeigerWerkzeug.setKunde(kunde);
    }

    /**
     * Setzt den Ausleihbutton auf benutzbar (enabled) falls die gerade
     * selektierten Medien alle ausgeliehen werden können und ein Kunde
     * ausgewählt ist.
     * 
     * Wenn keine Medien selektiert sind oder wenn mindestes eines der
     * selektierten Medien bereits ausgeliehen ist oder wenn kein Kunde
     * ausgewählt ist, wird der Button ausgegraut.
     */

    private void aktualisiereVormerkButton()
    {
        boolean istVormerkenMoeglich = istVormerkenMoeglich();
        _vormerkUI.getVormerkenButton()
            .setEnabled(istVormerkenMoeglich);
        aktualisiereVormerkCancelButton();
    }

    /**
     * 
     * Setzt den Ausleihbutton auf benutzbar oder unbenutzbar
     * 
     * Es ist davon abhängig , ob der Kunde berechtigt ist für
     * das ausgewählte Medium die Vormerkung zu löschen
     * 
     */
    private void aktualisiereVormerkCancelButton()
    {
        boolean istVormerkenCancelMoeglich = istVormerkenCancelMoeglich();
        _vormerkUI.getVormerkenCancelButton()
            .setEnabled(istVormerkenCancelMoeglich);
    }

    /**
     * Gibt das Panel, dass die UI-Komponente darstellt zurück.
     * 
     * @ensure result != null
     */
    public JPanel getUIPanel()
    {
        return _vormerkUI.getUIPanel();
    }
}
