select 'insert data';

-- ghjcnjehf11
INSERT INTO public.who_user (id,email,first_name,last_name,"password") VALUES
    (1,'admin@ura.ru','admin','ura','$2a$13$TbIuVWisWpq/QUwhnJYHL..WuwWHoo8w/n70mKXpE6.NOGCVMQqz6');
ALTER SEQUENCE user_seq RESTART WITH 2;

INSERT INTO public.who_role (id,role_name,who_user_id) VALUES
                                                           (1,'ROLE_USER',1),
                                                           (2,'ROLE_ADMIN',1);
ALTER SEQUENCE who_role_seq RESTART WITH 3;

INSERT INTO public.user_settings (id,rows_per_page,user_id) VALUES
    (1,3,1);
ALTER SEQUENCE user_settings_seq RESTART WITH 2;

insert into ip_records (id, ip, country, city)
    values (1,'37.131.200.107','Россия','Качканар'),
        (2,'176.126.15.243','Россия','Шадринск'),
        (3,'46.227.240.55','Польша','Познань'),
        (4,'109.163.217.54','Россия','Иркутск'),
        (5,'178.95.198.181','Украина','Черновцы'),
        (6,'178.68.74.13','Россия','Мурманск'),
        (7,'217.28.228.104','Россия','Москва'),
        (8,'5.101.51.174','Россия','Санкт-Петербург'),
        (9,'66.115.145.22','США','Торонто'),
        (10,'188.255.83.230','Россия','Москва'),
        (11,'188.66.32.82','Россия','Челябинск'),
        (12,'193.84.78.90','Не определен','Не определен'),
        (13,'51.250.66.167','Россия','Москва'),
        (14,'185.128.27.82','Италия','Милан'),
        (15,'85.203.21.118','Япония','Сингапур'),
        (16,'85.140.119.162','Россия','Екатеринбург');
ALTER SEQUENCE ip_record_seq RESTART WITH 17;

INSERT INTO ip_block_actions (id, start_time, end_time, is_active, block_period, user_id, record_id, is_synchronised)
    VALUES (1, '2022-10-28 16:21:00',null,true,'FOREVER',1,1, true),
           (2, '2022-10-28 16:22:00',null,true,'FOREVER',1,2, true),
           (3, '2022-10-28 16:23:00',null,true,'FOREVER',1,3, true),
           (4, '2022-10-28 16:24:00',null,true,'FOREVER',1,4, true),
           (5, '2022-10-28 16:25:00',null,true,'FOREVER',1,5, true),
           (6, '2022-10-28 16:26:00',null,true,'FOREVER',1,6, true),
           (7, '2022-10-28 16:27:00',null,true,'FOREVER',1,7, true),
           (8, '2022-10-28 16:28:00',null,true,'FOREVER',1,8, true),
           (9, '2022-10-28 16:29:00',null,true,'FOREVER',1,9, true),
           (10, '2022-10-28 16:30:00',null,true,'FOREVER',1,10, true),
           (11, '2022-10-28 16:31:00',null,true,'FOREVER',1,11, true),
           (12, '2022-10-28 16:32:00',null,true,'FOREVER',1,12, true),
           (13, '2022-10-28 16:33:00',null,true,'FOREVER',1,13, true),
           (14, '2022-10-28 16:34:00',null,true,'FOREVER',1,14, true),
           (15, '2022-10-28 16:35:00',null,true,'FOREVER',1,15, true),
           (16, '2022-10-28 16:36:00',null,true,'FOREVER',1,16, true);
ALTER SEQUENCE ip_block_actions_seq RESTART WITH 17;
