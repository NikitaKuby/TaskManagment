1) Скачать и открыть репозиторий в IDE
2) собираем проект: Maven -> TaskManagementSystem -> Lifecycle -> package
3) Открываем терминал в Ide(alt+f12) или запускаем его из корневой папки проекта и вводим команду 
docker-compose up
4) После запуска проекта и успешной контенеризации можем открыть localhost:8080/swagger-ui/index.html#/ в браузере
5) Для тестовой выдачи прав администратора рекомендуется осуществлять через Postman (сохраните JWT нужного пользователя)
GET запрос по localhost:8080/tasks/getAdmin (вставьте токен по Authorization -> authType -> Bearer token)
