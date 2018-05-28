package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Geldbetrag;
import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class VormerkkarteTest {
   
    private Vormerkkarte _karte;
    private Kunde _kunde;
    private Medium _medium;

    public VormerkkarteTest()
    {
        _kunde = new Kunde(new Kundennummer(123456), "ich", "du");
        _medium = new CD("bar", "baz", "foo", 123);
        _karte = new Vormerkkarte(_medium, _kunde);
    }

    

    @Test
    public void testeKonstruktor() throws Exception
    {
        assertEquals(_kunde, _karte.getVormerker());
        assertEquals(_medium, _karte.getMedium());
       
    }

    @Test
    public void TestEntfernen()
    {
    Kunde _kunde2 = new Kunde(new Kundennummer(213213), "Bernd", "Meier");
    _karte.fuegeKundeHinzu(_kunde2);
    _karte.loescheXtenKunden(_kunde);
    assertEquals(_karte.getVormerker(), _karte.getVormerker(0));
    	
    	
    	
    }
    
    
    @Test
    public void Kundevorhanden()
    {
    	assertTrue(_karte.kundeSchonInListe(_kunde));
    	Kunde _kunde2 = new Kunde(new Kundennummer(213213), "Bernd", "Meier");
    	_karte.fuegeKundeHinzu(_kunde2);
    	_karte.kundeSchonInListe(_kunde2);
    	
    	
    	
    }
    
   
    	
    
    	
    
   


}