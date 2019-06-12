DROP TABLE IF EXISTS public.products;
DROP SEQUENCE IF EXISTS public.products_seq;

CREATE TABLE products (
    id serial NOT NULL,
    name varchar(100),
    description varchar(250),
    defaultPrice float,
    defaultCurrency varchar(250),
    category_id integer,
    supplier_id integer
);

DROP TABLE IF EXISTS public.suppliers;
DROP SEQUENCE IF EXISTS public.suppliers_seq;
CREATE TABLE suppliers (
    id serial NOT NULL,
    name varchar(100),
    description varchar(250)
);

DROP TABLE IF EXISTS public.categories;
DROP SEQUENCE IF EXISTS public.categories_seq;
CREATE TABLE categories (
    id serial NOT NULL,
    name varchar(100),
    department varchar(50),
    description varchar(250)
);

ALTER TABLE ONLY products
    ADD CONSTRAINT pk_products_id PRIMARY KEY (id);

ALTER TABLE ONLY suppliers
    ADD CONSTRAINT pk_suppliers_id PRIMARY KEY (id);

ALTER TABLE ONLY categories
    ADD CONSTRAINT pk_categories_id PRIMARY KEY (id);

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_suppliers_id FOREIGN KEY (supplier_id) REFERENCES suppliers(id);

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_categories_id FOREIGN KEY (category_id) REFERENCES categories(id);
