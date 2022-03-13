drop table if exists active_subscriptions;
drop table if exists basket_items;
drop table if exists customers;
drop table if exists statuses;
drop table if exists subscriptions;
drop table if exists categories;
drop table if exists owners;
drop table if exists billing_accounts;
drop table if exists users;
drop table if exists roles;

create table billing_accounts
(
    id      bigserial not null primary key,
    balance integer,
    cvv     integer,
    number  varchar(255)
);

create table categories
(
    id   bigserial not null primary key,
    name varchar(255)
);

create table roles
(
    id   bigserial not null primary key,
    name varchar(255)
);

create table statuses
(
    id   bigserial not null primary key,
    name varchar(255)
);

create table users
(
    id       bigserial not null primary key,
    email    varchar(255) unique,
    login    varchar(255) unique,
    password varchar(255),
    role_id  bigint    not null references roles
);

create table customers
(
    id                 bigserial not null primary key,
    address            varchar(255),
    name               varchar(255),
    billing_account_id bigint references billing_accounts,
    status_id          bigint references statuses,
    user_id            bigint    not null references users
);

create table owners
(
    id                 bigserial not null primary key,
    name               varchar(255),
    billing_account_id bigint references billing_accounts,
    user_id            bigint    not null references users
);

create table subscriptions
(
    id          bigserial not null primary key,
    description varchar(255),
    image_url   varchar(255),
    name        varchar(255),
    price       integer   not null,
    category_id bigint references categories,
    owner_id    bigint    not null references owners
);

create table active_subscriptions
(
    id              bigserial not null primary key,
    customer_id     bigint,
    last_edit_date  bigint,
    quantity        integer   not null,
    subscription_id bigint    not null references subscriptions
);

create table basket_items
(
    id              bigserial not null primary key,
    customer_id     bigint,
    quantity        integer   not null,
    subscription_id bigint references subscriptions
);

insert into statuses(name)
values ('valid'),
       ('blocked');

insert into roles(name)
values ('admin'),
       ('customer'),
       ('owner');

insert into users(email, login, password, role_id)
values (null, 'admin', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS', (select id from roles where name like 'admin')),
       ('customer@mail.ru', 'customer1', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer01@mail.ru', 'customer01', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer02@mail.ru', 'customer02', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer03@mail.ru', 'customer03', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer04@mail.ru', 'customer04', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer05@mail.ru', 'customer05', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer06@mail.ru', 'customer06', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer07@mail.ru', 'customer07', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer08@mail.ru', 'customer08', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer09@mail.ru', 'customer09', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer10@mail.ru', 'customer10', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer11@mail.ru', 'customer11', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('customer12@mail.ru', 'customer12', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'customer')),
       ('owner@mail.ru', 'owner1', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner01@mail.ru', 'owner01', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner02@mail.ru', 'owner02', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner03@mail.ru', 'owner03', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner04@mail.ru', 'owner04', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner05@mail.ru', 'owner05', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner06@mail.ru', 'owner06', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner07@mail.ru', 'owner07', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner08@mail.ru', 'owner08', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner09@mail.ru', 'owner09', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner10@mail.ru', 'owner10', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner11@mail.ru', 'owner11', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner12@mail.ru', 'owner12', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner13@mail.ru', 'owner13', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner14@mail.ru', 'owner14', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner')),
       ('owner15@mail.ru', 'owner15', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS',
        (select id from roles where name like 'owner'));

insert into billing_accounts(balance, cvv, number)
values ('1000', '666', '123456'),
       ('1000', '666', '654321'),
       ('1000', '777', '222222');

insert into owners(name, billing_account_id, user_id)
values ('Steve Jobs', (select id from billing_accounts where number like '123456'), (select id from users where login like 'owner1')),
       ('Owner01', (select id from billing_accounts where number like '222222'), (select id from users where login like 'owner01')),
       ('Owner02', null, (select id from users where login like 'owner02')),
       ('Owner03', null, (select id from users where login like 'owner03')),
       ('Owner04', null, (select id from users where login like 'owner04')),
       ('Owner05', null, (select id from users where login like 'owner05')),
       ('Owner06', null, (select id from users where login like 'owner06')),
       ('Owner07', null, (select id from users where login like 'owner07')),
       ('Owner08', null, (select id from users where login like 'owner08')),
       ('Owner09', null, (select id from users where login like 'owner09')),
       ('Owner10', null, (select id from users where login like 'owner10')),
       ('Owner11', null, (select id from users where login like 'owner11')),
       ('Owner12', null, (select id from users where login like 'owner12')),
       ('Owner13', null, (select id from users where login like 'owner13')),
       ('Owner14', null, (select id from users where login like 'owner14')),
       ('Owner15', null, (select id from users where login like 'owner15'));

insert into customers(address, name, billing_account_id, status_id, user_id)
values ('Belarus, Minsk', 'Petya', (select id from billing_accounts where number like '654321'), 1,
        (select id from users where login like 'customer1')),
       ('Belarus, Minsk', 'Customer01', null, 1, (select id from users where login like 'customer01')),
       ('Belarus, Minsk', 'Customer02', null, 1, (select id from users where login like 'customer02')),
       ('Belarus, Minsk', 'Customer03', null, 1, (select id from users where login like 'customer03')),
       ('Belarus, Minsk', 'Customer04', null, 1, (select id from users where login like 'customer04')),
       ('Belarus, Minsk', 'Customer05', null, 1, (select id from users where login like 'customer05')),
       ('Belarus, Minsk', 'Customer06', null, 1, (select id from users where login like 'customer06')),
       ('Belarus, Minsk', 'Customer07', null, 1, (select id from users where login like 'customer07')),
       ('Belarus, Minsk', 'Customer08', null, 1, (select id from users where login like 'customer08')),
       ('Belarus, Minsk', 'Customer09', null, 1, (select id from users where login like 'customer09')),
       ('Belarus, Minsk', 'Customer10', null, 1, (select id from users where login like 'customer10')),
       ('Belarus, Minsk', 'Customer11', null, 1, (select id from users where login like 'customer11')),
       ('Belarus, Minsk', 'Customer12', null, 1, (select id from users where login like 'customer12'));

insert into categories(name)
values ('music'),
       ('games'),
       ('sport'),
       ('media'),
       ('other');

insert into subscriptions(description, image_url, name, price, category_id, owner_id)
values ('Apple Music description', 'https://i.pinimg.com/originals/67/f6/cb/67f6cb14f862297e3c145014cdd6b635.jpg', 'Apple Music', 9.99,
        (select id from categories where categories.name like 'music'),
        (select id from owners where name like 'Steve Jobs')),
       ('Apple TV description', 'https://play-lh.googleusercontent.com/zovfDsfyegE7SF3hCrN_hWPiQ2VLSh_Hreg20YsgQD5d9rfeq_HLA1fdq3q9zn-QNg',
        'Apple TV', 6.99, (select id from categories where categories.name like 'media'),
        (select id from owners where name like 'Steve Jobs')),
       ('Apple Arcade description', 'https://i1.wp.com/www.appgefahren.de/wp-content/uploads/2019/09/Apple-Arcade-Icon.jpg', 'Apple Arcade',
        5.99, (select id from categories where categories.name like 'games'), (select id from owners where name like 'Steve Jobs')),
       ('Apple News description', 'https://seeklogo.com/images/A/apple-news-logo-AE5FC95C6B-seeklogo.com.png', 'Apple News', 3.99, 4,
        (select id from owners where name like 'Steve Jobs')),
       ('iCloud description', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7aRXIsaVuG8EMhoeMM0LMUsBUlcmEEcDKhA&usqp=CAU',
        'iCloud', 2.99, (select id from categories where categories.name like 'music'),
        (select id from owners where name like 'Steve Jobs')),
       ('Apple Fitness+ description',
        'https://www.gannett-cdn.com/presto/2020/12/14/USAT/7f3e26ed-032d-4ac9-9c4e-ff9b9951f1e9-8x_iOS14-Activity-Icon_US-EN_00-0051-092_v1_w.png',
        'Apple Fitness+', 3.99, (select id from categories where categories.name like 'sport'),
        (select id from owners where name like 'Steve Jobs')),

       ('Subscription 1',
        'https://media.istockphoto.com/vectors/pay-per-click-vector-id1182361258?k=20&m=1182361258&s=612x612&w=0&h=pLfZupXKAayGiTV3Odb_Z59oU-3ioJ8tZVnATDBFacg=',
        'Subscription 1', 9.99, (select id from categories where categories.name like 'other'),
        (select id from owners where name like 'Owner01')),
       ('Subscription 2',
        'https://media.istockphoto.com/vectors/pay-per-click-vector-id1182361258?k=20&m=1182361258&s=612x612&w=0&h=pLfZupXKAayGiTV3Odb_Z59oU-3ioJ8tZVnATDBFacg=',
        'Subscription 2', 9.99, (select id from categories where categories.name like 'other'),
        (select id from owners where name like 'Owner01')),
       ('Subscription 3',
        'https://media.istockphoto.com/vectors/pay-per-click-vector-id1182361258?k=20&m=1182361258&s=612x612&w=0&h=pLfZupXKAayGiTV3Odb_Z59oU-3ioJ8tZVnATDBFacg=',
        'Subscription 3', 9.99, (select id from categories where categories.name like 'other'),
        (select id from owners where name like 'Owner01')),
       ('Subscription 4',
        'https://media.istockphoto.com/vectors/pay-per-click-vector-id1182361258?k=20&m=1182361258&s=612x612&w=0&h=pLfZupXKAayGiTV3Odb_Z59oU-3ioJ8tZVnATDBFacg=',
        'Subscription 4', 9.99, (select id from categories where categories.name like 'other'),
        (select id from owners where name like 'Owner01')),
       ('Subscription 5',
        'https://media.istockphoto.com/vectors/pay-per-click-vector-id1182361258?k=20&m=1182361258&s=612x612&w=0&h=pLfZupXKAayGiTV3Odb_Z59oU-3ioJ8tZVnATDBFacg=',
        'Subscription 5', 9.99, (select id from categories where categories.name like 'other'),
        (select id from owners where name like 'Owner01')),
       ('Subscription 6',
        'https://media.istockphoto.com/vectors/pay-per-click-vector-id1182361258?k=20&m=1182361258&s=612x612&w=0&h=pLfZupXKAayGiTV3Odb_Z59oU-3ioJ8tZVnATDBFacg=',
        'Subscription 6', 9.99, (select id from categories where categories.name like 'other'),
        (select id from owners where name like 'Owner01')),
       ('Subscription 7',
        'https://media.istockphoto.com/vectors/pay-per-click-vector-id1182361258?k=20&m=1182361258&s=612x612&w=0&h=pLfZupXKAayGiTV3Odb_Z59oU-3ioJ8tZVnATDBFacg=',
        'Subscription 7', 9.99, (select id from categories where categories.name like 'other'),
        (select id from owners where name like 'Owner01')),
       ('Subscription 8',
        'https://media.istockphoto.com/vectors/pay-per-click-vector-id1182361258?k=20&m=1182361258&s=612x612&w=0&h=pLfZupXKAayGiTV3Odb_Z59oU-3ioJ8tZVnATDBFacg=',
        'Subscription 8', 9.99, (select id from categories where categories.name like 'other'),
        (select id from owners where name like 'Owner01'));