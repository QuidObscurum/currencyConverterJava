--drop table if exists banksells;
create table banksells (
    id bigint not null auto_increment,
    fromCurrency varchar(3) not null,
    rate decimal(19,4),
    toCurrency varchar(3) not null,
    primary key (id)
);