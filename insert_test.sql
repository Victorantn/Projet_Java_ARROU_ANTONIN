-- =============================================================
-- Données fictives : Licence MIASHS parcours MIAGE (3 années)
-- 60 ECTS par an — 180 ECTS au total — 58 UE
-- =============================================================

-- 1. FORMATION & PARCOURS
INSERT INTO Formation (nomFormation) VALUES ('L MENTION MIASHS');
INSERT INTO Parcours (nomParcours, codeFormation) VALUES ('MIAGE', 1);

-- =============================================================
-- 2. UNITÉS D''ENSEIGNEMENT
-- =============================================================

-- =========== L1 — Semestre 1 (IMPAIR) — UE 1-10 — 30 ECTS ===========
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('LE POSTE DE TRAVAIL', 3, 0);                                          -- 1
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('CRÉATION WEB', 3, 0);                                                 -- 2
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('CONNAISSANCE DE L''ORGANISATION ET DU MONDE DU TRAVAIL', 3, 0);       -- 3
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ÉCONOMIE', 3, 0);                                                     -- 4
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('SI : ENJEUX ET USAGES POUR LES ENTREPRISES', 3, 0);                   -- 5
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('CONFIGURATION D''UN POSTE DE TRAVAIL', 3, 0);                         -- 6
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('STRUCTURE DE DONNÉES', 3, 0);                                         -- 7
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('BD : MODÉLISATION', 3, 0);                                            -- 8
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('INTERACTION HOMME-MACHINE', 3, 0);                                    -- 9
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('MISE EN OEUVRE D''ALGORITHMES', 3, 0);                                 -- 10

-- =========== L1 — Semestre 2 (PAIR) — UE 11-20 — 30 ECTS ===========
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('PROJET DATA', 3, 0);                                                  -- 11
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('USAGE AVANCÉ D''UN POSTE DE TRAVAIL', 3, 0);                          -- 12
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('PRODUCTION D''ALGORITHMES', 3, 0);                                    -- 13
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('INTRODUCTION À LA GESTION D''ENTREPRISE', 3, 0);                      -- 14
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('CULTURE GÉNÉRALE', 3, 0);                                             -- 15
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('INTERACTIVITÉ ET GAMIFICATION', 3, 0);                                -- 16
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('PROGRAMMATION OBJET', 3, 0);                                          -- 17
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ANGLAIS', 3, 0);                                                      -- 18
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('LANGUE 2', 3, 0);                                                     -- 19
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('DEVENIR ÉTUDIANT', 3, 0);                                             -- 20

-- =========== L2 — Semestre 3 (IMPAIR) — UE 21-30 — 30 ECTS ===========
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('RÉSEAUX ET SERVICES', 3, 0);                                          -- 21
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('PSYCHOLOGIE SOCIALE ET SOCIOLOGIE DES ORGANISATIONS', 3, 0);          -- 22
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('PRINCIPES, DÉMARCHES ET OUTILS DE LA GESTION DE PROJET', 3, 0);      -- 23
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ANALYSE FINANCIÈRE', 3, 0);                                           -- 24
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('CONCEPTION OBJET', 3, 0);                                             -- 25
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ALGÈBRE RELATIONNELLE', 3, 0);                                        -- 26
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('GRAPHES ET OPTIMISATION', 3, 0);                                      -- 27
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ÉCONOMIE ET DROIT', 3, 0);                                            -- 28
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('MÉTHODES DE DÉVELOPPEMENT STRUCTURÉ', 3, 0);                          -- 29
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('MATHÉMATIQUES POUR L''INFORMATIQUE', 3, 0);                           -- 30

-- =========== L2 — Semestre 4 (PAIR) — UE 31-39 — 30 ECTS ===========
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('RÉSEAUX POUR LES ORGANISATIONS', 3, 0);                               -- 31
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('CONCEPTS AVANCÉS DE GESTION', 3, 0);                                  -- 32
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('INITIATION À LA GESTION DE PROJETS', 3, 0);                           -- 33
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ALGÈBRE POUR L''INFORMATIQUE', 3, 0);                                 -- 34
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('PROJET LOGICIEL : APPLICATION BD ET WEB', 6, 0);                      -- 35
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('OUTILS STATISTIQUES', 3, 0);                                          -- 36
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ANGLAIS DE SPÉCIALITÉ 2', 3, 0);                                      -- 37
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('LANGUE 2 (S4)', 3, 0);                                                -- 38
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('PROFESSIONNALISATION L2', 3, 0);                                      -- 39

-- =========== L3 — Semestre 5 (IMPAIR) — UE 40-49 — 30 ECTS ===========
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('MÉTHODES D''ANALYSE ET DE MODÉLISATION DE SI', 3, 0);                 -- 40
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ANGLAIS DE SPÉCIALITÉ 1', 3, 0);                                      -- 41
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('HARMONISATION GESTION', 3, 0);                                        -- 42
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('HARMONISATION INFORMATIQUE', 3, 0);                                   -- 43
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('OPTION : SCIENCES NUMÉRIQUES', 3, 0);                                 -- 44
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('OPTION : ENSEMBLES ET LOGIQUE', 3, 0);                                -- 45
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('OPTION : FONCTIONS ET CALCULS', 3, 0);                                -- 46
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('OPTION : PROBABILITÉS ET STATISTIQUES', 3, 0);                        -- 47
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('OPTION : SOCIOLOGIE DES ORGANISATIONS', 3, 0);                        -- 48
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('LANGUE 2 (S5)', 3, 0);                                                -- 49

-- =========== L3 — Semestre 6 (PAIR) — UE 50-58 — 30 ECTS ===========
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('INTERCONNEXION DE RÉSEAUX ET VIRTUALISATION', 3, 0);                  -- 50
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('CONCEPTION OBJET AVANCÉE', 3, 0);                                     -- 51
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ACCÈS CONCURRENTS DE BASES DE DONNÉES', 3, 0);                        -- 52
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('DÉVELOPPEMENT FULL STACK', 3, 0);                                     -- 53
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('OUTILLAGE ET PROJET', 3, 0);                                          -- 54
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('PROFESSIONNALISATION L3', 6, 0);                                      -- 55
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('PROJET ENTREPRENEURIAL DANS UNE APPROCHE SOCIÉTALE', 3, 0);           -- 56
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('ANGLAIS (S6)', 3, 0);                                                 -- 57
INSERT INTO Ue (nomUe, ects, ouverture) VALUES ('LANGUE 2 (S6)', 3, 0);                                                -- 58

-- =============================================================
-- 3. PROMOTION DE 100 ÉTUDIANTS
-- =============================================================
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('ANTONIN', 'VICTOR', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MARTIN', 'LUCAS', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BERNARD', 'EMMA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('THOMAS', 'CHLOE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('PETIT', 'HUGO', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('ROBERT', 'ALICE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('RICHARD', 'LOUIS', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('DURAND', 'LEA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('DUBOIS', 'ARTHUR', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MOREAU', 'JULIETTE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LAURENT', 'GABRIEL', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('SIMON', 'CAMILLE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MICHEL', 'JULES', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LEFEBVRE', 'SARAH', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LEROY', 'MAXIME', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('ROUX', 'ZOE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('DAVID', 'PAUL', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MOREL', 'CLARA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('FOURNIER', 'TOM', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('GIRARD', 'INÈS', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BONNET', 'ENZO', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BLANC', 'LOLA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('FRANCOIS', 'ANTOINE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MERCIER', 'EVA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('DUPONT', 'CLEMENT', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LAMBERT', 'LINA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('FONTAINE', 'MATHIS', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('ROUSSEAU', 'JADE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('VINCENT', 'NATHAN', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MULLER', 'MIA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LEFEVRE', 'BAPTISTE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('FAURE', 'AMBRE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('ANDRE', 'THEO', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('GUERIN', 'LOU', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BOYER', 'ROMAIN', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('GARNIER', 'ROSE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('CHEVALIER', 'RAPHAEL', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('GAUTHIER', 'ANNA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('PERRIN', 'MAEL', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('ROBIN', 'NINA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('CLEMENT', 'NOAH', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('COLIN', 'AGATHE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('NICOLAS', 'VICTOR', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LE GALL', 'MARGOT', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MATHIEU', 'AXEL', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MARCHAND', 'JEANNE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('DENIS', 'TIMOTHEE', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LEMAIRE', 'ELISA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('DUVAL', 'SIMON', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('GAILLARD', 'ELENA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('RENARD',   'FELIX',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MEYER',    'LUCIE',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('CARPENTIER','JULIEN',  1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('NOEL',     'PAULINE',  1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('PICARD',   'ALEXIS',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('VIDAL',    'MANON',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('DUMAS',    'THOMAS',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('AUBERT',   'CAMILLE',  1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('CARON',    'ETHAN',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('FLEURY',   'SOLENE',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BAILLY',   'QUENTIN',  1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('RIVIERE',  'CHARLOTTE',1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('PREVOST',  'OSCAR',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('HENRY',    'AURORE',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LECLERCQ', 'DORIAN',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('PARIS',    'MARGAUX',  1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('ADAM',     'KILLIAN',  1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('PIRES',    'ILONA',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('GERMAIN',  'SAMUEL',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BERTRAND', 'OCEANE',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MAILLARD', 'FLORIAN',  1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('GRONDIN',  'LILAS',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('PASQUIER', 'ADRIEN',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('CHARRON',  'ANAÏS',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('TESSIER',  'PIERRE',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('COULON',   'MARIE',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('DIALLO',   'IBRAHIMA', 1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BENALI',   'YASMINE',  1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LOPES',    'MATHIEU',  1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('NGUYEN',   'LINH',     1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('TRAN',     'KEVIN',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('HASSAN',   'SARA',     1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('TORRES',   'DIEGO',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('DA SILVA', 'ANA',      1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('SCHMITT',  'LUKAS',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('WAGNER',   'LEA',      1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BECKER',   'MAX',      1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('FERRARI',  'GIULIA',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('ROSSI',    'MARCO',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('HERRERA',  'SOFIA',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('PEREZ',    'CARLOS',   1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('ALVES',    'JOAO',     1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('COSTA',    'MARIA',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MOREIRA',  'HUGO',     1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('EL AMRANI','NADIA',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('MEKKI',    'KARIM',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BOUDOU',   'LOIC',     1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('LACROIX',  'ANAIS',    1, 1);
INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours) VALUES ('BARON',    'EDOUARD',  1, 1);

-- =============================================================
-- 4. AFFECTATION DES UEs À LA FORMATION ET AU PARCOURS
-- =============================================================
INSERT INTO Formation_Ue (codeFormation, codeUe) SELECT 1, codeUe FROM Ue WHERE ouverture = 0;
INSERT INTO Parcours_Ue (codeParcours, codeUe) SELECT 1, codeUe FROM Ue WHERE ouverture = 0;

-- =============================================================
-- 5. PRÉREQUIS
-- =============================================================
-- CONCEPTION OBJET AVANCÉE (51) requiert CONCEPTION OBJET (25)
INSERT INTO Ue_Prerequis (codeUe, codeUePreRequis) VALUES (51, 25);
-- ACCÈS CONCURRENTS DE BD (52) requiert BD : MODÉLISATION (8)
INSERT INTO Ue_Prerequis (codeUe, codeUePreRequis) VALUES (52, 8);

-- =============================================================
-- 6. INSCRIPTIONS SUR 3 ANNÉES (85 % VALIDE / 15 % ECHOUE)
-- =============================================================
BEGIN
FOR etu IN (SELECT numeroEtudiant FROM Etudiant) LOOP

        -- ============ L1 — 2023 / 2024 ============

        -- S1 (IMPAIR) — UE 1 à 10
        FOR mat IN (SELECT codeUe FROM Ue WHERE codeUe BETWEEN 1 AND 10) LOOP
            INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut)
            VALUES (etu.numeroEtudiant, mat.codeUe, '2023 / 2024', 'IMPAIR',
                    CASE WHEN DBMS_RANDOM.VALUE(0, 100) <= 15 THEN 'ECHOUE' ELSE 'VALIDE' END);
END LOOP;

        -- S2 (PAIR) — UE 11 à 20
FOR mat IN (SELECT codeUe FROM Ue WHERE codeUe BETWEEN 11 AND 20) LOOP
            INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut)
            VALUES (etu.numeroEtudiant, mat.codeUe, '2023 / 2024', 'PAIR',
                    CASE WHEN DBMS_RANDOM.VALUE(0, 100) <= 15 THEN 'ECHOUE' ELSE 'VALIDE' END);
END LOOP;

        -- ============ L2 — 2024 / 2025 ============

        -- S3 (IMPAIR) — UE 21 à 30
FOR mat IN (SELECT codeUe FROM Ue WHERE codeUe BETWEEN 21 AND 30) LOOP
            INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut)
            VALUES (etu.numeroEtudiant, mat.codeUe, '2024 / 2025', 'IMPAIR',
                    CASE WHEN DBMS_RANDOM.VALUE(0, 100) <= 15 THEN 'ECHOUE' ELSE 'VALIDE' END);
END LOOP;

        -- S4 (PAIR) — UE 31 à 39
FOR mat IN (SELECT codeUe FROM Ue WHERE codeUe BETWEEN 31 AND 39) LOOP
            INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut)
            VALUES (etu.numeroEtudiant, mat.codeUe, '2024 / 2025', 'PAIR',
                    CASE WHEN DBMS_RANDOM.VALUE(0, 100) <= 15 THEN 'ECHOUE' ELSE 'VALIDE' END);
END LOOP;

        -- ============ L3 — 2025 / 2026 ============

        -- S5 (IMPAIR) — UE 40 à 49
FOR mat IN (SELECT codeUe FROM Ue WHERE codeUe BETWEEN 40 AND 49) LOOP
            INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut)
            VALUES (etu.numeroEtudiant, mat.codeUe, '2025 / 2026', 'IMPAIR',
                    CASE WHEN DBMS_RANDOM.VALUE(0, 100) <= 15 THEN 'ECHOUE' ELSE 'VALIDE' END);
END LOOP;

        -- S6 (PAIR) — UE 50 à 58
FOR mat IN (SELECT codeUe FROM Ue WHERE codeUe BETWEEN 50 AND 58) LOOP
            INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut)
            VALUES (etu.numeroEtudiant, mat.codeUe, '2025 / 2026', 'PAIR',
                    CASE WHEN DBMS_RANDOM.VALUE(0, 100) <= 15 THEN 'ECHOUE' ELSE 'VALIDE' END);
END LOOP;

END LOOP;

    -- Mise à jour du total ECTS validés pour chaque étudiant
UPDATE Etudiant e SET totalECTS = (
    SELECT NVL(SUM(u.ects), 0)
    FROM InscriptionUe i JOIN Ue u ON i.codeUe = u.codeUe
    WHERE i.numeroEtudiant = e.numeroEtudiant AND i.statut = 'VALIDE'
);

COMMIT;
END;
