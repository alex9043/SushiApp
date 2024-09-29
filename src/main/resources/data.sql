-- Продукты
insert into product (price, name)
values (580, 'Samurai Ролл'); -- id = 1
insert into product (price, name)
values (620, 'Sakura Delight'); -- id = 2
insert into product (price, name)
values (510, 'Tokyo Sunset'); -- id = 3
insert into product (price, name)
values (690, 'Dragon Fire'); -- id = 4
insert into product (price, name)
values (750, 'Ocean Breeze'); -- id = 5
insert into product (price, name)
values (450, 'Avocado Dream'); -- id = 6
insert into product (price, name)
values (820, 'Royal Dragon'); -- id = 7
insert into product (price, name)
values (390, 'Veggie Delight'); -- id = 8
insert into product (price, name)
values (760, 'Unagi Supreme'); -- id = 9
insert into product (price, name)
values (520, 'Spicy Volcano');
-- id = 10

-- Отзывы
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (5, '2024-05-18', 1, 'Свежий и нежный ролл с насыщенным вкусом. Отличный выбор!', 'Алексей'); -- Samurai Ролл
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (4, '2021-05-19', 1, 'Вкусный лосось! Тобико не очень', 'Александр'); -- Samurai Ролл
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (4, '2020-11-22', 2, 'Очень интересное сочетание креветок и авокадо, но немного остро.',
        'Ольга'); -- Sakura Delight
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (5, '2019-07-15', 3, 'Вкусно и свежо! Особенно понравился угорь.', 'Дмитрий'); -- Tokyo Sunset
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (5, '2022-03-09', 4, 'Острота в меру, люблю такие роллы!', 'Анна'); -- Dragon Fire
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (4, '2024-02-05', 5, 'Вкусный и свежий ролл, но хотелось бы больше креветок.', 'Екатерина'); -- Ocean Breeze
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (5, '2022-07-12', 6, 'Нежный и освежающий ролл! Легкий вкус авокадо просто прекрасен.',
        'Марина'); -- Avocado Dream
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (4, '2021-04-21', 7, 'Королевский вкус! Единственное, слишком много тобико.', 'Евгений'); -- Royal Dragon
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (5, '2023-06-09', 8, 'Отличный вариант для тех, кто любит овощные роллы. Очень свежо!',
        'Ирина'); -- Veggie Delight
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (1, '2024-07-09', 8, 'Нет рыбы', 'Александр'); -- Veggie Delight
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (5, '2020-10-17', 9, 'Угорь просто тает во рту! Один из лучших роллов, которые пробовал.',
        'Антон'); -- Unagi Supreme
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (4, '2022-12-02', 10, 'Острый, но сбалансированный вкус. Для любителей острого — самое то!',
        'Наталья'); -- Spicy Volcano
insert into product_review (rating, created_date, product_id, review_text, reviewer_name)
values (5, '2022-10-02', 10, 'ОСТРО!', 'Александр');
-- Spicy Volcano

-- Ингредиенты
insert into ingredient (name)
values ('Рис'); -- id = 1
insert into ingredient (name)
values ('Нори'); -- id = 2
insert into ingredient (name)
values ('Угорь'); -- id = 3
insert into ingredient (name)
values ('Лосось'); -- id = 4
insert into ingredient (name)
values ('Тобико'); -- id = 5
insert into ingredient (name)
values ('Авокадо'); -- id = 6
insert into ingredient (name)
values ('Огурец'); -- id = 7
insert into ingredient (name)
values ('Сливочный сыр'); -- id = 8
insert into ingredient (name)
values ('Креветка'); -- id = 9
insert into ingredient (name)
values ('Спайси соус'); -- id = 10
insert into ingredient (name)
values ('Унаги соус'); -- id = 11
insert into ingredient (name)
values ('Крабовые палочки'); -- id = 12
insert into ingredient (name)
values ('Тунец');
-- id = 13

-- Теги
insert into tag (name)
values ('Новинка'); -- id = 1
insert into tag (name)
values ('Острое'); -- id = 2
insert into tag (name)
values ('Вегетарианский');
-- id = 3

-- Категории
insert into category (name)
values ('Новинки'); -- id = 1
insert into category (name)
values ('Фирменные роллы'); -- id = 2
insert into category (name)
values ('Классические роллы'); -- id = 3
insert into category (name)
values ('Премиум роллы'); -- id = 4
insert into category (name)
values ('Вегетарианские роллы'); -- id = 5
insert into category (name)
values ('Роллы с угрем');
-- id = 6

-- Ингредиенты для каждого продукта
-- Samurai Ролл
insert into product_ingredients (product_id, ingredients_id)
values (1, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (1, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (1, 4); -- Лосось
insert into product_ingredients (product_id, ingredients_id)
values (1, 5); -- Тобико
insert into product_ingredients (product_id, ingredients_id)
values (1, 8);
-- Сливочный сыр

-- Sakura Delight
insert into product_ingredients (product_id, ingredients_id)
values (2, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (2, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (2, 6); -- Авокадо
insert into product_ingredients (product_id, ingredients_id)
values (2, 9); -- Креветка
insert into product_ingredients (product_id, ingredients_id)
values (2, 10);
-- Спайси соус

-- Tokyo Sunset
insert into product_ingredients (product_id, ingredients_id)
values (3, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (3, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (3, 3); -- Угорь
insert into product_ingredients (product_id, ingredients_id)
values (3, 4); -- Лосось
insert into product_ingredients (product_id, ingredients_id)
values (3, 6);
-- Авокадо

-- Dragon Fire
insert into product_ingredients (product_id, ingredients_id)
values (4, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (4, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (4, 5); -- Тобико
insert into product_ingredients (product_id, ingredients_id)
values (4, 10); -- Спайси соус
insert into product_ingredients (product_id, ingredients_id)
values (4, 13);
-- Тунец

-- Ocean Breeze
insert into product_ingredients (product_id, ingredients_id)
values (5, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (5, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (5, 6); -- Авокадо
insert into product_ingredients (product_id, ingredients_id)
values (5, 9); -- Креветка
insert into product_ingredients (product_id, ingredients_id)
values (5, 11);
-- Унаги соус

-- Avocado Dream
insert into product_ingredients (product_id, ingredients_id)
values (6, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (6, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (6, 6); -- Авокадо
insert into product_ingredients (product_id, ingredients_id)
values (6, 8);
-- Сливочный сыр

-- Royal Dragon
insert into product_ingredients (product_id, ingredients_id)
values (7, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (7, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (7, 4); -- Лосось
insert into product_ingredients (product_id, ingredients_id)
values (7, 5); -- Тобико
insert into product_ingredients (product_id, ingredients_id)
values (7, 9); -- Креветка
insert into product_ingredients (product_id, ingredients_id)
values (7, 10);
-- Спайси соус

-- Veggie Delight
insert into product_ingredients (product_id, ingredients_id)
values (8, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (8, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (8, 6); -- Авокадо
insert into product_ingredients (product_id, ingredients_id)
values (8, 7);
-- Огурец

-- Unagi Supreme
insert into product_ingredients (product_id, ingredients_id)
values (9, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (9, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (9, 3); -- Угорь
insert into product_ingredients (product_id, ingredients_id)
values (9, 5); -- Тобико
insert into product_ingredients (product_id, ingredients_id)
values (9, 11);
-- Унаги соус

-- Spicy Volcano
insert into product_ingredients (product_id, ingredients_id)
values (10, 1); -- Рис
insert into product_ingredients (product_id, ingredients_id)
values (10, 2); -- Нори
insert into product_ingredients (product_id, ingredients_id)
values (10, 4); -- Лосось
insert into product_ingredients (product_id, ingredients_id)
values (10, 5); -- Тобико
insert into product_ingredients (product_id, ingredients_id)
values (10, 10);
-- Спайси соус

-- Теги для продуктов
-- Samurai Ролл
insert into product_tags (product_id, tags_id)
values (1, 1);
-- Новинка
-- Sakura Delight
insert into product_tags (product_id, tags_id)
values (2, 2);
-- Острое
-- Dragon Fire
insert into product_tags (product_id, tags_id)
values (4, 2);
-- Острое
-- Ocean Breeze
insert into product_tags (product_id, tags_id)
values (5, 1);
-- Новинка
-- Royal Dragon
insert into product_tags (product_id, tags_id)
values (7, 2);
-- Острое
-- Veggie Delight
insert into product_tags (product_id, tags_id)
values (8, 3);
-- Вегетарианский
-- Spicy Volcano
insert into product_tags (product_id, tags_id)
values (10, 2);
-- Острое

-- Категории для продуктов
-- Новинки
insert into product_categories (categories_id, product_id)
values (1, 5); -- Ocean Breeze
insert into product_categories (categories_id, product_id)
values (1, 1);
-- Samurai Ролл
-- Фирменные роллы
insert into product_categories (categories_id, product_id)
values (2, 1); -- Samurai Ролл
insert into product_categories (categories_id, product_id)
values (2, 2); -- Sakura Delight
insert into product_categories (categories_id, product_id)
values (2, 3); -- Tokyo Sunset
insert into product_categories (categories_id, product_id)
values (2, 4); -- Dragon Fire
insert into product_categories (categories_id, product_id)
values (2, 7); -- Royal Dragon
insert into product_categories (categories_id, product_id)
values (2, 10);
-- Spicy Volcano
-- Классические роллы
insert into product_categories (categories_id, product_id)
values (3, 6); -- Avocado Dream
insert into product_categories (categories_id, product_id)
values (3, 8);
-- Veggie Delight
--Премиум роллы
insert into product_categories (categories_id, product_id)
values (4, 9); -- Unagi Supreme
insert into product_categories (categories_id, product_id)
values (4, 7); -- Royal Dragon
insert into product_categories (categories_id, product_id)
values (4, 5);
-- Ocean Breeze
-- Вегетарианские роллы
insert into product_categories (categories_id, product_id)
values (5, 8);
-- Veggie Delight
-- Роллы с угрем
insert into product_categories (categories_id, product_id)
values (6, 3); -- Tokyo Sunset
insert into product_categories (categories_id, product_id)
values (6, 9); -- Unagi Supreme


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

insert into users (name, phone, email, password, roles)
values ('ADMIN', '+88888888888', 'admin@test.test', '$2a$10$apqBTejdMEqqe/lSAVfSsOT7ViGsuZTlEdAYnu1.Tw7AoPvZ1UrQi',
        array ['ROLE_USER', 'ROLE_ADMIN']);

