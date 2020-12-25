# Metro
Приложение для происка маршрутов между двумя заданными станциями метро, также для примера реализована basic авторизация и регистрация (без подтверждения через почту). 
Для примера взята часть метро Санкт-Петербурга. 

Для реализации использовались следущие технологии:
* Maven
* Spring framework (Data, Security, MVC), Thymeleaf
* H2 database, liquibase, Hibernate

При первом запуске необходимо зарегистрировать нового пользователя, после этого можно авторизоваться и перейти на страницу поиска. На странице поиска есть изображение, в котором
показаны станции метро, используемые в этом примере, и сколько минут занимает путь между ними. Для поиска необходимо задать начальную и конечную станции, при желании можно
установить лимит для количества результатов. Успешно выполненные поисковые запросы сохраняются, на странице поиска отображаются последние 10 запросов, отсортированные по дате, по убыванию, 
при желании можно использовать запрос из списка недавних запросов. Одинаковые запросы дублируются - если несколько раз подряд использовать одни и те же станции для запроса, то в
"недавних запросах" будет несколько одинаковых записей.

Примечания:
* Проект делал не для себя, поэтому в данном примере используются не все станции и исключена фиолетовая ветка метро, такие данные даны по условию. В дальнейшем будут добавлены остальные станции и заменено изображение.
* Контроллеры в пакете `com.example.metro.controller.rest` не используются фронтом и сделаны просто, чтобы показать вариант реализации с rest архитектурой. Будут удалены из этого проекта, когда появится другой проект в котором будет использоваться rest архитектура.
