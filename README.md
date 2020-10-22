# small-reactive-web
<h3>v.1.0.0<h3>
<h4>Учебный проект по тестовому заданию.</h4>

<p>Реализация REST сервера на WebFlux с выводом интерфейса на Swagger.</p>
<p>Текст тестового задания см. файл <a href="/blob/master/Задание">Задание</a> в корне проекта.</p>
<p>Используется Springfox в качестве реализации Swagger. При запуске, доступ к UI - <a href="http://localhost:8080/swagger-ui/">http://localhost:8080/swagger-ui/</a>.</p>
<p>Реализация WebFlux выполнена на основе контроллера, так как текущая реализация Springfox плохо работает с RouterFunction.</p>
<p>Выполнены тесты для WebFlux.</p>