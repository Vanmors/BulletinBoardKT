## Был создан простейший движок форума/доски объявлений (только backend).
Суть задачи:
Есть топики (темы), в каждом топике может быть одно или более сообщений.  
Движок должен обеспечивать хранение в БД (IMDB) и CRUD операции с топиками (темами) и сообщениями в топиках.  
Топик должен содержать заголовок (название темы). Топик не может быть пустым, т.е. должен содержать как минимум одно сообщение.  
Сообщение должно содержать имя (ник) автора, текст сообщения, дату создания.  
Сообщение обязательно должно относиться к одному из топиков.  
Необходимо реализовать клиентский REST-API:

Было реализовано:
1) 2 основных контроллера [topic](src/main/kotlin/com/example/demo/controller/TopicController.kt) и [message](src/main/kotlin/com/example/demo/controller/MessageController.kt)
2) Также было добавлена аутентификация пользователей с ролевой моделью, которая включает админа, который дополнительно
   может редактировать и удалять любые сообщения и топики.
   [security config](src/main/kotlin/com/example/demo/config/SecurityConfig.kt)  
   Для создания пользоватея были написаны [UserController](src/main/kotlin/com/example/demo/controller/UserController.kt) и
   [RoleController](src/main/kotlin/com/example/demo/controller/RoleController.kt)
3) В качестве базы данных используется Postgresql