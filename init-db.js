db = db.getSiblingDB("cv_website");

db.createCollection("profiles");
db.profiles.insert({
    fullName: "Aome Itinose",
    aboutMe: "Я начинающий программист, специализирующийся на Java и фронтенд-разработке. Мой опыт включает создание веб-приложений с использованием Spring Boot, Hibernate, React и Docker. Участвую в проектах по разработке инструментов управления задачами и данных сотрудников. Люблю программирование, изучаю новые технологии, тестирую приложения с JUnit. Активно развиваюсь в IT и готов к коммерческим проектам.",
    photoPath:"AomeItinose/2024_12_07_22_26_07",
    timestamp: new Date()
})