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
    email    varchar(255),
    login    varchar(255),
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

insert into statuses(id, name)
values (1, 'valid'),
       (2, 'blocked');

insert into roles(id, name)
values (1, 'admin'),
       (2, 'customer'),
       (3, 'owner');

insert into users(id, email, login, password, role_id)
values (1, null, 'admin', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS', 1),
       (2, 'customer@mail.ru', 'customer1', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS', 2),
       (3, 'owner@mail.ru', 'owner1', '$2a$10$qjHmq9fz8E9NhsZPhw2.D.jS2meQKM4BwKeX9Q4aLW0lghmtY51mS', 3);

insert into billing_accounts(id, balance, cvv, number)
values (1, '1000', '666', '123456'),
       (2, '1000', '666', '654321');

insert into owners(id, name, billing_account_id, user_id)
values (1, 'Steve Jobs', 1, 3);

insert into customers(address, name, billing_account_id, status_id, user_id)
values ('Belarus, Minsk', 'Petya', 1, 1, 2);

insert into categories(id, name)
values (1, 'music'),
       (2, 'games'),
       (3, 'sport'),
       (4, 'media'),
       (5, 'other');

insert into subscriptions(description, image_url, name, price, category_id, owner_id)
values ('Apple Music description', 'https://i.pinimg.com/originals/67/f6/cb/67f6cb14f862297e3c145014cdd6b635.jpg', 'Apple Music', 9.99, 1,
        1),
       ('Apple TV description', 'https://play-lh.googleusercontent.com/zovfDsfyegE7SF3hCrN_hWPiQ2VLSh_Hreg20YsgQD5d9rfeq_HLA1fdq3q9zn-QNg',
        'Apple TV', 6.99, 4, 1),
       ('Apple Arcade description', 'https://i1.wp.com/www.appgefahren.de/wp-content/uploads/2019/09/Apple-Arcade-Icon.jpg', 'Apple Arcade',
        5.99, 2, 1),
       ('Apple News description', 'https://seeklogo.com/images/A/apple-news-logo-AE5FC95C6B-seeklogo.com.png', 'Apple News', 3.99, 4, 1),
       ('iCloud description', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7aRXIsaVuG8EMhoeMM0LMUsBUlcmEEcDKhA&usqp=CAU',
        'iCloud', 2.99, 1, 1),
       ('Apple Fitness+ description',
        'https://www.gannett-cdn.com/presto/2020/12/14/USAT/7f3e26ed-032d-4ac9-9c4e-ff9b9951f1e9-8x_iOS14-Activity-Icon_US-EN_00-0051-092_v1_w.png',
        'Apple Fitness+', 3.99, 3, 1);