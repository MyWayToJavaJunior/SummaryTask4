# SummaryTask4
final project in EPAM

Description
Environment: Tomcat 7, JSTL, Derby DB (or any other RDBMS), Log4j.

Разработать WEB-приложение, которое поддерживает заданную функциональность.
Требования к реализации следующие:

1. На основе сущностей предметной области создать классы которые им соответствуют.

2. Классы и методы должны иметь названия, которые отражают их функциональность, и должны быть грамотно разнесены по пакетам.

3. Оформление кода должно соответствовать Java Code Convention.

4. Информацию о предметной области хранить в базе данных (в качестве СУБД рекомендуется использовать Apache Derby).

5. Для доступа к данным использовать API JDBC с использованием пула соединений (не допускается использование ORM фреймверков).

6. Приложение должно поддерживать работу с кириллицей (быть многоязычным), в том числе при хранении информации в базе данных:

- должна быть возможность переключения языка интерфейса;
- должна быть поддержка ввода, вывода и хранения информации (в базе данных), записанной на разных языках (см. ниже);
- в качестве поддерживаемых языков выбрать минимум два: один на основе кириллицы, другой на основе латиницы.

7. Архитектура приложения должна соответствовать шаблону MVC (не допускается использование MVC фреймверков).

8. При реализации алгоритмов бизнес-логики использовать шаблоны.

9. Используя сервлеты и JSP, реализовать функциональность, приведенную в постановке задачи.

10. В качестве контейнера сервлетов использовать Apache Tomcat.

11. На страницах JSP применять теги из библиотеки JSTL и разработанные собственные теги (минимум один custom tag library тег и минимум один tag file тег).

12. При разработке использовать сессии, фильтры, слушатели.

13. Использовать журналирование событий с использованием библиотеки Apache Log4j.

14. Код должен содержать комментарии документатора (все классы верхнего уровня, нетривиальные методы и конструкторы).

15. Написать модульные тесты которые по максимуму покрывают функциональность.

Самостоятельное расширение постановки задачи по функциональности приветствуется.

Дополнительно, к требованиям изложенным выше, более чем желательно обеспечить выполнение следующих требований.

1. Реализовать разграничение прав доступа пользователей системы к компонентам приложения.
2. Реализовать защиту от повторной отправки данных на сервер при обновлении страницы.
3. Все поля ввода должны быть с валидацией данных.

Theme: Приемная комиссия

Система имеет перечень Факультетов, для которого необходимо реализовать возможность сортировки:

- по имени(a-z, z-a);
- количеству бюджетных мест,
- общему количеству мест.

Абитуриент регистрируется в системе. Во время регистрации необходимо ввести ФИО, email, город, область, название учебного заведения (опционально: прикрепить скан аттестата с оценками).

Абитуриент может зарегистрироваться на один или несколько факультетов. При регистрации на факультет студент вводит результаты по соответствующим предметам, а так же оценки из аттестата.

Администратор системы владеет правами:

- добавления, удаления или редактирования факультета;
- блокирования или разблокирования абитуриента;
- добавления результатов абитуриентов в Ведомость;

После создания ведомости система подсчитывает сумму баллов и определяет абитуриентов, зачисленных в учебное заведение.

Необходимо добавить оповещение о результате зачисления на email абитуриента.
