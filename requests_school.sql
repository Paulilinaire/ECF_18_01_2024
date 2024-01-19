-- Afficher la liste des classes (sans les élèves).
SELECT * FROM class;

-- Afficher le nombre de matières d'un élève.
SELECT s.id_s, s.lastName_s, s.firstName_s, c.name_class, COUNT(g.id_grade) AS nombre_matieres
FROM student s
JOIN class c ON s.id_class = c.id_class
LEFT JOIN grade g ON s.id_s = g.id_s
WHERE s.id_s = 403;

-- Afficher la liste des notes d'un élève (avec les détails).
SELECT g.id_grade, g.value_g, g.comment_g, s.lastName_s, s.firstName_s, sub.title_sub
FROM grade g
JOIN student s ON g.id_s = s.id_s
JOIN subject sub ON g.id_sub = sub.id_sub
WHERE s.id_s = 405;

-- Afficher la moyenne d'un élève.
SELECT s.id_s, s.lastName_s, s.firstName_s, AVG(g.value_g) AS moyenne
FROM student s
JOIN grade g ON s.id_s = g.id_s
WHERE s.id_s = 405;

-- Afficher le nombre d'élèves d'un département.
SELECT d.name_dep, COUNT(s.id_s) AS nombre_eleves
FROM department d
JOIN class c ON d.id_dep = c.id_dep
JOIN student s ON c.id_class = s.id_class
WHERE d.id_dep = 1;

-- Afficher tous les noms des élèves d'un niveau.
SELECT s.lastName_s, s.firstName_s, c.level_class
FROM student s
JOIN class c ON s.id_class = c.id_class
WHERE c.level_class = 'lycée';







