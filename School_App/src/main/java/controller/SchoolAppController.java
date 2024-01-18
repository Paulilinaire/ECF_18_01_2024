package controller;

import impl.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import service.SchoolService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SchoolAppController {
    private final Scanner scanner = new Scanner(System.in);

    private static SchoolService schoolService;
    private static ClassroomDAOImpl classroomDao;
    private static DepartmentDAOImpl departmentDAO;
    private static GradeDAOimpl gradeDAO;
    private static ScheduleDAOImpl scheduleDAO;
    private static StudentDAOImpl studentDAO;
    private static SubjectDAOImpl subjectDAO;
    private static TeacherDAOImpl teacherDAO;


    public SchoolAppController() {
        classroomDao = new ClassroomDAOImpl();
        departmentDAO = new DepartmentDAOImpl();
        gradeDAO = new GradeDAOimpl();
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
            System.out.println("9. Afficher le nombre de matière d'un élève");
            System.out.println("10. Afficher la liste des notes d'un eleve (avec les détails)");
            System.out.println("11. Aficher la moyenne d'un eleve");
            System.out.println("12. Afficher le nombre d'eleve d'un département");
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
        long id = scanner.nextLong();

        Department department = schoolService.getDepartmentById(id);

        Teacher teacher = new Teacher(lastName, firstName, age, level, isFormTeacher, isHeadTeacher, department);

        schoolService.addTeacher(teacher);

        System.out.println("Enseignant ajouté avec succès !");
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

        System.out.println("Saisir la date de naissance de l'étudiant : ");
        String birthDateStr = scanner.nextLine();

        LocalDate birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

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

        System.out.println("Etudiant ajouté avec succès !");
    }


    private void createSubject() {
        System.out.println("Saisir le titre du matière : ");
        String title = scanner.nextLine();

        System.out.println("Saisir la durée du matière (en minutes) : ");
        int duration = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Saisir le coefficient du matière : ");
        int coefficient = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Saisir la description du matière : ");
        String description = scanner.nextLine();

        Subject subject = new Subject(title, duration, coefficient, description);

        schoolService.addSubject(subject);

        System.out.println("Matière ajoutée avec succès !");
    }


    private void createGrade() {
        System.out.println("Saisir la note sur 20: ");
        int value = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Saisir le commentaire de la note : ");
        String comment = scanner.nextLine();

        System.out.println("Saisir l'ID de l'étudiant pour lequel vous ajoutez la note : ");
        Long studentId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Saisir l'ID de la matière pour lequel vous ajoutez la note : ");
        Long subjectId = scanner.nextLong();
        scanner.nextLine();


        Student student = schoolService.getStudentById(studentId);
        Subject subject = schoolService.getSubjectById(subjectId);

        Grade grade = new Grade(value, comment, student, subject);

        schoolService.addGrade(grade);

        System.out.println("Note ajoutée avec succès !");
    }


    private void createClassroom() {
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
                System.out.println("reference : " + classroom.getName() + ", " + "marque : " + classroom.getLevel());
            }
        }
    }

    private void showStudentNbOfSubject() {

    }

    private void showStudentListOfGrades() {

    }

    private void showStudentAvgGrade() {

    }

    private void showNbOfStudentByDep() {

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
