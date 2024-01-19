package controller;

import impl.*;
import model.*;
import service.SchoolService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SchoolAppController {
    private final Scanner scanner = new Scanner(System.in);

    private static SchoolService schoolService;
    private static ClassroomDAOImpl classroomDao;
    private static DepartmentDAOImpl departmentDAO;
    private static GradeDAOImpl gradeDAO;
    private static ScheduleDAOImpl scheduleDAO;
    private static StudentDAOImpl studentDAO;
    private static SubjectDAOImpl subjectDAO;
    private static TeacherDAOImpl teacherDAO;


    public SchoolAppController() {
        classroomDao = new ClassroomDAOImpl();
        departmentDAO = new DepartmentDAOImpl();
        gradeDAO = new GradeDAOImpl();
        scheduleDAO = new ScheduleDAOImpl();
        studentDAO = new StudentDAOImpl();
        subjectDAO = new SubjectDAOImpl();
        teacherDAO = new TeacherDAOImpl();
        schoolService = new SchoolService(classroomDao, departmentDAO, gradeDAO, scheduleDAO, studentDAO, subjectDAO, teacherDAO);
    }

    public void start() {
    int choice;
        do {
            System.out.println("#### School App ####");
            System.out.println("1. Créer un département");
            System.out.println("2. Créer un enseignant");
            System.out.println("3. Créer un étudiant");
            System.out.println("4. Créer une matière");
            System.out.println("5. Créer une note");
            System.out.println("6. Créer une classe");
            System.out.println("7. Créer un emploi du temps");


            System.out.println("8. Afficher la liste des classes(sans les élèves)");
            System.out.println("9. Afficher le nbr de matière d'un élève");
            System.out.println("10. Afficher la liste des notes d'un eleve (avec les détails)");
            System.out.println("11. Aficher la moyenne d'un eleve");
            System.out.println("12. Afficher le nbr d'eleve d'un département");
            System.out.println("13. Afficher tous les noms des eleves d'un niveau");

            System.out.println("14. Suppression d'un eleve, supprimera sa note mais pas sa classe");
            System.out.println("15. Suppression classe => supprime uniquement les éléves de cette classe");
            System.out.println("16. Suppression d'un departement => Supprime toutes les classes et tous les professeurs");

            System.out.println("0. Quitter l'application");

            System.out.println("Choix : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createDepartment();
                    break;
                case 2:
                    createTeacher();
                    break;
                case 3:
                    createStudent();
                    break;
                case 4:
                    createSubject();
                    break;
                case 5:
                    createGrade();
                    break;
                case 6:
                    createClassroom();
                    break;
                case 7:
                    createSchedule();
                    break;
                case 8:
                    showClasslist();
                    break;
                case 9:
                    showStudentNbOfSubject();
                    break;
                case 10:
                    showStudentListOfGrades();
                    break;
                case 11:
                    showStudentAvgGrade();
                    break;
                case 12:
                    showNbOfStudentByDep();
                    break;
                case 13:
                    showStudentsByLevel();
                    break;
                case 14:
                    deleteStudent();
                    break;
                case 15:
                    deleteClassRoom();
                    break;
                case 16:
                    deleteDepartment();
                    break;
                case 0:
                    System.out.println("Bye");
                    closeAll();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");

            }

        } while (choice != 0);
    }




    private void createDepartment(){
        System.out.println("Saisir le nom du département : ");
        String depName = scanner.nextLine();

        Department department = new Department(depName);
        schoolService.addDepartment(department);

        System.out.println("Département ajouté avec succés !");
    }

    private void createTeacher(){
        String lastName;
        String firstName;
        int age;

        do {
            System.out.println("Saisir le nom de l'enseignant : ");
            lastName = scanner.nextLine();

            System.out.println("Saisir le prénom de l'enseignant : ");
            firstName = scanner.nextLine();

            if (lastName.length() < 3 || firstName.length() < 3) {
                System.out.println("Le nom et le prénom de l'enseignant doivent contenir au moins 3 caractères.");
            }
        } while (lastName.length() < 3 || firstName.length() < 3);

        do {
            System.out.println("Saisir l'âge de l'enseignant : ");
            age = scanner.nextInt();
            scanner.nextLine();

            if (age <= 18) {
                System.out.println("L'âge de l'enseignant doit être supérieur à 18.");
            }
        } while (age <= 18);

        System.out.println("L'enseignant est-il un professeur principal ? (oui/non) : ");
        boolean isFormTeacher = "oui".equalsIgnoreCase(scanner.nextLine());

        System.out.println("L'enseignant est-il le chef de département ? (oui/non) : ");
        boolean isHeadTeacher = "oui".equalsIgnoreCase(scanner.nextLine());

        System.out.println("Saisir le grade de l'enseignant : ");
        String level = scanner.nextLine();

        System.out.println("Saisir l'id du département auquel appartient l'enseignant : ");
        int id = scanner.nextInt();

        Department department = schoolService.getDepartmentById(id);

        if (department != null) {
            Teacher teacher = new Teacher(lastName, firstName, age, level, isFormTeacher, isHeadTeacher, department);
            schoolService.addTeacher(teacher);
            System.out.println("Enseignant ajouté avec succès !");
        } else {
            System.out.println("Département non trouvé. Veuillez vérifier l'ID du département.");
        }
    }


    private void createStudent() {
        String lastName;
        String firstName;

        do {
            System.out.println("Saisir le nom de l'étudiant : ");
            lastName = scanner.nextLine();

            System.out.println("Saisir le prénom de l'étudiant : ");
            firstName = scanner.nextLine();

            if (lastName.length() < 3 || firstName.length() < 3) {
                System.out.println("Le nom et le prénom de l'étudiant doivent contenir au moins 3 caractères.");
            }
        } while (lastName.length() < 3 || firstName.length() < 3);

        System.out.println("Saisir la date de naissance de l'étudiant (format dd.MM.yyyy) : ");
        String birthDateStr = scanner.nextLine();

        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Format de date incorrect. Utilisez le format dd.MM.yyyy.");
            return;
        }

        String email;
        do {
            System.out.println("Saisir l'adresse mail de l'étudiant : ");
            email = scanner.nextLine();

            if (!email.matches("\\b[A-Za-z0-9._%+-]+@gmail\\.com\\b")) {
                System.out.println("L'adresse mail doit être au format example@gmail.com.");
            }
        } while (!email.matches("\\b[A-Za-z0-9._%+-]+@gmail\\.com\\b"));

        Student student = new Student(lastName, firstName, birthDate, email);
        schoolService.addStudent(student);
        System.out.println("Etudiant ajouté avec succés !");
    }



    private void createSubject() {
        System.out.println("Saisir le titre de la matière : ");
        String title = scanner.nextLine();

        System.out.println("Saisir la durée de la matière (en minutes) : ");
        int duration = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Saisir le coefficient de la matière : ");
        int coefficient = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Saisir la description de la matière : ");
        String description = scanner.nextLine();

        Subject subject = new Subject(title, duration, coefficient, description);

        schoolService.addSubject(subject);

        System.out.println("Matière ajoutée avec succès !");
    }



    private void createGrade() {
            BigDecimal userGrade;
            System.out.println("Combien de notes souhaitez-vous ajouter ?");
            int nbr = scanner.nextInt();
            scanner.nextLine();

            // Associer note à l'étudiant
            for (int i = 0; i < nbr; i++) {
                System.out.println("Quel est l'id de l'étudiant dont vous voulez mettre une note ?");
                int idStudent = scanner.nextInt();
                Student student = schoolService.getStudentById(idStudent);
                System.out.println("idStudent: " + idStudent);

                if (student != null) {
                    System.out.println("l'étudiant existe");
                }

                System.out.println("Quel est l'id de la matière dont vous souhaitez mettre une note ?");
                int idSubject = scanner.nextInt();
                Subject subject = schoolService.getSubjectById(idSubject);
                System.out.println("idSubject: " + idSubject);

                System.out.println("Veuillez saisir la note (sur 20)");
                userGrade = scanner.nextBigDecimal();
                scanner.nextLine();

                System.out.println("Veuillez saisir un commentaire ");
                String comment = scanner.nextLine();

                Grade grade = new Grade(userGrade, comment, student, subject);

                schoolService.addGrade(grade);
            }
    }


    private void createClassroom() {
        System.out.println("Quel est le nom de la classe ? ");
        String name = scanner.next();

        System.out.println("Quel est le niveau de la classe ?");
        String level = scanner.next();

        System.out.println("A quel département la classe est associée :");
        int departmentId = scanner.nextInt();
        Department department = schoolService.getDepartmentById(departmentId);

        List<Student> students = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();

        Classroom classroom = new Classroom(name, level, department, students, teachers);

        // Associer des étudiants à la classe
        System.out.println("Combien d'élèves souhaitez-vous ajouter à la classe ?");
        int numberOfStudents = scanner.nextInt();

        for (int i = 0; i < numberOfStudents; i++) {
            System.out.println("Saisir l'ID de l'élève à ajouter à la classe :");
            int studentId = scanner.nextInt();
            Student student = schoolService.getStudentById(studentId);

            if (student != null) {
                students.add(student);
                student.setClassroom(classroom);
                schoolService.updateStudent(student);
            } else {
                System.out.println("Étudiant non trouvé avec l'ID fourni.");
            }
        }

        // Associer des enseignants à la classe
        System.out.println("Combien d'enseignants souhaitez-vous ajouter à la classe ?");
        int numberOfTeachers = scanner.nextInt();

        for (int i = 0; i < numberOfTeachers; i++) {
            System.out.println("Saisir l'ID de l'enseignant à ajouter à la classe :");
            int teacherId = scanner.nextInt();
            Teacher teacher = schoolService.getTeacherById(teacherId);

            if (teacher != null) {
                teachers.add(teacher);
                teacher.addClassroom(classroom);
                schoolService.updateTeacher(teacher);
            } else {
                System.out.println("Enseignant non trouvé avec l'ID fourni.");
            }
        }

        schoolService.addClassroom(classroom);

        System.out.println("Classe ajoutée avec succès!");
    }



    


    private void createSchedule() {
    }



    private void showClasslist() {
        List<Classroom> classrooms = schoolService.getAllClassrooms();

        if (classrooms.isEmpty()) {
            System.out.println("Aucune classe trouvée.");
        } else {
            System.out.println("=== Liste des classes ===");
            for (Classroom classroom : classrooms) {
                System.out.println("Nom de la classe: " + classroom.getName() + ", Niveau : " + classroom.getLevel());
            }
        }
    }


    private void showStudentNbOfSubject() {
        System.out.println("Entrer l'id de l'étudiant:");
        int studentId = scanner.nextInt();

        Student student = schoolService.getStudentById(studentId);

        if (student != null) {
            System.out.println("Etudiant: " + student.getFirstName() + " " + student.getLastName());

            List<Grade> grades = student.getGrades();

            Set<Subject> uniqueSubjects = new HashSet<>();

            for (Grade grade : grades) {
                uniqueSubjects.add(grade.getSubject());
            }

            System.out.println("Nombre de matières: " + uniqueSubjects.size());
            System.out.println("-------------------------");
        } else {
            System.out.println("Etudiant avec l'id n°  " + studentId + " non trouvé.");
        }
    }





    private void showStudentListOfGrades() {
        System.out.println("Entrer l'id de l'étudiant:");
        int studentId = scanner.nextInt();

        Student student = schoolService.getStudentById(studentId);

        if (student != null) {
            System.out.println("Etudiant: " + student.getFirstName() + " " + student.getLastName());

            List<Grade> grades = student.getGrades();

            for (Grade grade : grades) {
                System.out.println("  Matière: " + grade.getSubject().getTitle());
                System.out.println("  Note: " + grade.getValue());
                System.out.println("  Commentaire: " + grade.getComment());
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("Etudiant avec l'id n°" + studentId + " non trouvé.");
        }
    }



    private void showStudentAvgGrade() {
        System.out.println("Entrez l'ID de l'étudiant :");
        int studentId = scanner.nextInt();

        Student student = schoolService.getStudentById(studentId);

        if (student != null) {
            System.out.println("Étudiant : " + student.getFirstName() + " " + student.getLastName());

            List<Grade> grades = student.getGrades();

            if (!grades.isEmpty()) {
                BigDecimal totalGrade = BigDecimal.ZERO;

                for (Grade grade : grades) {
                    totalGrade = totalGrade.add(grade.getValue());
                }

                BigDecimal avgGrade = totalGrade.divide(BigDecimal.valueOf(grades.size()), 2, RoundingMode.HALF_UP);
                System.out.println("Moyenne des notes : " + avgGrade);
            } else {
                System.out.println("Aucune note trouvée pour l'étudiant.");
            }
        } else {
            System.out.println("Étudiant non trouvé avec l'ID fourni.");
        }
    }



    private void showNbOfStudentByDep() {
        System.out.println("Entrez le nom du département :");
        String departmentName = scanner.nextLine();

        if (departmentName != null) {
            List<Student> studentsInDepartment = schoolService.getAllStudentsInDepartment(departmentName);
            int numberOfStudents = studentsInDepartment.size();

            System.out.println("Nombre d'étudiants dans le département " + departmentName + ": " + numberOfStudents);
        } else {
            System.out.println("Nom du département non fourni.");
        }
    }



    private void showStudentsByLevel() {

    }

    private void deleteStudent() {

    }

    private void deleteClassRoom() {
    }

    private void deleteDepartment() {

    }


    private void closeAll() {
        scanner.close();
        schoolService.close();
    }
}
