# SearchEng

Многофункциональное приложение написанное для владельца интернет магазинов, магазины находятся на портале Prom.ua.

1. "Поиск заказа"

Название окна говорит само за себя, в нем по ключевому слову можно найти заказ на одном из интернет магазинов пользователя (Сами магазины он сам добавляет в настройках, поиск  происходит на всех магазинах одновременно).
Поиск происходит по совпадению с ключеввого слова с  именем фамилией/id заказа/номером телефона покупателя/датой заказа. Как видно по скрину, полное совпадение ключевого слова не обязательно. 

![image](https://user-images.githubusercontent.com/71173909/127339185-9324c7c6-dcfd-4f67-81ec-dd0c6fa192d6.png)

2. "Поиск товара"

Задача практически та же, что и в окне поиска заказов, только на этот раз нужно найти товар на одном из магазинов пользователя (Поиск  происходит на всех магазинах одновременно).
Сам товар можно найти по названию или уникальному идентификатору (ID). Как видно по скрину, полное совпадение ключевого слова не обязательно.

![image](https://user-images.githubusercontent.com/71173909/127341208-00b58a7f-08c1-4a68-b5c9-fdbec663446a.png)

3. "Статистика"

Окно для просмотра статистики по магазинам, в нем есть два режима:

1. Просмотр статистика заказов
2. Просмотр статистики заработка

Сам режим переключается с помощью кнопок слева от графика.
При загрузке, по умолчанию на графике будет нарисована статистика заказов каждого магазина пользователя.
Если, к примеру, ему не удобно просматривать статистику всех магазинов одновременно, то он может нажать на нужную ему кнопку под графиком и просмотреть ститистику нужного ему магазина
(Название кнопки соответствует названию магазина, количество кнопок динамическое, зависит
от количества зарегистрированных магазинов пользователем в настройках, и будет нарисован график в зависимости от режима, если к примеру автивен режим статистики заказов, соответственно он и будет нарисован).
 Так же, в левом нижнем углу окна, присутствует календарь, в котором пользователь может выбрать нужню ему дату, и в зависимости от режима просмотреть количетсво заказов или прибыли за этот день.
 
 ![scr1](https://user-images.githubusercontent.com/71173909/127346823-22b54ac4-c5d7-498d-b08a-9ddf3e36be00.png)
 ![scr2](https://user-images.githubusercontent.com/71173909/127346917-5bc155b9-faa8-49c6-8538-a67240363cc6.png)
 
 3. "Отправь mail"
 
 Суть проста - отправить письмо с просьбой об отзыве покупателям.
 
  
  Письмо будет отправлено всем покупателям, которые что либо заказали в выбранную дату.
  Сам текст письма корректируется индивидуально, вставляется имя покупателя, название товара и ссылка на сайт с отзывами магазина, на котором они что-то заказали.
  Пример
  
  ![image](https://user-images.githubusercontent.com/71173909/127349795-4833fea2-3d44-4e56-bb01-16f2cc10c813.png)
  
  Как видно по скрину, есть возможность выбирать, покупателям какого магазина отправлять письмо.
  
 ![image](https://user-images.githubusercontent.com/71173909/127350067-d9f7f1d2-f70a-4ef1-8ea2-1a05a95d8642.png)
 
4. "Конвертер"

Окно предназначено для конвертации содержимого нужных сайтов из XML в Excel формат (Сайты так же пользователь редактирует сам в определенном файле) 

![image](https://user-images.githubusercontent.com/71173909/127350964-26411396-a15a-4010-8d5f-6703dc45260c.png)

5. "Настройки"

Окно добавлени/удаления магазинов.
Названия полей говорят сами за себя, особенность в том, что все нужно добавлять по порядку, если, к примеру, первый магазин в поле для названий магазинов "Livante",
то в остальных полях, так же должны быть первыми ссылки на ресурсы магазина "Livante".

![image](https://user-images.githubusercontent.com/71173909/127352211-e160df25-a224-4fee-a578-d2d49f8a0571.png)