create table customer(
  `id` bigint(20) not null auto_increment,
  `name` varchar(255) default null,
  `contact` varchar(255) default null,
  `telephone` varchar(255) default null,
  `email` varchar(255) default null,
  `remark` text,
  primary key (`id`)
) engine = innodb default charset utf8mb4;
insert into customer values(1, 'customer1','jack','18612345678','jack@gmail.com',null);
insert into customer values(2, 'customer2','rose','13813245678','rose@gmail.com',null);