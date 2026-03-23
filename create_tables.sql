/*Script de creation des tables*/

DROP TABLE Ue_Prerequis;
DROP TABLE Parcours_Ue;
DROP TABLE Formation_Ue;
DROP TABLE InscriptionUe;
DROP TABLE Etudiant;
DROP TABLE Parcours;
DROP TABLE Ue;
DROP TABLE Formation;

CREATE TABLE Formation (
    codeFormation INT           GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nomFormation  VARCHAR2(100) NOT NULL
);

CREATE TABLE Ue (
    codeUe    INT           GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nomUe     VARCHAR2(100) NOT NULL,
    ects      INT           NOT NULL,
    ouverture NUMBER(1)     DEFAULT 0 NOT NULL,
    CONSTRAINT chk_ouverture CHECK (ouverture IN (0, 1))
);

CREATE TABLE Parcours (
    codeParcours  INT           GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nomParcours   VARCHAR2(100) NOT NULL,
    codeFormation INT           NOT NULL,
    FOREIGN KEY (codeFormation) REFERENCES Formation(codeFormation)
);

CREATE TABLE Etudiant (
    numeroEtudiant INT           GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nomE           VARCHAR2(100) NOT NULL,
    prenomE        VARCHAR2(100) NOT NULL,
    codeFormation  INT           NOT NULL,
    codeParcours   INT           NOT NULL,
    totalECTS      INT           DEFAULT 0 NOT NULL,
    FOREIGN KEY (codeFormation) REFERENCES Formation(codeFormation),
    FOREIGN KEY (codeParcours)  REFERENCES Parcours(codeParcours)
);

CREATE TABLE InscriptionUe (
    codeInscrip        INT          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    numeroEtudiant     INT          NOT NULL,
    codeUe             INT          NOT NULL,
    anneeUniversitaire VARCHAR2(9)  NOT NULL,
    semestre           VARCHAR2(6)  NOT NULL,
    statut             VARCHAR2(7)  DEFAULT 'ENCOURS' NOT NULL,
    CONSTRAINT chk_semestre CHECK (semestre IN ('PAIR', 'IMPAIR')),
    CONSTRAINT chk_statut   CHECK (statut   IN ('ENCOURS', 'VALIDE', 'ECHOUE')),
    FOREIGN KEY (numeroEtudiant) REFERENCES Etudiant(numeroEtudiant),
    FOREIGN KEY (codeUe)         REFERENCES Ue(codeUe)
);

CREATE TABLE Formation_Ue (
    codeFormation INT NOT NULL,
    codeUe        INT NOT NULL,
    PRIMARY KEY (codeFormation, codeUe),
    FOREIGN KEY (codeFormation) REFERENCES Formation(codeFormation),
    FOREIGN KEY (codeUe)        REFERENCES Ue(codeUe)
);

CREATE TABLE Parcours_Ue (
    codeParcours INT NOT NULL,
    codeUe       INT NOT NULL,
    PRIMARY KEY (codeParcours, codeUe),
    FOREIGN KEY (codeParcours) REFERENCES Parcours(codeParcours),
    FOREIGN KEY (codeUe)       REFERENCES Ue(codeUe)
);

CREATE TABLE Ue_Prerequis (
    codeUe          INT NOT NULL,
    codeUePreRequis INT NOT NULL,
    PRIMARY KEY (codeUe, codeUePreRequis),
    FOREIGN KEY (codeUe)          REFERENCES Ue(codeUe),
    FOREIGN KEY (codeUePreRequis) REFERENCES Ue(codeUe)
);