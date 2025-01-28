CREATE DATABASE patient_db;

USE patient_db;


CREATE TABLE insurance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE -- Name der Versicherung
);

INSERT INTO insurance (name)
VALUES
('STGKK'),
('BVAEB'),
('SVS'),
('KFA'),
('VAEB'),
('Andere');

CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    anrede VARCHAR(10), -- 'Herr', 'Frau', etc.
    vorname VARCHAR(50) NOT NULL,
    nachname VARCHAR(50) NOT NULL,
    geburtstag DATE NOT NULL,
    svnr VARCHAR(20) NOT NULL, -- Sozialversicherungsnummer
    insurance_id INT NOT NULL, -- Verweis auf die Versicherung
    telefonnummer VARCHAR(20),
    strasse VARCHAR(100),
    plz VARCHAR(10),
    ort VARCHAR(50),
    bundesland VARCHAR(50),
    FOREIGN KEY (insurance_id) REFERENCES insurance(id) -- Verknüpfung mit der Versicherung
);

DROP Table patients;

CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    anrede VARCHAR(10), -- 'Herr', 'Frau', etc.
    vorname VARCHAR(50) NOT NULL,
    nachname VARCHAR(50) NOT NULL,
    geburtstag DATE NOT NULL,
    svnr VARCHAR(20) NOT NULL, -- Sozialversicherungsnummer
    insurance_id INT NOT NULL, -- Verweis auf die Versicherung
    telefonnummer VARCHAR(20),
    strasse VARCHAR(100),
    plz VARCHAR(10),
    ort VARCHAR(50),
    bundesland VARCHAR(50),
    FOREIGN KEY (insurance_id) REFERENCES insurance(id) -- Verknüpfung mit der Versicherung
);

INSERT INTO patients (anrede, vorname, nachname, geburtstag, svnr, insurance_id, telefonnummer, strasse, plz, ort, bundesland)
VALUES
('Herr', 'Kajetan', 'Jauk', '1995-10-11', '1235', 1, '123456789', 'Musterstraße 1', '8010', 'Graz', 'Steiermark');

INSERT INTO patients (anrede, vorname, nachname, geburtstag, svnr, insurance_id, telefonnummer, strasse, plz, ort, bundesland)
VALUES
('Frau', 'Anna', 'Müller', '1985-05-22', '54321', 2, '987654321', 'Beispielgasse 5', '1010', 'Wien', 'Wien');

SELECT vorname, nachname, geburtstag, COUNT(*)
FROM patients
GROUP BY vorname, nachname, geburtstag
HAVING COUNT(*) > 1;



DELETE p1
FROM patients p1
JOIN patients p2
ON p1.vorname = p2.vorname
   AND p1.nachname = p2.nachname
   AND p1.geburtstag = p2.geburtstag
   AND p1.svnr = p2.svnr
   AND p1.id > p2.id;
   
   CREATE TABLE gender (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE -- 'männlich', 'weiblich', 'divers'
);

INSERT INTO gender (name)
VALUES
('männlich'),
('weiblich'),
('divers');

ALTER TABLE patients
ADD gender_id INT,
ADD CONSTRAINT fk_gender FOREIGN KEY (gender_id) REFERENCES gender(id);


UPDATE patients SET gender_id = 1 WHERE vorname = 'Kajetan' AND nachname = 'Jauk'; -- männlich
UPDATE patients SET gender_id = 2 WHERE vorname = 'Anna' AND nachname = 'Müller';  -- weiblich
UPDATE patients SET gender_id = 3 WHERE vorname = 'Sam' AND nachname = 'Taylor';   -- divers

UPDATE patients SET gender_id = 3 WHERE gender_id IS NULL;

SELECT 
    p.id AS patient_id,
    p.vorname,
    p.nachname,
    g.name AS geschlecht
FROM patients p
JOIN gender g ON p.gender_id = g.id;

ALTER TABLE patients
MODIFY gender_id INT NOT NULL;

CREATE TABLE bundesland (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE -- Name des Bundeslands
);

INSERT INTO bundesland (name)
VALUES
('Burgenland'),
('Kärnten'),
('Niederösterreich'),
('Oberösterreich'),
('Salzburg'),
('Steiermark'),
('Tirol'),
('Vorarlberg'),
('Wien');

ALTER TABLE patients
ADD bundesland_id INT,
ADD CONSTRAINT fk_bundesland FOREIGN KEY (bundesland_id) REFERENCES bundesland(id);

UPDATE patients
SET bundesland_id = (
    SELECT id FROM bundesland WHERE name = patients.bundesland
);

ALTER TABLE patients
DROP COLUMN bundesland;


SELECT 
    p.id AS patient_id,
    p.vorname,
    p.nachname,
    b.name AS bundesland
FROM patients p
JOIN bundesland b ON p.bundesland_id = b.id;

CREATE TABLE befund (
    BefundID INT AUTO_INCREMENT PRIMARY KEY,
    PatientID INT NOT NULL,
    Pfad TEXT, -- Datei- oder Pfadangabe für den Befund
    Datum DATE, -- Datum des Befunds
    FOREIGN KEY (PatientID) REFERENCES patients(id) -- Verknüpfung mit patients
);


INSERT INTO befund (PatientID, Pfad, Datum)
VALUES
(1, 'path/to/radiology/file1.dcm', '2024-12-23'),
(2, 'path/to/radiology/file2.dcm', '2024-12-22');

SELECT id FROM patients WHERE id IN (1, 2);

ALTER TABLE befund
DROP COLUMN Pfad;

ALTER TABLE befund
ADD Beschreibung TEXT NOT NULL;

INSERT INTO befund (PatientID, Beschreibung, Datum)
VALUES
(1, 'Röntgenaufnahme des linken Arms', '2024-12-23'),
(2, 'CT-Scan des Thorax', '2024-12-22');

SELECT * FROM befund;

CREATE TABLE medikamente (
    MedikamentID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL, -- Name des Medikaments
    Beschreibung TEXT           -- Beschreibung des Medikaments
);

CREATE TABLE patient_medikamente (
    PatientID INT NOT NULL,
    MedikamentID INT NOT NULL,
    Dosierung VARCHAR(255),  -- Dosierung des Medikaments (optional)
    Startdatum DATE,         -- Beginn der Medikation (optional)
    Enddatum DATE,           -- Ende der Medikation (optional)
    FOREIGN KEY (PatientID) REFERENCES patients(id),
    FOREIGN KEY (MedikamentID) REFERENCES medikamente(MedikamentID),
    PRIMARY KEY (PatientID, MedikamentID)
);

INSERT INTO medikamente (Name, Beschreibung)
VALUES
('Paracetamol', 'Schmerzlinderndes Medikament'),
('Ibuprofen', 'Entzündungshemmendes Medikament'),
('Amoxicillin', 'Antibiotikum zur Behandlung von Infektionen');

INSERT INTO patient_medikamente (PatientID, MedikamentID, Dosierung, Startdatum, Enddatum)
VALUES
(1, 1, '500mg 3x täglich', '2024-12-23', '2024-12-30'), -- Paracetamol für Patient 1
(1, 2, '200mg 2x täglich', '2024-12-23', NULL),         -- Ibuprofen für Patient 1
(2, 3, '1x täglich', '2024-12-22', '2024-12-28');       -- Amoxicillin für Patient 2

SELECT 
    p.vorname,
    p.nachname,
    m.Name AS Medikament,
    pm.Dosierung,
    pm.Startdatum,
    pm.Enddatum
FROM patients p
JOIN patient_medikamente pm ON p.id = pm.PatientID
JOIN medikamente m ON pm.MedikamentID = m.MedikamentID;

INSERT INTO medikamente (Name, Beschreibung)
VALUES
-- Schmerzmittel
('Aspirin', 'Blutverdünnend, schmerzlindernd, entzündungshemmend'),
('Diclofenac', 'Entzündungshemmendes Medikament zur Schmerzlinderung'),
('Naproxen', 'Langwirksames Schmerzmittel zur Behandlung von Entzündungen'),
('Tramadol', 'Opioid-Schmerzmittel für moderate bis starke Schmerzen'),
('Codein', 'Schmerzmittel und Hustenblocker'),
('Morphin', 'Starkes Opioid-Schmerzmittel für schwere Schmerzen'),

-- Antibiotika
('Penicillin', 'Antibiotikum zur Behandlung bakterieller Infektionen'),
('Ceftriaxon', 'Breitbandantibiotikum zur Behandlung schwerer Infektionen'),
('Erythromycin', 'Makrolid-Antibiotikum für Atemwegsinfektionen'),
('Clindamycin', 'Antibiotikum zur Behandlung von Weichteilinfektionen'),
('Metronidazol', 'Antibiotikum zur Behandlung von Anaerobierinfektionen'),
('Vancomycin', 'Antibiotikum zur Behandlung schwerer grampositiver Infektionen'),

-- Blutdrucksenker
('Bisoprolol', 'Betablocker zur Behandlung von Bluthochdruck'),
('Carvedilol', 'Betablocker und Vasodilatator'),
('Enalapril', 'ACE-Hemmer zur Senkung des Blutdrucks'),
('Ramipril', 'ACE-Hemmer zur Behandlung von Bluthochdruck'),
('Hydrochlorothiazid', 'Diuretikum zur Blutdrucksenkung'),

-- Diabetesmedikamente
('Glimepirid', 'Orales Antidiabetikum zur Behandlung von Typ-2-Diabetes'),
('Sitagliptin', 'DPP-4-Inhibitor zur Blutzuckersenkung'),
('Empagliflozin', 'SGLT2-Hemmer zur Blutzuckerkontrolle'),
('Glucagon', 'Hormon zur Behandlung von Hypoglykämie'),

-- Cholesterinsenker
('Pravastatin', 'Statin zur Senkung des Cholesterinspiegels'),
('Rosuvastatin', 'Starkes Statin zur Senkung des LDL-Cholesterins'),
('Ezetimib', 'Hemmt die Cholesterinaufnahme im Darm'),

-- Asthma und COPD
('Theophyllin', 'Bronchodilatator zur Behandlung von Asthma'),
('Fluticason', 'Kortikosteroid zur Langzeitkontrolle von Asthma'),
('Tiotropium', 'Langwirksames Bronchodilatator zur COPD-Behandlung'),

-- Schilddrüsenmedikamente
('Thiamazol', 'Hemmt die Produktion von Schilddrüsenhormonen'),
('Propylthiouracil', 'Medikament zur Behandlung von Hyperthyreose'),

-- Sonstige
('Warfarin', 'Blutverdünner zur Verhinderung von Thrombosen'),
('Apixaban', 'Direkter Faktor-Xa-Inhibitor zur Antikoagulation'),
('Rivaroxaban', 'Blutverdünner zur Vorbeugung von Schlaganfällen'),
('Allopurinol', 'Medikament zur Senkung des Harnsäurespiegels'),
('Colchicin', 'Zur Behandlung von Gichtanfällen'),
('Methotrexat', 'Immunsuppressivum zur Behandlung von Autoimmunerkrankungen'),
('Leflunomid', 'Immunsuppressivum bei rheumatoider Arthritis'),
('Azathioprin', 'Immunsuppressivum zur Organtransplantation'),
('Tacrolimus', 'Immunsuppressivum zur Transplantationsprävention'),
('Prednisolon', 'Entzündungshemmendes Kortikosteroid'),
('Betamethason', 'Starkes Kortikosteroid zur Entzündungshemmung'),
('Esomeprazol', 'Protonenpumpenhemmer gegen Magensäureprobleme'),
('Pantoprazol', 'Protonenpumpenhemmer gegen Refluxkrankheit'),
('Ondansetron', 'Antiemetikum zur Behandlung von Übelkeit und Erbrechen'),
('Domperidon', 'Zur Behandlung von Magenentleerungsstörungen'),
('Loperamid', 'Medikament zur Behandlung von Durchfall'),
('Mesalazin', 'Entzündungshemmendes Medikament bei Colitis ulcerosa'),
('Sulfasalazin', 'Zur Behandlung von rheumatoider Arthritis und Darmerkrankungen');

INSERT INTO medikamente (Name, Beschreibung)
VALUES
-- Schmerzmittel
('Fentanyl', 'Starkes Opioid zur Behandlung schwerer Schmerzen'),
('Ketorolac', 'Nichtsteroidales Antirheumatikum für akute Schmerzen'),
('Buprenorphin', 'Opioid für moderate bis starke Schmerzen'),
('Tapentadol', 'Starkes Analgetikum mit dualem Wirkmechanismus'),

-- Antibiotika
('Levofloxacin', 'Fluorchinolon-Antibiotikum zur Behandlung von Infektionen'),
('Azithromycin', 'Makrolid-Antibiotikum für Atemwegsinfektionen'),
('Tetracyclin', 'Antibiotikum zur Behandlung von bakteriellen Infektionen'),
('Rifampicin', 'Antibiotikum zur Behandlung von Tuberkulose'),
('Linezolid', 'Antibiotikum zur Behandlung von MRSA-Infektionen'),
('Daptomycin', 'Antibiotikum gegen grampositive Bakterien'),

-- Antimykotika
('Fluconazol', 'Antimykotikum zur Behandlung von Pilzinfektionen'),
('Itraconazol', 'Antimykotikum zur Behandlung systemischer Pilzinfektionen'),
('Amphotericin B', 'Starkes Antimykotikum für schwere Pilzinfektionen'),

-- Antivirale Medikamente
('Acyclovir', 'Antivirales Medikament zur Behandlung von Herpesinfektionen'),
('Valacyclovir', 'Prodrug von Acyclovir zur Behandlung von Herpesviren'),
('Oseltamivir', 'Antivirales Medikament gegen Influenza'),
('Tenofovir', 'Antivirales Medikament für HIV und Hepatitis B'),
('Lamivudin', 'Antivirales Medikament gegen HIV und Hepatitis B'),

-- Herz-Kreislauf-Medikamente
('Clopidogrel', 'Thrombozytenaggregationshemmer zur Schlaganfallprävention'),
('Dabigatran', 'Oraler direkter Thrombininhibitor'),
('Nitroglycerin', 'Vasodilatator zur Behandlung von Angina pectoris'),
('Isosorbiddinitrat', 'Langwirksamer Vasodilatator'),
('Spironolacton', 'Aldosteronantagonist zur Behandlung von Herzinsuffizienz'),

-- Diabetesmedikamente
('Pioglitazon', 'Insulinsensitizer bei Typ-2-Diabetes'),
('Canagliflozin', 'SGLT2-Hemmer zur Blutzuckersenkung'),

-- Cholesterinsenker
('Lovastatin', 'Statin zur Behandlung von Hypercholesterinämie'),

-- Asthma- und COPD-Medikamente
('Formoterol', 'Langwirksames Bronchodilatator bei Asthma und COPD'),
('Ipratropiumbromid', 'Bronchodilatator bei akuter Atemwegsverengung'),

-- Immunsuppressiva
('Cyclosporin', 'Immunsuppressivum zur Transplantationsprävention'),
('Mycophenolatmofetil', 'Immunsuppressivum bei Organtransplantationen'),

-- Psychopharmaka
('Fluoxetin', 'SSRI zur Behandlung von Depressionen'),
('Sertralin', 'SSRI zur Behandlung von Angststörungen'),
('Citalopram', 'SSRI zur Behandlung von Depressionen und Angststörungen'),
('Quetiapin', 'Atypisches Antipsychotikum zur Behandlung von Schizophrenie und Bipolarstörung'),
('Olanzapin', 'Atypisches Antipsychotikum zur Behandlung von Schizophrenie'),
('Haloperidol', 'Antipsychotikum zur Behandlung von Psychosen'),

-- Antiepileptika
('Valproat', 'Antikonvulsivum zur Behandlung von Epilepsie'),
('Lamotrigin', 'Antikonvulsivum für fokale und generalisierte Epilepsien'),
('Levetiracetam', 'Antikonvulsivum für verschiedene Epilepsieformen'),

-- Parkinson-Medikamente
('Levodopa', 'Standardtherapie bei Parkinson-Krankheit'),
('Pramipexol', 'Dopaminagonist zur Behandlung von Parkinson'),

-- Gastrointestinale Medikamente
('Ranitidin', 'H2-Blocker zur Behandlung von Refluxkrankheit'),
('Lansoprazol', 'Protonenpumpenhemmer gegen Magensäureprobleme'),
('Mebeverin', 'Antispasmodikum bei Reizdarmsyndrom'),

-- Sonstige Medikamente
('Adrenalin', 'Notfallmedikament bei anaphylaktischem Schock'),
('Atropin', 'Anticholinergikum für Notfallanwendungen'),
('Propranolol', 'Betablocker zur Behandlung von Herzrhythmusstörungen'),
('Clonidin', 'Alpha-Agonist zur Behandlung von Hypertonie und Entzugssymptomen'),
('Carbimazol', 'Thyreostatikum zur Behandlung von Schilddrüsenüberfunktion'),
('Zoledronsäure', 'Bisphosphonat zur Behandlung von Osteoporose und Hyperkalzämie'),
('Denosumab', 'Monoklonaler Antikörper zur Behandlung von Osteoporose');


CREATE TABLE arzt (
    ArztID INT AUTO_INCREMENT PRIMARY KEY,
    Vorname VARCHAR(50) NOT NULL,
    Nachname VARCHAR(50) NOT NULL,
    Fachgebiet VARCHAR(100), -- z. B. Allgemeinmedizin, Kardiologie
    Telefonnummer VARCHAR(20),
    Email VARCHAR(100)
);

CREATE TABLE rezept (
    RezeptID INT AUTO_INCREMENT PRIMARY KEY,
    PatientID INT NOT NULL,
    ArztID INT NOT NULL,
    MedikamentID INT NOT NULL,
    Dosierung VARCHAR(255) NOT NULL, -- z. B. "500 mg 2x täglich"
    Startdatum DATE NOT NULL,
    Enddatum DATE,
    Bemerkung TEXT, -- Zusätzliche Hinweise oder Anweisungen
    FOREIGN KEY (PatientID) REFERENCES patients(id),
    FOREIGN KEY (ArztID) REFERENCES arzt(ArztID),
    FOREIGN KEY (MedikamentID) REFERENCES medikamente(MedikamentID)
);

INSERT INTO arzt (Vorname, Nachname, Fachgebiet, Telefonnummer, Email)
VALUES
('Max', 'Musterarzt', 'Allgemeinmedizin', '0123456789', 'max.musterarzt@hospital.at'),
('Anna', 'Kardiologin', 'Kardiologie', '0987654321', 'anna.kardiologin@hospital.at');

INSERT INTO rezept (PatientID, ArztID, MedikamentID, Dosierung, Startdatum, Enddatum, Bemerkung)
VALUES
(1, 1, 2, '200 mg 3x täglich', '2024-12-23', '2024-12-30', 'Nach dem Essen einnehmen'),
(2, 2, 3, '1x täglich', '2024-12-22', NULL, 'Für 7 Tage anwenden');

SELECT id, vorname, nachname, geburtstag, svnr
FROM patients;

DROP TABLE IF EXISTS befund;

CREATE TABLE diagnose (
    DiagnoseID INT AUTO_INCREMENT PRIMARY KEY,
    PatientID INT NOT NULL,
    Name VARCHAR(255) NOT NULL, -- Name der Diagnose
    Beschreibung TEXT,          -- Details zur Diagnose
    Datum DATE NOT NULL,        -- Datum der Diagnose
    FOREIGN KEY (PatientID) REFERENCES patients(id) -- Verknüpfung mit Patienten
);

INSERT INTO diagnose (PatientID, Name, Beschreibung, Datum)
VALUES
(1, 'Grippe', 'Akute Virusinfektion der oberen Atemwege', '2024-12-20'),
(1, 'Bluthochdruck', 'Erhöhter systolischer und diastolischer Blutdruck', '2024-12-23'),
(2, 'Diabetes Typ 2', 'Chronische Stoffwechselerkrankung mit erhöhtem Blutzucker', '2024-12-21');


DROP TABLE IF EXISTS diagnose;

CREATE TABLE diagnosen (
    DiagnoseID INT AUTO_INCREMENT PRIMARY KEY,
    ICD10_Code VARCHAR(10) NOT NULL, -- z. B. "J11.0"
    Name VARCHAR(255) NOT NULL,      -- Name der Diagnose
    Beschreibung TEXT                -- Zusätzliche Details zur Diagnose
);

CREATE TABLE patient_diagnosen (
    PatientDiagnoseID INT AUTO_INCREMENT PRIMARY KEY,
    PatientID INT NOT NULL,          -- Verknüpfung mit der Patiententabelle
    DiagnoseID INT NOT NULL,         -- Verknüpfung mit der Diagnosetabelle
    Datum DATE NOT NULL,             -- Datum der Diagnose
    Bemerkung TEXT,                  -- Zusätzliche Hinweise
    FOREIGN KEY (PatientID) REFERENCES patients(id),
    FOREIGN KEY (DiagnoseID) REFERENCES diagnosen(DiagnoseID)
);


INSERT INTO diagnosen (ICD10_Code, Name, Beschreibung)
VALUES
('J11.0', 'Grippe', 'Akute Virusinfektion der oberen Atemwege'),
('E11', 'Diabetes Typ 2', 'Chronische Stoffwechselerkrankung mit erhöhtem Blutzucker'),
('I10', 'Essentielle Hypertonie', 'Erhöhter systolischer und diastolischer Blutdruck'),
('M54.5', 'Rückenschmerzen', 'Schmerzen im unteren Rückenbereich'),
('J45', 'Asthma bronchiale', 'Chronische Entzündung der Atemwege'),
('F32', 'Depressive Episode', 'Psychische Störung mit depressiver Stimmung'),
('K21.0', 'Refluxkrankheit', 'Rückfluss von Magensäure in die Speiseröhre'),
('L20', 'Neurodermitis', 'Chronische, entzündliche Hauterkrankung'),
('N39.0', 'Harnwegsinfektion', 'Infektion der unteren Harnwege'),
('G44.1', 'Spannungskopfschmerz', 'Chronischer Kopfschmerz durch Muskelverspannung');

INSERT INTO patient_diagnosen (PatientID, DiagnoseID, Datum, Bemerkung)
VALUES
(1, 1, '2024-12-20', 'Mild verlaufend'),
(1, 3, '2024-12-23', 'Regelmäßige Kontrolle empfohlen'),
(2, 2, '2024-12-22', 'Erfordert Ernährungsumstellung und Bewegung');

SELECT 
    p.vorname,
    p.nachname,
    d.ICD10_Code,
    d.Name AS Diagnose,
    d.Beschreibung,
    pd.Datum,
    pd.Bemerkung
FROM patient_diagnosen pd
JOIN patients p ON pd.PatientID = p.id
JOIN diagnosen d ON pd.DiagnoseID = d.DiagnoseID
WHERE p.id = 1; -- ID des gewünschten Patienten


INSERT INTO diagnosen (ICD10_Code, Name, Beschreibung)
VALUES
-- Infektionskrankheiten
('A09', 'Durchfall und Gastroenteritis', 'Infektion des Verdauungstrakts'),
('B20', 'HIV-Erkrankung', 'HIV mit anderen Infektionen und Krankheiten'),
('B95', 'Streptokokken als Ursache', 'Infektionen durch Streptokokken'),

-- Atemwegserkrankungen
('J00', 'Akute Nasopharyngitis', 'Erkältung'),
('J02', 'Akute Pharyngitis', 'Entzündung des Rachens'),
('J03', 'Akute Tonsillitis', 'Mandelentzündung'),
('J11.0', 'Grippe', 'Akute Virusinfektion der oberen Atemwege'),
('J20', 'Akute Bronchitis', 'Entzündung der Bronchien'),
('J45', 'Asthma bronchiale', 'Chronische Atemwegsentzündung'),

-- Herz-Kreislauf-Erkrankungen
('I10', 'Essentielle Hypertonie', 'Bluthochdruck ohne bekannte Ursache'),
('I20', 'Angina pectoris', 'Herzschmerzen durch Minderdurchblutung'),
('I21', 'Akuter Myokardinfarkt', 'Herzinfarkt'),
('I50', 'Herzinsuffizienz', 'Unzureichende Herzfunktion'),

-- Stoffwechselerkrankungen
('E10', 'Diabetes Typ 1', 'Insulinabhängiger Diabetes mellitus'),
('E11', 'Diabetes Typ 2', 'Chronische Stoffwechselerkrankung'),
('E66', 'Adipositas', 'Übergewicht und Fettleibigkeit'),
('E78.0', 'Hypercholesterinämie', 'Erhöhter Cholesterinspiegel'),

-- Nervensystem-Erkrankungen
('G40', 'Epilepsie', 'Anfallsleiden des Gehirns'),
('G43', 'Migräne', 'Anfallsartige Kopfschmerzen'),
('G44.1', 'Spannungskopfschmerz', 'Chronischer Kopfschmerz durch Muskelverspannung'),

-- Psychische Erkrankungen
('F32', 'Depressive Episode', 'Psychische Störung mit depressiver Stimmung'),
('F41', 'Angststörungen', 'Angst und Panikattacken'),
('F90', 'ADHS', 'Aufmerksamkeitsdefizit-Hyperaktivitätsstörung'),

-- Verdauungssystem
('K20', 'Refluxösophagitis', 'Entzündung der Speiseröhre durch Reflux'),
('K29', 'Gastritis', 'Magenschleimhautentzündung'),
('K50', 'Morbus Crohn', 'Chronisch-entzündliche Darmerkrankung'),
('K51', 'Colitis ulcerosa', 'Chronisch-entzündliche Darmerkrankung des Dickdarms'),

-- Hautkrankheiten
('L20', 'Neurodermitis', 'Chronische, entzündliche Hauterkrankung'),
('L40', 'Psoriasis', 'Schuppenflechte'),

-- Erkrankungen der Harnwege
('N30', 'Zystitis', 'Entzündung der Harnblase'),
('N39.0', 'Harnwegsinfektion', 'Infektion der unteren Harnwege'),
('N20', 'Nierensteine', 'Steine in den Nieren oder Harnwegen'),

-- Schwangerschaft und Geburt
('O26', 'Schwangerschaftskomplikationen', 'Komplikationen während der Schwangerschaft'),
('O80', 'Spontane Geburt', 'Natürliche Entbindung ohne Komplikationen'),

-- Onkologische Erkrankungen
('C34', 'Bronchialkarzinom', 'Lungenkrebs'),
('C50', 'Mammakarzinom', 'Brustkrebs'),
('C61', 'Prostatakarzinom', 'Prostatakrebs'),

-- Augenkrankheiten
('H10', 'Konjunktivitis', 'Bindehautentzündung'),
('H52', 'Refraktionsfehler', 'Kurzsichtigkeit oder Weitsichtigkeit'),
('H40', 'Glaukom', 'Grüner Star'),
('H25', 'Katarakt', 'Grauer Star'),

-- Ohrenerkrankungen
('H60', 'Otitis externa', 'Entzündung des äußeren Gehörgangs'),
('H65', 'Otitis media', 'Mittelohrentzündung'),
('H90', 'Hörverlust', 'Schwerhörigkeit'),

-- Bewegungsapparat
('M54.5', 'Rückenschmerzen', 'Schmerzen im unteren Rückenbereich'),
('M17', 'Gonarthrose', 'Arthrose des Kniegelenks'),
('M16', 'Koxarthrose', 'Arthrose des Hüftgelenks'),
('M25', 'Gelenkschmerzen', 'Schmerzen in den Gelenken'),

-- Infektionskrankheiten
('A15', 'Tuberkulose', 'Infektion durch Mykobakterien'),
('B19', 'Hepatitis B', 'Entzündung der Leber durch Hepatitis B'),
('A54', 'Gonorrhoe', 'Bakterielle Geschlechtskrankheit'),

-- Weitere Erkrankungen
('R51', 'Kopfschmerzen', 'Allgemeine Kopfschmerzen ohne spezifische Ursache'),
('Z71.3', 'Beratung bei Ernährungsproblemen', 'Ernährungsberatung'),
('R00.2', 'Palpitationen', 'Herzklopfen'),
('E86', 'Dehydratation', 'Flüssigkeitsmangel'),
('T78.4', 'Allergische Reaktion', 'Nicht näher bezeichnete allergische Reaktion');


INSERT INTO diagnosen (ICD10_Code, Name, Beschreibung)
VALUES
-- Infektionskrankheiten
('A00', 'Cholera', 'Akute bakterielle Infektionskrankheit durch Vibrio cholerae'),
('A37', 'Keuchhusten', 'Bakterielle Infektion durch Bordetella pertussis'),
('A80', 'Akute Poliomyelitis', 'Entzündung der grauen Rückenmarkssubstanz'),
('B05', 'Masern', 'Viruserkrankung mit Hautausschlag und Fieber'),
('B06', 'Röteln', 'Viruserkrankung mit Hautausschlag und Lymphknotenschwellung'),
('B16', 'Akute Hepatitis B', 'Akute Virusinfektion der Leber'),
('B18', 'Chronische Hepatitis B', 'Chronische Entzündung der Leber durch Hepatitis B'),
('B19', 'Nicht näher bezeichnete Virushepatitis', 'Entzündung der Leber durch unbekanntes Virus'),

-- Atemwegserkrankungen
('J40', 'Bronchitis, nicht näher bezeichnet', 'Chronische oder akute Entzündung der Bronchien'),
('J44', 'Chronisch obstruktive Lungenerkrankung (COPD)', 'Chronische Atemwegsverengung'),
('J46', 'Status asthmaticus', 'Schwerer Asthmaanfall mit Lebensgefahr'),
('J98.4', 'Lungenfibrose', 'Chronische Vernarbung des Lungengewebes'),

-- Herz-Kreislauf-Erkrankungen
('I25', 'Chronische ischämische Herzkrankheit', 'Verminderte Durchblutung des Herzmuskels'),
('I35', 'Aortenklappenfehler', 'Fehlfunktion der Aortenklappe'),
('I70', 'Arteriosklerose', 'Verhärtung und Verengung der Arterien'),
('I71', 'Aortenaneurysma', 'Erweiterung der Hauptschlagader'),
('I80', 'Tiefe Venenthrombose', 'Blutgerinnsel in tiefen Venen'),

-- Stoffwechselerkrankungen
('E70', 'Störungen des Phenylalanin-Stoffwechsels', 'Stoffwechselstörung, z. B. Phenylketonurie'),
('E72', 'Störungen des Aminosäuren-Stoffwechsels', 'Erkrankungen des Proteinstoffwechsels'),
('E74', 'Störungen des Kohlenhydratstoffwechsels', 'z. B. Glykogenosen'),

-- Erkrankungen des Nervensystems
('G20', 'Morbus Parkinson', 'Neurodegenerative Erkrankung mit Bewegungsstörungen'),
('G35', 'Multiple Sklerose', 'Chronische Autoimmunerkrankung des Zentralnervensystems'),
('G60', 'Erbliche und idiopathische Neuropathien', 'Erkrankungen der peripheren Nerven'),
('G71', 'Primäre Muskeldystrophie', 'Muskelschwund durch genetische Defekte'),

-- Psychische Erkrankungen
('F20', 'Schizophrenie', 'Psychische Erkrankung mit Realitätsverlust und Halluzinationen'),
('F31', 'Bipolare affektive Störung', 'Wechsel von depressiven und manischen Phasen'),
('F33', 'Rezidivierende depressive Störung', 'Wiederkehrende depressive Episoden'),
('F50', 'Essstörungen', 'z. B. Anorexia nervosa, Bulimie'),

-- Verdauungssystem
('K31', 'Andere Krankheiten des Magens', 'z. B. Magenausgangsstenose'),
('K80', 'Gallensteine', 'Steinbildung in der Gallenblase oder den Gallenwegen'),
('K81', 'Cholezystitis', 'Entzündung der Gallenblase'),
('K90', 'Malabsorption', 'z. B. Zöliakie, Laktoseintoleranz'),

-- Hautkrankheiten
('L50', 'Urtikaria', 'Nesselsucht mit Hautausschlag und Juckreiz'),
('L63', 'Alopecia areata', 'Haarverlust durch Autoimmunreaktion'),
('L70', 'Akne vulgaris', 'Hauterkrankung durch verstopfte Poren'),
('L73', 'Follikulitis', 'Entzündung der Haarfollikel'),

-- Erkrankungen der Harnwege
('N15', 'Nierenabszess', 'Abszessbildung in den Nieren'),
('N18', 'Chronische Niereninsuffizienz', 'Langsam fortschreitender Verlust der Nierenfunktion'),
('N25', 'Renale Osteodystrophie', 'Knochenveränderungen durch Nierenerkrankungen'),

-- Genetische Erkrankungen
('Q21', 'Vorhofseptumdefekt', 'Fehlbildung im Herzen'),
('Q61', 'Zystennieren', 'Genetisch bedingte Zystenbildung in den Nieren'),
('Q87', 'Andere angeborene Fehlbildungssyndrome', 'Seltene genetische Syndrome'),

-- Onkologische Erkrankungen
('C18', 'Kolonkarzinom', 'Dickdarmkrebs'),
('C20', 'Rektumkarzinom', 'Mastdarmkrebs'),
('C64', 'Nierenzellkarzinom', 'Bösartiger Tumor der Niere'),
('C71', 'Hirntumor', 'Primärer Tumor des Gehirns'),

-- Bewegungsapparat
('M05', 'Rheumatoide Arthritis', 'Autoimmunerkrankung der Gelenke'),
('M32', 'Systemischer Lupus erythematodes', 'Autoimmunerkrankung mit Organbeteiligung'),
('M41', 'Skoliose', 'Seitliche Verkrümmung der Wirbelsäule'),
('M62', 'Muskelatrophie', 'Muskelschwund durch verschiedene Ursachen'),

-- Augenkrankheiten
('H53', 'Sehstörungen', 'z. B. Doppeltsehen, Nachtblindheit'),
('H54', 'Blindheit und Sehverlust', 'Kompletter oder teilweiser Sehverlust'),

-- Weitere Erkrankungen
('R53', 'Müdigkeit', 'Chronische Erschöpfung und Schwächegefühl'),
('R55', 'Synkope', 'Kurzzeitiger Bewusstseinsverlust'),
('R73', 'Erhöhte Blutzuckerwerte', 'Ohne Diagnose eines Diabetes'),
('T88', 'Komplikationen nach Operationen', 'z. B. Infektionen, Narbenbildung'),
('Z00', 'Gesundheitsuntersuchung', 'Routinemäßige Vorsorgeuntersuchung'),
('Z20', 'Kontakt mit Infektionskrankheiten', 'z. B. Kontakt mit Tuberkulose, HIV');


CREATE TABLE allergien (
    AllergieID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,       -- Name der Allergie
    Beschreibung TEXT                 -- Beschreibung der Allergie
);


CREATE TABLE patient_allergien (
    PatientAllergieID INT AUTO_INCREMENT PRIMARY KEY,
    PatientID INT NOT NULL,          -- Verknüpfung mit Patiententabelle
    AllergieID INT NOT NULL,         -- Verknüpfung mit Allergietabelle
    Bemerkung TEXT,                  -- Zusätzliche Hinweise (z. B. Schweregrad)
    FOREIGN KEY (PatientID) REFERENCES patients(id),
    FOREIGN KEY (AllergieID) REFERENCES allergien(AllergieID)
);

INSERT INTO allergien (Name, Beschreibung)
VALUES
('Pollenallergie', 'Allergische Reaktion auf Pollen von Bäumen, Gräsern oder Kräutern'),
('Hausstaubmilbenallergie', 'Allergische Reaktion auf Hausstaubmilben'),
('Tierhaarallergie', 'Allergische Reaktion auf Proteine in Tierhaaren'),
('Nahrungsmittelallergie', 'Allergie gegen Lebensmittel wie Erdnüsse, Milch, oder Eier'),
('Medikamentenallergie', 'Allergische Reaktion auf Medikamente wie Penicillin'),
('Insektengiftallergie', 'Allergie gegen Stiche von Bienen oder Wespen'),
('Latexallergie', 'Allergische Reaktion auf Latexprodukte'),
('Schimmelpilzallergie', 'Allergie gegen Schimmelpilzsporen');

INSERT INTO patient_allergien (PatientID, AllergieID, Bemerkung)
VALUES
(1, 1, 'Starke allergische Rhinitis im Frühling'),   -- Patient 1 hat eine Pollenallergie
(1, 5, 'Allergische Reaktion auf Penicillin'),      -- Patient 1 hat eine Medikamentenallergie
(2, 4, 'Unverträglichkeit gegenüber Erdnüssen'),    -- Patient 2 hat eine Nahrungsmittelallergie
(2, 6, 'Gefahr eines anaphylaktischen Schocks');    -- Patient 3 hat eine Insektengiftallergie


CREATE TABLE blutparameter (
    ParameterID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,           -- Name des Parameters (z. B. Hämoglobin)
    Einheit VARCHAR(50),                  -- Einheit des Wertes (z. B. g/dL, mmol/L)
    Referenzbereich_Min FLOAT,            -- Unterer Referenzwert
    Referenzbereich_Max FLOAT             -- Oberer Referenzwert
);

CREATE TABLE patient_laborwerte (
    LaborwertID INT AUTO_INCREMENT PRIMARY KEY,
    PatientID INT NOT NULL,               -- Verknüpfung mit Patiententabelle
    ParameterID INT NOT NULL,             -- Verknüpfung mit Blutparametertabelle
    Wert FLOAT NOT NULL,                  -- Gemessener Laborwert
    Datum DATE NOT NULL,                  -- Datum der Messung
    Bemerkung TEXT,                       -- Zusätzliche Informationen
    FOREIGN KEY (PatientID) REFERENCES patients(id),
    FOREIGN KEY (ParameterID) REFERENCES blutparameter(ParameterID)
);

INSERT INTO blutparameter (Name, Einheit, Referenzbereich_Min, Referenzbereich_Max)
VALUES
('Hämoglobin', 'g/dL', 12.0, 16.0),
('Leukozyten', '10^9/L', 4.0, 10.0),
('Thrombozyten', '10^9/L', 150.0, 400.0),
('Kreatinin', 'mg/dL', 0.6, 1.2),
('Gesamtcholesterin', 'mmol/L', 3.0, 5.0),
('HDL-Cholesterin', 'mmol/L', 1.0, NULL),
('LDL-Cholesterin', 'mmol/L', 0.0, 3.0),
('Triglyceride', 'mmol/L', 0.0, 1.7),
('Glukose (nüchtern)', 'mmol/L', 3.9, 5.6),
('HbA1c', '%', 4.0, 5.6);

INSERT INTO patient_laborwerte (PatientID, ParameterID, Wert, Datum, Bemerkung)
VALUES
(1, 1, 14.5, '2024-12-23', 'Normwert'),            -- Hämoglobin für Patient 1
(1, 2, 6.8, '2024-12-23', 'Leukozyten leicht erhöht'), -- Leukozyten für Patient 1
(1, 5, 5.5, '2024-12-23', 'Cholesterin im Grenzbereich'), -- Gesamtcholesterin
(2, 3, 180.0, '2024-12-22', 'Normwert'),           -- Thrombozyten für Patient 2
(2, 9, 6.3, '2024-12-22', 'Hyperglykämie');        -- Glukose für Patient 2


INSERT INTO blutparameter (Name, Einheit, Referenzbereich_Min, Referenzbereich_Max)
VALUES
-- Hämatologie
('Erythrozyten', '10^12/L', 4.5, 5.9),
('Hämatokrit', '%', 40.0, 50.0),
('MCV', 'fL', 80.0, 96.0),
('MCH', 'pg', 27.0, 33.0),
('MCHC', 'g/dL', 32.0, 36.0),
('Retikulozyten', '%', 0.5, 2.0),

-- Nierenfunktion
('Harnstoff', 'mg/dL', 15.0, 45.0),
('Harnsäure', 'mg/dL', 2.4, 6.0),
('eGFR', 'mL/min/1.73m²', 90.0, NULL), -- Geschätzte glomeruläre Filtrationsrate

-- Leberwerte
('Bilirubin gesamt', 'mg/dL', 0.1, 1.2),
('Bilirubin direkt', 'mg/dL', 0.0, 0.3),
('ALAT (GPT)', 'U/L', 10.0, 35.0),
('ASAT (GOT)', 'U/L', 10.0, 35.0),
('Gamma-GT', 'U/L', 10.0, 55.0),
('Alkalische Phosphatase', 'U/L', 40.0, 129.0),
('Albumin', 'g/dL', 3.5, 5.0),

-- Elektrolyte
('Natrium', 'mmol/L', 135.0, 145.0),
('Kalium', 'mmol/L', 3.5, 5.0),
('Chlorid', 'mmol/L', 98.0, 107.0),
('Kalzium', 'mg/dL', 8.8, 10.2),
('Phosphat', 'mg/dL', 2.5, 4.5),
('Magnesium', 'mg/dL', 1.7, 2.5),

-- Entzündungsparameter
('CRP (C-reaktives Protein)', 'mg/L', 0.0, 5.0),
('BSG (Blutsenkungsgeschwindigkeit)', 'mm/h', 0.0, 20.0),
('Fibrinogen', 'g/L', 2.0, 4.0),

-- Schilddrüsenparameter
('TSH', 'mU/L', 0.4, 4.0),
('fT3 (freies T3)', 'pg/mL', 2.3, 4.2),
('fT4 (freies T4)', 'ng/dL', 0.8, 1.7),

-- Lipidprofil
('Apolipoprotein A1', 'mg/dL', 110.0, 180.0),
('Apolipoprotein B', 'mg/dL', 55.0, 125.0),
('Lipoprotein (a)', 'mg/dL', 0.0, 30.0),

-- Gerinnungsparameter
('INR', '', 0.8, 1.2),
('PTT (partielle Thromboplastinzeit)', 'Sekunden', 25.0, 35.0),
('Fibrinogen', 'g/L', 2.0, 4.0),
('D-Dimere', 'mg/L', 0.0, 0.5),

-- Vitamine und Spurenelemente
('Vitamin D', 'ng/mL', 20.0, 50.0),
('Vitamin B12', 'pg/mL', 200.0, 900.0),
('Folat', 'ng/mL', 3.0, 17.0),
('Eisen', 'µg/dL', 50.0, 170.0),
('Ferritin', 'ng/mL', 15.0, 200.0),
('Kupfer', 'µg/dL', 70.0, 140.0),
('Zink', 'µg/dL', 70.0, 120.0),
('Selen', 'µg/L', 50.0, 120.0),

-- Diabetes-Parameter
('Fruktosamin', 'µmol/L', 205.0, 285.0),
('Insulin', 'µU/mL', 2.0, 25.0),
('C-Peptid', 'ng/mL', 0.9, 4.3),

-- Weitere Parameter
('Laktat', 'mmol/L', 0.5, 2.2),
('Ammoniak', 'µmol/L', 15.0, 50.0),
('Procalcitonin', 'ng/mL', 0.0, 0.5),
('Troponin T', 'ng/mL', 0.0, 0.014),
('BNP (Brain Natriuretic Peptide)', 'pg/mL', 0.0, 100.0),
('Myoglobin', 'ng/mL', 0.0, 70.0),
('LDH (Laktatdehydrogenase)', 'U/L', 125.0, 243.0);


CREATE TABLE termine (
    TerminID INT AUTO_INCREMENT PRIMARY KEY, -- Eindeutige ID für jeden Termin
    PatientID INT NOT NULL,                  -- Verknüpfung mit Patient
    ArztID INT NOT NULL,                     -- Verknüpfung mit Arzt
    Datum DATE NOT NULL,                     -- Datum des Termins
    Uhrzeit TIME NOT NULL,                   -- Uhrzeit des Termins
    Grund TEXT,                              -- Grund des Termins
    Status ENUM('geplant', 'abgesagt', 'abgeschlossen') DEFAULT 'geplant', -- Status des Termins
    FOREIGN KEY (PatientID) REFERENCES patients(id),
    FOREIGN KEY (ArztID) REFERENCES arzt(ArztID)
);

INSERT INTO termine (PatientID, ArztID, Datum, Uhrzeit, Grund, Status)
VALUES
(1, 1, '2024-12-24', '10:00:00', 'Routineuntersuchung', 'geplant'),
(2, 2, '2024-12-25', '14:30:00', 'Blutdruckkontrolle', 'geplant'),
(1, 2, '2024-12-26', '09:00:00', 'Nachbesprechung Laborwerte', 'geplant');

SELECT 
    t.TerminID,
    p.vorname AS PatientVorname,
    p.nachname AS PatientNachname,
    a.vorname AS ArztVorname,
    a.nachname AS ArztNachname,
    t.Datum,
    t.Uhrzeit,
    t.Grund,
    t.Status
FROM termine t
JOIN patients p ON t.PatientID = p.id
JOIN arzt a ON t.ArztID = a.ArztID
ORDER BY t.Datum, t.Uhrzeit;

SELECT 
    PatientID, 
    MedikamentID, 
    Dosierung, 
    Startdatum, 
    Enddatum, 
    COUNT(*) AS Anzahl
FROM patient_medikamente
GROUP BY PatientID, MedikamentID, Dosierung, Startdatum, Enddatum
HAVING COUNT(*) > 1;



ALTER TABLE rezept MODIFY ArztID INT DEFAULT 0;



ALTER TABLE rezept MODIFY ArztID INT NULL;

INSERT INTO rezept (PatientID, ArztID, MedikamentID, Dosierung, Startdatum, Enddatum)
SELECT 
    PatientID,
    NULL AS ArztID,
    MedikamentID,
    Dosierung,
    Startdatum,
    Enddatum
FROM patient_medikamente;

DROP TABLE patient_medikamente;

CREATE TABLE rechnungen (
    RechnungID INT AUTO_INCREMENT PRIMARY KEY,
    PatientID INT NOT NULL,
    Betrag DECIMAL(10, 2) NOT NULL,
    Datum DATE NOT NULL,
    Status ENUM('offen', 'bezahlt', 'überfällig') DEFAULT 'offen',
    FOREIGN KEY (PatientID) REFERENCES patients(id)
);

INSERT INTO rechnungen (PatientID, Betrag, Datum, Status)
VALUES
(1, 150.50, '2024-12-20', 'offen'),
(2, 250.00, '2024-12-18', 'bezahlt'),
(1, 100.00, '2024-12-22', 'überfällig');

SELECT 
    r.RechnungID,
    p.vorname AS PatientVorname,
    p.nachname AS PatientNachname,
    r.Betrag,
    r.Datum,
    r.Status
FROM rechnungen r
JOIN patients p ON r.PatientID = p.id
ORDER BY r.Datum DESC;

UPDATE rechnungen
SET Status = 'bezahlt'
WHERE RechnungID = 1; -- RechnungID anpassen

ALTER TABLE rechnungen ADD VersicherungID INT;
ALTER TABLE rechnungen ADD FOREIGN KEY (VersicherungID) REFERENCES insurance(id);

ALTER TABLE rechnungen ADD Beschreibung TEXT;

ALTER TABLE rechnungen ADD Zahlungsdatum DATE;

SELECT 
    r.RechnungID,
    p.vorname AS PatientVorname,
    p.nachname AS PatientNachname,
    r.Betrag,
    r.Beschreibung,
    r.Datum,
    r.Zahlungsdatum,
    r.Status
FROM rechnungen r
JOIN patients p ON r.PatientID = p.id;

CREATE TABLE benutzer (
    BenutzerID INT AUTO_INCREMENT PRIMARY KEY,
    Benutzername VARCHAR(255) UNIQUE NOT NULL, -- Eindeutiger Benutzername
    Passwort VARCHAR(255) NOT NULL,           -- Sicher speichern, z. B. mit Hashing
    ArztID INT,                               -- Verknüpfung mit der Arzt-Tabelle
    Aktiv BOOLEAN DEFAULT TRUE,               -- Aktivierungsstatus des Benutzers
    FOREIGN KEY (ArztID) REFERENCES arzt(ArztID) -- Optionaler Bezug zu einem Arzt
);

INSERT INTO benutzer (Benutzername, Passwort, ArztID, Aktiv)
VALUES
('dr.kajetan', 'hashed_password_123', 1, TRUE), -- Verknüpft mit Arzt Max Musterarzt
('anna.kardiologin', 'hashed_password_456', 2, TRUE), -- Verknüpft mit Ärztin Anna Kardiologin
('admin', 'hashed_password_789', NULL, TRUE); -- Kein Bezug zu einem Arzt


CREATE TABLE protokoll (
    ProtokollID INT AUTO_INCREMENT PRIMARY KEY, -- Eindeutige ID für jede Protokolleintragung
    BenutzerID INT,                             -- Verknüpfung zum Benutzer, der die Aktion ausgeführt hat
    Aktion TEXT NOT NULL,                       -- Beschreibung der durchgeführten Aktion
    Zeitpunkt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Zeitpunkt der Aktion
    FOREIGN KEY (BenutzerID) REFERENCES benutzer(BenutzerID) -- Verbindung zur Benutzertabelle
);

INSERT INTO protokoll (BenutzerID, Aktion)
VALUES
(1, 'Neue Diagnose für PatientID 1 hinzugefügt: Grippe'),
(2, 'Laborwert für PatientID 2 aktualisiert: Glukose = 6.8 mmol/L');


DELIMITER $$

CREATE TRIGGER nach_diagnose_insert
AFTER INSERT ON patient_diagnosen
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Neue Diagnose hinzugefügt: PatientID = ', NEW.PatientID, ', DiagnoseID = ', NEW.DiagnoseID, ', Datum = ', NEW.Datum));
END$$

DELIMITER ;


DELIMITER $$
CREATE TRIGGER nach_diagnose_update
AFTER UPDATE ON patient_diagnosen
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Diagnose aktualisiert: PatientID = ', NEW.PatientID, ', DiagnoseID = ', NEW.DiagnoseID, ', Datum = ', NEW.Datum));
END$$
DELIMITER ;

DELIMITER $$

-- 2. Trigger für patient_laborwerte
CREATE TRIGGER nach_labor_insert
AFTER INSERT ON patient_laborwerte
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Neuer Laborwert hinzugefügt: PatientID = ', NEW.PatientID, ', ParameterID = ', NEW.ParameterID, ', Wert = ', NEW.Wert));
END$$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER nach_labor_update
AFTER UPDATE ON patient_laborwerte
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Laborwert aktualisiert: PatientID = ', NEW.PatientID, ', ParameterID = ', NEW.ParameterID, ', Neuer Wert = ', NEW.Wert));
END$$
DELIMITER ;

DELIMITER $$
-- 3. Trigger für rezept
CREATE TRIGGER nach_rezept_insert
AFTER INSERT ON rezept
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Neues Rezept erstellt: PatientID = ', NEW.PatientID, ', MedikamentID = ', NEW.MedikamentID, ', Dosierung = ', NEW.Dosierung));
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER nach_rezept_update
AFTER UPDATE ON rezept
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Rezept aktualisiert: PatientID = ', NEW.PatientID, ', MedikamentID = ', NEW.MedikamentID, ', Neue Dosierung = ', NEW.Dosierung));
END$$
DELIMITER ;

DELIMITER $$
-- 4. Trigger für termine
CREATE TRIGGER nach_termin_insert
AFTER INSERT ON termine
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Neuer Termin erstellt: PatientID = ', NEW.PatientID, ', ArztID = ', NEW.ArztID, ', Datum = ', NEW.Datum, ', Uhrzeit = ', NEW.Uhrzeit));
END$$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER nach_termin_update
AFTER UPDATE ON termine
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Termin aktualisiert: PatientID = ', NEW.PatientID, ', ArztID = ', NEW.ArztID, ', Neuer Status = ', NEW.Status));
END$$
DELIMITER ;


DELIMITER $$
-- 5. Trigger für rechnungen
CREATE TRIGGER nach_rechnung_insert
AFTER INSERT ON rechnungen
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Neue Rechnung erstellt: PatientID = ', NEW.PatientID, ', Betrag = ', NEW.Betrag, ', Datum = ', NEW.Datum));
END$$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER nach_rechnung_update
AFTER UPDATE ON rechnungen
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Rechnung aktualisiert: PatientID = ', NEW.PatientID, ', Neuer Status = ', NEW.Status));
END$$
DELIMITER ;


DELIMITER $$
-- 6. Trigger für patients
CREATE TRIGGER nach_patient_update
AFTER UPDATE ON patients
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NULL, CONCAT('Patientendaten aktualisiert: PatientID = ', NEW.id, ', Vorname = ', NEW.vorname, ', Nachname = ', NEW.nachname));
END$$
DELIMITER ;


DELIMITER $$
-- 7. Trigger für benutzer
CREATE TRIGGER nach_benutzer_insert
AFTER INSERT ON benutzer
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NEW.BenutzerID, CONCAT('Neuer Benutzer erstellt: Benutzername = ', NEW.Benutzername));
END$$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER nach_benutzer_update
AFTER UPDATE ON benutzer
FOR EACH ROW
BEGIN
    INSERT INTO protokoll (BenutzerID, Aktion)
    VALUES (NEW.BenutzerID, CONCAT('Benutzerdaten aktualisiert: Benutzername = ', NEW.Benutzername));
END$$
DELIMITER ;

INSERT INTO patient_diagnosen (PatientID, DiagnoseID, Datum, Bemerkung)
VALUES (1, 2, '2024-12-24', 'Testdiagnose hinzugefügt');


SELECT pd.PatientDiagnoseID, pd.PatientID, pd.DiagnoseID, pd.Datum, pd.Bemerkung, 
       d.Name AS DiagnoseName, d.Beschreibung AS DiagnoseBeschreibung, 
       CONCAT(p.vorname, ' ', p.nachname) AS PatientName
FROM patient_diagnosen pd
LEFT JOIN diagnosen d ON pd.DiagnoseID = d.DiagnoseID
LEFT JOIN patients p ON pd.PatientID = p.id;
