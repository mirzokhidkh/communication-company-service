insert into role (role_name)
values ('ROLE_DIRECTOR'),
       ('ROLE_BRANCH_DIRECTOR'),
       ('ROLE_BRANCH_MANAGER'),
       ('ROLE_NUMBERS_MANAGER'),
       ('ROLE_PERSONNEL_MANAGER'),
       ('ROLE_STAFF'),
       ('ROLE_CLIENT'),
       ('ROLE_STAFF'),
       ('ROLE_PHYSICAL_PERSON'),
       ('ROLE_LEGAL_PERSON'),
       ('ROLE_TARIFF_MANAGER');

insert into payment_type(payment_type_name)
values ('CLICK'),
       ('PAYME'),
       ('CASH');

insert into client_move_type(name)
values ('PURCHASED_SIMCARD'),
       ('PURCHASED_TARIFF'),
       ('PURCHASED_PACKAGE'),
       ('PURCHASED_EXTRA_SERVICE'),
       ('CHANGED_TARIFF'),
       ('SENT_MESSAGE'),
       ('CALLED'),
       ('USED_THE_INTERNET'),
       ('USED_FROM_PACKAGE'),
       ('USED_FROM_EXTRA_SERVICE');

insert into purchase_type(purchase_type_name)
values ('DAILY'),
       ('MONTHLY');

insert into service_type(service_type_name)
values ('MB'),
       ('SMS'),
       ('MINUTE'),
       ('VOICE_MESSAGE');

insert into region(name)
values ('Tashkent'),
       ('Jizzakh'),
       ('Sirdaryo'),
       ('Samarqand'),
       ('Surxondaryo'),
       ('Qashqadaryo'),
       ('Andijan'),
       ('Bukhara'),
       ('Fergana'),
       ('Namangan'),
       ('Navoiy'),
       ('Karakalpakstan'),
       ('Xorazm');