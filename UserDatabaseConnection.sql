-- Neue Datenbank für Benutzer erstellen
CREATE DATABASE IF NOT EXISTS BenutzerDatenbank;

-- Datenbank verwenden
USE BenutzerDatenbank;

-- Tabelle für Benutzer erstellen
CREATE TABLE Benutzer (
    benutzer_id INT AUTO_INCREMENT PRIMARY KEY, -- Eindeutige Benutzer-ID
    benutzername VARCHAR(50) NOT NULL UNIQUE,   -- Benutzername
    passwort VARCHAR(50) NOT NULL               -- Passwort
);

-- Beispiel-Daten für Benutzer einfügen
INSERT INTO Benutzer (benutzername, passwort) VALUES
('andres.fierro', 'andres123'),
('anna.mueller', 'anna1988'),
('max.mustermann', 'max1975'),
('laura.schmidt', 'laura1990'),
('thomas.huber', 'thomas1982'),
('julia.weber', 'julia1993'),
('peter.gruber', 'peter1985'),
('maria.hofer', 'maria1978'),
('lukas.koch', 'lukas1992'),
('sophie.lehner', 'sophie1987'),
('markus.eder', 'markus1996'),
('nina.bauer', 'nina1991'),
('johann.mayer', 'johann1983'),
('katharina.fischer', 'kathi1989'),
('david.wagner', 'david1997'),
('elisabeth.schneider', 'liz1986'),
('michael.haas', 'michael1990'),
('carina.winkler', 'carina1982'),
('florian.kreuzer', 'florian1994'),
('lisa.egger', 'lisa1980'),
('georg.schuster', 'georg1995'),
('eva.berger', 'eva1988'),
('christian.brandl', 'chris1993'),
('teresa.pichler', 'teresa1992'),
('patrick.lang', 'patrick1985');





