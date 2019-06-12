INSERT INTO "public"."categories" ("id", "name", "department", "description") VALUES (DEFAULT, 'Hero', 'Animation', 'Lorem Ipsum');
INSERT INTO "public"."categories" ("id", "name", "department", "description") VALUES (DEFAULT, 'Villain', 'Animation', 'Steve Jobs Company');
INSERT INTO "public"."categories" ("id", "name", "department", "description") VALUES (DEFAULT, 'Princess', 'Animation', 'Little guy on the edge of the moon');

INSERT INTO "public"."suppliers" ("id", "name", "description") VALUES (DEFAULT, 'DreamWorks', 'Hollywood');
INSERT INTO "public"."suppliers" ("id", "name", "description") VALUES (DEFAULT, 'Pixar', 'Steve Jobs');
INSERT INTO "public"."suppliers" ("id", "name", "description") VALUES (DEFAULT, 'Disney', 'Mind washers');

INSERT INTO "public"."products" ("id", "name", "description", "defaultprice", "defaultcurrency", "category_id", "supplier_id") VALUES (DEFAULT, 'Ariel', 'fish bitch', 3278, 'USD', 1, 1);
INSERT INTO "public"."products" ("id", "name", "description", "defaultprice", "defaultcurrency", "category_id", "supplier_id") VALUES (DEFAULT, 'Snow White', 'apple bitch', 78688, 'USD', 2, 2);
INSERT INTO "public"."products" ("id", "name", "description", "defaultprice", "defaultcurrency", "category_id", "supplier_id") VALUES (DEFAULT, 'Random Bitch', 'bitch', 786, 'USD', 3, 3);