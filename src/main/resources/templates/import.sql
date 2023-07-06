--Photo
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 1', 'Descrizione della foto 1', 'https://picsum.photos/500', true);
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 2', 'Descrizione della foto 2', 'https://picsum.photos/500', true);
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 3', 'Descrizione della foto 3', 'https://picsum.photos/500', true);
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 4', 'Descrizione della foto 4', 'https://picsum.photos/500', true);
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 5', 'Descrizione della foto 5', 'https://picsum.photos/500', true);
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 6', 'Descrizione della foto 6', 'https://picsum.photos/500', true);
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 7', 'Descrizione della foto 7', 'https://picsum.photos/500', true);
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 8', 'Descrizione della foto 8', 'https://picsum.photos/500', true);
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 9', 'Descrizione della foto 9', 'https://picsum.photos/500', true);
INSERT INTO photos (titolo, descrizione, url, visibile) VALUES ('Foto 10', 'Descrizione della foto 10', 'https://picsum.photos/500', true);

--Categorie
INSERT INTO categories (name, description) VALUES ('Natura', 'Foto di paesaggi naturali');
INSERT INTO categories (name, description) VALUES ('Animali', 'Foto di animali selvatici');
INSERT INTO categories (name, description) VALUES ('Architettura', 'Foto di edifici e strutture architettoniche');
INSERT INTO categories (name, description) VALUES ('Ritratti', 'Foto di volti e persone');

-- Associazione delle foto alle categorie
INSERT INTO photo_category (photo_id, category_id) VALUES (1, 1);
INSERT INTO photo_category (photo_id, category_id) VALUES (2, 2);
INSERT INTO photo_category (photo_id, category_id) VALUES (3, 3);
INSERT INTO photo_category (photo_id, category_id) VALUES (4, 1);
INSERT INTO photo_category (photo_id, category_id) VALUES (4, 4);
INSERT INTO photo_category (photo_id, category_id) VALUES (5, 1);
INSERT INTO photo_category (photo_id, category_id) VALUES (6, 2);
INSERT INTO photo_category (photo_id, category_id) VALUES (7, 3);
INSERT INTO photo_category (photo_id, category_id) VALUES (8, 4);
INSERT INTO photo_category (photo_id, category_id) VALUES (9, 1);
INSERT INTO photo_category (photo_id, category_id) VALUES (10, 2);
