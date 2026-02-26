/*Script d'insertion des données dans la BD*/

-- Formation (pas de FK)
INSERT INTO Formation (nomFormation) VALUES ('Licence Informatique');
INSERT INTO Formation (nomFormation) VALUES ('Master Informatique');

-- Ue (pas de FK)
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('Algorithmique',      6, 0);
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('Bases de données',   6, 0);
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('Anglais technique',  3, 1);

-- Parcours (FK: codeFormation -> Formation)
-- codeFormation 1 = Licence Informatique
INSERT INTO Parcours (nomParcours, codeFormation) VALUES ('Génie Logiciel',      1);
INSERT INTO Parcours (nomParcours, codeFormation) VALUES ('Systèmes et Réseaux', 1);

-- Etudiant (FK: codeFormation -> Formation, codeParcours -> Parcours)
-- codeFormation 1 = Licence Informatique, codeParcours 1 = Génie Logiciel
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('Dupont', 'Alice', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('Martin', 'Bob',   1, 2);

-- InscriptionUe (FK: numeroEtudiant -> Etudiant, codeUe -> Ue)
-- numeroEtudiant 1 = Dupont Alice, codeUe 1 = Algorithmique
INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut)
    VALUES (1, 1, '2024-2025', 'IMPAIR', 'ENCOURS');
-- numeroEtudiant 1 = Dupont Alice, codeUe 2 = Bases de données
INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut)
    VALUES (1, 2, '2024-2025', 'PAIR',   'VALIDE');
-- numeroEtudiant 2 = Martin Bob, codeUe 1 = Algorithmique
INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut)
    VALUES (2, 1, '2024-2025', 'IMPAIR', 'ECHOUE');

-- Formation_Ue (UEs de base de la formation)
-- Formation 1 (Licence Info) a pour UEs de base : Algorithmique (1) et BDD (2)
INSERT INTO Formation_Ue (codeFormation, codeUe) VALUES (1, 1);
INSERT INTO Formation_Ue (codeFormation, codeUe) VALUES (1, 2);

-- Parcours_Ue (UEs spécialisées du parcours)
-- Parcours 1 (Génie Logiciel) et Parcours 2 (Sys & Réseaux) ont Anglais technique (3)
INSERT INTO Parcours_Ue (codeParcours, codeUe) VALUES (1, 3);
INSERT INTO Parcours_Ue (codeParcours, codeUe) VALUES (2, 3);

-- Ue_Prerequis (prérequis entre UEs)
-- Bases de données (2) nécessite Algorithmique (1)
INSERT INTO Ue_Prerequis (codeUe, codeUePreRequis) VALUES (2, 1);

COMMIT;