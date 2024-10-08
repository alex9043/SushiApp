insert into product (price, name)
values (560, 'Fish Ролл');
insert into product (price, name)
values (560, 'Green Ролл');
insert into product (price, name)
values (690, 'Kabayaki ролл');
insert into product (price, name)
values (440, 'LOVE Ролл');
insert into product (price, name)
values (570, 'TUNA ролл');

insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2019-10-06 00:00:00.000000 +03:00', 1,
        'Для тех, кто на ПП) очень удачное сочетание ингредиентов, нам оч понравилось! Будем брать еще!',
        'Ирина');
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2019-05-08 00:00:00.000000 +03:00', 1, 'ОООООЧЕНЬ вкусный! будем брать', 'Вера');
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2018-06-28 00:00:00.000000 +03:00', 1, 'Очень вкусный и нежный. Будет одним из моих фаворитов)', 'Ирина');
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2018-03-17 00:00:00.000000 +03:00', 1,
        'Вкусный и сытный ролл для тех, кому не нужны лишние калории. Обязательно закажу ещё!', 'Юлия');
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (4, '2017-12-23 00:00:00.000000 +03:00', 1,
        'Заказывали недавно, свеженький. Авокадо спелое, была бы "картошка", было бы не так вкусно. Чего-то не хватает. Может быть, салата.',
        'Наталья');
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2017-12-09 00:00:00.000000 +03:00', 1, 'Очень вкусный ролл, нам понравился,необычный, насыщенный вкус.',
        'Екатерина');

insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2018-03-23 00:00:00.000000 +03:00', 2, 'Свежий ролл за счёт огурца, интересный вкус', 'Наталья');
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2018-01-21 00:00:00.000000 +03:00', 2,
        'Ролл очень вкусный. Хотя нет, очень много огурца, который перебивает все. Отзывы не удаляйте пожалуйста!',
        'Постоянный покупатель');

insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2020-09-27 00:00:00.000000 +03:00', 3,
        'Оригинальное сочетание, большие порции, сытные, вкусные. Будем брать ещё.', 'Анастасия');
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2020-08-23 00:00:00.000000 +03:00', 3,
        'Очень вкусно!!! Обожаю роллы с угрем и авакадо.А с креветкой вообще класс)', 'Кира');

insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2019-03-03 00:00:00.000000 +03:00', 4, 'Космос, очень понравились!:)', 'Наталия');
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
VALUES (5, '2019-12-18 00:00:00.000000 +03:00', 4, 'Самые вкусные роллы из всех! Всем советую!', 'Кира');

insert into ingredient (name)
values ('Рис');
insert into ingredient (name)
values ('Нори');
insert into ingredient (name)
values ('Угорь');
insert into ingredient (name)
values ('Лосось');
insert into ingredient (name)
values ('Тобико');
insert into ingredient (name)
values ('Авокадо');
insert into ingredient (name)
values ('Огурец');
insert into ingredient (name)
values ('Сливочный сыр');
insert into ingredient (name)
values ('Креветка');
insert into ingredient (name)
values ('Спайси соус');
insert into ingredient (name)
values ('Унаги соус');
insert into ingredient (name)
values ('Крабовые палочки');
insert into ingredient (name)
values ('Тунец');

insert into tag (name)
values ('Новинка');
insert into tag (name)
values ('Острое');

insert into category (name)
values ('Новинки');
insert into category (name)
values ('Фирменные роллы');

insert into product_ingredients (product_id, ingredients_id)
values (1, 1);
insert into product_ingredients (product_id, ingredients_id)
values (1, 2);
insert into product_ingredients (product_id, ingredients_id)
values (1, 3);
insert into product_ingredients (product_id, ingredients_id)
values (1, 4);
insert into product_ingredients (product_id, ingredients_id)
values (1, 5);
insert into product_ingredients (product_id, ingredients_id)
values (1, 6);

insert into product_ingredients (product_id, ingredients_id)
values (2, 1);
insert into product_ingredients (product_id, ingredients_id)
values (2, 2);
insert into product_ingredients (product_id, ingredients_id)
values (2, 7);
insert into product_ingredients (product_id, ingredients_id)
values (2, 8);
insert into product_ingredients (product_id, ingredients_id)
values (2, 4);
insert into product_ingredients (product_id, ingredients_id)
values (2, 3);

insert into product_ingredients (product_id, ingredients_id)
values (3, 1);
insert into product_ingredients (product_id, ingredients_id)
values (3, 2);
insert into product_ingredients (product_id, ingredients_id)
values (3, 6);
insert into product_ingredients (product_id, ingredients_id)
values (3, 9);
insert into product_ingredients (product_id, ingredients_id)
values (3, 3);
insert into product_ingredients (product_id, ingredients_id)
values (3, 10);
insert into product_ingredients (product_id, ingredients_id)
values (3, 11);

insert into product_ingredients (product_id, ingredients_id)
values (4, 1);
insert into product_ingredients (product_id, ingredients_id)
values (4, 2);
insert into product_ingredients (product_id, ingredients_id)
values (4, 8);
insert into product_ingredients (product_id, ingredients_id)
values (4, 12);
insert into product_ingredients (product_id, ingredients_id)
values (4, 4);
insert into product_ingredients (product_id, ingredients_id)
values (4, 6);
insert into product_ingredients (product_id, ingredients_id)
values (4, 5);

insert into product_ingredients (product_id, ingredients_id)
values (5, 1);
insert into product_ingredients (product_id, ingredients_id)
values (5, 2);
insert into product_ingredients (product_id, ingredients_id)
values (5, 13);
insert into product_ingredients (product_id, ingredients_id)
values (5, 6);
insert into product_ingredients (product_id, ingredients_id)
values (5, 10);

insert into product_tags (product_id, tags_id)
values (3, 2);
insert into product_tags (product_id, tags_id)
values (5, 1);
insert into product_tags (product_id, tags_id)
values (5, 2);

insert into product_categories (categories_id, product_id)
values (2, 1);
insert into product_categories (categories_id, product_id)
values (2, 2);
insert into product_categories (categories_id, product_id)
values (2, 3);
insert into product_categories (categories_id, product_id)
values (2, 4);

insert into product_categories (categories_id, product_id)
values (1, 5);
insert into product_categories (categories_id, product_id)
values (2, 5);


insert into district (name)
values ('Адмиралтейский район');
insert into district (name)
values ('Василеостровской район');
insert into district (name)
values ('Выборгский район');
insert into district (name)
values ('Калининский район');
insert into district (name)
values ('Кировский район');
insert into district (name)
values ('Колпинский район');
insert into district (name)
values ('Красногвардейский район');
insert into district (name)
values ('Красносельский район');
