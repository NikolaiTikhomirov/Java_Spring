public class Homework11 {

  /**
   Проблематика: имеется несколько микросервисов (проектов) на spring-boot: reader-service, book-service, issue-service, ...
   Хочется, чтобы в каждом из этих проектов работал аспект-таймер, замеряющий время выполнения метода бина, помеченного аннотацией @Timer (см. дз к уроку 8)

   Решение: создать стартер, который будет инкапсулировать в себе аспект и его автоматический импорт в подключающий проект.
   То есть:
   1. Пишем стартер, в котором задекларирован аспект и его работа
   2. Подключаем стартер в reader-service, book-service, issue-service, ...

   Шаги реализации:
   1. Создаем новый модуль в микросервисном проекте - это и будет наш стартер
   2. Берем код с ДЗ-8 (класс аспекта и аннотации) и переносим в стартер
   3. В стартере декларируем Configuration и внутри нее декларируем бин - аспект
   4. В проекте стартера в resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports прописываем полный путь конфигурации
   5. Подключаем зависимость стартера (pom-dependency) в микросервисы
   6. Проверяем, что аспект работает


   * Доп. задание (со звездочкой): придумать точки расширения\конфигурирования аспекта:
   1. Включить\выключить по флажку в конфиге (ConditionalOnProperty)
   2. ...

   https://www.youtube.com/watch?v=yy43NOreJG4
   */

}
